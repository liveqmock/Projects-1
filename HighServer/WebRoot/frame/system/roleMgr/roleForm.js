$(function() {
	// 页面操作类型，add(添加)？edit(编辑)？view(查看)
	var operation = $("#operation").val();
	roleMgr = {
		init : function() {
			
			// 查看的时候不显示保存按钮
			if (operation == "view") {
				$("input").attr("readOnly", "readOnly");
				$("textarea").attr("readOnly", "readOnly");
				$("#btnCommit").hide();
				
				$("#assignedOperationDiv").show();
			} else {
				$("#assignedOperationDiv").hide();
				
				// 角色用户双击
				$("#roleUserNames").bind("dblclick", function() {
					var selectedUsers = new Array();
					selectedUsers.push($("#roleUserIds").val());
					selectedUsers.push($("#roleUserNames").val());
					var result = window
							.showModalDialog(
									WWWROOT
											+ "/frame/system/selUserByOrgUnit.jsp?companyId="
											+ $("#orgId").val(),
									selectedUsers,
									"dialogWidth:670px;dialogHeight:380px;center:yes;help:no;scroll:no;status:no;resizable:yes;");
					if (!result) {
						return;
					}
					$("#roleUserIds").val(result[0]);
					$("#roleUserNames").val(result[1]);
				});
			}
		},
		submitForm : function() {
			var url = WWWROOT + "/sys/roleMgr/submitRoleForm";
			AjaxSubmit(url, function() {
						try {
							window.parent.$.alert("提交成功。");
							window.parent.changePage();
							if (window.parent.RoleManager) {
								window.parent.RoleManager.reloadGrid();
							}
						} catch (e) {
							alert("提交成功,刷新页面后方可看到最新数据。");
						}
						window.close();
					},"roleForm");
		}
	};
	// 执行初始化
	roleMgr.init();
});