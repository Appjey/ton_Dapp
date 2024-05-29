package ru.dedov.tonappbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dedov.tonappbackend.dto.LLaMaRequestDto;
import ru.dedov.tonappbackend.service.LLaMaService;

/**
 * Контроллер для обработки запросов к модели LLaMa
 *
 * @author Alexander Dedov
 * @since 28.05.2024
 */
@RestController
@RequestMapping("api/v1")
public class AnswerGenerationController {

	private final LLaMaService lLaMaService;

	@Autowired
	public AnswerGenerationController(LLaMaService lLaMaService) {
		this.lLaMaService = lLaMaService;
	}

	@GetMapping("/generate_answer_on_text")
	public ResponseEntity<?> generateAnswer(@RequestBody LLaMaRequestDto lLaMaRequestDto) {
		return lLaMaService.generateAnswerByLLaMa(lLaMaRequestDto);
	}
}
