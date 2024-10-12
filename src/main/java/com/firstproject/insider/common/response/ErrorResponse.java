package com.firstproject.insider.common.response;


import com.firstproject.insider.common.dto.ErrorResponseDto;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String status;
    private final String message;


    public ErrorResponse(ErrorResponseDto errorResponseDto) {
        this.status = errorResponseDto.getStatus();
        this.message = errorResponseDto.getMessage();
    }

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

}