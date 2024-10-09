package com.firstproject.insider.system.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginCheck {
	
	public static enum UserType{
		GUEST, USER, ADMIN
	}
	
	UserType type() ;

}
