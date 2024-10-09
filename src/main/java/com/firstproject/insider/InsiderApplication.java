package com.firstproject.insider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class InsiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsiderApplication.class, args);
	}

}
