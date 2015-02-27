<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/userscoredetail/userscoredetail_list.js"></script>
<nui:grid id="userscoredetailListGrid" title="userscoredetail列表" height="400"
	url="${ctx}/userscoredetail/load_list" pageId="userscoredetailListPageId"
	pkName="userscoredetailId" addCheck="UserscoredetailManager.addCheck" addClick="UserscoredetailManager.addUserscoredetail" 
	addOperator="Frame_Userscoredetail_Add" editOperator="Frame_Userscoredetail_Edit"
	delOperator="Frame_Userscoredetail_Delete" 
	editClick="UserscoredetailManager.editUserscoredetail" 
	delClick="UserscoredetailManager.deleteUserscoredetail"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center"
		formatter="UserscoredetailManager.formatViewLink"></nui:gridCell>
      	<nui:gridCell name="userid" title="userid"  align="center"></nui:gridCell>
  	<nui:gridCell name="levelkey" title="levelkey"  align="center"></nui:gridCell>
  	<nui:gridCell name="score" title="score"  align="center"></nui:gridCell>
  	<nui:gridCell name="cratetime" title="cratetime"  align="center"></nui:gridCell>
 </nui:grid>