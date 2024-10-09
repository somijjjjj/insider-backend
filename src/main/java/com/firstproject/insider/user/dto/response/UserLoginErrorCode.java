package com.firstproject.insider.user.dto.response;

import com.firstproject.insider.common.annotation.ExplainDescription;
import com.firstproject.insider.common.dto.ErrorReason;
import com.firstproject.insider.system.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum UserLoginErrorCode implements BaseErrorCode {

    @ExplainDescription("이미 존재하는 아이디인 경우 발생하는 오류")
    USER_ID_DUPLICATION(HttpStatus.BAD_REQUEST, "USER_ID_DUPLICATION", "동일한 아이디가 존재 합니다."),

    @ExplainDescription("이미 해당 정보로 회원가입한 유저일시 발생하는 오류")
    USER_ALREADY_SIGNUP(HttpStatus.BAD_REQUEST,"USER_ALREADY_SIGNUP", "이미 회원가입한 유저입니다."),

    @ExplainDescription("접근 가능한 권한이 없는 사용자일 때 발생하는 오류")
    USER_FORBIDDEN(HttpStatus.BAD_REQUEST, "USER_FORBIDDEN", "접근이 제한된 유저입니다."),

    @ExplainDescription("정지 처리된 유저일 경우 발생")
    USER_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "USER_ALREADY_DELETED", "이미 지워진 유저입니다."),

    @ExplainDescription("탈퇴한 사용자 이거나 사용자 정보가 없는 경우 발생하는 오류")
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND", "유저 정보를 찾을 수 없습니다."),

    @ExplainDescription("null값이 들어온 경우 발생 ")
    PARAMETER_EMPTY(HttpStatus.BAD_REQUEST, "PARAMETER_EMPTY", "파라미터 중 빈 값이 존재합니다.");

    private HttpStatus code;
    private String status;
    private String message;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder().code(code).status(status).message(message).build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainDescription annotation = field.getAnnotation(ExplainDescription.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getMessage();
    }
}