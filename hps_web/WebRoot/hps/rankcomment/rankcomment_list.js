$(function() {
	RankcommentManager = {				
				addCheck : function() {					
					return true;
				},
				
				addRankcomment : function() {
					window.open(WWWROOT + "/rankcomment/add_form");
				},
				
				editRankcomment : function(grid, rowData) {
					window.open(WWWROOT+ "/rankcomment/edit_form?rankcommentId="+ rowData.rankcommentId);
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/rankcomment/view_form?rankcommentId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteRankcomment : function(grid, rankcommentIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/rankcomment/delete",
								data : {
									rankcommentIds: rankcommentIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										RankcommentManager.reloadGrid();
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
					$("#rankcommentListGrid").jqGrid("setGridParam", {
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