<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/frame/system/orgUnitMgr/orgTree.js"></script>
<nui:tree id="orgTree" checkbox="false" imagePath="${ctx}/frame/system/orgUnitMgr/image/"
	url="${ctx}/sys/orgUnitMgr/loadOrgUnitTree?defaultOrgId=${defaultOrgId}&sessionUserId=${sessionUserId}&orgType=${param.orgType}"
	showroot="false" onactivate="OrgTree.active" onclick="OrgTree.active"></nui:tree>
