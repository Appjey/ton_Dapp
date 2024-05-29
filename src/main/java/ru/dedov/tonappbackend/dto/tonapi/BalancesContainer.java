package ru.dedov.tonappbackend.dto.tonapi;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO со списком балансов
 *
 * @author Alexander Dedov
 * @since 25.05.2024
 */
@Data
@NoArgsConstructor
public class BalancesContainer {

	private List<Balance> balances;
}
