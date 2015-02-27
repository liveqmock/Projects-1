<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑userscoredetail">
<script type="text/javascript" src="${ctx}/record/userscoredetail/userscoredetail_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="userscoredetailForm" onclick="userscoredetailFormSubmit"
		callback="userscoredetailMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑userscoredetail">
		<form id="userscoredetailForm">
            <nui:field type="input">
				<nui:input title="id：" size="x3" require="true">
					<input type="text" id="id" name="id"
						value="${userscoredetail.id}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="userid：" size="x3" require="true">
					<input type="text" id="userid" name="userid"
						value="${userscoredetail.userid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="levelkey：" size="x3" require="true">
					<input type="text" id="levelkey" name="levelkey"
						value="${userscoredetail.levelkey}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="score：" size="x3" require="true">
					<input type="text" id="score" name="score"
						value="${userscoredetail.score}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="cratetime：" size="x3" require="true">
					<input type="text" id="cratetime" name="cratetime"
						value="${userscoredetail.cratetime}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="userscoredetailFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
