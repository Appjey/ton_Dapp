package ru.dedov.tonappbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO Class Description
 *
 * @author Alexander Dedov
 * @since 28.05.2024
 */
@Data
@NoArgsConstructor
public class LLaMaRequestDto {

	private String text;
	private String question;
}
