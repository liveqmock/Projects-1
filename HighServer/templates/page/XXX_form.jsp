<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
#set ($context="${ctx}")
#set ($D="$")
<nui:head plugin="validate" title="编辑${params.moduleName}">
<script type="text/javascript" src="$context/record/${params.moduleName}/${params.moduleName}_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="${params.moduleName}Form" onclick="${params.moduleName}FormSubmit"
		callback="${params.moduleName}Mgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑${params.moduleName}">
		<form id="${params.moduleName}Form">
#foreach ($fld in $params.fieldList)
            <nui:field type="input">
				<nui:input title="${fld.fieldName}：" size="x3" require="true">
					<input type="text" id="${fld.fieldName}" name="${fld.fieldName}"
						value="$D{${params.moduleName}.${fld.fieldName}}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
#end				
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="${params.moduleName}FormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
