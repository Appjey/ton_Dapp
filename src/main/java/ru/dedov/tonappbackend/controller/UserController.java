package ru.dedov.tonappbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dedov.tonappbackend.dto.TokenDeductionRequestDto;
import ru.dedov.tonappbackend.model.entity.User;
import ru.dedov.tonappbackend.service.UserService;

/**
 * Контроллер для обработки операций связанных с пользователями
 *
 * @author Alexander Dedov
 * @since 23.05.2024
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/deduct_token")
	public ResponseEntity<?> deductToken(@RequestBody TokenDeductionRequestDto tokenDeductionRequestDto) {
		return userService.deductTokenFromUser(tokenDeductionRequestDto);
	}

	@PostMapping("/user")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		return userService.saveUser(user);
	}

	@PutMapping("/user")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@GetMapping("/user/{accountId}")
	public ResponseEntity<User> findUser(@PathVariable String accountId) {
		return userService.findUserByAccountId(accountId);
	}

}
