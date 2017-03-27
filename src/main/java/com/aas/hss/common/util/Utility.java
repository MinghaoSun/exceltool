package com.aas.hss.common.util;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * @ClassName: Utility
 * @Description: 字符串工具包
 * @author LiuKai
 * @date 2015-9-29 下午12:03:01
 * 
 */
public class Utility {
	
	private static final int PAGE_INDEX=1;
	
	private static final int PAGE_SIZE=10;
	
	private static final int PAGE_SIZE_WINDOW=10;

	public static String toSafeString(Object obj) {
		 byte bytes[] = {(byte) 0xC2,(byte) 0xA0};
		 try {
		 String UTFSpace = new String(bytes,"utf-8");
			if ((obj == null) || (obj.equals(null))) {
				return "";
			}
			String s = obj.toString().trim();
			s = s.replaceAll(UTFSpace, "	");
			return s;
		} catch (Exception e) {
		}
		return "";
	}

	public static Integer toSafeInt(Object obj) {
		if (isInteger(obj)) {
			return Integer.parseInt(toSafeString(obj));
		}
		return null;
	}

	public static String[] getStrArray(String str)
	  {
	    return str.split("\\,");
	  }
	
	public static int toSafeInt(Object obj, int nInitValue) {
		int nResult = toSafeInt(obj);
		if (nResult == -1) {
			nResult = nInitValue;
		}
		return nResult;
	}

	public static long toSafeLong(Object obj) {
		long num = 0L;
		try {
			num = Long.parseLong(toSafeString(obj));
		} catch (Exception localException) {
		}
		return num;
	}

	public static long toSafeShort(Object obj) {
		short num = 0;
		try {
			num = Short.parseShort(toSafeString(obj));
		} catch (Exception localException) {
		}
		return num;
	}

	public static float toSafeFloat(Object obj) {
		float num = 0.0F;
		try {
			num = Float.parseFloat(toSafeString(obj));
		} catch (Exception localException) {
		}
		return num;
	}

	public static double toSafeDouble(Object obj) {
		double num = 0.0D;
		try {
			num = Double.parseDouble(toSafeString(obj));
		} catch (Exception localException) {
		}
		return num;
	}

	public static Date toSafeDateTime(Object objDate) {
		Date date = new Date();
		try {
			String strDate = toSafeString(objDate);
			if (strDate.indexOf(":") > 0) {
				SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = formatDateTime.parse(strDate);
			} else {
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
				date = formatDate.parse(strDate);
			}
		} catch (Exception localException) {
		}
		return date;
	}

	public static boolean toSafeBool(Object obj) {
		if (toSafeString(obj).equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}

	public static boolean isInteger(Object str) {
		try {
			Integer.parseInt(toSafeString(str));
			return true;
		} catch (Exception err) {
		}
		return false;
	}

	public static int toSafePageIndex(Object obj) {
		if (isInteger(obj)) {
			return Integer.parseInt(toSafeString(obj));
		} else {
			return PAGE_INDEX;
		}
	}

	public static int toSafePageSize(Object obj) {
		if (isInteger(obj)) {
			return Integer.parseInt(toSafeString(obj));
		} else {
			return PAGE_SIZE;
		}
	}

	public static int toSafePageSize(Object obj, String where) {
		if (isInteger(obj)) {
			return Integer.parseInt(toSafeString(obj));
		} else {
			if (where == "WINDOW") {
				return PAGE_SIZE_WINDOW;
			} else {
				return PAGE_SIZE;
			}
		}
	}


	/**
	 * @Description: TODO(文件名的一个转码)
	 * @param fileName
	 *            文件名
	 * @return 转码后的文件名
	 * @date 2015-10-12 下午8:17:09
	 * @return String 返回类型（如果没有转换成功就返回当前时间）
	 */
	public static String fileNameEncode(HttpServletRequest request, String fileName) {
		String agent = request.getHeader("User-agent");
		try {
			// 如果浏览器是IE浏览器，就得进行编码转换
			if (agent.contains("MSIE") || (agent.contains("Gecko") && agent.contains("rv:11"))) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				fileName = new String(fileName.getBytes(), "ISO-8859-1");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fileName = new Date().getTime() + "";
		}
		return fileName;
	}
	
	public static Date getDateSkipHours(Date date, int nHours) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(11, nHours);
			return cal.getTime();
		} catch (Exception localException) {
		}
		return date;
	}
	
	//验证字符串中的字符是否为汉字
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
    
    //验证字符长度
    public static int checkLength(String str){
        int len=0;
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                len+=2;
            }else {
                len++;
            }
        }
        return len;
    }
    
    /** 
	* @Description: List 转化为String
	* @param list 数组
	* @param sep 分割符号
	* @return String    返回类型  
	*/ 
	public static String listToString(List<String> list, String sep) {
		if (list == null) {
			return null;
		}
		StringBuilder result=new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			result.append(list.get(i));
			if (i < list.size()) {
				result.append(",");
			}
		}
		return result.toString();
	}
}
