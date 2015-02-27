$(function() {
	MedialevelManager = {				
				addCheck : function() {					
					return true;
				},
				
				addMedialevel : function() {
					window.open(WWWROOT + "/scorelevel/add_form");
				},
				
				editMedialevel : function(grid, rowData) {
					$("#listGrid").hide();
					$("#formIframe").attr("src", window.WWWROOT
							+ "/scorelevel/edit_form?ScorelevelId="+ rowData.levelkey);
					$("#formIframe").show();
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/scorelevel/view_form?ScorelevelId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteMedialevel : function(grid, medialevelIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/scorelevel/ScorelevelIds",
								data : {
									medialevelIds: medialevelIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										MedialevelManager.reloadGrid();
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
					$("#medialevelListGrid").jqGrid("setGridParam", {
								page : 1,
								postData : {
									
								}
							}).trigger("reloadGrid");
				},
				callBack : function() {
					$.alert("提交成功");
				}
			};
		});