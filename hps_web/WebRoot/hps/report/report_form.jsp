<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑report">
<script type="text/javascript" src="${ctx}/record/report/report_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="reportForm" onclick="reportFormSubmit"
		callback="reportMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑report">
		<form id="reportForm">
            <nui:field type="input">
				<nui:input title="id：" size="x3" require="true">
					<input type="text" id="id" name="id"
						value="${report.id}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="userid：" size="x3" require="true">
					<input type="text" id="userid" name="userid"
						value="${report.userid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="xcode：" size="x3" require="true">
					<input type="text" id="xcode" name="xcode"
						value="${report.xcode}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="ycode：" size="x3" require="true">
					<input type="text" id="ycode" name="ycode"
						value="${report.ycode}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="occtime：" size="x3" require="true">
					<input type="text" id="occtime" name="occtime"
						value="${report.occtime}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="type：" size="x3" require="true">
					<input type="text" id="type" name="type"
						value="${report.type}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="reportFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
