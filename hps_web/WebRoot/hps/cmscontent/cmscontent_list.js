$(function() {
	CmscontentManager = {				
				addCheck : function() {					
					return true;
				},
				addCmscontent : function() {
					window.open(WWWROOT + "/cmscontent/add_form");
				},
				
				editCmscontent : function(grid, rowData) {
					window.open(WWWROOT+ "/cmscontent/edit_form?cmscontentId="+ rowData.id);
				},
				
				formatTitle : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/cmscontent/view_form?cmscontentId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				delCheck : function() {
					return true;
				},
				
				deleteCmscontent : function(grid, cmscontentIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/cmscontent/delete",
								data : {
									cmscontentIds: cmscontentIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										CmscontentManager.reloadGrid();
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
				formatCreateTime : function(val, cell, colpos, rwdat, _act) {
					var time = new Date(val.time);
					return time.dateFormat("Y-m-d hh:mm:ss");
				},
				reloadGrid : function() {
					$("#cmscontentListGrid").jqGrid("setGridParam", {
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