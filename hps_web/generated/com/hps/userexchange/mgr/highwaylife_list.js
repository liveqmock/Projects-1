$(function() {
	HighwaylifeManager = {				
				addCheck : function() {					
					return true;
				},
				
				addHighwaylife : function() {
					window.open(WWWROOT + "/highwaylife/add_form");
				},
				
				editHighwaylife : function(grid, rowData) {
					window.open(WWWROOT+ "/highwaylife/edit_form?highwaylifeId="+ rowData.highwaylifeId);
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/highwaylife/view_form?highwaylifeId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteHighwaylife : function(grid, highwaylifeIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/highwaylife/delete",
								data : {
									highwaylifeIds: highwaylifeIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										HighwaylifeManager.reloadGrid();
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
					$("#highwaylifeListGrid").jqGrid("setGridParam", {
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