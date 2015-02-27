$(function() {
	BuyerinfoManager = {				
				addCheck : function() {					
					return true;
				},
				
				addBuyerinfo : function() {
					window.open(WWWROOT + "/buyerinfo/add_form");
				},
				
				editBuyerinfo : function(grid, rowData) {
					window.open(WWWROOT+ "/buyerinfo/edit_form?buyerinfoId="+ rowData.buyerinfoId);
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/buyerinfo/view_form?buyerinfoId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteBuyerinfo : function(grid, buyerinfoIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/buyerinfo/delete",
								data : {
									buyerinfoIds: buyerinfoIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										BuyerinfoManager.reloadGrid();
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
					$("#buyerinfoListGrid").jqGrid("setGridParam", {
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