package ru.dedov.tonappbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.dedov.tonappbackend.api.TonNetworkApiClient;
import ru.dedov.tonappbackend.dto.*;
import ru.dedov.tonappbackend.dto.tonapi.*;
import ru.dedov.tonappbackend.model.entity.User;

import java.util.Optional;

/**
 * Сервис для работы с объектами сети TON
 *
 * @author Alexander Dedov
 * @since 25.05.2024
 */
@Service
public class TonService {

	private final int MAX_RETRY = 12;

	private final TonNetworkApiClient tonNetworkApiClient;
	private final UserService userService;

	@Autowired
	public TonService(TonNetworkApiClient tonNetworkApiClient, UserService userService) {
		this.tonNetworkApiClient = tonNetworkApiClient;
		this.userService = userService;
	}

	/**
	 * Метод возвращает информацию о балансе (количеству токенов) по symbol токена
	 * @param balanceRequestDto accountId + tokenSymbol
	 */
	public ResponseEntity<?> getTokenBalance(TokenBalanceRequestDto balanceRequestDto) {
		BalancesContainer balancesContainer = tonNetworkApiClient.getBalancesById(balanceRequestDto.getAccountId());
		Optional<Balance> optionalTokenBalance = balancesContainer.getBalances().stream()
			.filter(balance -> balance.getJetton().getSymbol().equals(balanceRequestDto.getTokenSymbol()))
			.findFirst();
		if (optionalTokenBalance.isPresent()) {
			Balance tokenBalance = optionalTokenBalance.get();
			return ResponseEntity.ok(
				new TokenBalanceResponseDto(tokenBalance.getBalance(), tokenBalance.getJetton().getSymbol())
			);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
			new ErrorDto("token " + balanceRequestDto.getTokenSymbol() + " not found")
		);
	}

	public ResponseEntity<?> processUserBalanceAfterSuccessfulTransaction(UpdateUserBalanceRequestDto updateDto)
		throws InterruptedException
	{
		Event firstTakenEvent = getNewestEvent(updateDto);
		for (int i = 0; i < MAX_RETRY; i++) {
			Event expectedNewestEvent = getNewestEvent(updateDto);
			if (!firstTakenEvent.equals(expectedNewestEvent)) {
				Optional<Action> optionalAction = expectedNewestEvent.getActions().stream()
					.filter(filteringAction ->
						filteringAction.getType().equals("JettonTransfer") && filteringAction.getStatus().equals("ok"))
					.findFirst();
				if (optionalAction.isPresent()) {
					Action action = optionalAction.get();
					JettonTransfer jettonTransfer = action.getJettonTransfer();
					Double transactionAmount = Double.valueOf(jettonTransfer.getAmount());
					User user =userService.increaseUserBalanceByAccountId(updateDto.getAccountId(), transactionAmount);
					return ResponseEntity.ok(
						new SuccessDto(true, "balance updated, new balance = " + user.getAccountBalance())
					);
				}
			} else {
				Thread.sleep(5000);
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
			new ErrorDto("failed to fetch new transactions after " + MAX_RETRY + " attempts")
		);
	}

	public Event getNewestEvent(UpdateUserBalanceRequestDto updateDto) {
		EventsContainer eventsContainer = tonNetworkApiClient.getEventsByParameters(
			updateDto.getAccountId(),
			updateDto.getJettonId(),
			1L,
			updateDto.getStartDate(),
			updateDto.getEndDate()
		);
		return eventsContainer.getEvents().get(0);
	}
}
