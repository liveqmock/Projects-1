<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/userexchnage/userexchnage_list.js"></script>
<nui:grid id="userexchnageListGrid" title="userexchnage列表" height="400"
	url="${ctx}/userexchnage/load_list" pageId="userexchnageListPageId"
	pkName="userexchnageId" addCheck="UserexchnageManager.addCheck" addClick="UserexchnageManager.addUserexchnage" 
	addOperator="Frame_Userexchnage_Add" editOperator="Frame_Userexchnage_Edit"
	delOperator="Frame_Userexchnage_Delete" 
	editClick="UserexchnageManager.editUserexchnage" 
	delClick="UserexchnageManager.deleteUserexchnage"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center"
		formatter="UserexchnageManager.formatViewLink"></nui:gridCell>
      	<nui:gridCell name="userid" title="userid"  align="center"></nui:gridCell>
  	<nui:gridCell name="productid" title="productid"  align="center"></nui:gridCell>
  	<nui:gridCell name="producttitle" title="producttitle"  align="center"></nui:gridCell>
  	<nui:gridCell name="status" title="status"  align="center"></nui:gridCell>
  	<nui:gridCell name="score" title="score"  align="center"></nui:gridCell>
  	<nui:gridCell name="handler" title="handler"  align="center"></nui:gridCell>
  	<nui:gridCell name="handlername" title="handlername"  align="center"></nui:gridCell>
  	<nui:gridCell name="reicaddress" title="reicaddress"  align="center"></nui:gridCell>
  	<nui:gridCell name="reicphone" title="reicphone"  align="center"></nui:gridCell>
  	<nui:gridCell name="createtime" title="createtime"  align="center"></nui:gridCell>
 </nui:grid>