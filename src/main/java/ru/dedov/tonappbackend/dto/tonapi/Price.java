package ru.dedov.tonappbackend.dto.tonapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 *
 *
 * @author Alexander Dedov
 * @since 25.05.2024
 */
@Data
@NoArgsConstructor
public class Price {

	private Map<String, Double> prices;
	@JsonProperty("diff_24h")
	private Map<String, String> diff24h;
	@JsonProperty("diff_7d")
	private Map<String, String> diff7d;
	@JsonProperty("diff_30d")
	private Map<String, String> diff30d;
}
