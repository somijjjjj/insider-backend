package com.firstproject.insider.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Schema(description = "로그인 요청 객체")
public class UserLoginRequest {

    @NotEmpty(message = "사용자아이디는 비어 있을 수 없습니다.")
    @Schema(description = "사용자 아이디", example = "example")
    private String userId;

    @NotEmpty(message = "비밀번호는 비어 있을 수 없습니다.")
    @Schema(description = "사용자 비밀번호", example = "example123!")
    private String password;
}