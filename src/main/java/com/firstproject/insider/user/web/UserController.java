package com.firstproject.insider.user.web;

import com.firstproject.insider.common.dto.CommonResponseDto;
import com.firstproject.insider.common.response.CommonResponse;
import com.firstproject.insider.common.annotation.ApiErrorCodeExample;
import com.firstproject.insider.system.exception.GlobalExceptionCode;
import com.firstproject.insider.system.utils.SessionUtil;
import com.firstproject.insider.user.dto.request.UserLoginRequest;
import com.firstproject.insider.user.dto.request.UserSingUpRequest;
import com.firstproject.insider.user.dto.response.UserLoginErrorCode;
import com.firstproject.insider.user.dto.UserDTO;
import com.firstproject.insider.user.dto.response.UserSignUpErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.firstproject.insider.user.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Tag(name = "사용자 관련 API", description = "사용자 관련 정보 등록, 수정, 삭제에 대한 API 명세를 제공합니다.")
@Validated
@RequiredArgsConstructor
public class UserController {


	private final UserServiceImpl userService;


	/**
	 * 회원가입 요청
	 */
	@PostMapping("sign-up")
	@Operation(summary = "회원가입", description = "회원 신규 등록 API")
	@ApiErrorCodeExample(UserSignUpErrorCode.class)
	public CommonResponseDto<Object> singUp(@RequestBody @Valid UserSingUpRequest requestBody) {
		userService.signUp(requestBody);

		return CommonResponse.SUCCESS("회원가입 성공");
	}

	/**
	 * 아이디 중복 체크
	 */
	@PostMapping("check-duplicate-userid")
	@Operation(summary = "아이디 중복 체크", description = "아이디 중복 여부 확인",
			parameters = {@Parameter(name = "userId", description = "중복 여부를 확인할 사용자 아이디")})
	@ApiErrorCodeExample(GlobalExceptionCode.class)
	public CommonResponseDto<Object> checkDuplicateUserId(@RequestParam String userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("isDuplicated", userService.isDuplicateUserId(userId));

		return CommonResponse.SUCCESS("아이디 중복 확인 성공", resultMap);
	}

	/**
	 * 이메일 중복 체크
	 */
	@PostMapping("check-duplicate-email")
	@Operation(summary = "이메일 중복 체크", description = "이메일 중복 여부 확인",
			parameters = {@Parameter(name = "email", description = "중복 여부를 확인할 이메일")})
	@ApiErrorCodeExample(GlobalExceptionCode.class)
	public CommonResponseDto<Object> checkDuplicateEmail(@RequestParam String email) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("isDuplicated", userService.isDuplicateEmail(email));

		return CommonResponse.SUCCESS("이메일 중복 확인 성공", resultMap);
	}


	/**
	 * 계정 로그인 요청
	 */
	@PostMapping("sign-in")
	@Operation(summary = "계정 로그인 요청", description = "계정 로그인 요청")
	@ApiErrorCodeExample(UserSignUpErrorCode.class)
	public CommonResponseDto<Object> signIn(@RequestBody @Valid UserLoginRequest requestBody) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("token", userService.login(requestBody));

		return CommonResponse.SUCCESS("로그인 성공", resultMap);
	}

	/**
	 * 계정 아이디 찾기 요청
	 */
	@PostMapping("find-id")
	@Operation(summary = "계정 아이디 찾기 요청", description = "가입된 이메일로 아이디 찾기 요청")
	@ApiErrorCodeExample(GlobalExceptionCode.class)
	public CommonResponseDto<Object> findId(@RequestParam String email) {
		String userId = userService.findIdByEmail(email);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("userId", userId);

		return CommonResponse.SUCCESS("아이디 찾기 성공", resultMap);
	}

	/**
	 * 계정 비밀번호 찾기 요청
	 */
	@PostMapping("find-password")
	@Operation(summary = "계정 비밀번호 찾기 요청", description = "가입된 이메일과 아이디로 비밀번호 찾기 요청")
	@ApiErrorCodeExample(GlobalExceptionCode.class)
	public CommonResponseDto<Object> findPassword(@RequestParam String userId, @RequestParam String email) {
		boolean resetStatus = userService.resetPassword(userId, email);
		return resetStatus
				? CommonResponse.SUCCESS("인증 성공, 이메일로 비밀번호 재설정 링크가 전송되었습니다.")
				: CommonResponse.FAIL("인증 실패, 해당 계정을 찾을 수 없습니다.");
	}


}


