package com.firstproject.insider.user.web;

import com.firstproject.insider.common.CommonResponse;
import com.firstproject.insider.common.annotation.ApiErrorCodeExample;
import com.firstproject.insider.system.exception.GlobalExceptionCode;
import com.firstproject.insider.user.dto.request.UserIdCheckRequest;
import com.firstproject.insider.user.dto.request.UserLoginRequest;
import com.firstproject.insider.user.dto.request.UserSingUpRequest;
import com.firstproject.insider.user.dto.response.UserLoginErrorCode;
import com.firstproject.insider.user.dto.UserDTO;
import com.firstproject.insider.user.dto.response.UserSignUpErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstproject.insider.user.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Tag(name = "사용자 관련 API", description = "사용자 관련 정보 등록, 수정, 삭제에 대한 API 명세를 제공합니다.")
@Log4j2
@Validated
@RequiredArgsConstructor
public class UserController {
	

	private final UserServiceImpl userService;


	/**
	 * 회원가입 요청
	 */
	@PostMapping("sign-up")
	@Operation(summary = "회원가입", description = "회원 신규 등록 API",
				parameters = {@Parameter(name = "UserSingUpRequest", description = "회원가입 요청 객체")})
	@ApiErrorCodeExample(UserSignUpErrorCode.class)
	public ResponseEntity<CommonResponse> singUp(@RequestBody @Valid UserSingUpRequest requestBody) {
		//@Valid 어노테이션으로 유효성 검증 처리
		userService.signUp(requestBody);

		return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success("회원가입 성공"));
	}

	/**
	 * 아이디 중복 체크
	 */
	@PostMapping("checkDuplicateUserId")
	@Operation(summary = "아이디 중복 체크", description = "아이디 중복 여부 확인",
			parameters = {@Parameter(name = "userId", description = "중복 여부를 확인할 사용자 아이디")})
	@ApiErrorCodeExample(GlobalExceptionCode.class)
	public ResponseEntity<CommonResponse> singUp(@RequestBody UserIdCheckRequest requestBody) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("isDuplicated", userService.isDuplicateUserId(requestBody.getUserId()));

		return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success(resultMap));
	}


	/**
	 * 로그인 요청
	 */
	@PostMapping("sign-in")
	public HttpStatus login(@RequestBody UserLoginRequest requestBody,
							HttpSession session) {
		ResponseEntity<UserLoginErrorCode> responseEntity = null;
		String id = requestBody.getUserId();
		String password = requestBody.getPassword();
		UserDTO userInfo = userService.login(id, password);

		if(userInfo == null) {
			return HttpStatus.NOT_FOUND;
		}else {
//			userLoginResponse = UserLoginErrorCode.success(userInfo);
//			if(userInfo.getStatus() == Status.ADMIN) {
//				SessionUtil.setLoginAdminId(session, id);
//			}else {
//				SessionUtil.setLoginMemberId(session, id);
//			}
//			responseEntity = new ResponseEntity<UserLoginErrorCode>(userLoginResponse, HttpStatus.OK);
		}
		return HttpStatus.OK;
	}

//	@GetMapping("my-info")
//	public UserInfoResponse memberInfo(HttpSession session) {
//		String id = SessionUtil.getLoginMemberId(session);
//		if(id == null) {
//			id = SessionUtil.getLoginAdminId(session);
//		}
//		UserDTO memberInfo = userService.getUserInfo(id);
//		return new UserInfoResponse(memberInfo);
//	}
	
//	@PutMapping("logout")
//	public void logout(HttpSession session) {
//		SessionUtil.clear(session);
//	}
//
//	@PatchMapping("password")
//	@LoginCheck(type = LoginCheck.UserType.USER)
//	public ResponseEntity<LoginResponse> updateUserPassword(String accountId, @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
//															HttpSession session){
//		ResponseEntity<LoginResponse> responseEntity = null;
//		String id = SessionUtil.getLoginMemberId(session);
//		String beforePassword = userUpdatePasswordRequest.getBeforePassword();
//		String afterPassword = userUpdatePasswordRequest.getAfterPassword();
//
//		try {
//			userService.updatePassword(id, beforePassword, afterPassword);
//			ResponseEntity.ok(new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK));
//		}catch(IllegalArgumentException e) {
//			log.error("updatePassword 실패", e);
//			responseEntity = FAIL_RESPONSE;
//		}
//		return responseEntity;
//	}
//
//	@DeleteMapping
//	public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId,
//												 HttpSession session){
//		ResponseEntity<LoginResponse> responseEntity = null;
//		String id = SessionUtil.getLoginMemberId(session);
//
//		try {
//			userService.deleteId(id, userDeleteId.getPassword());
//			responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
//		}catch(RuntimeException e) {
//			log.error("deleteId 실패", e);
//			responseEntity = FAIL_RESPONSE;
//		}
//		return responseEntity;
//	}
}
