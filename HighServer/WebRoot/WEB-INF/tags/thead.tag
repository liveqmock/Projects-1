<%@ tag pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="showNum" type="java.lang.String" required="false"  %>
<%@ attribute name="highlight" type="java.lang.String" required="false"  %>
<%@ attribute name="noshot" type="java.lang.Boolean" required="false"  %>
<%
	String noshotstr = "";
	if(noshot != null && noshot == true)
		noshotstr = "noshot='true'";
%>
<c:if test="${empty highlight}">
	<c:set var="highlight" value="true"></c:set>
</c:if>
<c:set var="uid">uid_<%=Math.round(Math.pow(10,10)*Math.random())%></c:set>
<c:if test="${!empty showNum}">
<script type="text/javascript">
$(function(){
	//最少显示行数的实现
	$("#${uid}").each(function(){
		var row = $(this).find("tbody tr");
		var gap = Number("${showNum}") - row.length;
		var cell = $(this).find("thead tr td");

		for(var i=0;i<gap;i++){
			var tr = $("<tr isCollect='false'></tr>");
			for(var j = 0 ; j<cell.length ; j++){
				var td=$("<td>&nbsp;</td>");
				td.css("display",cell[j].style.display);
				tr.append(td);
			}
			$(this).find("tbody").append(tr);
		}
	});
});
</script>
</c:if>
<c:if test="${highlight eq 'true'}">
<script type="text/javascript">
$(function(){
	//单双行实现
	var tr = $("#${uid} tbody tr");
	tr.each(function(index){
		$(this).hover(function(){
			$(this).addClass("ui-state-default ui-state-highlight ui-state-active");
		},function(){
			$(this).removeClass("ui-state-default ui-state-highlight ui-state-active");
		});
	}).filter(":odd").addClass("odd");
	
	//处理thead的td或th左边border:border-left:1px solid
	$("#${uid} thead tr td[group!='check']").css("border-left","1px solid");
	$("#${uid} thead tr th[group!='check']").css("border-left","1px solid");
});
</script>
</c:if>
<table id="${uid}" ${noshotstr} class="ui-state-hover" style="width:100%;">
<thead class="ui-accordion-header ui-helper-reset ui-state-hover">
	<jsp:doBody></jsp:doBody>
</thead>
