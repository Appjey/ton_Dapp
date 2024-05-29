package ru.dedov.tonappbackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Сущность Пользователь
 *
 * @author Alexander Dedov
 * @since 23.05.2024
 */
@Data
@Entity
@Table(name = "users")
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@Column(name = "account_id")
	private String accountId;

	@Column(name = "ton_balance")
	private Double tonBalance;

	@Column(name = "account_balance")
	private Double accountBalance;

}
