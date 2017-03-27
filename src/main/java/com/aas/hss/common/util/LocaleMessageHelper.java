package com.aas.hss.common.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;

/**
 * @ClassName: LocaleMessageHelper
 * @Description: TODO(国际化文件帮助类)
 * @author <a href="MailTo:data55.126.com">杨磊</a>
 * @date 2015-10-13 下午7:50:51
 * @version 1.0-SNAPSHOT
 */
public class LocaleMessageHelper {
	private static ResourceBundleModel rsbm;
	/**
	 * 国际化资源文件
	 */
	private static Map<String, ResourceBundleModel> i18nRoot = new HashMap<String, ResourceBundleModel>();

	public static ResourceBundleModel getResourceByLan(String lan){
		rsbm=i18nRoot.get(lan);
		if (null == i18nRoot.get(lan)) {
			Locale locale = getLocale(lan);
			ResourceBundle resource_bundle = ResourceBundle.getBundle("resource", locale);
			rsbm = new ResourceBundleModel(resource_bundle, new BeansWrapper());
			i18nRoot.put(lan, rsbm);
		} 
		return rsbm;
	}

	public String get(String key) {
		try {
			return rsbm.get(key).toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return key;
	}

	public static Locale getLocale(String lan) {
		Locale locale = null;
		if (StringUtils.isBlank(lan)) {
			locale = Locale.CHINESE;
		} else if ("en".equals(lan)) {
			locale = Locale.ENGLISH;
		} else {
			locale = Locale.CHINESE;
		}
		return locale;
	}
}
