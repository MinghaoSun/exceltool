package com.aas.hss.common.util;

import java.math.BigDecimal;
import java.util.UUID;


import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: StringHelper
 * @Description: String 处理类
 * @author <a href="MailTo:data55.126.com">杨磊</a>
 * @date 2014-5-29 上午9:25:43
 * @version 1.0-SNAPSHOT
 */
public class StringHelper {
	
	/**
	 * 空值转换默认值
	 * @param param
	 * @param defaultvalue
	 * @return
	 */
	public static String emptyValidate(String param, String defaultvalue) {
		return StringUtils.isBlank(param)? defaultvalue:param;
	}
	
	/**
	 * 字符串加减
	 * @param param
	 * @param value
	 * @return
	 */
	public static String stringAdd(String param,String value){
		return new BigDecimal(param).add(new BigDecimal(value)).toString();
	}

	
	/**
	 * @Description 产生UUID
	 * @return 去掉"-"的String类型UUID
	 */
	public static String createUUID(){
	 return	UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * @Description: 去除字符串多余的逗号，和最后一个逗号
	 * @param strInfo
	 *            字符串
	 * @date 2014-5-29 上午9:29:14
	 * @return String 返回类型
	 */
	public static String commaProcess(String strInfo) {
		strInfo=strInfo==null?"":strInfo.replaceAll("\\s*", "");
		String[] tempStrings = strInfo.split(",");
		StringBuffer buffString = new StringBuffer();
		for (int i = 0; i < tempStrings.length; i++) {
			if (StringUtils.isEmpty(tempStrings[i])) {
				continue;
			}
			buffString.append(tempStrings[i]);
			if (i < (tempStrings.length - 1)) {
				buffString.append(",");
			}
		}
		return buffString.toString();
	}
	
	public static String defaultString(String str, String defaultStr) {
		return StringUtils.isEmpty(str) ? defaultStr : str;
	}
	public static String objToSting(Object o){
		return o==null?"":o.toString();
	}
	
	
	/**
	 * decimal
	 */
	public static BigDecimal stringToDecimal(String s){
		return  StringUtils.isEmpty(s)?new BigDecimal(0):new BigDecimal(s);
	}
}
