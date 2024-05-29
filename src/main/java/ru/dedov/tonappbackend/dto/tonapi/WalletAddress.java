package ru.dedov.tonappbackend.dto.tonapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WalletAddress DTO
 *
 * @author Alexander Dedov
 * @since 25.05.2024
 */
@Data
@NoArgsConstructor
public class WalletAddress {

	private String address;
	private String name;
	@JsonProperty("is_scam")
	private boolean isScam;
	private String icon;
	@JsonProperty("is_wallet")
	private boolean isWallet;
}
