package com.firstproject.insider.common.dto;

import com.firstproject.insider.system.utils.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponseDto<T> {

    /** 응답 상태 코드 */
    @Schema(description = "응답 결과에 대한 상태코드")
    private final ResponseStatus status;

    /** 응답 메시지  */
    @Schema(description = "응답 결과에 대한 메시지")
    private final String message;

    /** 성공 시 반환할 데이터 객체 */
    @Schema(description = "성공 시 반환할 데이터 객체")
    private final T data;
}