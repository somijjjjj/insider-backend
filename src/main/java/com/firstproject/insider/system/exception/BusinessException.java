package com.firstproject.insider.system.exception;

import com.firstproject.insider.common.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;

@Getter
public class BusinessException extends RuntimeException{//실제 런타임 시 예외 던질 수 있음

	private final BaseErrorCode errorCode;

	public BusinessException(BaseErrorCode errorCode) {
		super(errorCode.getErrorReason().getMessage());
		this.errorCode = errorCode;
	}

}
