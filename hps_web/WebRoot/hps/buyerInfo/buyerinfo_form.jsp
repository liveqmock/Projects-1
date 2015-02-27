<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate" title="编辑buyerinfo">
<script type="text/javascript" src="${ctx}/record/buyerinfo/buyerinfo_form.js"></script>
</nui:head>
<nui:body>
	<nui:validate formId="buyerinfoForm" onclick="buyerinfoFormSubmit"
		callback="buyerinfoMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑buyerinfo">
		<form id="buyerinfoForm">
            <nui:field type="input">
				<nui:input title="id：" size="x3" require="true">
					<input type="text" id="id" name="id"
						value="${buyerinfo.id}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="userid：" size="x3" require="true">
					<input type="text" id="userid" name="userid"
						value="${buyerinfo.userid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="goodsid：" size="x3" require="true">
					<input type="text" id="goodsid" name="goodsid"
						value="${buyerinfo.goodsid}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="realname：" size="x3" require="true">
					<input type="text" id="realname" name="realname"
						value="${buyerinfo.realname}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="address：" size="x3" require="true">
					<input type="text" id="address" name="address"
						value="${buyerinfo.address}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="phone：" size="x3" require="true">
					<input type="text" id="phone" name="phone"
						value="${buyerinfo.phone}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="postcode：" size="x3" require="true">
					<input type="text" id="postcode" name="postcode"
						value="${buyerinfo.postcode}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="buyerinfoFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.close();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
