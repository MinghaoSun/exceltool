package com.aas.hss.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
* <p>Title: Config.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2013</p>
* <p>Company: RCOLLAR</p>
* @author <a href="mailto:fanjinhu@gmail.com">fjh</a>
* @date 2014-1-16
* @version 1.0
 */
public class Config {

	private static Properties prop = new Properties();
	
	static {
		try {
			prop.load(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("ini.properties"), "UTF-8"));   
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Config() {
	}
	
	public static Object getObject(String key) {
		return prop.get(key);
	}
	
	public static String get(String key) {
		return (String) prop.get(key);
	}
	
	public static int getInt(String key) {
		return Integer.parseInt(get(key));
	}
}
