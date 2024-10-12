package com.firstproject.insider.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@ToString
@Schema(description = "회원가입 요청 객체")
public class UserSingUpRequest {
	
	@NotEmpty(message = "사용자아이디는 비어 있을 수 없습니다.")
	@Schema(description = "사용자 아이디", example = "example")
	private String userId;

	@NotEmpty(message = "비밀번호는 비어 있을 수 없습니다.")
	@Schema(description = "사용자 비밀번호", example = "example123!")
	private String password;

	@NotEmpty(message = "닉네임은 비어 있을 수 없습니다.")
	@Schema(description = "닉네임", example = "example")
	private String nickName;

	@NotEmpty(message = "이메일은 비어 있을 수 없습니다.")
	@Email(message = "유효하지 않은 이메일 형식입니다.")
	@Schema(description = "이메일", example = "example@email.com")
	private String email;

	@NotEmpty(message = "생년월일은 비어 있을 수 없습니다.")
	@Schema(description = "생년월일", example = "2024-10-01")
	private String birthDate;

	@NotEmpty(message = "성별은 비어 있을 수 없습니다.")
	@Schema(description = "성별", example = "M")
	private String gender;

	@Schema(description = "이용약관 동의",example = "true")
	private boolean agreeTerms;

	@Schema(description = "개인정보처리방침 동의" , example = "true")
	private boolean agreePrivacy;

	public void setPassword(String password){
		this.password = password;
	}

}
