<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/frame/system/orgUnitMgr/orgList.js"></script>
<nui:field id="filterField" type="input" style="margin-left:-40px;width:800px;">
	<nui:input title="名称：" size="x30">
		<input type="text" name="orgName" value="" filterType="like" />
	</nui:input>
	<nui:input title="类型：" size="x20">
		<select name="orgType" filterType="=" id="orgTypeSelect">
			<option value="">
			</option>
			<option value="0">
				部门
			</option>
			<option value="1">
				公司
			</option>
		</select>
	</nui:input>
	<nui:input title="层级码：" size="x20">
		<input type="text" name="orgLevelCode" filterType="like" value="" />
	</nui:input>
	<nui:button title="查询" style="margin-top:10px;"
		onclick="OrgManager.filterClick();"></nui:button>
</nui:field>
<!-- addClick="OrgManager.addOrg" addCheck="OrgManager.addCheck" -->
<nui:grid id="orgListGrid" pageId="orgListGrid_pageId" offsetHeight="-40"
	pkName="orgId" title="组织机构列表"
	url="${ctx}/sys/orgUnitMgr/loadOrgListData?defaultOrgId=${defaultOrgId}&sessionUserId=${sessionUserId}"
	addClick="OrgManager.addDepartment" addText="添加部门"
	addOperator="Frame_OrgUnit_Add" editClick="OrgManager.editOrg"
	delClick="OrgManager.delOrg" editOperator="Frame_OrgUnit_Edit"
	delOperator="Frame_OrgUnit_Delete"
	otherButtons="{'operator':'Frame_OrgUnit_Add','name':'添加公司','onClick':'OrgManager.addCompany','position':'first','ui':'ui-icon-plus'}">
	<nui:gridCell name="orgId" title="" hidden="true" align="center"></nui:gridCell>
	<nui:gridCell name="orgName" title="机构名称" align="left" width="300"
		sortable="true" formatter="OrgManager.formatOrgName"></nui:gridCell>
	<nui:gridCell name="orgLevelCode" title="层级码" align="center"
		width="100"></nui:gridCell>
	<nui:gridCell name="orgType" title="机构类型" align="center" width="100"
		formatter="OrgManager.formatOrgType"></nui:gridCell>
	<nui:gridCell name="orgState" title="是否启用" align="center" width="100"
		formatter="OrgManager.formatOrgState"></nui:gridCell>
	<nui:gridCell name="managers" title="负责人" align="center" width="100"></nui:gridCell>
	<nui:gridCell name="leaders" title="分管领导" align="center" width="100"></nui:gridCell>
	<nui:gridCell name="operation" title="操作" align="center" width="50"
		formatter="OrgManager.sequenceOrg"></nui:gridCell>
</nui:grid>
