<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/frame/js/plugin/jquery-ui-1.8.16.custom/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript"
	src="${ctx}/frame/js/plugin/dynatree-1.2.0/src/jquery.cookie.js"></script>
	<script type="text/javascript"
	src="${ctx}/frame/js/plugin/jquery.validate/jquery.validate.js"></script>
<script type="text/javascript"
	src="${ctx}/frame/js/plugin/jquery.validate/additional-methods.js"></script>
<nui:layout container="body" westSize="0">
	<div class="ui-layout-west">		
	</div>
	<div class="ui-layout-center">
		<jsp:include page="/subscribe/list"></jsp:include>
	</div>
</nui:layout>
