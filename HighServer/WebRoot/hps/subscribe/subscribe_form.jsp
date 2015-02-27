<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑subscribe">
<script type="text/javascript" src="${ctx}/record/subscribe/subscribe_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="subscribeForm" onclick="subscribeFormSubmit"
		callback="subscribeMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑subscribe">
		<form id="subscribeForm">
            <nui:field type="input">
				<nui:input title="id：" size="x3" require="true">
					<input type="text" id="id" name="id"
						value="${subscribe.id}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="membername：" size="x3" require="true">
					<input type="text" id="membername" name="membername"
						value="${subscribe.membername}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="memberid：" size="x3" require="true">
					<input type="text" id="memberid" name="memberid"
						value="${subscribe.memberid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="subtime：" size="x3">
					<input type="text" id="subtime" name="subtime"
						value="${subscribe.subtime}" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="recordroom：" size="x3" require="true">
					<input type="text" id="recordroom" name="recordroom"
						value="${subscribe.recordroom}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="roomid：" size="x3" require="true">
					<input type="text" id="roomid" name="roomid"
						value="${subscribe.roomid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="confirm：" size="x3" require="true">
					<input type="text" id="confirm" name="confirm"
						value="${subscribe.confirm}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="confirmemeber：" size="x3" require="true">
					<input type="text" id="confirmemeber" name="confirmemeber"
						value="${subscribe.confirmemeber}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="subscribeFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
