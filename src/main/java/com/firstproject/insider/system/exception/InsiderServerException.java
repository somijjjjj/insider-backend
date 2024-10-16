package com.firstproject.insider.system.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class InsiderServerException extends RuntimeException{

	private HttpStatus code;
	private String status;
	private String message;

}
