package com.aas.hss.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/** 
 * @ClassName: ExceptionHelper 
 * @Description: 异常基本业务处理 
 * @author Minghao
 * @date 2017年3月27日16:42:00 
 * @version 1.0-SNAPSHOT
 */
public class ExceptionHelper {
	/** 
	* @Description: 异常信息输出
	* @param t 异常
	* @date 2014-6-9 上午10:48:30
	* @return String    返回类型  
	*/ 
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		String result=buffer.toString().replace("'", "\"");
		return result;
	}
}

