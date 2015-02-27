$(function() {
	// 处理操作资源全选按钮
	$("#operatorResourceAll").click(function() {
		if ($(this).attr("checked") == 'checked') {
			$('input[name="operatorRes"]:visible').attr("checked", true);
			$('#operatorPanelId fieldset legend input:visible').attr("checked",
					true);
		} else {
			$('input[name="operatorRes"]:visible').removeAttr("checked");
			$('#operatorPanelId fieldset legend input:visible')
					.removeAttr("checked");
		}
	});
	// 处理fieldset上面的全选
	$('fieldset legend input').click(function() {
		if ($(this).attr("checked") == 'checked') {
			$(this).parent().siblings().find("input:visible").attr("checked",
					true);
		} else {
			$(this).parent().siblings().find("input:visible")
					.removeAttr("checked");
		}
	});
	// 判断fieldset是否选中
	$('fieldset').each(function(i) {
		// 如果该fieldset下面没有可选的资源，整个fieldset隐藏
		if ($(this).find("div input:visible").length == 0) {
			$(this).hide();
		}
		// 默认情况下fieldset下的全选按钮选中，只要有一个可见的checkbox未选中，则反选
		if ($(this).find("div input[checked]:visible").length != $(this)
				.find("div input[type='checkbox']:visible").length) {
			$(this).removeAttr("checked");
		} else if ($(this).find("legend input:visible").length > 0) {
			$(this).find("legend input:visible")[0].checked = true;
		}
	});
	// 最上面的全选按钮
	if ($('#operatorPanelId fieldset legend input[checked]:visible').length != $('#operatorPanelId fieldset legend input:visible').length) {
		$("#operatorResourceAll").removeAttr("checked");
	} else {
		$("#operatorResourceAll").attr("checked", true);
	}
	// 提交按钮事件处理
	Authorize = {
		// 暂时未使用该方式提交
		submitForm : function() {
			var url = WWWROOT + "/sys/roleMgr/saveRoleAuthrize";
			AjaxSubmit(url, function() {
						window.opener.$.alert("提交成功。");
						window.opener.RoleManager.reloadGrid();
						window.close();
					},"nsForm");
		}
	}
});