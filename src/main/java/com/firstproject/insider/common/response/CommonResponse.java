package com.firstproject.insider.common.response;

import com.firstproject.insider.common.dto.CommonResponseDto;
import com.firstproject.insider.system.utils.ResponseStatus;


public class CommonResponse {

	/**
	 * 성공 응답 코드와 데이터를 포함한 응답 객체를 반환한다.
     */
	public static <T>CommonResponseDto<T> SUCCESS(String message, T data) {
		return new CommonResponseDto<>(ResponseStatus.SUCCESS, message, data);
	}

	/**
	 * 성공 응답 코드와 메시지만을 포함한 응답 객체를 반환한다.
	 */
	public static <T>CommonResponseDto<T> SUCCESS(String message) {
		return new CommonResponseDto<>(ResponseStatus.SUCCESS, message, null);
	}

	/**
	 * 실패 응답 코드와 데이터를 포함한 응답 객체를 반환한다.
	 */
	public static <T>CommonResponseDto<T> FAIL(String message, T data) {
		return new CommonResponseDto<>(ResponseStatus.FAILURE, message, data);
	}

	/**
	 * 실패 응답 코드와 메시지만을 포함한 응답 객체를 반환한다.
	 */
	public static <T>CommonResponseDto<T> FAIL(String message) {
		return new CommonResponseDto<>(ResponseStatus.FAILURE, message, null);
	}

	/**
	 * 에러 응답 코드와 데이터를 포함한 응답 객체를 반환한다.
	 */
	public static <T> CommonResponseDto<T> ERROR (String message, T data){
		return new CommonResponseDto<>(ResponseStatus.ERROR, message, data);
	}
}
