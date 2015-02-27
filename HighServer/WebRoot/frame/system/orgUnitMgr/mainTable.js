$(function() {
	treeActive = function(orgId, orgName, orgType) {
		OrgManager.orgId = orgId;
		OrgManager.orgName = orgName;
		OrgManager.orgType = orgType;
		OrgManager.doQuery();
	}
	OrgManager = {
		orgId : defaultOrgId,
		orgName : defaultOrgName,
		orgType : true,
		doQuery : function() {
			var pageNumber = 1;
			if ($("#pageNumber").val()) {
				pageNumber = $("#pageNumber").val();
			}
			var url = WWWROOT + "/sys/orgUnitMgr/orgTableListPage?parentId="
					+ this.orgId + "&defaultOrgId=" + defaultOrgId
					+ "&sessionUserId=" + sessionUserId + "&page=" + pageNumber
					+ "&rows=10";
			AjaxSubmit(url, OrgManager.reDraw, null,null, 'html');
		},
		reDraw : function(html) {
			$("#tableId").html(html);
		},
		addCheck : function() {
			// 检查是否允许添加
			if (this.orgId == "" || this.orgName == "") {
				$.alert("请先选择上级公司！");
				return false;
			}
			if (!this.orgType) {
				$.alert("部门下面不能再添加组织！");
				return false;
			}
			return true;
		},
		/**
		 * 添加公司
		 */
		addCompany : function($grid) {
			if (!OrgManager.addCheck()) {
				return false;
			}
			window
					.open(WWWROOT
							+ "/sys/orgUnitMgr/addOrgFormPage?orgType=true&parentOrgId="
							+ this.orgId);
		},
		/**
		 * 添加部门
		 */
		addDepartment : function($grid) {
			if (!OrgManager.addCheck()) {
				return false;
			}
			window
					.open(WWWROOT
							+ "/sys/orgUnitMgr/addOrgFormPage?orgType=false&parentOrgId="
							+ this.orgId);
		},
		/**
		 * 修改选中的组织机构
		 */
		editOrg : function(grid, rowData) {
			var records = $('input[name=orgId]:checked').length;
			if (records == 0) {
				$.alert("请选择要修改的记录。");
				return false;
			}
			if (records > 1) {
				$.alert("每次只能修改一条记录。");
				return false;
			}
			var selectedId = $('input[name=orgId]:checked').val();
			window.open(WWWROOT + "/sys/orgUnitMgr/editOrgFormPage?orgId="
					+ selectedId);
		},
		/**
		 * 删除选中的组织机构
		 */
		delOrg : function() {
			if ($('input[name=orgId]:checked').length == 0) {
				$.alert("请选择要删除的记录。");
				return;
			}
			var selectedIds = "";
			$('input[name=orgId]:checked').each(function() {
						selectedIds += $(this).val() + ",";
					});
			if (!confirm("是否确实要删除所选组织机构，删除公司将同步删除该公司下所有用户和角色？")) {
				return;
			}
			$.ajax({
						type : "post",
						url : WWWROOT + "/sys/orgUnitMgr/deleteOrgUnit",
						cache : false,
						data : {
							orgIds : selectedIds
						},
						dataType : 'json',
						success : function(data) {
							if (data && data.success) {
								$.alert("已经删除成功。");
								OrgTree.expand(this.orgId);
								OrgManager.doQuery();
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
		/**
		 * 格式化组织机构名称
		 */
		formatOrgName : function(val, cell, colpos, rwdat, _act) {
			return "<a href=\"javascript:window.open('" + WWWROOT
					+ "/sys/orgUnitMgr/viewOrgFormPage?orgId=" + cell.rowId
					+ "');void(0);\">" + val + "</a>";
		},
		/**
		 * 格式化机构状态
		 */
		formatOrgState : function(val, cell, colpos, rwdat, _act) {
			if (val) {
				return '是';
			} else {
				return '否';
			}
		},
		/**
		 * 刷新grid
		 */
		reloadGrid : function() {
			OrgManager.doQuery();
		},
		/**
		 * 排序
		 */
		moveUpDown : function(orgId, orderType) {
			$.ajax({
						type : "post",
						url : WWWROOT + "/sys/orgUnitMgr/orgUnitSort",
						cache : false,
						data : {
							orgId : orgId,
							orderType : orderType
						},
						dataType : 'json',
						success : function(data) {
							if (data && data.success) {
								OrgManager.doQuery();
								OrgTree.expand(data.parentOrgId);
							}
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							$.alert("操作失败，请稍后再次尝试！");
						}
					});
		}
	}
});