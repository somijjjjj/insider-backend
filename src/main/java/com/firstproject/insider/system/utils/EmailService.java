package com.firstproject.insider.system.utils;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    /**
     * 비밀번호 재설정 이메일 보내기
     */
    public void sendPasswordResetEmail(String email, String tempPassword) {
        String subject = "비밀번호 재설정";
        String body = "임시 비밀번호: " + tempPassword ;

        sendEmail(email, subject, body);
    }

    private void sendEmail(String email, String subject, String body) {
        // @todo: JavaMailSender
        // mailSender.send(createEmail(email, subject, body));
    }
}
