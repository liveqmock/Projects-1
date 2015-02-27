<%@ tag pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="type" type="java.lang.String" required="true"  %>
<%@ attribute name="align" type="java.lang.String" required="false"  %>
<%@ attribute name="id" type="java.lang.String" required="false"  %>
<%@ attribute name="display" type="java.lang.String" required="false"  %>
<%@ attribute name="paddingLeft" type="java.lang.String" required="false"  %>
<%@ attribute name="paddingRight" type="java.lang.String" required="false"  %>
<%@ attribute name="style" type="java.lang.String" required="false"  %>
<c:choose>
	<c:when test="${type eq 'input'}">
		<c:set var="class" value="ui-inputCon"></c:set>
	</c:when>
	<c:when test="${type eq 'button'}">
		<c:set var="class" value="ui-inputBtn"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="class" value="ui-inputCon"></c:set>
	</c:otherwise>
</c:choose>
<c:if test="${!empty display}">
	<c:set var="display" value="display:${display};"/>
</c:if>
<c:if test="${!empty paddingLeft}">
	<c:set var="paddingLeft" value="padding-left:${paddingLeft}px;"/>
</c:if>
<c:if test="${!empty paddingRight}">
	<c:set var="paddingRight" value="padding-right:${paddingRight}px;"/>
</c:if>
<c:if test="${!empty id}">
	<c:set var="id" value="id='${id}'"/>
</c:if>
<div style="${paddingLeft}${paddingRight}${display}${style}" class="${class}" align="${align}" ${id}>
	<jsp:doBody></jsp:doBody>
	<div class="c"></div>
</div>