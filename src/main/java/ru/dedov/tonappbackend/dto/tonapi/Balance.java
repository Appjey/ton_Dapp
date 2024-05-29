package ru.dedov.tonappbackend.dto.tonapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO баланса
 *
 * @author Alexander Dedov
 * @since 25.05.2024
 */
@Data
@NoArgsConstructor
public class Balance {

	private String balance;
	private Price price;
	@JsonProperty("wallet_address")
	private WalletAddress walletAddress;
	private Jetton jetton;
	private Lock lock;
}
