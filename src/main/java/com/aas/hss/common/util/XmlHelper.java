package com.aas.hss.common.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: XmlHelper
 * @Description: Xml序列化和反序列化
 * @author Minghao
 * @date 2014-4-28 上午8:17:53
 * @version 1.0-SNAPSHOT
 */
public class XmlHelper {
	
	private static Logger logger=LoggerFactory.getLogger(XmlHelper.class);
	
	/** 
	* @Description: 实体类转化Xml方法
	* @param obj 实体类
	* @date 2014-4-28 上午8:22:07
	* @return String    返回类型  
	*/ 
	public static String doObjectToStrXml(Object obj) {
		String strXml = "";
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			//marshaller.setProperty(Marshaller.JAXB_ENCODING, "GB2312"); //欧洲
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(obj, os);
			strXml = new String(os.toByteArray());
		} catch (Exception e) {
			// TODO 实体类转换为XML字符串
			e.printStackTrace();
		}
		return strXml;
	}

	/** 
	* @param <T>
	 * @Description: Xml转化实体类方法 
	* @param strXml xml字符串
	* @param classType 类型
	 * @return 
	* @date 2014-4-28 上午8:22:27
	* @return Object    返回类型  
	*/ 
	@SuppressWarnings("unchecked")
	public static <T>  T doStrXmlToObject(String strXml, Class<T> classType) {
		try {
			JAXBContext context = JAXBContext.newInstance(classType);
			Unmarshaller marshaller = context.createUnmarshaller();
			logger.error(strXml);
			T  xmlBean = (T) marshaller.unmarshal(new StringReader(strXml));
			return xmlBean;
		} catch (Exception e) {
			// TODO 字符串转换为实体类
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		return null;
	}
}
