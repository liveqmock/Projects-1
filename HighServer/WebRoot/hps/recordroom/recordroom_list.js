function changePage() {
	$("#listGrid").show();
	$("#formIframe").hide();
}

$(function() {
	RecordroomManager = {				
				addCheck : function() {					
					return true;
				},
				
				addRecordroom : function() {
					$("#listGrid").hide();
					$("#formIframe").attr("src", WWWROOT + "/recordroom/add_form");
					$("#formIframe").show();
				},
				
				editRecordroom : function(grid, rowData) {
					$("#listGrid").hide();
					$("#formIframe").attr("src", WWWROOT+ "/recordroom/edit_form?recordroomId="+ rowData.id);
					$("#formIframe").show();
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/recordroom/view_form?recordroomId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteRecordroom : function(grid, recordroomIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/recordroom/delete",
								data : {
									recordroomIds: recordroomIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										RecordroomManager.reloadGrid();
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
					$("#recordroomListGrid").jqGrid("setGridParam", {
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