package com.firstproject.insider.system.exception;


import com.firstproject.insider.common.dto.ErrorReason;

public interface BaseErrorCode {
    public ErrorReason getErrorReason();

    String getExplainError() throws NoSuchFieldException;
}
