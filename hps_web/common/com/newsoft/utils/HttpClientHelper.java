package com.newsoft.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.parser.ParseException;

/**
 * The HTTP client tool via Apache HttpClient.
 * 
 * @author guohb
 * 
 */
public class HttpClientHelper {
	private static String DEFAULT_ENCODE = "UTF-8";

	private static Log logger = LogFactory.getLog(HttpClientHelper.class);

	public static String getContentFromUrl(String url,Map<String, String> paramMap) {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GB2312");
		method.addRequestHeader("Content-Type","text/html;charset=GB2312");
		method.setRequestHeader("Content-Type", "text/html;charset=GB2312");
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		//mengxw add parameter
		if (paramMap!=null) {
			for (Entry<String,String> entry : paramMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				method.getParams().setParameter(key, value);
			}
		}

		String responseText = "";
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			}
			byte[] responseBody = method.getResponseBody();

			responseText = new String(responseBody, DEFAULT_ENCODE);
		} catch (HttpException e) {
			logger.error("Fatal protocol violation: " + e.getMessage(), e);
		} catch (IOException e) {
			logger.error("Fatal transport error: " + e.getMessage(), e);
		} finally {
			method.releaseConnection();
		}

		if (logger.isInfoEnabled()) {
			logger.info("Get response: " + responseText);
		}
		return responseText;
	}

	public static String getContentFromUrl4Post(String url,Map<String, String> paramMap) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GB2312");
		//method.addRequestHeader("Content-Type","text/html;charset=GB2312");
		//method.setRequestHeader("Content-Type", "text/html;charset=GB2312");
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		//mengxw add parameter
		List<NameValuePair> pairlist = new ArrayList<NameValuePair>();
		if (paramMap!=null) {
			for (Entry<String,String> entry : paramMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				NameValuePair nameValuePair = new NameValuePair(key, value);
				pairlist.add(nameValuePair);
			}
			method.setRequestBody(pairlist.toArray(new NameValuePair[0]));
		}
		String responseText = "";
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			}
			byte[] responseBody = method.getResponseBody();

			responseText = new String(responseBody, "GB2312");
		} catch (HttpException e) {
			logger.error("Fatal protocol violation: " + e.getMessage(), e);
		} catch (IOException e) {
			logger.error("Fatal transport error: " + e.getMessage(), e);
		} finally {
			method.releaseConnection();
		}

		if (logger.isInfoEnabled()) {
			logger.info("Get response: " + responseText);
		}
		return responseText;
	}
	
	public static void main(String[] args) throws ParseException {
		
	}
}
