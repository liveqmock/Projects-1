<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="title" type="java.lang.String" required="true"%>
<%@ attribute name="type" type="java.lang.String" required="false"%>
<%@ attribute name="plugin" type="java.lang.String" required="false"%>
<%@tag
	import="java.util.Map,java.util.HashMap,java.util.List,java.util.ArrayList,com.newsoft.sysmanager.vo.UserVo,com.newsoft.security.acegi.AcegiHelper,com.newsoft.common.spring.SpringBeanManager"%>
<%
	UserVo user = ((AcegiHelper) SpringBeanManager.getBean("acegiHelper")).getSessionUser();
	System.err.println(user);
%>
<head>
<title>
<%=user.getUserName()%>
</title>
<link rel="icon" href="${ctx}/favicon.ico"  type="image/x-icon"/>
<base href="${servurl}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<c:import url="${servurl}/frame/common/includeAll.jsp"></c:import>
<script type="text/javascript">
	window.WWWROOT = "${ctx}";
	window.BASEPATH = "${servurl}";
	//当前登录用户和用户默认公司Id和名称 
	var sessionUserId='<%=user.getUserId()%>';
	var sessionUserAccount='<%=user.getAccount()%>';
	var sessionUserName='<%=user.getUserName()%>';
	var defaultOrgId = '';
	var defaultOrgName = '';
	var defaultOrgLevelCode = '';
	<c:set var="sessionUserId" value="<%=user.getUserId()%>" scope="session"></c:set>
	<c:set var="sessionUserAccount" value="<%=user.getAccount()%>" scope="session"></c:set>
	<c:set var="sessionUserName" value="<%=user.getUserName()%>" scope="session"></c:set>
</script>
<script for="window" event="onunload">
</script>
<c:if test="${!empty plugin}">
	<c:if test="${fn:indexOf(plugin, 'menu') ne -1}">
		<%-- 菜单插件 --%>
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/jquery.mb.menu.2.8.5/mbMenu.js"></script>
	</c:if>
	<c:if test="${fn:indexOf(plugin, 'tree') ne -1}">
		<%---tree--%>
		<link rel="stylesheet" type="text/css" media="all"
			href="${ctx}/frame/js/plugin/dynatree-1.2.0/src/skin/ui.dynatree.css" />
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/dynatree-1.2.0/src/jquery.dynatree.js"></script>
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/dynatree-1.2.0/src/jquery.cookie.js"></script>
	</c:if>
	<c:if test="${fn:indexOf(plugin, 'grid') ne -1}">
		<%-- 引入jqgrid库 --%>
		<link rel="stylesheet" type="text/css"
			href="${ctx}/frame/js/plugin/jquery.jqGrid-4.1.2/css/ui.jqgrid.css" />
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/jquery.jqGrid-4.1.2/js/i18n/grid.locale-cn.js"></script>
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/jquery.jqGrid-4.1.2/js/jquery.jqGrid.src.js"></script>
		<%-- 引入jqgrid库 
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/jqGrid-v4.3.1/jquery.jqGrid.js"></script>	--%>
	</c:if>
	<c:if test="${fn:indexOf(plugin, 'validate') ne -1}">
		<%--- 引入validate库----%>
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/jquery.validate/jquery.validate.js"></script>
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/jquery.validate/additional-methods.js"></script>
		<%--<script type="text/javascript"
			src="${ctx}/frame/js/plugin/jquery.validate/messages_cn.js"></script>--%>
	</c:if>
	<c:if test="${fn:indexOf(plugin, 'layout') ne -1}">
		<%---<link rel="stylesheet" type="text/css" media="all"
			href="${ctx}/frame/js/plugin/jquery.layout/layout-default.css" />
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/jquery.layout/jquery.layout.js"></script>----%>
	</c:if>
	<c:if test="${fn:indexOf(plugin, 'newlayout') ne -1}">
		<link rel="stylesheet" type="text/css" media="all"
			href="${ctx}/frame/js/plugin/jquery.layout/layout-default.css" />
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/jquery.layout/jquery.layout-1.4.0.js"></script>
	</c:if>
	<c:if test="${fn:indexOf(plugin, 'upload') ne -1}">
		<%--- 引入附件库----%>
		<link href="${ctx}/frame/js/plugin/uploadify-v3.1/uploadify.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/uploadify-v3.1/jquery.uploadify-3.1.js"></script>
	</c:if>
</c:if>
<jsp:doBody></jsp:doBody><%-- 执行标签的body体的内容 --%>
</head>