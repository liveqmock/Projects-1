<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ attribute name="title" type="java.lang.String" required="true"%>
<%@ attribute name="id" type="java.lang.String" required="false"%>
<%@ attribute name="name" type="java.lang.String" required="false"%>
<%@ attribute name="type" type="java.lang.String" required="false"%>
<%@ attribute name="onclick" type="java.lang.String" required="false"%>
<%@ attribute name="size" type="java.lang.String" required="false"%>
<%@ attribute name="style" type="java.lang.String" required="false"%>
<%--设置图标的样式类--%>
<%@ attribute name="icon" type="java.lang.String" required="false"%>
<%@ attribute name="iconPath" type="java.lang.String" required="false"%>

<%
	if (icon == null || icon.trim().equals("")) {
		String t = title.replaceAll("[\\s　]", "");
		if (t.startsWith("新建") || t.startsWith("创建")) {
			icon = "ui-icon-folder-open";
		} else if (t.startsWith("新增") || t.startsWith("添加")
				|| t.startsWith("增加")) {
			icon = "ui-icon-plus";
		} else if (t.startsWith("查询") || t.startsWith("查找")) {
			icon = "ui-icon-search";
		} else if (t.startsWith("复制")) {
			icon = "ui-icon-copy";
		} else if (t.startsWith("删除")) {
			icon = "ui-icon-trash";
		} else if (t.startsWith("修改") || t.startsWith("更改")) {
			icon = "ui-icon-wrench";
		} else if (t.startsWith("编辑")) {
			icon = "ui-icon-pencil";
		} else if (t.startsWith("预览") || t.startsWith("查看")
				|| t.startsWith("详细")) {
			icon = "ui-icon-info";
		} else if (t.startsWith("帮助")) {
			icon = "ui-icon-help";
		} else if (t.startsWith("重置") || t.startsWith("复位")
				|| t.startsWith("清空")) {
			icon = "ui-icon-arrow-2-e-w";
		} else if (t.startsWith("刷新")) {
			icon = "ui-icon-refresh";
		} else if (t.startsWith("关闭") || t.startsWith("取消")) {
			icon = "ui-icon-close";
		} else if (t.startsWith("上一")) {
			icon = "ui-icon-triangle-1-w";
		} else if (t.startsWith("下一")) {
			icon = "ui-icon-triangle-1-e";
		} else if (t.startsWith("返回")) {
			icon = "ui-icon-arrowreturnthick-1-w";
		} else {
			icon = "ui-icon-check";
		}
	}
%>
<c:choose>
	<c:when test="${empty size}">
		<c:if test="${fn:length(title)<4}">
			<c:set var="size" value="x6"></c:set>
		</c:if>
		<c:if test="${fn:length(title)>=4}">
			<c:set var="size" value="x11"></c:set>
		</c:if>
		<c:if test="${fn:length(title)>=8}">
			<c:set var="size" value="x15"></c:set>
		</c:if>
	</c:when>
</c:choose> 
<c:choose>
	<c:when test="${!empty type}">
		<c:set var="type" value="type='${type}'"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="type" value="type='button'"></c:set>
	</c:otherwise>
</c:choose>
<button class="ui-state-default ui-corner-all ${size}"
	${type} id="${id}" name="${name}" onclick="${onclick}"
	onmouseover="$(this).addClass('ui-state-hover')"
	onmouseout="$(this).removeClass('ui-state-hover')" style="${style}" value="${title}">
	
<c:choose>
	<c:when test="${not empty iconPath}">
		<span class="ui-icon" style="background-image: url(${iconPath});"></span>
	</c:when>
	<c:otherwise>
		<span class="ui-icon <%=icon%>"></span>
	</c:otherwise>
</c:choose>

<span class="ui-inputHead_t">
${title}
</span>
</button>