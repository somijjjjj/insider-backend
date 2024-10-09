package com.firstproject.insider.user.dto.response;

import com.firstproject.insider.common.annotation.ExplainDescription;
import com.firstproject.insider.common.dto.ErrorReason;
import com.firstproject.insider.system.exception.BaseErrorCode;
import com.firstproject.insider.system.exception.GlobalExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum UserSignUpErrorCode implements BaseErrorCode {

    @ExplainDescription("이미 존재하는 아이디인 경우 발생하는 오류")
    USER_ID_DUPLICATION(HttpStatus.BAD_REQUEST, "USER_ID_DUPLICATION", "동일한 아이디가 존재 합니다."),

    @ExplainDescription("해당 이메일로 회원가입한 유저일시 발생하는 오류")
    USER_ALREADY_SIGNUP(HttpStatus.BAD_REQUEST,"USER_ALREADY_SIGNUP", "이미 회원가입한 유저입니다."),

    @ExplainDescription("이용약관 동의 또는 개인정보처리방침 동의가 필요합니다.")
    USER_DISAGREE(HttpStatus.BAD_REQUEST,"USER_DISAGREE", "이용약관 동의 또는 개인정보처리방침 동의가 필요합니다."),

    @ExplainDescription("잘못된 요청입니다. 요청 본문을 확인해 주세요.")
    ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "ARGUMENT_NOT_VALID", "파라미터 값이 올바르지 않습니다.");

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