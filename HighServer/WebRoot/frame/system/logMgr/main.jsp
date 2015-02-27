<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/frame/system/logMgr/main.js"></script>
<nui:layout container="body" westSize="0">
	<div class="ui-layout-west">
	</div>
	<div class="ui-layout-center">
		<jsp:include page="/log/logList"></jsp:include>
		<%--<iframe src="${ctx}/log/logList" id="logList" width="99%"
			scrolling="no" height="99%" frameborder="0" style="overflow: hidden;"></iframe> --%>
	</div>
</nui:layout>
