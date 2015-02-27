<%@ page language="java" pageEncoding="UTF-8"%>
<nui:layout container="body" westSize="0">
	<div class="ui-layout-west">		
	</div>
	<div class="ui-layout-center">
		<div id="listGrid" style="width: 100%; height: 100%;">
			<jsp:include page="/adphoto/list"></jsp:include>
		</div>
		<iframe src="" id="formIframe" scrolling="yes" frameborder="0"
			style="width: 99%; height: 99%; display: none;"></iframe>
	</div>
</nui:layout>
