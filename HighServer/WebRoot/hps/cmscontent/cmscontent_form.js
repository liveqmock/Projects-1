$(function() {
	//编辑器图片上传
	$('#editor_id').xheditor({
		upImgUrl : WWWROOT + "/editorAttach/imageUpload?immediate=1",
		upImgExt : "jpg,jpeg,gif,png,bmp"
	});
	// 页面操作类型：add(添加)-edit(编辑)-view(查看)
	var operation = $("#operation").val();
	cmscontentMgr = {
		init : function() {			
			// 查看的时候不显示保存按钮
			if (operation == "view") {
				$("input").attr("readOnly", "readOnly");
				$("textarea").attr("readOnly", "readOnly");
				$("#btnCommit").hide();								
			} 
		},
		submitForm : function() {
			var url = WWWROOT + "/cmscontent/submit_form";
			AjaxSubmit(url, function() {
						try {
							window.opener.$.alert("提交成功。");
							if (window.opener.CmscontentManager) {
								window.opener.CmscontentManager.reloadGrid();
							}
						} catch (e) {
							alert("提交成功,刷新页面后方可看到最新数据。");
						}
						window.close();
					},"cmscontentForm");
		}
	};
	// 执行初始化
	cmscontentMgr.init();
});