<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑rankscore">
<script type="text/javascript" src="${ctx}/frame/rankscore/rankscore_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="rankscoreForm" onclick="rankscoreFormSubmit"
		callback="rankscoreMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑rankscore">
		<form id="rankscoreForm">
            <nui:field type="input">
				<nui:input title="id：" size="x3" require="true">
					<input type="text" id="id" name="id"
						value="${rankscore.id}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="mediaid：" size="x3" require="true">
					<input type="text" id="mediaid" name="mediaid"
						value="${rankscore.mediaid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="memberid：" size="x3" require="true">
					<input type="text" id="memberid" name="memberid"
						value="${rankscore.memberid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="membername：" size="x3" require="true">
					<input type="text" id="membername" name="membername"
						value="${rankscore.membername}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="medianame：" size="x3" require="true">
					<input type="text" id="medianame" name="medianame"
						value="${rankscore.medianame}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="authorid：" size="x3" require="true">
					<input type="text" id="authorid" name="authorid"
						value="${rankscore.authorid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="authorname：" size="x3" require="true">
					<input type="text" id="authorname" name="authorname"
						value="${rankscore.authorname}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="score：" size="x3" require="true">
					<input type="text" id="score" name="score"
						value="${rankscore.score}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="rankscoreFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
