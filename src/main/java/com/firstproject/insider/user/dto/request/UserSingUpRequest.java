package com.firstproject.insider.user.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@ToString
public class UserSingUpRequest {
	
	@NotEmpty(message = "User ID cannot be null")
	private String userId;

	@NotEmpty(message = "Password cannot be null")
	private String password;

	@NotEmpty(message = "Nickname cannot be null")
	private String nickName;

	@NotEmpty(message = "Email cannot be null")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank
	private String birthDate;

	@NotBlank
	private String gender;

	private boolean agreeTerms;

	private boolean agreePrivacy;

	public void setPassword(String password){
		this.password = password;
	}

}
