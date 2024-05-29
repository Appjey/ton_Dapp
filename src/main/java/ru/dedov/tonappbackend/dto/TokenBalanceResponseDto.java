package ru.dedov.tonappbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для вывода баланса по конкретному токену
 *
 * @author Alexander Dedov
 * @since 25.05.2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenBalanceResponseDto {

	@JsonProperty("token_balance")
	private String tokenBalance;
	@JsonProperty("token_symbol")
	private String tokenSymbol;
}
