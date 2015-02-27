$(function() {
	CommentManager = {				
				addCheck : function() {					
					return true;
				},
				
				addComment : function() {
					window.open(WWWROOT + "/comment/add_form");
				},
				
				editComment : function(grid, rowData) {
					window.open(WWWROOT+ "/comment/edit_form?commentId="+ rowData.commentId);
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/comment/view_form?commentId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteComment : function(grid, commentIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/comment/delete",
								data : {
									commentIds: commentIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										CommentManager.reloadGrid();
									} else {
										$.alert("删除失败，原因：" + data.msg);
									}
								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									$.alert("删除失败，请稍后再次尝试删除！");
								}
							});
				},											
				reloadGrid : function() {
					$("#commentListGrid").jqGrid("setGridParam", {
								page : 1,
								postData : {
									
								}
							}).trigger("reloadGrid");
				},
				formatTime : function(val, cell, colpos, rwdat, _act) {
					if (val == null)
						return '';
					var time = new Date(val.time);
					return time.dateFormat("Y-m-d hh:mm:ss");
				},
				callBack : function() {
					$.alert("提交成功");
				}
			};
		});