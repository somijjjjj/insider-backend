package com.firstproject.insider.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponseDto {
    private final HttpStatus code;
    private final String status;
    private final String message;
}