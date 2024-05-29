package ru.dedov.tonappbackend.dto.tonapi;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO со списком ивентов
 *
 * @author Alexander Dedov
 * @since 26.05.2024
 */
@Data
@NoArgsConstructor
public class EventsContainer {

	private List<Event> events;
}
