$(function() {
	UserscoredetailManager = {				
				addCheck : function() {					
					return true;
				},
				
				addUserscoredetail : function() {
					window.open(WWWROOT + "/userscoredetail/add_form");
				},
				
				editUserscoredetail : function(grid, rowData) {
					window.open(WWWROOT+ "/userscoredetail/edit_form?userscoredetailId="+ rowData.userscoredetailId);
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/userscoredetail/view_form?userscoredetailId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteUserscoredetail : function(grid, userscoredetailIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/userscoredetail/delete",
								data : {
									userscoredetailIds: userscoredetailIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										UserscoredetailManager.reloadGrid();
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
					$("#userscoredetailListGrid").jqGrid("setGridParam", {
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