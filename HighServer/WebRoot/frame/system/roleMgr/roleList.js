function changePage() {
	$("#listGrid").show();
	$("#formIframe").hide();
}

$(function() {
			treeActive = function(orgId, orgName, orgType) {
				RoleManager.orgId = orgId;
				RoleManager.orgName = orgName;
				RoleManager.reloadGrid();
			};
			RoleManager = {
				orgId : defaultOrgId,
				orgName : defaultOrgName,
				addCheck : function() {
					this.orgId = "00000000000000000000000000000000";
					// 检查是否允许添加
					//if (this.orgId == ""
					//		|| /^00000000000000000000000000000000/
					//				.test(this.orgId)) {
					//	$.alert("请先选择公司！");
					//	return false;
					//}
					return true;
				},
				/**
				 * 添加组织机构
				 */
				addRole : function() {
					$("#listGrid").hide();
					$("#formIframe").attr("src", WWWROOT + "/sys/roleMgr/addRoleFormPage?orgId="
							+ this.orgId);
					$("#formIframe").show();
				},
				/**
				 * 修改选中的组织机构
				 */
				editRole : function(grid, rowData) {
					$("#listGrid").hide();
					$("#formIframe").attr("src", WWWROOT
							+ "/sys/roleMgr/editRoleFormPage?roleId="
							+ rowData.roleId);
					$("#formIframe").show();
				},
				/**
				 * 删除之前验证
				 */
				delCheck : function() {
					return true;
				},
				/**
				 * 删除选中的组织机构
				 */
				deleteRole : function(grid, roleIds) {
					if (!confirm("确认要删除选中角色？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/sys/roleMgr/deleteRole",
								data : ({
									orgId : this.orgId,
									roleIds : roleIds
								}),
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功。");
										RoleManager.reloadGrid();
									} else {
										$.alert("删除失败，原因：" + data.msg);
									}
								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									$.alert("删除失败，请稍后再次尝试删除！！");
								}
							});
				},
				/**
				 * 格式化角色名称
				 */
				formatRoleName : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/sys/roleMgr/viewRoleFormPage?roleId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				rolePermission : function(grid) {
					var selectedIds = grid.jqGrid("getGridParam", "selarrrow");
					if (selectedIds == '') {
						$.alert("请选择需需要分配权限的角色。");
						return false;
					}
					if ((selectedIds + "").indexOf(",") != -1) {
						$.alert("选择的角色不能多于一个。");
						return false;
					}
					window.open(WWWROOT + "/sys/roleMgr/authorizePage?roleId="
							+ selectedIds);
				},
				/**
				 * 刷新grid
				 */
				reloadGrid : function() {
					$("#roleListGrid").jqGrid("setGridParam", {
								page : 1,
								postData : {
									orgId : this.orgId
								}
							}).trigger("reloadGrid");
				},
				callBack : function() {
					$.alert("提交成功。");
				}
			}
		});