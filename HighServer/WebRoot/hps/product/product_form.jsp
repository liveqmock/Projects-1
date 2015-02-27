<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑product">
<script type="text/javascript" src="${ctx}/record/product/product_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="productForm" onclick="productFormSubmit"
		callback="productMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑product">
		<form id="productForm">
            <nui:field type="input">
				<nui:input title="id：" size="x3" require="true">
					<input type="text" id="id" name="id"
						value="${product.id}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="title：" size="x3" require="true">
					<input type="text" id="title" name="title"
						value="${product.title}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="protype：" size="x3" require="true">
					<input type="text" id="protype" name="protype"
						value="${product.protype}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="fileid：" size="x3" require="true">
					<input type="text" id="fileid" name="fileid"
						value="${product.fileid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="score：" size="x3" require="true">
					<input type="text" id="score" name="score"
						value="${product.score}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="creator：" size="x3" require="true">
					<input type="text" id="creator" name="creator"
						value="${product.creator}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="creatorname：" size="x3" require="true">
					<input type="text" id="creatorname" name="creatorname"
						value="${product.creatorname}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="createtime：" size="x3" require="true">
					<input type="text" id="createtime" name="createtime"
						value="${product.createtime}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="productFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
