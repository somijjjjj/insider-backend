package com.firstproject.insider.user.mapper;

import com.firstproject.insider.user.dto.UserDTO;
import com.firstproject.insider.user.dto.request.UserLoginRequest;
import com.firstproject.insider.user.dto.request.UserSingUpRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface UserMapper {

	/**
	 * 사용자 등록
	 */
    int insertUser(UserSingUpRequest userInfo);

	/**
	 * 사용자 권한 등록
	 */
	int insertUserAuth(@Param("userId") String id, @Param("role") String role);

	/**
	 * 사용자 아이디 중복 확인
	 */
	int checkUserId(@Param("userId") String id);

	/**
	 * 이메일 중복 확인
	 */
	int checkEmail(@Param("email") String email);

	/**
	 * 로그인 정보 조회
	 */
	String selectLoginInfo(@Param("userId") String id);

	/**
	 * 계정 아이디 조회
	 */
	String selectUserIdByEmail(@Param("email") String email);

	/**
	 * 계정 이메일 조회
	 */
	String selectEmailByUserId(@Param("userId") String id);

	/**
	 * 비밀번호 업데이트
	 */
	String updateUserPassword(@Param("userId") String id, @Param("encodedPassword") String encodedPassword);


}
