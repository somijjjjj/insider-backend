package com.firstproject.insider.system.exception.handler;

import com.firstproject.insider.common.dto.ErrorReason;
import com.firstproject.insider.common.dto.ErrorResponse;
import com.firstproject.insider.system.exception.BaseErrorCode;
import com.firstproject.insider.system.exception.BusinessException;
import com.firstproject.insider.system.exception.GlobalExceptionCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.firstproject.insider.common.CommonResponse;
import com.firstproject.insider.system.exception.InsiderServerException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {

	/**
	 * 400 Bad Request: 유효성 검사 실패
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		GlobalExceptionCode globalExceptionCode = GlobalExceptionCode.ARGUMENT_NOT_VALID;

		log.error("ARGUMENT_NOT_VALID", ex.getMessage());

		List<String> errorMessages = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList());

		return ResponseEntity
				.status(globalExceptionCode.getCode())
				.body(new ErrorResponse(
						globalExceptionCode.getStatus(),
						errorMessages.toString()));
	}

	/**
	 * 400 Bad Request: 요청 본문을 읽을 수 없을 때 (잘못된 JSON 구조 등)
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		GlobalExceptionCode globalExceptionCode = GlobalExceptionCode.MESSAGE_NOT_READABLE;

		return ResponseEntity
				.status(globalExceptionCode.getCode())
				.body(new ErrorResponse(
						globalExceptionCode.getStatus(),
						globalExceptionCode.getMessage()));
	}

	/**
	 * 405 Method Not Allowed: 지원되지 않는 HTTP 메서드 호출
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		GlobalExceptionCode globalExceptionCode = GlobalExceptionCode.METHOD_NOT_ALLOWED;

		return ResponseEntity
				.status(globalExceptionCode.getCode())
				.body(new ErrorResponse(
						globalExceptionCode.getStatus(),
						globalExceptionCode.getMessage()));
	}

	/**
	 * 500 Internal Server Error: 그 외 모든 예외 처리
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
		GlobalExceptionCode globalExceptionCode = GlobalExceptionCode.INTERNAL_SERVER_ERROR;

		log.error("INTERNAL_SERVER_ERROR", ex);

		return ResponseEntity
				.status(globalExceptionCode.getCode())
				.body(new ErrorResponse(
						globalExceptionCode.getStatus(),
						globalExceptionCode.getMessage()));
	}

	/**
	 * 200 Insider Server Error:
	 */
	@ExceptionHandler( {InsiderServerException.class} )
	public ResponseEntity<ErrorResponse> handleRuntimeException(InsiderServerException e){
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ErrorResponse(
						"Insider Server Error",
						e.getMessage()));
	}

	/**
	 * 400 BusinessException:
	 */
	@ExceptionHandler( {BusinessException.class} )
	public ResponseEntity<ErrorResponse> handleRuntimeException(BusinessException e){
		ErrorReason errorCode = e.getErrorCode().getErrorReason();

		ErrorResponse errorResponse = new ErrorResponse(errorCode.getStatus(), errorCode.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
