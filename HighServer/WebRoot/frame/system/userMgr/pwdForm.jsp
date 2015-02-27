<%@ page language="java" pageEncoding="UTF-8"%>
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/jquery.validate/jquery.validate.js"></script>
		<script type="text/javascript"
			src="${ctx}/frame/js/plugin/jquery.validate/additional-methods.js"></script>
<script type="text/javascript">
			$(function(){
				$("#btnReset").bind("click",function(){
					$("input[type=password]").val("");
				});
				submitForm=function(){
					$.ajax({
						type : "post",
						url : WWWROOT+ "/sys/userMgr/repasswordSubmit",
						data : {
							oldPwd : $("#oldPwd").val(),
							newPwd : $("#newPwd").val()
						},
						dataType : 'json',
						success : function(data) {
							if (data==1) {
								$.alert("密码修改已经成功！");
							} else if(data==0){
								$.alert("密码修改失败，请与管理员联系！");
							} else if(data==-1){
								$.alert("原密码输入不正确！");
							}
						},
						error : function(XMLHttpRequest,textStatus,errorThrown) {
							$.alert("密码修改失败，请稍后再次尝试修改！！");
						}
					});
				} 
			});	
	</script>
<nui:validate formId="passwordForm" onclick="passwordFormSubmit"
	callback="submitForm"></nui:validate>
<nui:panel style="width:600px;margin:0 auto;margin-top:20px;"
	title="登录密码修改">
	<form id="passwordForm">
		<nui:field type="input">
			<nui:input title="原密码：" size="x3" require="true">
				<input type="password" id="oldPwd" name="oldPwd" required="true"
					maxlength="50" />
			</nui:input>
		</nui:field>
		<nui:field type="input">
			<nui:input title="新密码：" size="x3" require="true">
				<input type="password" id="newPwd" name="newPwd" required="true"
					maxlength="50" />
			</nui:input>
		</nui:field>
		<nui:field type="input">
			<nui:input title="确认新密码：" size="x3" require="true">
				<input type="password" name="renewPwd" id="renewPwd" required="true"
					maxlength="50" sametext="#newPwd" />
			</nui:input>
		</nui:field>
		<nui:field type="button"
			style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">
			<nui:button title="保存" id="btnCommit" onclick="passwordFormSubmit()"></nui:button>
			<nui:button title="重置" id="btnReset" type="button"></nui:button>
		</nui:field>
	</form>
</nui:panel>
