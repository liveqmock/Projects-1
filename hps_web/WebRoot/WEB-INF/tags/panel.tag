<%@ tag pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="title" type="java.lang.String" required="false"  %>
<%@ attribute name="id" type="java.lang.String" required="false"  %>
<%@ attribute name="isFold" type="java.lang.String" required="false"  %>
<%@ attribute name="isToggle" type="java.lang.String" required="false"  %>
<%@ attribute name="fullscreen" type="java.lang.String" required="false"  %>
<%@ attribute name="offset" type="java.lang.String" required="false"  %>
<%@ attribute name="paddingLeft" type="java.lang.String" required="false"  %>
<%@ attribute name="paddingRight" type="java.lang.String" required="false"  %>
<%@ attribute name="style" type="java.lang.String" required="false"  %>
<%@ attribute name="height" type="java.lang.String" required="false"  %>

<c:if test="${!empty height}">
	<c:set var="height" value="overflow:auto;height:${height}px;" />
</c:if>
<c:if test="${fullscreen eq 'true'}">
	<c:set var="fullscreen" value="fullscreen" />
</c:if>
<c:if test="${empty offset}">
	<c:set var="offset" value="0" />
</c:if>
<c:if test="${!empty title}">
	<c:set var="title">
		<div class="ui-inputHead">
			<span class="ui-icon ui-icon-triangle-1-s"></span>
			<b><span class="ui-inputHead_t">${title}</span></b>
		</div>
	</c:set>
	<c:if test="${empty isToggle}"><c:set var="isToggle" value="true" /></c:if>
	<c:if test="${empty isFold}"><c:set var="isFold" value="false" /></c:if>
	<c:if test="${!empty id and isToggle eq 'true'}">
		<script type="text/javascript">
			$(function(){
				$("#${id}").parent().find(".ui-inputHead:first")
				.css("cursor","pointer")
				.toggle(function(){
					$(this).find("span:first").removeClass("ui-icon-triangle-1-s").addClass("ui-icon-triangle-1-e");
					$(this).nextAll().hide("fast");
				},function(){
					$(this).find("span:first").removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s");
					$(this).nextAll().show("fast");
				});
				<c:if test="${isFold eq 'true'}">$("#${id}").parent().find(".ui-inputHead:first").click();</c:if>
			});
		</script>
	</c:if>
</c:if>
<c:if test="${!empty paddingLeft}">
	<c:set var="paddingLeft" value="padding-left:${paddingLeft}px;"/>
</c:if>
<c:if test="${!empty paddingRight}">
	<c:set var="paddingRight" value="padding-right:${paddingRight}px;"/>
</c:if>
<div class="ui-field" style="${paddingLeft}${paddingRight}${style}">
	<div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-all ui-accordion-content-active">
		<div id="${id}" class="ui-inputBody ${fullscreen}" offset="${offset}" style="${height}">
			${title}
			<jsp:doBody />
			<div class="c"></div>
		</div>
		<div class="c"></div>
	</div>
</div>