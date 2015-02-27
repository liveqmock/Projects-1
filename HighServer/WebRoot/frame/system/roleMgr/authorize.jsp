<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head title="权限分配">
	<script type="text/javascript">
			$(function(){
				//将该角色已经拥有的资源选上
				<c:forEach items="${roleOperatorList}" var="roleOperator">
					$("#${roleOperator.operatorId}").attr("checked",true);
				</c:forEach>
				//将用户有权限修改的资源显示出来
				<c:forEach items="${userRoleOperatorList}" var="userRoleOperator">
					$("#${userRoleOperator.operatorId}").parent().show();
				</c:forEach>
			});
		</script>
	<script type="text/javascript"
		src="${ctx}/frame/system/roleMgr/authorize.js"></script>
</nui:head>
<nui:body>
	<nui:panel id="operatorPanelId" title="操作资源"
		style="margin:20px auto;width:900px;height:600px;" isToggle="false">
		<form id="nsForm" action="${ctx}/sys/roleMgr/saveRoleAuthrize"
			method="post">
			<div style="margin-left: 11px;">
				<input type='checkbox' id='operatorResourceAll'
					name='operatorResAll' style="padding-left: 3px;" />
				全选
			</div>
			<c:forEach items="${systemNameBeanList}" var="systemNameBean">
				<c:forEach items="${systemNameBean.modelNameBeanList}"
					var="modelNameBean">
					<fieldset style="margin: 10px;" id="operatorDefSet">
						<legend style="margin-left: 2px;">
							<input type='checkbox' value='${modelNameBean.modleName}'
								name=${modelNameBean.modleName } />
							${modelNameBean.modleName}
						</legend>
						<c:forEach items="${modelNameBean.operatorBeanList}"
							var="operatorBean">
							<div
								style="width: 170px; float: left; margin: 2px 0 2px 2px; display: none;">
								<input type='checkbox' value='${operatorBean.operatorId}'
									id='${operatorBean.operatorId}' name='operatorRes' />
								${operatorBean.operatorName}
							</div>
						</c:forEach>
					</fieldset>
				</c:forEach>
			</c:forEach>
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">
				<input name='roleId' type='hidden' value='${param.roleId}' />
				<nui:button title="保存" id="btnCommit" type="submit"></nui:button>
				<nui:button title="关闭" id="cancelCommit" onclick='window.close()'></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
