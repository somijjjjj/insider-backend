package com.firstproject.insider.user.service.impl;

import com.firstproject.insider.user.dto.UserDTO;
import com.firstproject.insider.user.dto.request.UserSingUpRequest;
import com.firstproject.insider.user.dto.response.UserLoginErrorCode;
import com.firstproject.insider.user.dto.response.UserSignUpErrorCode;
import com.firstproject.insider.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.firstproject.insider.system.exception.BusinessException;
import com.firstproject.insider.user.service.UserService;
import com.firstproject.insider.system.utils.SHA256Util;

import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    @Override
    public UserDTO getUserInfo(String userId) {
        return userMapper.getUserProfile(userId);
    }

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 비밀번호 암호화
     */
    public static String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 비밀번호 검증
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


    /**
     * 회원가입 요청 처리
     */
    @Transactional
    @Override
    public void signUp(UserSingUpRequest requestBody) {

        //아이디 중복 체크
        String userId = requestBody.getUserId();
        if( isDuplicateUserId(userId) ) {
            throw new BusinessException(UserSignUpErrorCode.USER_ID_DUPLICATION);
        }

        //이메일 가입 유무 체크
        if( isDuplicateEmail(requestBody.getEmail()) ) {
            throw new BusinessException(UserSignUpErrorCode.USER_ALREADY_SIGNUP);
        }

        //이용약관 동의, 개인정보처리방침 동의 확인
        if(requestBody.isAgreeTerms() && requestBody.isAgreePrivacy()){

            //패스워드 암호화 처리, BCrypt 알고리즘으로 솔트 자동 생성
            requestBody.setPassword(passwordEncoder.encode(requestBody.getPassword()));

            int result = userMapper.insertUser(requestBody);

            if(result == 1){
                //회원가입 시 무조건 USER로 가입
                userMapper.insertUserAuth(userId, "ROLE_USER");

            }
        }else{
            throw new BusinessException(UserSignUpErrorCode.USER_DISAGREE);
        }
    }


    @Override
    public UserDTO login(String id, String password) {
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        return userMapper.findByIdAndPassword(id, cryptoPassword);
    }



    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptoPassword = SHA256Util.encryptSHA256(beforePassword);
        UserDTO memberInfo = userMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            memberInfo.setPassword(SHA256Util.encryptSHA256(afterPassword));
            int insertCount = userMapper.updatePassword(memberInfo);
        } else {
            log.error("updatePassword ERROR! {}", memberInfo);
            throw new IllegalArgumentException("updatePassword ERROR! 비밀번호 변경 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }

    @Override
    public void deleteId(String id, String passWord) throws Exception {
        String cryptoPassword = SHA256Util.encryptSHA256(passWord);
        UserDTO memberInfo = userMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            userMapper.deleteUserProfile(memberInfo.getUserId());
        } else {
            log.error("deleteId ERROR! {}", memberInfo);
            throw new RuntimeException("deleteId ERROR! id 삭제 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }


    /**
     * 사용자 아이디 중복 확인
     */
    public boolean isDuplicateUserId(String id) {
        return userMapper.checkUserId(id) == 1;
    }

    /**
     * 사용자 이메일 중복 확인
     */
    private boolean isDuplicateEmail(String email) {
        return userMapper.checkEmail(email) == 1;
    }

}
