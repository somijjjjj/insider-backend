package com.firstproject.insider.user.service;

import com.firstproject.insider.user.dto.UserDTO;
import com.firstproject.insider.user.dto.request.UserSingUpRequest;

public interface UserService {

	/**
	 * 회원가입 요청 처리
	 */
	void signUp(UserSingUpRequest requestBody) throws Exception;
	
	//로그인
	UserDTO login(String id, String password) throws Exception ;

	//유저 정보 조회
	UserDTO getUserInfo(String userId) throws Exception;

	//비밀번호 변경
	void updatePassword(String id, String beforePassword, String afterPassword) throws Exception;
	
	//유저 삭제
	void deleteId(String id, String password) throws Exception;
}
