<%@ tag pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tag import="net.sf.json.JSONArray,net.sf.json.JSONObject"%>
<%@ attribute name="id" type="java.lang.String" required="true" %>
<%@ attribute name="title" type="java.lang.String" required="true" %>
<%@ attribute name="height" type="java.lang.String" required="false" %>
<%@ attribute name="width" type="java.lang.String" required="false" %>
<%@ attribute name="minHeight" type="java.lang.String" required="false" %>
<%@ attribute name="minWidth" type="java.lang.String" required="false" %>
<%@ attribute name="maxHeight" type="java.lang.String" required="false" %>
<%@ attribute name="maxWidth" type="java.lang.String" required="false" %>
<%@ attribute name="autoOpen" type="java.lang.Boolean" required="false" %>
<%@ attribute name="resizable" type="java.lang.Boolean" required="false" %>
<%@ attribute name="modal" type="java.lang.Boolean" required="false" %>
<%@ attribute name="zIndex" type="java.lang.String" required="false" %>
<%@ attribute name="draggable" type="java.lang.Boolean" required="false" %>
<%@ attribute name="hideAllButtons" type="java.lang.Boolean" required="false" %>
<%@ attribute name="dialogClass" type="java.lang.String" required="false" %>

<%@ attribute name="closeText" type="java.lang.String" required="false" %>
<%@ attribute name="saveText" type="java.lang.String" required="false" %>
<%@ attribute name="saveClick" type="java.lang.String" required="false" %>
<%@ attribute name="otherButtons" type="java.lang.String" required="false" %>

<%@ attribute name="appendTo" type="java.lang.String" required="false" %><!-- 对话框添加的位置，传递DOM的ID -->

<c:if test="${empty modal}">
   <c:set var="modal">${true}</c:set>
</c:if>
<c:if test="${empty autoOpen}">
   <c:set var="autoOpen">${false}</c:set>
</c:if>
<c:if test="${empty resizable}">
   <c:set var="resizable">${false}</c:set>
</c:if>
<c:if test="${empty saveText}">
   <c:set var="saveText">"保存"</c:set>
</c:if>
<c:if test="${empty closeText}">
   <c:set var="closeText">"关闭"</c:set>
</c:if>
<script type="text/javascript">
$(function(){
	$("#${id}").dialog({
				title : "${title}",
				autoOpen : ${autoOpen},
				<c:if test="${!empty appendTo}"> appendTo : $("#" + "${appendTo}").get(0),</c:if>
				<c:if test="${empty appendTo}"> appendTo : document.body,</c:if>
				modal:${modal},
				resizable:${resizable},
				<c:if test="${!empty height}">height:${height},</c:if>
				<c:if test="${!empty width}">width:${width},</c:if>
				<c:if test="${!empty minHeight}">minHeight : ${minHeight},</c:if>
				<c:if test="${!empty minWidth}">minWidth:${minWidth},</c:if>
				<c:if test="${!empty maxHeight}">maxHeight:${maxHeight},</c:if>
				<c:if test="${!empty maxWidth}">maxWidth : ${maxWidth},</c:if>
				<c:if test="${!empty zIndex}">zIndex:${zIndex},</c:if>
				<c:if test="${!empty draggable}">draggable:${draggable},</c:if>
				<c:if test="${!empty dialogClass}">dialogClass:"${dialogClass}",</c:if>
				buttons : {
				<c:if test="${empty hideAllButtons}">
					<c:if test="${!empty saveClick}">
						${saveText}: function() {
							${saveClick}();
						},
					</c:if>
					<c:if test="${!empty otherButtons}"> 
					<% 
						JSONArray jsonArray = JSONArray.fromObject("["+otherButtons.replaceAll("'","\"")+"]");
						for(int i=0;i<jsonArray.size();i++){ 
						   JSONObject json= jsonArray.getJSONObject(i);
						   try{%>
							<%if(json.containsKey("name")){%>'<%=json.getString("name")%>': function() {
								 <%if(json.containsKey("onClick")){%> <%=json.getString("onClick").trim()%>();<%}%>
							},<%}%>
						  <% }catch(Exception e){e.printStackTrace();}}
					%>
					</c:if>
						${closeText}: function() {
							$(this).dialog("close");
						}
				</c:if>
				}
			});
});
</script>
<div id="${id}" style="display:none">
	<jsp:doBody></jsp:doBody>
</div>