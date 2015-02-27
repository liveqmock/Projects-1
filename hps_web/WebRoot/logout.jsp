<%@page import="java.util.Properties"%>
<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html; charset=UTF-8"%>
<%@page import="org.jasig.cas.client.validation.Assertion"%>
<%@page import="org.jasig.cas.client.authentication.AttributePrincipal"%>
<%@page import="org.jasig.cas.client.util.AbstractCasFilter"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;

	//The CAS service URL

	String casServerUrl = (String) application.getAttribute("CAS_SERVER_URL");
	if (casServerUrl == null) {
		Properties props = new Properties();
		try {
			props.load(Assertion.class.getResourceAsStream("/security/cas.properties"));
		} catch (Exception e) {
			System.out.println("Failed to read cas.properties, error: " + e.getMessage());
		}
		String configuredServerUrl = props.getProperty("server.prefix");

		System.out.println("Load configuredServerUrl: " + configuredServerUrl);
		if (configuredServerUrl != null) {
			application.setAttribute("CAS_SERVER_URL", configuredServerUrl);
			casServerUrl = configuredServerUrl;
		}
	}

	String logoutUrl = casServerUrl + "/logout?url=" + basePath;
	String sessionId = request.getSession().getId();

	String userId = request.getRemoteUser();
	if (userId == null) {
		Assertion assertion = (Assertion) (session == null ? request
				.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : session
				.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION));

		AttributePrincipal principal = assertion == null ? null : assertion.getPrincipal();
		userId = principal == null ? "" : principal.getName();
	}

	session.invalidate();
	if (casServerUrl != null) {
		System.out.println("将会话 [" + sessionId + "]中用户[" + userId + "] 的登出请求跳转到CAS服务器上进行注销，URL： " + logoutUrl);

		response.sendRedirect(logoutUrl);
	}
%>