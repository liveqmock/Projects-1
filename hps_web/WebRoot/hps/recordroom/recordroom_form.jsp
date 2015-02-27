<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="录音室管理">
	<script type="text/javascript"
		src="${ctx}/record/recordroom/recordroom_form.js"></script>
</nui:head>
<nui:body>
	<input type="hidden" id="id" name="id" value="${recordroom.id}"/>
	<nui:validate formId="recordroomForm" onclick="recordroomFormSubmit"
		callback="RecordroomMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="录音室管理">
		<form id="recordroomForm">
			<nui:field type="input">
				<nui:input title="名称：" size="x3" require="true">
					<input type="text" id="name" name="name" value="${recordroom.name}"
						required="true" maxlength="100" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="地址：" size="x3" require="true">
					<input type="text" id="adderss" name="adderss"
						value="${recordroom.adderss}" required="true" maxlength="200" />
				</nui:input>
			</nui:field>
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">
				<input type="hidden" id="operation" name="operation"
					value="${operation}" />
				<nui:button title="保存" onclick="recordroomFormSubmit()"
					id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.parent.changePage();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
