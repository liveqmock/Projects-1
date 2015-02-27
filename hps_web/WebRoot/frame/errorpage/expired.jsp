<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<title>当前会话已经失效，请重新登录。</title>
		<meta http-equiv="pragma" content="cache" />
		<meta http-equiv="cache-control" content="cache" />
		<meta http-equiv="expires" content="7200000" />
	</head>
	<body>
		<div>
			当前会话已经失效，请重新登录。
			<a href="javascript:window.close();">关闭当前页面</a>&nbsp;&nbsp;
			<a href="javascript:window.location.href = '${ctx}/login.jsp';">回登录页</a>
		</div>
	</body>
</html>
