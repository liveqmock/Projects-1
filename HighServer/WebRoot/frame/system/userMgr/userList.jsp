<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/frame/system/userMgr/userList.js"></script>
<nui:field id="filterField" type="input" style="margin-left:-20px;width:750px;">
	<nui:input title="角色名称：" size="x20">
		<select name="roleid" id="roleid" style="width:100px;">
			<option value="">所有--</option>
			<nui:option url="${ctx}/sys/roleMgr/allrole"
				value="" keyname="roleId" valuename="roleName">
			</nui:option>
		</select>
	</nui:input>
	<nui:input title="登录账号：" size="x20">
		<input type="text" name="account" value="" filterType="like" />
	</nui:input>
	<nui:input title="手机号：" size="x20">
		<input type="text" name="mobile" value="" filterType="like" />
	</nui:input>
	<nui:button title="查询" style="margin-top:10px;"
		onclick="UserList.filterClick('');"></nui:button>
</nui:field>
<nui:grid id="userListGrid" title="用户列表" offsetHeight="-60"
	url="${ctx}/sys/userMgr/loadUserListData"
	pageId="userlistPageId" pkName="userId" addClick="UserList.addUser"
	addOperator="Frame_User_Add" editOperator="Frame_User_Edit"
	delOperator="Frame_User_Delete" editClick="UserList.editUser"
	delClick="UserList.deleteUser">
	<nui:gridCell name="userId" title="用户Id" align="center" hidden="true"></nui:gridCell>
	<nui:gridCell name="userName" title="用户姓名" align="center"
		formatter="UserList.formatUserName" width="80"></nui:gridCell>
	<nui:gridCell name="account" title="登录账号" align="center" width="80"></nui:gridCell>
	<nui:gridCell name="sex" title="性别" align="center"
		formatter="UserList.formateSex" width="60"></nui:gridCell>
	<nui:gridCell name="mobile" title="手机号" align="center" width="80"></nui:gridCell>
	<nui:gridCell name="lastModifyTime" title="更新时间" align="center"
					formatter="UserList.formatTime"></nui:gridCell>
	<%-- <nui:gridCell name="operate" title="上传音乐" align="center" width="80" formatter="UserList.formatOperate"></nui:gridCell>--%>
	<nui:gridCell name="userRoleNames" title="所属角色" align="left"
		width="100"></nui:gridCell>
	<nui:gridCell name="createTime" title="注册时间" align="center"
					formatter="UserList.formatTime"></nui:gridCell>
</nui:grid>
