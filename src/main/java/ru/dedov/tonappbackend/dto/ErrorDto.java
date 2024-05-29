package ru.dedov.tonappbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для вывода текста ошибки
 *
 * @author Alexander Dedov
 * @since 25.05.2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

	@JsonProperty("error_message")
	private String errorMessage;
}
