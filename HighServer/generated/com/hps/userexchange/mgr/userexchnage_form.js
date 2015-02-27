$(function() {
	// 页面操作类型：add(添加)-edit(编辑)-view(查看)
	var operation = $("#operation").val();
	userexchnageMgr = {
		init : function() {			
			// 查看的时候不显示保存按钮
			if (operation == "view") {
				$("input").attr("readOnly", "readOnly");
				$("textarea").attr("readOnly", "readOnly");
				$("#btnCommit").hide();								
			} 
		},
		submitForm : function() {
			var url = WWWROOT + "/userexchnage/submit_form";
			AjaxSubmit(url, function() {
						try {
							window.opener.$.alert("提交成功。");
							if (window.opener.UserexchnageManager) {
								window.opener.UserexchnageManager.reloadGrid();
							}
						} catch (e) {
							alert("提交成功,刷新页面后方可看到最新数据。");
						}
						window.close();
					},"userexchnageForm");
		}
	};
	// 执行初始化
	userexchnageMgr.init();
});