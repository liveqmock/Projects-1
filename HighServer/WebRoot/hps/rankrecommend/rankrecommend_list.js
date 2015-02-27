$(function() {
	RankrecommendManager = {				
				addCheck : function() {					
					return true;
				},
				
				addRankrecommend : function() {
					window.open(WWWROOT + "/rankrecommend/add_form");
				},
				
				editRankrecommend : function(grid, rowData) {
					window.open(WWWROOT+ "/rankrecommend/edit_form?rankrecommendId="+ rowData.rankrecommendId);
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/rankrecommend/view_form?rankrecommendId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteRankrecommend : function(grid, rankrecommendIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/rankrecommend/delete",
								data : {
									rankrecommendIds: rankrecommendIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										RankrecommendManager.reloadGrid();
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
					$("#rankrecommendListGrid").jqGrid("setGridParam", {
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