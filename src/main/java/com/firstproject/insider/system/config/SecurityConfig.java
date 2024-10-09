package com.firstproject.insider.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //폼 로그인 및 CSRF 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                //서버에서 세션을 생성하지 않음. stateless 토큰 기반 인증을 사용
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 모든 요청 허용..
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()
                );

        //권한 부여 설정
        // http.authorizeRequests().expressionHandler(expressionHandler());

        //베이직 인증 설정
//        if (springEnvironmentHelper.isProdAndStagingProfile()) {
//            http.authorizeRequests().mvcMatchers(SwaggerPatterns).authenticated().and().httpBasic();
//        }

        return http.build();
    }
}