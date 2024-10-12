package com.firstproject.insider.system.exception;

import com.firstproject.insider.common.annotation.ExplainDescription;
import com.firstproject.insider.common.dto.ErrorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum GlobalExceptionCode implements BaseErrorCode {

    @ExplainDescription("잘못된 요청입니다. 요청 본문을 확인해 주세요.")
    ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "ARGUMENT_NOT_VALID", "파라미터 값이 올바르지 않습니다."),

    @ExplainDescription("잘못된 요청입니다. 요청 본문을 확인해 주세요.")
    MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "MESSAGE_NOT_READABLE", "잘못된 요청 입니다."),

    @ExplainDescription("접근하기 위해서 인증 자격 증명이 필요합니다.")
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "인증 자격 증명이 필요 합니다."),

    @ExplainDescription("접근할 권한이 없습니다.")
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "권한이 없습니다."),

    @ExplainDescription("서버에 존재하지 않은 리소스 입니다.")
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "존재하지 않은 리소스입니다."),

    @ExplainDescription("요청 HTTP 메서드를 다시 확인해주세요.")
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", "지원되지 않은 메서드 입니다."),

    @ExplainDescription("서버 관리자에게 문의 주세요")
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 오류. 관리자에게 문의 바랍니다.");


    private HttpStatus code;
    private String status;
    private String message;

    @Override
    public ErrorResponseDto getErrorReason() {
        return ErrorResponseDto.builder().code(code).status(status).message(message).build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainDescription annotation = field.getAnnotation(ExplainDescription.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getMessage();
    }
}