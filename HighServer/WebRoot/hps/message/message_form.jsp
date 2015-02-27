<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑message">
<script type="text/javascript" src="${ctx}/record/message/message_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="messageForm" onclick="messageFormSubmit"
		callback="messageMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑message">
		<form id="messageForm">
            <nui:field type="input">
				<nui:input title="id：" size="x3" require="true">
					<input type="text" id="id" name="id"
						value="${message.id}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="memberid：" size="x3" require="true">
					<input type="text" id="memberid" name="memberid"
						value="${message.memberid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="membername：" size="x3" require="true">
					<input type="text" id="membername" name="membername"
						value="${message.membername}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="messagetype：" size="x3" require="true">
					<input type="text" id="messagetype" name="messagetype"
						value="${message.messagetype}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="title：" size="x3" require="true">
					<input type="text" id="title" name="title"
						value="${message.title}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="content：" size="x3" require="true">
					<input type="text" id="content" name="content"
						value="${message.content}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="fromid：" size="x3" require="true">
					<input type="text" id="fromid" name="fromid"
						value="${message.fromid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="fromname：" size="x3" require="true">
					<input type="text" id="fromname" name="fromname"
						value="${message.fromname}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="cratetime：" size="x3" require="true">
					<input type="text" id="cratetime" name="cratetime"
						value="${message.cratetime}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="viewtime：" size="x3" require="true">
					<input type="text" id="viewtime" name="viewtime"
						value="${message.viewtime}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="messageFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
