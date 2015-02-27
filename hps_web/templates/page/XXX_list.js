$(function() {
	${classNamePrefix}Manager = {				
				addCheck : function() {					
					return true;
				},
				
				add${classNamePrefix} : function() {
					window.open(WWWROOT + "/${params.moduleName}/add_form");
				},
				
				edit${classNamePrefix} : function(grid, rowData) {
					window.open(WWWROOT+ "/${params.moduleName}/edit_form?${params.moduleName}Id="+ rowData.${params.moduleName}Id);
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/${params.moduleName}/view_form?${params.moduleName}Id="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				delete${classNamePrefix} : function(grid, ${params.moduleName}Ids) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/${params.moduleName}/delete",
								data : {
									${params.moduleName}Ids: ${params.moduleName}Ids
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										${classNamePrefix}Manager.reloadGrid();
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
					$("#${params.moduleName}ListGrid").jqGrid("setGridParam", {
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