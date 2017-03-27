package com.aas.hss.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @ClassName: SpringUtils
 * @Description: TODO(spring的工具类)
 * @author <a href="MailTo:data55.126.com">杨磊</a>
 * @date 2015-12-18 上午11:01:41
 * @version 1.0-SNAPSHOT
 */
public class SpringUtils implements ApplicationContextAware {
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		context = applicationContext;
	}

	/**
	 * @Description: TODO(获得bean)
	 * @param beanName
	 * @date 2015-12-18 上午11:06:07
	 * @return Object 返回类型
	 */
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
}
