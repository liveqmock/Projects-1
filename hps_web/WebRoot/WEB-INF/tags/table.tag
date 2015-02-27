<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="title" type="java.lang.String" required="false"%>
<%@ attribute name="paddingLeft" type="java.lang.String"
	required="false"%>
<%@ attribute name="paddingRight" type="java.lang.String"
	required="false"%>
<%@ attribute name="height" type="java.lang.String" required="false"%>
<%@ attribute name="maxheight" type="java.lang.String" required="false"%>
<%@ attribute name="style" type="java.lang.String" required="false"%>
<c:if test="${menu eq 'true'}">
	<script type="text/javascript">
		$(function(){
			$("#${id}").find("thead:first tr").contextMenu($.getContextMenuData("list"),{theme:"vista"}); 
		});
	</script>
</c:if>
<c:if test="${!empty height}">
	<c:set var="height" value="height:${height}px;" />
</c:if>
<c:if test="${!empty maxheight}">
	<c:set var="maxheight" value="max-height:${maxheight}px;" />
	<c:set var="height" value="${height}${maxheight}" />
</c:if>
<c:if test="${!empty paddingLeft}">
	<c:set var="paddingLeft" value="margin-left:${paddingLeft}px;" />
</c:if>
<c:if test="${!empty paddingRight}">
	<c:set var="paddingRight" value="margin-right:${paddingRight}px;" />
</c:if>
<script type="text/javascript">
		$(function(){
			var height = document.documentElement.clientHeight;
			$("#${id} .ui-column").each(function(index){
					if($(this).attr("fixed") == "true"){
						$(this).css("width","100%"); 
						$(this).parent().css("width","100%"); 
						/**var co=$(this).find(".ui-inputBody:first");
						co.css({
							"overflow-y":"auto"
							,"position":"relative"
							,"margin":"0 2px 0 0"
						}).height(height - 50);
						if($.browser.msie && $.browser.version=="7.0"){
							co.css({"width":"100%"});
						}
						$(window).resize(function(){
							co.height(document.documentElement.clientHeight - 45);
						});
						$(window).resize();**/
					} 
					if(width[index].indexOf("%")>-1){
						$(this).parent().attr("width",width[index]);
					}else{
						$(this).css("width",width[index]+"px");
					}
			}); 
		});
	</script>
<div id="${id}" class="ui-container"
	style="${paddingLeft}${paddingRight}${height}${style}">
<table width="100%" cellspacing="0">
<tr>
<jsp:doBody></jsp:doBody>
</tr>
</table>
</div>