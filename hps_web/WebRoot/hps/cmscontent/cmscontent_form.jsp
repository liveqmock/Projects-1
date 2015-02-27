<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑文章">
	<script type="text/javascript"
		src="${ctx}/frame/js/plugin/xheditor-1.1.10/xheditor-1.1.10-zh-cn.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/hps/cmscontent/cmscontent_form.js"></script>
</nui:head>
<nui:body>
	<input type="hidden" id="categoryid" name="categoryid"
		value="${cmscontent.categoryid}" maxlength="50" />
	<input type="hidden" id="id" name="id"
		value="${cmscontent.id}" maxlength="50" />
	<nui:validate formId="cmscontentForm" onclick="cmscontentFormSubmit"
		callback="cmscontentMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑文章">
		<form id="cmscontentForm">
			<nui:field type="input">
				<nui:input title="标题：" size="x3" require="true">
					<input type="text" id="title" name="title"
						value="${cmscontent.title}" required="true" maxlength="50" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="内容：" size="x3">
					<textarea id="editor_id" name="content"
						style="height: 200px; width: 600px;">${cmscontent.content}</textarea>
				</nui:input>
			</nui:field>
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">
				<input type="hidden" id="operation" name="operation"
					value="${operation}" />
				<nui:button title="保存" onclick="cmscontentFormSubmit()"
					id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
