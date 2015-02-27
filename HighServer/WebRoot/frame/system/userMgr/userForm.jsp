<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate,upload" title="用户">
	<link rel="stylesheet"
		href="${ctx}/frame/js/plugin/jcrob/css/jquery.Jcrop.min.css">
	<script type="text/javascript"
		src="${ctx}/frame/js/plugin/jcrob/js/jquery.Jcrop.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/frame/system/userMgr/userForm.js"></script>
	<style>
.gray_font {
	color: #A8A8A8;
}
</style>
</nui:head>
<nui:body>
	<nui:validate formId='userFormId' onclick="userFormSubmit"
		callback="userMgr.submitForm"></nui:validate>
	<nui:panel
		title="${operation eq 'add'?'添加':(operation eq 'edit'?'编辑':'查看')}用户"
		style="width:800px;margin:20px auto;" isToggle="true" isFold="true">
		<form id="userFormId">
			<nui:field type="input">
				<nui:input title="性别：" size="x0d5">
					<input type="radio" name="sex" value="true"
						<c:if test="${user.sex}">checked</c:if> />男
    				<input type="radio" name="sex" value="false"
						<c:if test="${!user.sex}">checked</c:if> />女
				</nui:input>
				<nui:input title="状态：" size="x0d5">
					<input type="radio" name="userState" value="true"
						<c:if test="${user.userState}">checked</c:if> />启用
    				<input type="radio" name="userState" value="false"
						<c:if test="${!user.userState}">checked</c:if> />禁用
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="登录账号：" size="x0d5" require="true">
					<input type="text" id="account" name="account"
						value="${user.account}" maxlength="50"
						required="true"
						remote="${ctx}/sys/userMgr/validateAccount?userId=${user.userId}" />
				</nui:input>
				<nui:input title="用户姓名：" size="x0d5" require="true">
					<input type="text" id="userName" name="userName"
						value="${user.userName}" required="true" maxlength="50"/>
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="登录密码：" size="x0d5" require="true">
					<input type="password" id="pwd" name="pwd" value="^default$"
						required="true" maxlength="50" />
				</nui:input>
				<nui:input title="重复输入密码：" size="x0d5" require="true">
					<input type="password" id="rePwd" name="rePwd" value="^default$"
						required="true" maxlength="50" sametext="#pwd" />
				</nui:input>
			</nui:field>

			<nui:field type="input">
				<nui:input title="手机：" size="x0d5" require="true">
					<input type="text" id="mobile" name="mobile" value="${user.mobile}"
						mobile="true" required="true"/>
				</nui:input>
				<nui:input title="电话：" size="x0d5">
					<input type="text" id="telephone" name="telephone"
						value="${user.telephone }" phone="true" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="Email：" size="x3">
					<input type="text" id="email" name="email" value="${user.email}"
						maxlength="50" email="true" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="联系地址：" size="x3">
					<input type="text" id="position" name="position"
						value="${user.position}" maxlength="50" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="联系公司：" size="x3">
					<input type="text" id="contactOrgName" name="contactOrgName"
						value="${user.contactOrgName}" maxlength="150" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="联系人：" size="x3">
					<input type="text" id="contactPerson" name="contactPerson"
						value="${user.contactPerson}" maxlength="150" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="所属角色：" size="x3">
					<textarea style="height: 60px;" id="selRoles"
						title="${operation ne 'view'?'请双击选择角色':''}" name="displaySelRoles"
						readonly="readonly">${user.userRoleNames}</textarea>
					<input type="hidden" id="hiddenSelRoles" name="userRoleIds"
						value="${user.userRoleIds}" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="备注：" size="x3">
					<textarea style="height: 60px;" id="memo" name="memo"
						maxlength="255">${user.memo}</textarea>
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="上传头像：" size="x3">
					<fieldset>
						<nui:attachnew id="memberImage" uploadUrl="${ctx}/attach/uploadImageAttachToDir" fileTypeExts="*.jpeg;*.jpg;*.bmp;*.gif;*.png;"
							uploadRevoke="userMgr.uploadRevoke"></nui:attachnew>
					</fieldset>
				</nui:input>
			</nui:field>
			<div style="margin-left: 118px;" id="imageArea"></div>
			<nui:field type="button" align="center">
				<input type="hidden" id="userId" name="userId"
					value="${user.userId}" />
				<input type="hidden" id="defaultOrgId" name="defaultOrgId"
					value="${user.defaultOrgId}" />
				<input type="hidden" id="operation" name="operation"
					value="${operation}" />
				<nui:button title="保存" type="button" id="btnCommit"
					onclick="userFormSubmit()"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.parent.changePage();"></nui:button>
			</nui:field>
		</form>
		<input type="hidden" id="x" name="x" value="" />
		<input type="hidden" id="y" name="y" value="}" />
		<input type="hidden" id="w" name="w" value="" />
		<input type="hidden" id="h" name="h" value="" />
		<input type="hidden" id="fileId" name="fileId" value="" />
	</nui:panel>
</nui:body>
</nui:html>
