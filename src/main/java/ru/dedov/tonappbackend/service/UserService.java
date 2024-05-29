package ru.dedov.tonappbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.dedov.tonappbackend.dto.ErrorDto;
import ru.dedov.tonappbackend.dto.SuccessDto;
import ru.dedov.tonappbackend.dto.TokenDeductionRequestDto;
import ru.dedov.tonappbackend.model.entity.User;
import ru.dedov.tonappbackend.model.repository.UserRepository;

import java.util.Optional;

/**
 * Сервисный класс для операций с сущностью Пользователь
 *
 * @author Alexander Dedov
 * @since 23.05.2024
 */
@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Сохранить пользователя.
	 * Если пользователь с таким username уже существует - не сохраняем
	 */
	public ResponseEntity<?> saveUser(User user) {
		String accountId = user.getAccountId();
		if (userRepository.existsByAccountId(accountId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				new ErrorDto("user with accountId = " + accountId + " already exist")
			);
		}
		userRepository.save(user);
		return ResponseEntity.ok(
			new SuccessDto(
				true,
				"user with accountId = " + accountId + " saved successfully"
			)
		);
	}

	/**
	 * Найти пользователя по id.
	 * Если пользователя с таким id не существует - 404
	 */
	public ResponseEntity<User> findUserByAccountId(String accountId) {
		Optional<User> optionalUser = userRepository.findByAccountId(accountId);
		return optionalUser
			.map(ResponseEntity::ok)
			.orElse(ResponseEntity.notFound().build());
	}

	public User increaseUserBalanceByAccountId(String accountId, Double amount) {
		Optional<User> optionalUser = userRepository.findByAccountId(accountId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setAccountBalance(user.getAccountBalance() + (amount / 1e9));
			userRepository.save(user);
			return user;
		}
		return new User();
	}

	/**
	 * Обновляем пользователя по id
	 */
	public ResponseEntity<?> updateUser(User user) {
		String accountId = user.getAccountId();
		if (userRepository.existsByAccountId(accountId)) {
			userRepository.save(user);
			return ResponseEntity.ok(
				new SuccessDto(
					true,
					"user with id = " + accountId + " updated successfully"
				)
			);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
			new ErrorDto("user with id = " + accountId + " not exist"
			)
		);
	}

	/**
	 * Списать у пользователя с id == userId с баланса deductionAmount токенов
	 *
	 * @param deductionRequestDto accountId + deductionAmount
	 */
	public ResponseEntity<?> deductTokenFromUser(TokenDeductionRequestDto deductionRequestDto) {
		Optional<User> optionalUser = userRepository.findByAccountId(deductionRequestDto.getAccountId());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.getAccountBalance().compareTo(deductionRequestDto.getDeductionAmount()) >= 0) {
				user.setAccountBalance(user.getAccountBalance() - deductionRequestDto.getDeductionAmount());
				userRepository.save(user);
				return ResponseEntity.ok(
					new SuccessDto(
						true,
						"token(s) deducted, new balance = " + user.getAccountBalance())
				);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				new ErrorDto("deduction amount = " + deductionRequestDto.getDeductionAmount() +
					" is greater than user account balance = " + user.getAccountBalance())
			);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
			new ErrorDto("user with accountId = " + deductionRequestDto.getAccountId() + " not found")
		);
	}
}
