package ru.dedov.tonappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TonAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TonAppBackendApplication.class, args);
	}

}
