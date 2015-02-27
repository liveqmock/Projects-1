<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@tag import="java.util.*,net.sf.json.JSONArray,net.sf.json.JSONObject"%> 

<%@ attribute name="id" type="java.lang.String" required="true"%><%--菜单id--%>
<%@ attribute name="title" type="java.lang.String" required="false"%><%-- 菜单名称 --%>
<%@ attribute name="iconpath" type="java.lang.String" required="false"%><%-- 图标父路径 --%>
<%@ attribute name="menuwidth" type="Integer" required="false"%><%-- 菜单的最小宽度 --%>
<%@ attribute name="fadeInTime" type="Integer" required="false"%><%-- 显示的淡入时间，单位为毫秒 --%>
<%@ attribute name="fadeOutTime" type="Integer" required="false"%><%-- 显示的淡出时间，单位为毫秒 --%>
<%@ attribute name="openOnClick" type="Boolean" required="false"%><%-- 是否点击时弹出菜单 --%>
<%@ attribute name="closeOnMouseOut" type="Boolean" required="false"%><%-- 是否鼠标移开后关闭菜单 --%>
<%@ attribute name="closeAfter" type="Integer" required="false"%><%-- 移开后菜单关闭的时间 --%>
<%@ attribute name="menuVertical" type="Boolean" required="false"%><%-- 菜单是横向还是纵向 --%>
<%@ attribute name="menuContext" type="Boolean" required="false"%><%-- 菜单是是否是上下文菜单 --%>


<c:if test="${empty menuVertical}">
	<c:set var="menuVertical" value="false" />
</c:if>
<c:if test="${empty menuVertical}">
	<c:set var="menuVertical" value="false" />
</c:if>

<c:choose>
	<c:when test="${menuVertical==true}">
	
	<c:choose>
		<c:when test="${menuContext==true}">
			<tr><td class="rootVoice cmVoice {cMenu:'${id}'}"> ${title}</td><tr>
		</c:when>
		<c:otherwise>
			<tr><td class="rootVoice {menu: '${id}'}">${title}</td><tr>
		</c:otherwise>
	</c:choose>
		
	</c:when>
	<c:otherwise>
	
		<c:choose>
			<c:when test="${menuContext==true}">
				<td class="rootVoice cmVoice {cMenu:'${id}'}"> ${title}</td>
			</c:when>
			<c:otherwise>
				<td class="rootVoice {menu: '${id}'}">${title}</td>
			</c:otherwise>
		</c:choose>
		
	</c:otherwise>
</c:choose>

<script type="text/javascript">
	//新建包装menu item的dom元素
	//$("body").append("<div id='${id}' class='mbmenu'></div>");
</script>
<div id='${id}' class='mbmenu'>
<jsp:doBody></jsp:doBody>
</div>