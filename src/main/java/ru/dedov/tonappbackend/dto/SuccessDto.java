package ru.dedov.tonappbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для вывода информации об успешной операции
 *
 * @author Alexander Dedov
 * @since 25.05.2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessDto {

	private boolean success;
	private String message;
}
