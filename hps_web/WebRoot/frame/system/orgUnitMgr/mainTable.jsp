<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head title="组织机构管理" plugin="tree,grid,layout">
	<script type="text/javascript"
		src="${ctx}/frame/system/orgUnitMgr/mainTable.js"></script>
</nui:head>
<nui:body>
	<nui:layout container="body">
		<div class="ui-layout-west">
			<jsp:include page="/sys/orgUnitMgr/orgTreePage?orgType="></jsp:include>
		</div>
		<div class="ui-layout-center">
			<nui:panel style="margin:0px;">
				<nui:field type="button" align="left" style="padding:0px;">
					<operator:authorize ifAnyGranted="Frame_OrgUnit_Add">
						<nui:button title="添加公司" onclick="OrgManager.addCompany()"
							size="x8"></nui:button>
					</operator:authorize>
					<operator:authorize ifAnyGranted="Frame_OrgUnit_Add">
						<nui:button title="添加部门" onclick="OrgManager.addDepartment()"
							size="x8"></nui:button>
					</operator:authorize>
					<operator:authorize ifAnyGranted="Frame_OrgUnit_Edit">
						<nui:button title="修改" onclick="OrgManager.editOrg()"></nui:button>
					</operator:authorize>
					<operator:authorize ifAnyGranted="Frame_OrgUnit_Delete">
						<nui:button title="删除" onclick="OrgManager.delOrg()"></nui:button>
					</operator:authorize>
					<button style="display: none" id="searchBtn"
						onclick="OrgManager.doQuery()">
						查询
					</button>
				</nui:field>
			</nui:panel>
			<nui:table id="tableId" height="360">
				<jsp:include
					page="/sys/orgUnitMgr/orgTableListPage?defaultOrgId=${defaultOrgId}&sessionUserId=${sessionUserId}&page=1&rows=10"></jsp:include>
			</nui:table>
		</div>
	</nui:layout>
</nui:body>
</nui:html>
