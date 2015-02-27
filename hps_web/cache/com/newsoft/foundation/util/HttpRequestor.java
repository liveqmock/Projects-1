package com.newsoft.foundation.util;

import java.io.File;

/**
 * Define a HTTP request client operations.
 * 
 * @author George Guo
 * 
 */
public interface HttpRequestor {

	/**
	 * send a post request
	 * 
	 * @param requestText
	 * @param remoteUrl
	 * @return the HTTP response content string
	 */
	String sendXmlPostRequest(String remoteUrl, String requestText);

	/**
	 * send a get request
	 * 
	 * @param request
	 * @return the HTTP response content string
	 */
	String sendGetRequest(String remoteUrl);

	/**
	 * send a post request
	 * 
	 * @param requestText
	 * @param remoteUrl
	 * @return the HTTP response content string
	 */
	String sendJsonPostRequest(String remoteUrl, String jsonText);

	/**
	 * Get the original input byte data
	 * 
	 * @param remoteUrl
	 * @return
	 */
	File getBinaryFromUrl(String remoteUrl);
}
