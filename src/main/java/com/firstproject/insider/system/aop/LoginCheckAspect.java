package com.firstproject.insider.system.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.firstproject.insider.system.utils.SessionUtil;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@Order(Ordered.LOWEST_PRECEDENCE)
@Aspect
public class LoginCheckAspect {

	@Around("@annotation(com.firstproject.insider.system.aop.LoginCheck) && @ annotation(loginCheck)")
	public Object adminLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable{
		HttpSession session = (HttpSession)((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession(); //세션가져옴
		String id = null;
		int idIndex = 0;
		
		String userType = loginCheck.type().toString();
		switch (userType) {
			case "ADMIN": {
				id = SessionUtil.getLoginAdminId(session);
				break;
			}
			case "USER":{
				id = SessionUtil.getLoginMemberId(session);
				break;
			}
		}
		if(id == null) {
			log.info(proceedingJoinPoint.toString() + " accountName : "+id);
			throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 ID값을 확인해주세요") {};
		}
		
		Object[] modefiedArgs = proceedingJoinPoint.getArgs();
		
		if(proceedingJoinPoint != null) {
			modefiedArgs[idIndex] = id;
		}
		return proceedingJoinPoint.proceed(modefiedArgs);
	}

}
