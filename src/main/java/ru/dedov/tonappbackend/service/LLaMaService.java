package ru.dedov.tonappbackend.service;

import de.kherud.llama.InferenceParameters;
import de.kherud.llama.LlamaModel;
import de.kherud.llama.LlamaOutput;
import de.kherud.llama.ModelParameters;
import de.kherud.llama.args.MiroStat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.dedov.tonappbackend.dto.LLaMaRequestDto;
import ru.dedov.tonappbackend.dto.SuccessDto;

/**
 * Сервис для работы с моделью LLaMa
 *
 * @author Alexander Dedov
 * @since 28.05.2024
 */
@Service
public class LLaMaService {

	public ResponseEntity<?> generateAnswerByLLaMa(LLaMaRequestDto lLaMaRequestDto) {
		ModelParameters modelParameters = new ModelParameters()
			.setModelFilePath("C:\\models\\mistral-7b-instruct-v0.2.Q4_K_M.gguf")
			.setNGpuLayers(43);
		StringBuilder result = new StringBuilder();
		try (LlamaModel model = new LlamaModel(modelParameters)) {
			InferenceParameters inferenceParameters = new InferenceParameters(composePrompt(lLaMaRequestDto))
				.setTemperature(0.7f)
				.setPenalizeNl(true)
				.setMiroStat(MiroStat.V2);
			for (LlamaOutput output : model.generate(inferenceParameters)) {
				result.append(output);
			}
		}
		return ResponseEntity.ok(
			new SuccessDto(true, result.toString())
		);
	}

	private String composePrompt(LLaMaRequestDto requestDto) {
		return String.format(
			"""
				You are an assistant who was given the following text and asked a question based on it.\s
				Use the text to answer the question as accurately as possible.\s
				Text: %s\s
				Question: %s\s
				Answer:""",
			requestDto.getText(),
			requestDto.getQuestion()
		);
	}
}
