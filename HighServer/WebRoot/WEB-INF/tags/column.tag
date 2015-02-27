<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="id" type="java.lang.String" required="false"%>
<%@ attribute name="fixed" type="java.lang.String" required="false"%>
<%@ attribute name="height" type="java.lang.String" required="false"%>
<c:if test="${!empty height}">
	<c:set var="height" value="height:${height}px;overflow:auto;" />
</c:if> 
<td valign="top" noshot="true" style="vertical-align:top;">
	<div <c:if test="${!empty id}">id="${id}"</c:if> class="ui-column"
		fixed="${fixed}" style="${height}">
		<jsp:doBody />
		<div class="c"></div>
	</div>
</td>