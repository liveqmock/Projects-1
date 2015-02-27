$(function() {
	
	// 页面操作类型，add(添加)？edit(编辑)？view(查看)
	var operation = $("#operation").val();
	userMgr = {
		// 初始化页面信息
		init : function() {
			// 编辑状态
			if (operation != 'view') {
				// 所属角色按钮
				$("#selRoles").bind("dblclick", function() {
					var hiddenSelRoles = $("#hiddenSelRoles").val();
					var result = window
							.showModalDialog(
									WWWROOT
											+ "/frame/system/selRole.jsp?limit=true",
									[hiddenSelRoles, $("#selRoles").val()],
									"dialogWidth:670px;dialogHeight:350px;center:yes;help:no;scroll:no;status:no;resizable:yes;");
					$("#selRoles").focus();
										
					$("#selRoles").removeClass("gray_font");
					if (!result) {
						return;
					}
					$("#selRoles").val(result[1]);
					var hiddenSelRoles = result[0];
					$("#hiddenSelRoles").val(hiddenSelRoles);
				});
								
				this.initTips();
			} else {
				// 查看的时候不显示保存按钮
				$("input").attr("readOnly", "readOnly");
				$("input[type='radio']").attr("disabled", "disabled");
				$("textarea").attr("readOnly", "readOnly");
				$("#btnCommit").hide();
			}
		},
		//初始化选择组织和选择角色的提示信息
		initTips:function(){
			var selectedOrgs=$("#selOrgUnits").val();
					
			if(selectedOrgs==''){
				$("#selOrgUnits").addClass("gray_font");
				$("#selOrgUnits").val("请双击选择所属组织");
			}
			
			var selectedRoles=$("#selRoles").val();					
			
			if(selectedRoles==''){
				$("#selRoles").addClass("gray_font");
				$("#selRoles").val("请双击选择所属角色");
			}
		},
		//去除掉提示信息
		removeTips:function(){
			var selectedOrgs=$("#selOrgUnits").val();
			if(selectedOrgs=='请双击选择所属组织'){				
				$("#selOrgUnits").val("");
			}
			
			var selectedRoles=$("#selRoles").val();					
			if(selectedRoles=='请双击选择所属角色'){				
				$("#selRoles").val("");
			}
		}
	};
	// 执行初始化方法
	userMgr.init();
	
});