$(function() {
	UserexchnageManager = {				
				addCheck : function() {					
					return true;
				},
				
				addUserexchnage : function() {
					window.open(WWWROOT + "/userexchnage/add_form");
				},
				
				editUserexchnage : function(grid, rowData) {
					window.open(WWWROOT+ "/userexchnage/edit_form?userexchnageId="+ rowData.userexchnageId);
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/userexchnage/view_form?userexchnageId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteUserexchnage : function(grid, userexchnageIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/userexchnage/delete",
								data : {
									userexchnageIds: userexchnageIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										UserexchnageManager.reloadGrid();
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
					$("#userexchnageListGrid").jqGrid("setGridParam", {
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