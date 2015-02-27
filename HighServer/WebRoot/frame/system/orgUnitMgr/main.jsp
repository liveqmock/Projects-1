<%@ page language="java" pageEncoding="UTF-8"%>
<nui:layout container="body">
	<div class="ui-layout-west">
		<jsp:include page="/sys/orgUnitMgr/orgTreePage"></jsp:include>
	</div>
	<div class="ui-layout-center">
		<jsp:include page="/sys/orgUnitMgr/orgListPage"></jsp:include>
	</div>
</nui:layout>
