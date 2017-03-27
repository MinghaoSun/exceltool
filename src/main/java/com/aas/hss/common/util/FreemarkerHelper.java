package com.aas.hss.common.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @ClassName: FreemarkerHelper
 * @Description: TODO(基于Freemarker模板技术的模板服务)
 * @author Minghao
 * @date 2015-10-14 上午9:35:07
 * @version 1.0-SNAPSHOT
 */
public class FreemarkerHelper {
	/**
	 * 邮件模板的存放位置
	 */
	private static final String TEMPLATE_PATH = "/email/";
	/**
	 * 启动模板缓存
	 */
	private static final Map<String, Template> TEMPLATE_CACHE = new HashMap<String, Template>();
	/**
	 * 模板文件后缀
	 */
	private static final String SUFFIX = ".ftl";
	/**
	 * 模板引擎配置
	 */
	private static Configuration configuration = null;

	static {
		configuration = new Configuration();
		configuration.setTemplateLoader(new ClassTemplateLoader(FreemarkerHelper.class, TEMPLATE_PATH));
		configuration.setEncoding(Locale.getDefault(), "UTF-8");
		configuration.setDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	/** 
	* @Description: TODO(获得模版的文本信息) 
	* @param templateId 模版id
	* @param parameters 参数
	* @date 2015-10-14 上午10:13:01
	* @return String    返回类型  
	*/ 
	public static String getTemplateText(String templateId, Map<Object, Object> parameters) {
		String templateFile = templateId + SUFFIX;
		try {
			Template template = TEMPLATE_CACHE.get(templateFile);
			if (template == null) {
				template = configuration.getTemplate(templateFile);
				TEMPLATE_CACHE.put(templateFile, template);
			}
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, parameters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static ByteArrayOutputStream getByteArrayOutputStream(String templateId, Map<Object, Object> parameters) {
		String templateFile = templateId + SUFFIX;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedWriter bw = null;
		try {
			Template template = TEMPLATE_CACHE.get(templateFile);
			if (template == null) {
				template = configuration.getTemplate(templateFile);
				TEMPLATE_CACHE.put(templateFile, template);
			}
			bw = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
			template.process(parameters, bw);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return out;
	}
}
