<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/recordroom/recordroom_list.js"></script>
<nui:grid id="recordroomListGrid" title="录音室列表" height="400" offsetHeight="-12"
	url="${ctx}/recordroom/load_list" pageId="recordroomListPageId"
	pkName="id" addCheck="RecordroomManager.addCheck" addClick="RecordroomManager.addRecordroom" 
	addOperator="Recordroom_Mgr" editOperator="Recordroom_Mgr"
	delOperator="Recordroom_Mgr" 
	editClick="RecordroomManager.editRecordroom" 
	delClick="RecordroomManager.deleteRecordroom"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center" hidden="true"></nui:gridCell>
      	<nui:gridCell name="name" title="名称"  align="center"></nui:gridCell>
  		<nui:gridCell name="adderss" title="地址"  align="center"></nui:gridCell>
 </nui:grid>