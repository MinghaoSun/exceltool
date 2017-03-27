package com.aas.hss.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidateUtil {
	
	
	/**
	 * 邮件地址合法性校验
	 * @param email
	 * @return
	 */
	public static boolean emailFormat(String email){
		if(StringUtils.isBlank(email))
			return false;
		Pattern pattern=Pattern.compile("^([a-zA-Z0-9_\\-\\.\\:]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher=pattern.matcher(email);
		return(matcher.matches());
	}
	
	public static boolean mobileFormat(String phoneNum){
		if(StringUtils.isBlank(phoneNum))
			return false;
		Pattern pattern=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17)[0,6,8])\\d{8}$");
		Matcher matcher=pattern.matcher(phoneNum);
		return(matcher.matches());
	}

}
