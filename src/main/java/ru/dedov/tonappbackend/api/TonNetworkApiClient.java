package ru.dedov.tonappbackend.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dedov.tonappbackend.config.TonApiClientConfig;
import ru.dedov.tonappbackend.dto.tonapi.BalancesContainer;
import ru.dedov.tonappbackend.dto.tonapi.EventsContainer;

/**
 * Feign Client для обращения к TON Api
 *
 * @author Alexander Dedov
 * @since 24.05.2024
 */
@FeignClient(name = "tonApiClient", url = "${ton-api.base-url.net}", configuration = TonApiClientConfig.class)
public interface TonNetworkApiClient {

	@GetMapping("/accounts/{account_id}/jettons")
	BalancesContainer getBalancesById(@PathVariable("account_id") String accountId,
									  @RequestParam("currencies") String currencies);

	@GetMapping("/accounts/{account_id}/jettons")
	BalancesContainer getBalancesById(@PathVariable("account_id") String accountId);

	@GetMapping("/accounts/{account_id}/jettons/{jetton_id}/history?limit={limit}&start_date={start_date}&end_date={end_date}")
	EventsContainer getEventsByParameters(@PathVariable("account_id") String accountId,
										  @PathVariable("jetton_id") String jettonId,
										  @PathVariable("limit") Long limit,
										  @PathVariable("start_date") Long startDate,
										  @PathVariable("end_date") Long endDate);
}
