package ru.dedov.tonappbackend.dto.tonapi;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Jetton DTO
 *
 * @author Alexander Dedov
 * @since 25.05.2024
 */
@Data
@NoArgsConstructor
public class Jetton {

	private String address;
	private String name;
	private String symbol;
	private int decimals;
	private String image;
	private String verification;
}
