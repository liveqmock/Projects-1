<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑积分等级">
<script type="text/javascript" src="${ctx}/hps/scorelevel/scorelevel_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="medialevelForm" onclick="medialevelFormSubmit"
		callback="medialevelMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑积分等级">
		<form id="medialevelForm">
            <nui:field type="input">
				<nui:input title="积分项：" size="x3" require="true">
					<input type="text" id="levelkey" name="levelkey"
						value="${Scorelevel.levelkey}" required="true" maxlength="50" readonly/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="分数：" size="x3" require="true">
					<input type="text" id="score" name="score"
						value="${Scorelevel.score}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
             <nui:field type="input">
				<nui:input title="名称：" size="x3" require="true">
					<input type="text" id="levelDes" name="levelDes"
						value="${Scorelevel.levelDes}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="备注：" size="x3" require="true">
					<input type="text" id="memo" name="memo"
						value="${Scorelevel.memo}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="medialevelFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.parent.changePage();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
