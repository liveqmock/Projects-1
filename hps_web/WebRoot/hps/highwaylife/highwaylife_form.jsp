<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑highwaylife">
<script type="text/javascript" src="${ctx}/record/highwaylife/highwaylife_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="highwaylifeForm" onclick="highwaylifeFormSubmit"
		callback="highwaylifeMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑highwaylife">
		<form id="highwaylifeForm">
            <nui:field type="input">
				<nui:input title="id：" size="x3" require="true">
					<input type="text" id="id" name="id"
						value="${highwaylife.id}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="type：" size="x3" require="true">
					<input type="text" id="type" name="type"
						value="${highwaylife.type}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="title：" size="x3" require="true">
					<input type="text" id="title" name="title"
						value="${highwaylife.title}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="userid：" size="x3" require="true">
					<input type="text" id="userid" name="userid"
						value="${highwaylife.userid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="createtime：" size="x3" require="true">
					<input type="text" id="createtime" name="createtime"
						value="${highwaylife.createtime}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="detail：" size="x3" require="true">
					<input type="text" id="detail" name="detail"
						value="${highwaylife.detail}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="highwaylifeFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
