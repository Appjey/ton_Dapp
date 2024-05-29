package ru.dedov.tonappbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для запроса баланса по конкретному токену и id аккаунта
 *
 * @author Alexander Dedov
 * @since 25.05.2024
 */
@Data
@NoArgsConstructor
public class TokenBalanceRequestDto {

	@JsonProperty("account_id")
	private String accountId;
	@JsonProperty("token_symbol")
	private String tokenSymbol;
}
