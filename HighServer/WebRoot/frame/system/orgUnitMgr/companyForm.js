$(function() {
	// 页面操作类型，add(添加)？edit(编辑)？view(查看)
	var operation = $("#operation").val();
	orgUnitMgr = {
		// 初始化页面信息
		init : function() {
			// 编辑状态
			if (operation == 'edit') {
				// 机构编码创建后不能修改
				$("#orgEngName").attr("readOnly", "true");
				// 机构层级码创建后不能修改
				$("#orgLevelCode").attr("readOnly", "true");
			}
			// 查看的时候不显示保存按钮
			if (operation == 'view') {
				$("input").attr("readOnly", "readOnly");
				$("input[type='radio']").attr("disabled", "disabled");
				$("textarea").attr("readOnly", "readOnly");
				$("#btnCommit").hide();
			} else {
			 
			}
		},
		// 提交按钮事件处理
		submitForm : function(form) {
			var url = WWWROOT + "/sys/orgUnitMgr/submitOrgUnitForm";
			AjaxSubmit(url, function() {
						try {
							window.opener.$.alert("提交成功。");
							window.opener.OrgTree.expand($("#parentOrgId")
									.val());
							window.opener.OrgManager.reloadGrid();
						} catch (e) {
							alert("提交成功,刷新页面后方可看到最新数据。");
						}
						window.close();
					},"orgForm");
		},// 选择分管领导
		selectLeaders : function() {
			var result = window
					.showModalDialog(
							WWWROOT
									+ "/frame/system/selUserByOrgUnit.jsp?limit=true",
							null,
							"dialogWidth:670px;dialogHeight:380px;center:yes;help:no;scroll:no;status:no;resizable:yes;");
			$("#leaders").focus();
			if (!result) {
				return;
			}
			$("#leaders").val(result[1]);
			$("#leaderIds").val(result[0]);
		},
		// 选择机构负责人
		selectManagers : function() {
			var result = window
					.showModalDialog(
							WWWROOT
									+ "/frame/system/selUserByOrgUnit.jsp?limit=true",
							null,
							"dialogWidth:670px;dialogHeight:380px;center:yes;help:no;scroll:no;status:no;resizable:yes;");
			$("#managers").focus();
			if (!result) {
				return;
			}
			$("#managers").val(result[1]);
			$("#managerIds").val(result[0]);

		},// 选择上级主管机构
		selectTopOrgs : function() {
			var result = window
					.showModalDialog(
							WWWROOT
									+ "/frame/system/selOrgUnit.jsp?limit=true&onlyOne=true",
							null,
							"dialogWidth:670px;dialogHeight:380px;center:yes;help:no;scroll:no;status:no;resizable:yes;");
			$("#topOrgs").focus();
			if (!result) {
				return;
			}
			$("#topOrgs").val(result[1]);
			$("#topOrgIds").val(result[0]);
		}
	}
	// 执行初始化方法
	orgUnitMgr.init();
});