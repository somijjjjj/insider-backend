package com.firstproject.insider.system.exception;


import com.firstproject.insider.common.dto.ErrorResponseDto;

public interface BaseErrorCode {
    public ErrorResponseDto getErrorReason();

    String getExplainError() throws NoSuchFieldException;
}
