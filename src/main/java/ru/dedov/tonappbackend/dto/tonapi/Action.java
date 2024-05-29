package ru.dedov.tonappbackend.dto.tonapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Action DTO
 *
 * @author Alexander Dedov
 * @since 26.05.2024
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Action {

	private String type;
	private String status;
	@JsonProperty("JettonTransfer")
	private JettonTransfer jettonTransfer;
}
