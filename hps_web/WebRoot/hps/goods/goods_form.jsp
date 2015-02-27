<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑goods">
<script type="text/javascript" src="${ctx}/record/goods/goods_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="goodsForm" onclick="goodsFormSubmit"
		callback="goodsMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑goods">
		<form id="goodsForm">
            <nui:field type="input">
				<nui:input title="id：" size="x3" require="true">
					<input type="text" id="id" name="id"
						value="${goods.id}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="image：" size="x3" require="true">
					<input type="text" id="image" name="image"
						value="${goods.image}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="largeimage：" size="x3" require="true">
					<input type="text" id="largeimage" name="largeimage"
						value="${goods.largeimage}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="title：" size="x3" require="true">
					<input type="text" id="title" name="title"
						value="${goods.title}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="score：" size="x3" require="true">
					<input type="text" id="score" name="score"
						value="${goods.score}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="type：" size="x3" require="true">
					<input type="text" id="type" name="type"
						value="${goods.type}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="detail：" size="x3" require="true">
					<input type="text" id="detail" name="detail"
						value="${goods.detail}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="goodsFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
