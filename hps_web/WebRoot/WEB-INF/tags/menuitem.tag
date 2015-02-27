<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.newsoft.com/tags-operator" prefix="operator"%>

<%@ attribute name="id" type="java.lang.String" required="true"%><%--菜单id--%>
<%@ attribute name="title" type="java.lang.String" required="false"%><%-- 菜单名称 --%>
<%@ attribute name="action" type="java.lang.String" required="false"%><%-- 菜单的响应方法 --%>
<%@ attribute name="icon" type="java.lang.String" required="false"%><%-- 菜单的图标 --%>
<%@ attribute name="url" type="java.lang.String" required="false"%><%-- 菜单的图标 --%>
<%@ attribute name="classstr" type="java.lang.String" required="false"%><%-- 菜单的图标 --%>
<%@ attribute name="seperator" type="Boolean" required="false"%><%-- 菜单项是否为分割标识 --%>
<%@ attribute name="operator" type="java.lang.String" required="false"%><%-- 菜单的授权 --%>


<c:if test="${empty seperator}">
	<c:set var="seperator" value="false" />
</c:if>
<c:if test="${empty url}">
	<c:set var="url" value="javascript:void(0);" />
</c:if>

<c:choose>
	<c:when test="${seperator==true}">
		<a rel="separator">
		</a>
	</c:when>
	<c:otherwise>
		<c:if test="${empty operator}">
			<script type="text/javascript">
		//添加菜单项到外层的div中
		$(function(){
				var $link = $("<a></a>");
		$link.attr("class","{action:\"${action}\",img: \"${icon}\"}");
		$link.attr("img","${icon}");
		$link.text("${title}");
		$("#${id}").append($link);
		});

		//$("#${id}").append("<a class=\"{action:'${action}',img: '${icon}'}\">${title}</a>");
</script>
			<!--  <a id="${id}" href="${url}" onclick="${action}" class="{img: '${icon}'}">${title}</a>-->
		</c:if>
		<c:if test="${!empty operator}">
			<operator:authorize ifAnyGranted="${operator}">.
<script type="text/javascript">
		//添加菜单项到外层的div中
		$(function(){
				var $link = $("<a></a>");
		$link.attr("class","{action:\"${action}\",img: \"${icon}\"}");
		$link.attr("img","${icon}");
		$link.text("${title}");
		$("#${id}").append($link);
		});
		//$("#${id}").append("<a class=\"{action:'${action}',img: '${icon}'}\">${title}</a>");
</script>
				<!-- <a id="${id}" href="${url}" onclick="${action}" class="{img: '${icon}'}">${title}</a> -->
			</operator:authorize>
		</c:if>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
		//添加菜单项到外层的div中
		//$("#${id}").append("<a class=\"{action:'${action}',img: '${icon}'}\">${title}</a>");
</script>


