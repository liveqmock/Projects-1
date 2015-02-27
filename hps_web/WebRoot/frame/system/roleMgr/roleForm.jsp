<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate"
	title="${operation eq 'add'?'添加':(operation eq 'edit'?'编辑':'查看')}角色">
	<script type="text/javascript"
		src="${ctx}/frame/system/roleMgr/roleForm.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="roleForm" onclick="roleFormSubmit"
		callback="roleMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;"
		title="${operation eq 'add'?'添加':(operation eq 'edit'?'编辑':'查看')}角色">
		<form id="roleForm">
			<nui:field type="input" style="display:none;">
				<nui:input title="所属公司：" size="x3">
					<input type="text" id="orgName" name="orgName" readonly="readonly"
						value="${role.orgName}" />
					<input type="hidden" id="orgId" value="${role.orgId}" name="orgId" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="角色名称：" size="x3" require="true">
					<input type="text" id="roleName" name="roleName"
						value="${role.roleName}" required="true" maxlength="50"/>
				</nui:input>
			</nui:field>
			<nui:field type="input">
			<!-- title="包含用户：<br/>${operation ne 'view'?'（双击选择）':''}" ,IE 7下label不支持换行符号<br>-->
				<nui:input title="包含用户：" size="x3">
					<textarea id="roleUserNames" name="roleUserNames" title="${operation ne 'view'?'双击选择用户':''}"
						style="height: 160px;" readonly="readonly">${role.roleUserNames}</textarea>
					<input type="hidden" id="roleUserIds" value="${role.roleUserIds}"
						name="roleUserIds" />
				</nui:input>
			</nui:field>
			<div id="assignedOperationDiv">
			<nui:field type="input">
				<nui:input title="操作权限：" size="x3">
					<textarea style="height: 80px;" id="assignedOperation" name="assignedOperation">${assigedOperations}</textarea>
				</nui:input>
			</nui:field>
			</div>
			<nui:field type="input">
				<nui:input title="备注：" size="x3">
					<textarea style="height: 80px;" id="memo" name="memo"
						maxlength="255">${role.memo}</textarea>
				</nui:input>
			</nui:field>
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">
				<input type="hidden" id="roleId" name="roleId"
					value="${role.roleId}" />
				<input type="hidden" id="roleType" name="roleType"
					value="${role.roleType}" />
				<input type="hidden" id="operation" name="operation"
					value="${operation}" />
				<nui:button title="保存" onclick="roleFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.parent.changePage();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
