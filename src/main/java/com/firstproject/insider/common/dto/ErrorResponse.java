package com.firstproject.insider.common.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
public class ErrorResponse {

    private String status;
    private String message;


    public ErrorResponse(ErrorReason errorReason) {
        this.status = errorReason.getStatus();
        this.message = errorReason.getMessage();
    }

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

}