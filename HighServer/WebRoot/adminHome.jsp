<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.newsoft.sysmanager.service.UserMgrService"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="com.newsoft.sysmanager.dao.UserMgrDAO,java.util.Map,java.util.HashMap,java.util.List,java.util.ArrayList,com.newsoft.sysmanager.vo.UserVo,com.newsoft.security.acegi.AcegiHelper,com.newsoft.common.spring.SpringBeanManager"%>
<%
	String javaVersion = System.getProperty("java.version");
	String osName = System.getProperty("os.name");
	String osA = System.getProperty("os.arch");
	String osVersion = System.getProperty("os.version");
	String javaHome = System.getProperty("java.home");  
	String userHome = System.getProperty("user.dir");
	UserMgrDAO userMgrService = (UserMgrDAO)SpringBeanManager.getBean("userMgrDAO");
	Integer userSize = userMgrService.getAllUsers().size();
	Integer orgSize = 0;
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title></title>
<link href="${ctx}/common.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.input .powered {
	font-size: 11px;
	text-align: right;
	color: #cccccc;
}
</style>
</head>
<body>
	<div class="path">
		管理中心首页
	</div>
	<table class="input">
		<tr>
			<th>
				系统名称:
			</th>
			<td>
				高速行后台管理
			</td>
			<th>
				系统版本:
			</th>
			<td>
				1.0 RELEASE 
			</td>
		</tr>
		<tr>
			<th>
				官方网站:
			</th>
			<td>
				建设中
			</td>
			<th>
				官方论坛:
			</th>
			<td>
				建设中
			</td>
		</tr>
		<tr>
			<td colspan="4">
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				JAVA版本:
			</th>
			<td>
				<%=javaVersion%>
			</td>
			<th>
				JAVA路径:
			</th>
			<td>
				<%=javaHome%>
			</td>
		</tr>
		<tr>
			<th>
				操作系统名称: 
			</th>
			<td>
				<%=osName%>
			</td>
			<th>
				操作系统构架: 
			</th>
			<td>
				<%=osA%>
			</td>
		</tr>
		<tr>
			<th>
				部署路径:
			</th>
			<td>
				<%=userHome%>
			</td>
			<th>
				操作系统版本: 
			</th>
			<td>
				<%=osVersion%>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				&nbsp;
			</td>
		</tr>
		<tr>
			<th>
				普通会员数：
			</th>
			<td>
				<%=userSize.toString()%>
			</td>
			<th>
				企业会员数：
			</th>
			<td>
				
			</td>
		</tr>
		<!-- <tr>
			<th>
				测试
			</th>
			<td>
				测试
			</td>
			<th>
				测试
			</th>
			<td>
				测试
			</td>
		</tr>
		<tr>
			<th>
				测试
			</th>
			<td>
				测试
			</td>
			<th>
				测试
			</th>
			<td>
				测试
			</td>
		</tr>
		<tr>
			<th>
				测试
			</th>
			<td>
				测试
			</td>
			<th>
				测试
			</th>
			<td>
				测试
			</td>
		</tr> -->
		<tr>
			<td class="powered" colspan="4">
				COPYRIGHT © 2013-2014 利通科技 ALL RIGHTS RESERVED.
			</td>
		</tr>
	</table>
</body>
</html>