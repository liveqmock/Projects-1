<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ tag import="net.sf.json.JSONArray,net.sf.json.JSONObject"%>
<%@ taglib uri="http://www.newsoft.com/tags-operator" prefix="operator"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="pageId" type="java.lang.String" required="true"%>
<%@ attribute name="pkName" type="java.lang.String" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="false"%>
<%@ attribute name="title" type="java.lang.String" required="false"%>
<%@ attribute name="height" type="java.lang.String" required="false"%>
<%@ attribute name="offsetHeight" type="java.lang.String"
	required="false"%>
<%@ attribute name="multiselect" type="Boolean" required="false"%>
<%@ attribute name="showRownumbers" type="Boolean" required="false"%>
<%@ attribute name="addText" type="java.lang.String" required="false"%>
<%@ attribute name="addCheck" type="java.lang.String" required="false"%>
<%@ attribute name="addClick" type="java.lang.String" required="false"%>
<%@ attribute name="addOperator" type="java.lang.String"
	required="false"%>
<%@ attribute name="editText" type="java.lang.String" required="false"%>
<%@ attribute name="editCheck" type="java.lang.String" required="false"%>
<%@ attribute name="editClick" type="java.lang.String" required="false"%>
<%@ attribute name="editOperator" type="java.lang.String"
	required="false"%>
<%@ attribute name="delText" type="java.lang.String" required="false"%>
<%@ attribute name="delCheck" type="java.lang.String" required="false"%>
<%@ attribute name="delClick" type="java.lang.String" required="false"%>
<%@ attribute name="delOperator" type="java.lang.String"
	required="false"%>

<%@ attribute name="otherButtons" type="java.lang.String"
	required="false"%>
<c:if test="${empty addText}">
	<c:set var="addText">添加</c:set>
</c:if>
<c:if test="${empty editText}">
	<c:set var="editText">修改</c:set>
</c:if>
<c:if test="${empty delText}">
	<c:set var="delText">删除</c:set>
</c:if>
<c:if test="${empty multiselect}">
	<c:set var="multiselect">${true}</c:set>
</c:if>
<c:if test="${!empty showRownumbers}">
	<c:set var="rownumbers">${true}</c:set>
</c:if>
<c:if test="${empty height}">
	<c:set var="height" value="230"></c:set>
</c:if>
<c:if test="${empty offsetHeight}">
	<c:set var="offsetHeight" value="0"></c:set>
</c:if>
<%--添加按钮的权限--%>
<c:set var="hasAddOperator">${false}</c:set>
<c:choose>
	<c:when test="${!empty addOperator}">
		<operator:authorize ifAnyGranted="${addOperator}">
			<c:set var="hasAddOperator">${true}</c:set>
		</operator:authorize>
	</c:when>
	<c:otherwise>
		<c:set var="hasAddOperator">${true}</c:set>
	</c:otherwise>
</c:choose>

<%--编辑按钮的权限--%>
<c:set var="hasEditOperator">${false}</c:set>
<c:choose>
	<c:when test="${!empty editOperator}">
		<operator:authorize ifAnyGranted="${editOperator}">
			<c:set var="hasEditOperator">${true}</c:set>
		</operator:authorize>
	</c:when>
	<c:otherwise>
		<c:set var="hasEditOperator">${true}</c:set>
	</c:otherwise>
</c:choose>

<%--删除按钮的权限--%>
<c:set var="hasDelOperator">${false}</c:set>
<c:choose>
	<c:when test="${!empty delOperator}">
		<operator:authorize ifAnyGranted="${delOperator}">
			<c:set var="hasDelOperator">${true}</c:set>
		</operator:authorize>
	</c:when>
	<c:otherwise>
		<c:set var="hasDelOperator">${true}</c:set>
	</c:otherwise>
</c:choose>
<table id="${id}"></table>
<div id="${pageId}"></div>
<script type="text/javascript"> 
$(function(){  
 var jqGrid_${id} = $("#${id}").jqGrid({
		colModel:[
		 <jsp:doBody></jsp:doBody>
			{
			"name":"act", 
			"hidden":true,
			"hidedlg":true,
			"align":"center"
			}
		],
		url:'${url}',
		datatype: "json",
		pager: '#${pageId}',
		rowNum:20, 
		rowList:[10,20,30,50,100], 
		<c:if test="${!empty title}">caption: "${title}",</c:if> 
		<c:if test="${!empty rownumbers}">rownumbers:true,</c:if>
		gridview:true,//加
		recordpos: 'right', 
		viewrecords: true, //显示记录
		sortorder: "desc", 
		multiselect: true, //多选
		multiboxonly: ${!multiselect},//单选一
		jsonReader: {
            id:"${pkName}",
            repeatitems: false
        },  
        height:"100%",
		<c:if test="${!empty height}">height:${height},</c:if>
		width:"auto", 
		shrinkToFit:true,
		forceFit:true,
		//hidegrid:false, 
 		toolbar:[true,"top"],
 		autowidth:true
	});
	$("#t_${id}").append('<table cellspacing="0" cellpadding="0" border="0" style="float:left;table-layout:auto;margin-top:2px" class="ui-pg-table topnavtable"><tr><td></td></tr></table>');
	
	jqGrid_${id}.navGrid('#${pageId}',{add:false,edit:false,del:false,search:false}) 
	<c:if test="${!empty addClick}">
		<c:if test="${hasAddOperator}">
			.navButtonAdd('#${pageId}',{   
			   caption:"${addText}", 
			   title: '添加新记录', 
			   buttonicon:"ui-icon-plus",    
			   onClickButton: function($btn){  
			   	  <c:if test="${!empty addCheck}">if(${addCheck}(jqGrid_${id},$btn)==false) return false;</c:if> 
			      ${addClick}(jqGrid_${id},$btn);
			   },    
			   position:"last"  
			}) 
		</c:if>
	</c:if>
	<c:if test="${!empty editClick}">
		<c:if test="${hasEditOperator}">
			 .navSeparatorAdd("#${pageId}",{sepclass : "ui-separator",sepcontent: ''}) 
			 .navButtonAdd('#${pageId}',{   
			   caption:"${editText}",    
			   title: '编辑选中记录', 
			   buttonicon:"ui-icon-pencil",    
			   onClickButton: function($btn){ 
				   var selectedIds = jqGrid_${id}.jqGrid("getGridParam", "selarrrow");
				   if(selectedIds==''){$.alert("请选择需要编辑的记录。");return false;}
				   if((selectedIds+"").indexOf(",")!=-1){$.alert("每次只能编辑一条记录。");return false;}
			 	   <c:if test="${!empty editCheck}">if(${editCheck}(jqGrid_${id},$btn)==false) return false;</c:if>    
			 	   var $rowData = jqGrid_${id}.jqGrid("getRowData", selectedIds);
			       ${editClick}(jqGrid_${id},$rowData);
			   },    
			   position:"last"  
			})
		</c:if>
	</c:if>
	<c:if test="${!empty delClick}">
		<c:if test="${hasDelOperator}">
			.navSeparatorAdd("#${pageId}",{sepclass : "ui-separator",sepcontent: ''})
			.navButtonAdd('#${pageId}',{   
			   caption:"${delText}", 
			   title: "删除选中记录",   
			   buttonicon:"ui-icon-trash",    
			   onClickButton: function($btn){  
			       var selectedIds = jqGrid_${id}.jqGrid("getGridParam", "selarrrow");  
				   if(selectedIds==''){$.alert("请选择需要删除的记录。");return false;} 
			  	   <c:if test="${!empty delCheck}">if(${delCheck}(jqGrid_${id},$btn)==false) return false;</c:if>  
			       ${delClick}(jqGrid_${id},selectedIds+"");
			   },    
			   position:"last"  
			}) 
		</c:if> 
	</c:if> 
	<c:if test="${!empty otherButtons}"> 
	<% 
		JSONArray jsonArray = JSONArray.fromObject("["+otherButtons.replaceAll("'","\"")+"]");
		for(int i=0;i<jsonArray.size();i++){
		   JSONObject json= jsonArray.getJSONObject(i);
		   try{
		   if(json.containsKey("operator")){
		  	 String operator = json.getString("operator");
		  %>
		    <operator:authorize ifAnyGranted="<%=operator%>">
			    <%if(json.containsKey("position")&&!json.getString("position").equals("first")){%> 
			    .navSeparatorAdd("#${pageId}",{sepclass : "ui-separator",sepcontent: ''})
			    <%}%>
				.navButtonAdd('#${pageId}',{
				 <%if(json.containsKey("id")){%>id:'<%=json.getString("id")%>',<%}%>
				 <%if(json.containsKey("name")){%>caption:'<%=json.getString("name")%>',<%}%>
				 <%if(json.containsKey("title")){%>title:'<%=json.getString("title")%>',<%}%>
				 <%if(json.containsKey("ui")){%> buttonicon:'<%=json.getString("ui")%>',<%}%>
				 <%if(json.containsKey("onClick")){%> onClickButton:function(){<%=json.getString("onClick").trim()%>(jqGrid_${id});},<%}%>
				 <%if(json.containsKey("position")){%> position:'<%=json.getString("position")%>'<%}else{%>
					 position:'last'
				 <%}%>
				})
			</operator:authorize>
			<%}else{%>
			   <%if(json.containsKey("position")&&!json.getString("position").equals("first")){%> 
			    .navSeparatorAdd("#${pageId}",{sepclass : "ui-separator",sepcontent: ''})
			   <%}%>
				.navButtonAdd('#${pageId}',{
				 <%if(json.containsKey("id")){%>id:'<%=json.getString("id")%>',<%}%>
				 <%if(json.containsKey("name")){%>caption:'<%=json.getString("name")%>',<%}%>
				 <%if(json.containsKey("title")){%>title:'<%=json.getString("title")%>',<%}%>
				 <%if(json.containsKey("ui")){%> buttonicon:'<%=json.getString("ui")%>',<%}%>
				 <%if(json.containsKey("onClick")){%> onClickButton:function(){<%=json.getString("onClick").trim()%>(jqGrid_${id});},<%}%>
				 <%if(json.containsKey("position")){%> position:'<%=json.getString("position")%>'<%}else{%>
					 position:'last'
				 <%}%>
				})
		  <% }}catch(Exception e){e.printStackTrace();}}
	%>
	</c:if>
	
	//使jqgrid自适应大小：
	;(function(){
	    var  resizeTimer = null;
		resizeGrid();
		// 窗口大小改变的时候
		window.onresize = function() { 
			if(resizeTimer==null){
	            resizeTimer =setTimeout(function(){
					resizeGrid();
				},500);
			}
		};
		function resizeGrid(){
			var size = getWidthAndHeigh(true);  
			jqGrid_${id}.jqGrid('setGridWidth',size.width).jqGrid('setGridHeight', size.height);
			resizeTimer=null;
		}
		// 获取父元素大小
		function getWidthAndHeigh(resize) {
			var width = $("#gbox_${id}").parent().width(); 
			var height = $("#gbox_${id}").parent().height()+${offsetHeight};
			// chrome
			if ($.browser.chrome) {
				width -= 2;height -= 104;
			}
			// firefox
			else if ($.browser.mozilla) {
				width -= 2;height -= 108;
			} 
			// IE
			else { 
				width -= 2;height -= 106;
			}
			return {width: width, height: height};
		}
	})();  
});
</script>