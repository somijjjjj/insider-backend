package com.firstproject.insider.common;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "공통 응답 객체")
public class CommonResponse<T> {

	private static final String SUCCESS_STATUS = "success";
	private static final String FAIL_STATUS = "fail";
	private static final String ERROR_STATUS = "error";

	@Schema(description = "상태 (success, fail, error)")
	private String status;
	private T data;
	@Schema(description = "응답 메시지")
	private String message;


	/**
	 * 응답 데이터를 포함한 성공 응답코드를 리턴한다.
     */
	public static <T> CommonResponse<T> success(T data) {
		return new CommonResponse<>(SUCCESS_STATUS, data, null);
	}

	/**
	 * 성공 응답코드만 리턴한다.
	 */
	public static CommonResponse<?> success() {
		return new CommonResponse<>(SUCCESS_STATUS, null, null);
	}

	/**
	 * 성공 응답코드와 응답 메시지를 리턴한다.
	 */
	public static CommonResponse<?> success(String message) {
		return new CommonResponse<>(SUCCESS_STATUS, null, message);
	}

	/**
	 * 응답 데이터 및 응답 메시지를 포함한 성공 응답코드를 리턴한다.
	 */
	public static <T> CommonResponse<T> success(T data, String message) {
		return new CommonResponse<>(SUCCESS_STATUS, data, message);
	}

	/**
	 * Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거부될때 반환
	 */
	public static CommonResponse<?> fail(BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();

		List<ObjectError> allErrors = bindingResult.getAllErrors();
		for (ObjectError error : allErrors) {
			if (error instanceof FieldError) {
				errors.put(((FieldError) error).getField(), error.getDefaultMessage());
			} else {
				errors.put( error.getObjectName(), error.getDefaultMessage());
			}
		}
		return new CommonResponse<>(FAIL_STATUS, errors, null);
	}

	/**
	 * 예외 발생으로 API 호출 실패시 반환
	 */
	public static CommonResponse<?> error(String message) {
		return new CommonResponse<>(ERROR_STATUS, null, message);
	}
}
