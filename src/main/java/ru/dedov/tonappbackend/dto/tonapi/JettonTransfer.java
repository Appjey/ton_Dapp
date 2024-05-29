package ru.dedov.tonappbackend.dto.tonapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Jetton transfer action DTO
 *
 * @author Alexander Dedov
 * @since 26.05.2024
 */
@Data
@NoArgsConstructor
public class JettonTransfer {

	private WalletAddress sender;
	private WalletAddress recipient;
	@JsonProperty("senders_wallet")
	private String sendersWallet;
	@JsonProperty("recipients_wallet")
	private String recipientsWallet;
	private String amount;
	private String comment;
}
