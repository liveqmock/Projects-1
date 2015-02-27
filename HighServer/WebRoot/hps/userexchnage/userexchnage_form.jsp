<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑userexchnage">
<script type="text/javascript" src="${ctx}/record/userexchnage/userexchnage_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="userexchnageForm" onclick="userexchnageFormSubmit"
		callback="userexchnageMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑userexchnage">
		<form id="userexchnageForm">
            <nui:field type="input">
				<nui:input title="id：" size="x3" require="true">
					<input type="text" id="id" name="id"
						value="${userexchnage.id}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="userid：" size="x3" require="true">
					<input type="text" id="userid" name="userid"
						value="${userexchnage.userid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="productid：" size="x3" require="true">
					<input type="text" id="productid" name="productid"
						value="${userexchnage.productid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="producttitle：" size="x3" require="true">
					<input type="text" id="producttitle" name="producttitle"
						value="${userexchnage.producttitle}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="status：" size="x3" require="true">
					<input type="text" id="status" name="status"
						value="${userexchnage.status}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="score：" size="x3" require="true">
					<input type="text" id="score" name="score"
						value="${userexchnage.score}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="handler：" size="x3" require="true">
					<input type="text" id="handler" name="handler"
						value="${userexchnage.handler}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="handlername：" size="x3" require="true">
					<input type="text" id="handlername" name="handlername"
						value="${userexchnage.handlername}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="reicaddress：" size="x3" require="true">
					<input type="text" id="reicaddress" name="reicaddress"
						value="${userexchnage.reicaddress}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="reicphone：" size="x3" require="true">
					<input type="text" id="reicphone" name="reicphone"
						value="${userexchnage.reicphone}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="createtime：" size="x3" require="true">
					<input type="text" id="createtime" name="createtime"
						value="${userexchnage.createtime}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="userexchnageFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
