$(function() {
	RankscoreManager = {				
				addCheck : function() {					
					return true;
				},
				
				addRankscore : function() {
					window.open(WWWROOT + "/rankscore/add_form");
				},
				
				editRankscore : function(grid, rowData) {
					window.open(WWWROOT+ "/rankscore/edit_form?rankscoreId="+ rowData.rankscoreId);
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/rankscore/view_form?rankscoreId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteRankscore : function(grid, rankscoreIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/rankscore/delete",
								data : {
									rankscoreIds: rankscoreIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										RankscoreManager.reloadGrid();
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
					$("#rankscoreListGrid").jqGrid("setGridParam", {
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