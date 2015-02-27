package com.newsoft.utils;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class PasswordEncoderUtil {
	private PasswordEncoderUtil(){
		
	}
	private static Md5PasswordEncoder md5PasswordEncoder=null;
	static{
		md5PasswordEncoder=new Md5PasswordEncoder();
		md5PasswordEncoder.setEncodeHashAsBase64(false);
	}
	public static String encode(String plainPassword){
		String pwdString=md5PasswordEncoder.encodePassword(plainPassword, null);
		return pwdString;
	}
}
