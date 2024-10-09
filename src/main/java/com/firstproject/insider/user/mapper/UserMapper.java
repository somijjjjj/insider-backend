package com.firstproject.insider.user.mapper;

import com.firstproject.insider.user.dto.UserDTO;
import com.firstproject.insider.user.dto.request.UserSingUpRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

	
	UserDTO getUserProfile(@Param("id") String id);
	
	//유저 탈퇴
	int deleteUserProfile(@Param("id") String id);
	
	//로그인 시 id, password 값 조회
	UserDTO findByIdAndPassword(@Param("id") String id,
									   @Param("password") String password);

	//비밀번호 변경
    int updatePassword(UserDTO loginDTO);

    //주소 변경
    int updateAddress(UserDTO loginDTO);
	

}
