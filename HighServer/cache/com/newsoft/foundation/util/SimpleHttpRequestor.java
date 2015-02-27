package com.newsoft.foundation.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A simple HTTP request client implementation which use native HTTPConnection
 * API.
 * 
 * @author George Guo
 * 
 */
public class SimpleHttpRequestor implements HttpRequestor {
	private static Log logger = LogFactory.getLog(SimpleHttpRequestor.class);

	private String encoding = "utf-8";

	private final String HTTP_METHOD_POST = "POST";

	private final String HTTP_METHOD_GET = "GET";

	private final String DEAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

	private final String CONTENT_TYPE_XML = "text/xml";

	private final String CONTENT_TYPE_JSON = "application/json";

	@Override
	public String sendXmlPostRequest(String remoteUrl, String requestText) {

		return sendHttpRequest(remoteUrl, HTTP_METHOD_POST, CONTENT_TYPE_XML, requestText);
	}

	@Override
	public String sendGetRequest(String remoteUrl) {
		return sendHttpRequest(remoteUrl, HTTP_METHOD_GET, DEAULT_CONTENT_TYPE, null);
	}

	@Override
	public String sendJsonPostRequest(String remoteUrl, String jsonText) {
		return sendHttpRequest(remoteUrl, HTTP_METHOD_POST, CONTENT_TYPE_JSON, jsonText);
	}

	private String sendHttpRequest(String remoteUrl, String method, String contentType, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug("Try to send request to url: " + remoteUrl + " , method: " + method);
		}

		URLConnection conn = null;
		try {
			URL url = new URL(remoteUrl);
			conn = url.openConnection();

			if (conn instanceof HttpURLConnection) {
				HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
				httpUrlConnection.setRequestMethod(method);

				if (method != null && method.equals(HTTP_METHOD_POST)) {

					httpUrlConnection.setDoOutput(true);
					httpUrlConnection.setUseCaches(false);
					httpUrlConnection.setInstanceFollowRedirects(false);
					httpUrlConnection.setRequestProperty("Content-Type", contentType + ";charset=" + this.encoding);

					OutputStream out = httpUrlConnection.getOutputStream();

					if (logger.isDebugEnabled()) {
						logger.debug("To post message: " + message);
					}

					byte[] data = message.getBytes(encoding);
					out.write(data);

					// flush and close
					out.flush();
					out.close();
				}
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));

			String line = null;
			final StringBuilder stringBuffer = new StringBuilder(255);

			while ((line = in.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			String responseMessage = stringBuffer.toString();

			if (logger.isDebugEnabled()) {
				logger.debug("Succeed to get response message: " + responseMessage);
			}

			return responseMessage;
		} catch (final Exception e) {
			logger.error("Error when send HTTP request to remote server", e);
		} finally {
			if (conn != null && conn instanceof HttpURLConnection) {
				((HttpURLConnection) conn).disconnect();

				if (logger.isDebugEnabled()) {
					logger.debug("Closed the HTTP connection");
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Didn't get normal response from the url: " + remoteUrl);
		}
		return null;
	}

	/**
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public File getBinaryFromUrl(String remoteUrl) {
		if (logger.isDebugEnabled()) {
			logger.debug("Try to get data from url: " + remoteUrl);
		}

		URLConnection conn = null;
		try {
			URL url = new URL(remoteUrl);
			conn = url.openConnection();

			if (conn instanceof HttpURLConnection) {
				HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
				httpUrlConnection.setRequestMethod("GET");
			}
			
			InputStream inputStream = conn.getInputStream();
			if (inputStream.available() <= 0) {
				return null;
			}

			File tempFile = createTempFile(remoteUrl);
			if (logger.isDebugEnabled()) {
				logger.debug("To persist the binary data to file: " + tempFile.getAbsolutePath());
			}

			OutputStream outputStream = new FileOutputStream(tempFile);
			IOUtils.copy(inputStream, outputStream);

			return tempFile;
		} catch (final Exception e) {
			logger.error("Error when send HTTP request to remote server", e);
		} finally {
			if (conn != null && conn instanceof HttpURLConnection) {
				((HttpURLConnection) conn).disconnect();

				if (logger.isDebugEnabled()) {
					logger.debug("Closed the HTTP connection");
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Didn't get normal response from the url: " + remoteUrl);
		}
		return null;
	}

	public File createTempFile(String url) throws Exception {
		String suffix = ".txt";
		if (url.indexOf("/") > -1) {
			url = url.substring(url.lastIndexOf("/") + 1);
			if (url.indexOf(".") > -1) {
				suffix = url.substring(url.lastIndexOf("."));
			}
		}
		return File.createTempFile("temp_", suffix);
	}
}
