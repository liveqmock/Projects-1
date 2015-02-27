$(function() {
	$("#roomid").change(function(){
		$("#roomname").val($(this).find("option:selected").text());
	});
	// 页面操作类型：add(添加)-edit(编辑)-view(查看)
	var operation = $("#operation").val();
	mediaMgr = {
		init : function() {			
			// 查看的时候不显示保存按钮
			if (operation == "view") {
				$("input").attr("readOnly", "readOnly");
				$("textarea").attr("readOnly", "readOnly");
				$("#btnCommit").hide();								
			} 
		},
		//文件上传成功后的回调方法
		uploadRevoke : function(file) {
			$("#title").val(file.name.substring(0,file.name.lastIndexOf(".")));
		},
		// 附件验证
		validateUpload : function() {
			// 验证合法性
			if (haveUploaded_mediaAttach) {
				return true;
			} else {
				$.msg('正在上传附件，待附件上传完毕再保存');
				return false;
			}
		},
		submitForm : function() {
			var deleteId = $("#deletedIds_mediaAttach").val();
			var fileId = $("#fileIds_mediaAttach").val();
			var title = $("#paneltitle").val();
			
			var attachHidden = $("input[name='successUploadFileName_mediaAttach']").val();
			if(title=="新增音乐") {
				if(attachHidden==undefined) {
					$.alert("请上传音乐");
					return;
				}
			} else {
				if(deleteId.length>1) {
					if(fileId.length<1) {
						$.alert("请上传音乐");
						return;
					}
				}
			}
			
			var url = WWWROOT + "/media/submit_form";
			AjaxSubmit(url, function() {
						try {
							window.parent.$.alert("提交成功");
							window.parent.changePage();
							if (window.parent.MediaManager) {
								window.parent.MediaManager.reloadGrid();
							}
						} catch (e) {
							alert("提交成功,刷新页面后方可看到最新数据。");
						}
						window.close();
					},"mediaForm");
		}
	};
	// 执行初始化
	mediaMgr.init();
});