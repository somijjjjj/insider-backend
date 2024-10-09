package com.firstproject.insider.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class UserDTO {
	
	private String userId;

	private String password;

	private String nickName;

	private String email;

	private String birthDate;
	
	private String gender;

	private boolean agreeTerms;

	private boolean agreePrivacy;
}
