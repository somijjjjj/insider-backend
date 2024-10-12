package com.firstproject.insider.user.service;

import com.firstproject.insider.user.dto.UserDTO;
import com.firstproject.insider.user.dto.request.UserLoginRequest;
import com.firstproject.insider.user.dto.request.UserSingUpRequest;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

	/**
	 * 회원가입 요청 처리
	 */
    void signUp(UserSingUpRequest requestBody) throws Exception;

	/**
	 * 사용자 아이디 중복 확인
	 */
	boolean isDuplicateUserId(String id) throws Exception;

	/**
	 * 사용자 이메일 중복 확인
	 */
	boolean isDuplicateEmail(String email) throws Exception;

	/**
	 * 계정 로그인 요청
	 */
	String login(UserLoginRequest requestBody) throws Exception ;

	/**
	 * 계정 아이디 찾기 요청
	 */
	String findIdByEmail(String email) throws Exception ;

	/**
	 * 계정 비밀번호 찾기 요청
	 */
	boolean resetPassword(String userId, String email) throws Exception ;
}
