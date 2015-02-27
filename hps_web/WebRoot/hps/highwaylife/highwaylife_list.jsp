<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/highwaylife/highwaylife_list.js"></script>
<nui:grid id="highwaylifeListGrid" title="highwaylife列表" height="400"
	url="${ctx}/highwaylife/load_list" pageId="highwaylifeListPageId"
	pkName="highwaylifeId" addCheck="HighwaylifeManager.addCheck" addClick="HighwaylifeManager.addHighwaylife" 
	addOperator="Frame_Highwaylife_Add" editOperator="Frame_Highwaylife_Edit"
	delOperator="Frame_Highwaylife_Delete" 
	editClick="HighwaylifeManager.editHighwaylife" 
	delClick="HighwaylifeManager.deleteHighwaylife"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center"
		formatter="HighwaylifeManager.formatViewLink"></nui:gridCell>
      	<nui:gridCell name="type" title="type"  align="center"></nui:gridCell>
  	<nui:gridCell name="title" title="title"  align="center"></nui:gridCell>
  	<nui:gridCell name="userid" title="userid"  align="center"></nui:gridCell>
  	<nui:gridCell name="createtime" title="createtime"  align="center"></nui:gridCell>
  	<nui:gridCell name="detail" title="detail"  align="center"></nui:gridCell>
 </nui:grid>