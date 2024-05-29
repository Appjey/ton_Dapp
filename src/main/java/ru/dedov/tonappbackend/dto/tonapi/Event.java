package ru.dedov.tonappbackend.dto.tonapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Event DTO
 *
 * @author Alexander Dedov
 * @since 26.05.2024
 */
@Data
@NoArgsConstructor
public class Event {

	@JsonProperty("event_id")
	private String eventId;
	private List<Action> actions;
}
