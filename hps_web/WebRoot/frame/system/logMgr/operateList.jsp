<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/frame/system/logMgr/main.js"></script>
<script type="text/javascript"
	src="${ctx}/frame/system/logMgr/operateList.js"></script>
<nui:field id="filterField" type="input"
	style="margin-left:-20px;width:770px;">
	<nui:input title="用户：" size="x20">
		<input type="text" name="userName" value="" filterType="like" />
	</nui:input>
	<nui:input title="起始时间：" size="x25">
		<input type="text" name="logDate" id="logDate" value="" filterType=">"
			readonly="true" calendar="1" />
	</nui:input>
	<nui:input title="结束时间：" size="x25">
		<input type="text" name="logDate" id="logDate1" value=""
			filterType="<" readonly=" true" calendar="1" />
	</nui:input>
	<nui:button title="查询" style="margin-top:10px;"
		onclick="LogManager.filterClick();"></nui:button>
</nui:field>
<nui:grid id="logListGrid" pageId="logListGrid_pageId" pkName="logId"
	title="日志列表" url="${ctx}/log/loadLogList"
	delClick="LogManager.deleteLog" offsetHeight="-50">
	<nui:gridCell name="logId" title="" hidden="true" align="center"></nui:gridCell>
	<nui:gridCell name="operateModule" title="操作模块" align="left"
		width="300" formatter="LogManager.formatLink"></nui:gridCell>
	<nui:gridCell name="logDes" formatter="LogManager.formatDetail"
		title="日志信息" align="left" width="300"></nui:gridCell>
	<nui:gridCell name="userName" title="创建者" align="center" width="100"></nui:gridCell>
	<nui:gridCell name="logDate" title="记录时间" align="center" width="100"
		formatter="LogManager.formateLogDate"></nui:gridCell>
</nui:grid>
</div>
