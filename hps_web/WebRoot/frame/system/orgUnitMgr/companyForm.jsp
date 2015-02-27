<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate"
	title="${operation eq 'add'?'添加':(operation eq 'edit'?'编辑':'查看')}公司">
	<script type="text/javascript"
		src="${ctx}/frame/system/orgUnitMgr/companyForm.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="orgForm" onclick="orgFormSubmit"
		callback="orgUnitMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;"
		title="${operation eq 'add'?'添加':(operation eq 'edit'?'编辑':'查看')}公司">
		<form id="orgForm">
			<nui:field type="input">
				<nui:input title="是否启用：" size="x1">
					<input type="radio" name="orgState" value="true"
						<c:if test="${orgUnit.orgState}">checked</c:if> />是
    				<input type="radio" name="orgState" value="false"
						<c:if test="${!orgUnit.orgState}">checked</c:if> />否
				</nui:input>
			</nui:field>

			<nui:field type="input">
				<nui:input title="上级公司：" size="x2" require="true">
					<input type="text" id="parentOrgName" name="parentOrgName"
						value="${orgUnit.parentOrgName}" readonly="readonly" />
					<input type="hidden" id="parentOrgId"
						value="${orgUnit.parentOrgId}" name="parentOrgId" />
				</nui:input>
			</nui:field>

			<nui:field type="input">
				<nui:input title="公司名称：" size="x2" require="true">
					<input type="text" id="orgName" name="orgName"
						value="${orgUnit.orgName}" required="true" maxlength="100" />
				</nui:input>
				<nui:input title="公司简称：" size="x1" require="true">
					<input type="text" id="orgShortName" name="orgShortName"
						value="${orgUnit.orgShortName}" maxlength="25" required="true" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="英文简称：" size="x1" require="true">
					<input type="text" id="orgEngName" name="orgEngName" stringEN="true"
						value="${orgUnit.orgEngName}" required="true" maxlength="25"
						<c:if test="${operation eq 'add'}">remote="${ctx}/sys/orgUnitMgr/validateOrgEngName?orgId=${orgUnit.orgId}"</c:if> />
				</nui:input>
				<nui:input title="层级编码：" size="x1" require="true">
					<input type="text" id="orgLevelCode" name="orgLevelCode" 
						value="${orgUnit.orgLevelCode}" required="true"
						<c:if test="${operation eq 'add'}">maxlength="4"</c:if>
						<c:if test="${not operation eq 'add'}">maxlength="100"</c:if>
						<c:if test="${operation eq 'add'}">remote="${ctx}/sys/orgUnitMgr/validateOrgLevelCode?parentOrgId=${orgUnit.parentOrgId}"</c:if> />
				</nui:input>
				<nui:input title="邮政编码：" size="x1">
					<input type="text" id="orgPostCode" name="orgPostCode"
						value="${orgUnit.orgPostCode}" isPostCode="true" />
				</nui:input>
			</nui:field>

			<nui:field type="input">
				<nui:input title="公司传真：" size="x1">
					<input type="text" id="orgFax" name="orgFax"
						value="${orgUnit.orgFax}" maxlength="25" />
				</nui:input>
				<nui:input title="公司电话：" size="x1">
					<input type="text" id="orgPhone" name="orgPhone"
						value="${orgUnit.orgPhone}" maxlength="25" />
				</nui:input>
				<nui:input title="公司邮箱：" size="x1">
					<input type="text" id="orgEmail" name="orgEmail" email="true"
						value="${orgUnit.orgEmail}" maxlength="50" />
				</nui:input>
			</nui:field>

			<nui:field type="input">
				<nui:input title="公司负责人：" size="x3">
					<input type="text" id="managers" name="managers"
						value="${orgUnit.managers}" maxlength="30" readonly="readonly"
						${operation ne 'view'?'selectFunction="orgUnitMgr.selectManagers"':''}/>
					<input type="hidden" id="managerIds" value="${orgUnit.managerIds}"
						name="managerIds" />
				</nui:input>
			</nui:field>

			<nui:field type="input">
				<nui:input title="分管领导：" size="x3">
					<input type="text" id="leaders" name="leaders"
						value="${orgUnit.leaders}" maxlength="30" readonly="readonly"
						${operation ne 'view'?'selectFunction="orgUnitMgr.selectLeaders"':''} />
					<input type="hidden" id="leaderIds" value="${orgUnit.leaderIds}"
						name="leaderIds" />
				</nui:input>
			</nui:field>

			<nui:field type="input">
				<nui:input title="上级主管机构：" size="x3">
					<input type="text" id="topOrgs" name="topOrgs"
						value="${orgUnit.topOrgs}" maxlength="100" readonly="readonly"
						${operation ne 'view'?'selectFunction="orgUnitMgr.selectTopOrgs"':''}/>
					<input type="hidden" id="topOrgIds" value="${orgUnit.topOrgIds}"
						name="topOrgIds" />
				</nui:input>
			</nui:field>

			<nui:field type="input">
				<nui:input title="公司地址：" size="x3">
					<input type="text" id="orgAddress" name="orgAddress"
						value="${orgUnit.orgAddress}" maxlength="100" />
				</nui:input>
			</nui:field>

			<nui:field type="input">
				<nui:input title="备注：" size="x3">
					<textarea style="height: 60px;" id="memo" name="memo"
						maxlength="100">${orgUnit.memo}</textarea>
				</nui:input>
			</nui:field>

			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">
				<input type="hidden" id="orgSort" name="orgSort"
					value="${orgUnit.orgSort}" />
				<input type="hidden" name="orgType" value="true" />
				<input type="hidden" id="orgId" name="orgId"
					value="${orgUnit.orgId}" />
				<input type="hidden" id="operation" name="operation"
					value="${operation}" />
				<nui:button title="保存" onclick="orgFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
