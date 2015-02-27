<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/frame/system/roleMgr/roleList.js"></script>
<nui:grid id="roleListGrid" title="角色列表" offsetHeight="-15"
	url="${ctx}/sys/roleMgr/loadRoleListData?defaultOrgId=${defaultOrgId}" pageId="rolelistPageId"
	pkName="roleId" addCheck="RoleManager.addCheck" addClick="RoleManager.addRole" addOperator="Frame_Role_Add" editOperator="Frame_Role_Edit"
	delOperator="Frame_Role_Delete" 
	editClick="RoleManager.editRole" delClick="RoleManager.deleteRole"
	otherButtons="{'operator':'Frame_Role_Permission','name':'分配权限','onClick':'RoleManager.rolePermission'}">
	<nui:gridCell name="roleId" title="" hidden="true" align="center"></nui:gridCell>
	<nui:gridCell name="roleName" title="角色名称" align="center" width="300"
		formatter="RoleManager.formatRoleName"></nui:gridCell>
	<nui:gridCell name="memo" title="备注" align="center" width="200"></nui:gridCell>
	<nui:gridCell name="orgId" title="" hidden="true" align="center"></nui:gridCell>
</nui:grid>