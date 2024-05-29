package ru.dedov.tonappbackend.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для Feign Client
 *
 * @author Alexander Dedov
 * @since 25.05.2024
 */
@Configuration
public class TonApiClientConfig {

	@Value("${ton-api.auth-header-secret}")
	private String authHeaderSecret;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + authHeaderSecret);
	}
}
