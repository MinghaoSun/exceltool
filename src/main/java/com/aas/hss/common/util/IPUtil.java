package com.aas.hss.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: IPUtil
 * @Description: IP 相关操作
 * @author <a href="MailTo:data55.126.com">杨磊</a>
 * @date 2014-12-15 下午4:22:34
 * @version 1.0-SNAPSHOT
 */
public class IPUtil {
	private static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static void main(String[] args) {
		String ip = IPUtil.getLocalIP();
		System.out.println(ip);
	}

	/**
	 * 获取本机的IP地址
	 * @return
	 */
	public static String getLocalIP() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		return ipAddrStr;
	}

	/**
	 * 获取IP地址的方法
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserIpAddr(HttpServletRequest request) {
		// 获取经过代理的客户端的IP地址; 排除了request.getRemoteAddr() 方法
		// 在通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址了
		String ip = getIpAddr(request);
		if (ip != null && ip.indexOf(",") > 0) {
			String[] arr = ip.split(",");
			ip = arr[arr.length - 1].trim();// 有多个ip时取最后一个ip
		}
		return ip;
	}
}
