$(function() {
	MessageManager = {				
				addCheck : function() {					
					return true;
				},
				
				addMessage : function() {
					window.open(WWWROOT + "/message/add_form");
				},
				
				editMessage : function(grid, rowData) {
					window.open(WWWROOT+ "/message/edit_form?messageId="+ rowData.messageId);
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/message/view_form?messageId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				formatCreateTime : function(val, cell, colpos, rwdat, _act) {
					var time = new Date(val.time);
					return time.dateFormat("Y-m-d hh:mm:ss");
				},
				delCheck : function() {
					return true;
				},
				
				deleteMessage : function(grid, messageIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/message/delete",
								data : {
									messageIds: messageIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										MessageManager.reloadGrid();
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
					$("#messageListGrid").jqGrid("setGridParam", {
								page : 1,
								postData : {
									
								}
							}).trigger("reloadGrid");
				},
				callBack : function() {
					$.alert("提交成功");
				}
			}
		});