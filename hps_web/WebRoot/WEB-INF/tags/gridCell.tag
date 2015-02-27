<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="title" type="java.lang.String" required="true"%>
<%@ attribute name="align" type="java.lang.String" required="false"%>
<%@ attribute name="sortable" type="Boolean" required="false"%>
<%@ attribute name="hidden" type="Boolean" required="false"%>
<%@ attribute name="width" type="java.lang.String" required="false"%>
<%@ attribute name="formatter" type="java.lang.String" required="false"%>

<c:if test="${empty sortable}">
	<c:set var="sortable" value="false"></c:set>
</c:if> 
{
	"name":"${name}",
	"index":"${name}",
	"sortable":${sortable},
	<c:if test="${!empty hidden}">"hidden":${hidden},</c:if>
	<c:if test="${!empty align}">"align":"${align}",</c:if>	
	<c:if test="${!empty width}">"width":${width},</c:if>
	<c:if test="${!empty formatter}">"formatter":${formatter},</c:if> 
	<c:if test="${!empty datefmt}">"datefmt":${datefmt},</c:if> 
	"label":"${title}"
}, 