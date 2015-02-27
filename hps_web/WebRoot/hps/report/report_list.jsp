<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/hps/report/report_list.js"></script>
<nui:grid id="reportListGrid" title="report列表" height="400"
	url="${ctx}/report/load_list" pageId="reportListPageId"
	pkName="reportId" addCheck="ReportManager.addCheck" addClick="ReportManager.addReport" 
	addOperator="Frame_Report_Add" editOperator="Frame_Report_Edit"
	delOperator="Frame_Report_Delete" 
	editClick="ReportManager.editReport" 
	delClick="ReportManager.deleteReport"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center"
		formatter="ReportManager.formatViewLink"></nui:gridCell>
      	<nui:gridCell name="userid" title="userid"  align="center"></nui:gridCell>
  	<nui:gridCell name="xcode" title="xcode"  align="center"></nui:gridCell>
  	<nui:gridCell name="ycode" title="ycode"  align="center"></nui:gridCell>
  	<nui:gridCell name="occtime" title="occtime"  align="center"></nui:gridCell>
  	<nui:gridCell name="type" title="type"  align="center"></nui:gridCell>
 </nui:grid>