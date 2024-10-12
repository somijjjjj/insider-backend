package com.firstproject.insider.user.service.impl;

import com.firstproject.insider.system.exception.GlobalExceptionCode;
import com.firstproject.insider.system.utils.EmailService;
import com.firstproject.insider.system.utils.JwtUtil;
import com.firstproject.insider.user.dto.UserDTO;
import com.firstproject.insider.user.dto.request.UserLoginRequest;
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

import java.util.Map;
import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    /**
     * 사용자 아이디 중복 확인
     */
    @Override
    public boolean isDuplicateUserId(String id) {
        if(id.isEmpty() || id.isBlank()){
            throw new BusinessException(GlobalExceptionCode.ARGUMENT_NOT_VALID);
        }
        return userMapper.checkUserId(id) == 1;
    }

    /**
     * 사용자 이메일 중복 확인
     */
    @Override
    public boolean isDuplicateEmail(String email) {
        if(email.isEmpty() || email.isBlank()){
            throw new BusinessException(GlobalExceptionCode.ARGUMENT_NOT_VALID);
        }
        return userMapper.checkEmail(email) == 1;
    }

    /**
     * 계정 로그인 요청
     */
    @Transactional
    @Override
    public String login(UserLoginRequest requestBody) {
        // 파라미터 값으로 유저 정보 조회
        String encodedPassword = userMapper.selectLoginInfo(requestBody.getUserId());

        if(encodedPassword == null){
            throw new BusinessException(UserLoginErrorCode.USER_NOT_FOUND);
        }
        String rawPassword  = requestBody.getPassword();

        // 비밀번호 검증
        boolean isMatch = passwordEncoder.matches(rawPassword, encodedPassword);

        if (isMatch) {
            // JWT 생성
            return jwtUtil.generateToken(requestBody.getUserId());
        } else {
            throw new BusinessException(UserLoginErrorCode.USER_NOT_FOUND);
        }
    }


    /**
     * 계정 아이디 찾기 요청
     */
    @Transactional
    @Override
    public String findIdByEmail(String email) {
        return userMapper.selectUserIdByEmail(email);
    }


    /**
     * 계정 비밀번호 초기화
     */
    @Transactional
    @Override
    public boolean resetPassword(String userId, String email) {
        String storedEmail = userMapper.selectEmailByUserId(userId);
        if (storedEmail != null && storedEmail.equals(email)) {
            // 임시 비밀번호(또는 재설정 토큰) 생성
            String tempPassword = generateTemporaryPassword();
            //String encodedPassword = passwordEncoder.encode(tempPassword);
            //userMapper.updateUserPassword(userId, encodedPassword);

            // 임시 비밀번호 또는 재설정 링크가 포함된 이메일 전송
            emailService.sendPasswordResetEmail(email, tempPassword);
            return true;
        }

        return false; //사용자 ID 또는 이메일이 일치하지 않음
    }

    /**
     * 임시 비밀번호 생성
     */
    private String generateTemporaryPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }


}
