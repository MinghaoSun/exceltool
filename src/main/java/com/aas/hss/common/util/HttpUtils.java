package com.aas.hss.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName: HttpUtils
 * @Description: Java http请求类封装，当然也可以引用apache的httpclient，但是封装的目的就是减少jar包的引用
 * @author Minghao
 * @date 2014-3-5 上午9:59:52
 * @version 1.0-SNAPSHOT
 */
public final class HttpUtils {

	private static RequestConfig requestConfig;
	public static String encoding = Charset.defaultCharset().name();
	static {
		requestConfig = RequestConfig.custom().setConnectionRequestTimeout(50000).setConnectTimeout(50000).setSocketTimeout(50000).build();
	}

	public static HttpRespons doGetJson(String url, Map<String, String> params, Map<String, String> headers) throws UnsupportedEncodingException {
		return doGet(url, params, "application/json", headers);
	}

	public static HttpRespons doGetJson(String url, Map<String, String> params) throws UnsupportedEncodingException {
		return doGet(url, params, "application/json", null);
	}

	public static HttpRespons doGetJson(String url) throws UnsupportedEncodingException {
		return doGet(url, null, "application/json", null);
	}

	public static HttpRespons doGetXml(String url, Map<String, String> params, Map<String, String> headers) throws UnsupportedEncodingException {
		return doGet(url, params, "application/xml", headers);
	}

	public static HttpRespons doGetXml(String url, Map<String, String> params) throws UnsupportedEncodingException {
		return doGet(url, params, "application/xml", null);
	}

	public static HttpRespons doGetXml(String url) throws UnsupportedEncodingException {
		return doGet(url, null, "application/xml", null);
	}

	private static HttpRespons doGet(String url, Map<String, String> params, String contentType, Map<String, String> headers) throws UnsupportedEncodingException {
		StringBuffer buf = new StringBuffer(url);
		// 如果是GET请求，则请求参数在URL中
		if (params != null && !params.isEmpty()) {
			buf.append("?");
			for (String key : params.keySet()) {
				buf.append(key).append("=");
				buf.append(URLEncoder.encode(params.get(key), encoding));
				buf.append("&");
			}
		}
		HttpGet get = new HttpGet(buf.toString());
		get.setConfig(requestConfig);
		get.setHeader(HttpHeaders.ACCEPT, contentType);
		get.setHeader(HttpHeaders.CONTENT_ENCODING, encoding);
		if (headers != null && !headers.isEmpty()) {
			for (String key : headers.keySet()) {
				get.setHeader(key, headers.get(key));
			}
		}
		return doSendHttp(get);
	}

	/**
	 * @Description: 提交xml返回xml格式
	 * @param url
	 *            地址
	 * @param xmlData
	 *            数据
	 * @date 2015-7-6 上午9:39:07
	 * @return HttpRespons 返回类型
	 */
	public static HttpRespons doPostXml(String url, String xmlData) {
		HttpPost post = doPost(url, xmlData, encoding);
		post.setConfig(requestConfig);
		post.setHeader(HttpHeaders.ACCEPT, "application/xml");
		post.setHeader(HttpHeaders.CONTENT_ENCODING, encoding);
		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
		return doSendHttp(post);
	}

	public static HttpRespons doPostXml(String url, String xmlData, Map<String, String> headers) {
		HttpPost post = doPost(url, xmlData, encoding);
		post.setConfig(requestConfig);
		post.setHeader(HttpHeaders.ACCEPT, "application/xml");
		post.setHeader(HttpHeaders.CONTENT_ENCODING, encoding);
		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/xml");
		if (headers != null && !headers.isEmpty()) {
			for (String key : headers.keySet()) {
				post.setHeader(key, headers.get(key));
			}
		}
		return doSendHttp(post);
	}

	/**
	 * @Description: 提交json返回json数据格式
	 * @param url
	 *            地址
	 * @param jsonData
	 *            数据
	 * @date 2015-7-6 上午9:38:48
	 * @return HttpRespons 返回类型
	 */
	public static HttpRespons doPostJson(String url, String jsonData) {
		HttpPost post = doPost(url, jsonData, encoding);
		post.setConfig(requestConfig);
		post.setHeader(HttpHeaders.ACCEPT, "application/json");
		post.setHeader(HttpHeaders.CONTENT_ENCODING, encoding);
		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		return doSendHttp(post);
	}

	public static HttpRespons doPostJson(String url, String jsonData, Map<String, String> headers) {
		HttpPost post = doPost(url, jsonData, encoding);
		post.setConfig(requestConfig);
		post.setHeader(HttpHeaders.ACCEPT, "application/json");
		post.setHeader(HttpHeaders.CONTENT_ENCODING, encoding);
		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		if (headers != null && !headers.isEmpty()) {
			for (String key : headers.keySet()) {
				post.setHeader(key, headers.get(key));
			}
		}
		return doSendHttp(post);
	}

	private static HttpPost doPost(String url, String jsonData, String defaultCharset) {
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(jsonData.toString(), defaultCharset));
		return post;
	}

	private static HttpRespons doSendHttp(HttpUriRequest request) {
		HttpRespons httpResponser = new HttpRespons();
		CloseableHttpClient client = HttpClientBuilder.create().build();
		try {
			CloseableHttpResponse response = client.execute(request);
			httpResponser.urlString = request.getURI().getPath();
			httpResponser.defaultPort = request.getURI().getPort();
			httpResponser.host = request.getURI().getHost();
			httpResponser.path = request.getURI().getPath();
			httpResponser.port = request.getURI().getPort();
			httpResponser.query = request.getURI().getQuery();
			httpResponser.userInfo = request.getURI().getUserInfo();
			httpResponser.code = response.getStatusLine().getStatusCode();
			httpResponser.contentType = ContentType.getOrDefault(response.getEntity()).getMimeType();
			httpResponser.content = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpResponser;
	}
}
