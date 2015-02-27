<%@ tag pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="title" type="java.lang.String" required="true"  %>
<%@ attribute name="target" type="java.lang.String" required="false"  %>
<%@ attribute name="require" type="java.lang.String" required="false"  %>
<%@ attribute name="width" type="java.lang.String" required="false"  %>
<%@ attribute name="size" type="java.lang.String" required="false"  %>
<c:choose>
	<c:when test="${empty size}">
		<c:set var="size" value="x1"></c:set>
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${require eq 'true'}">
		<c:set var="require" value="<div class='ui-state-error-text l'><span class='requiredStar' style='color:red;font-size:20px;margin:5px;'>*</span></div>"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="require" value=""></c:set>
	</c:otherwise>
</c:choose>
<div class="ui-inputIn ${size}" style="padding:5px 0 8px;">
	<label class="l" for="${target}">${title}</label>
	<div class="ui-inputInWidth">
		<jsp:doBody />
	</div>${require}
	<div class="c"></div>
</div>