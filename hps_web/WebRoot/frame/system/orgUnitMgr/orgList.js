$(function() {
	treeActive = function(orgId, orgName, orgType) {
		OrgManager.orgId = orgId;
		OrgManager.orgName = orgName;
		OrgManager.orgType = orgType;
		
		OrgManager.resetSearchCondition();
		OrgManager.filterClick();
	}
	OrgManager = {
		orgId : defaultOrgId,
		orgName : defaultOrgName,
		orgType : true,
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
			if (this.orgId == '00000000000000000000000000000000') {
				$.alert("根组织下不能添加部门！");
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
			window.open(WWWROOT + "/sys/orgUnitMgr/editOrgFormPage?orgId="
					+ rowData.orgId);
		},
		/**
		 * 删除选中的组织机构
		 */
		delOrg : function(grid, orgIds) {
			if (!confirm("是否确实要删除所选组织机构，删除公司将同步删除该公司下所有用户和角色？")) {
				return;
			}
			$.ajax({
						type : "post",
						url : WWWROOT + "/sys/orgUnitMgr/deleteOrgUnit",
						cache : false,
						data : {
							orgIds : orgIds
						},
						dataType : 'json',
						success : function(data) {
							if (data && data.success) {
								$.alert("已经删除成功。");
								OrgTree.expand(OrgManager.orgId);
								grid.trigger("reloadGrid");
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
		 * 格式化组织机构类型
		 */
		formatOrgType : function(val, cell, colpos, rwdat, _act) {
			if (val) {
				return '公司';
			} else {
				return '部门';
			}
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
			$("#orgListGrid").jqGrid("setGridParam", {
						page : 1,
						postData : {
							parentId : OrgManager.orgId
						}
					}).trigger("reloadGrid");
		},
		/**
		 * 格式化操作
		 */
		sequenceOrg : function(val, cell, colpos, rwdat, _act) {
			var html = '<div style="width:40px;margin:0 auto;"><div style="float:left;"><a class="ui-icon ui-icon-arrowthick-1-n" title="向上移动" href="javascript:OrgManager.moveUpDown(\''
					+ cell.rowId
					+ '\',\'up\')"></a></div><div style="margin-left:20px;">';
			html += '<a class="ui-icon ui-icon-arrowthick-1-s" title="向下移动" href="javascript:OrgManager.moveUpDown(\''
					+ cell.rowId + '\',\'down\')"></a></div></div>';
			return html;
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
								$("#orgListGrid").trigger("reloadGrid");
								OrgTree.expand(data.parentOrgId);
							}
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							$.alert("操作失败，请稍后再次尝试！");
						}
					});
		},
		resetSearchCondition:function(){
			$("input[name='orgName']").val('');
			$("input[name='orgLevelCode']").val('');
			$("#orgTypeSelect").val('');
		},
		filterClick : function() {
			// grid_filter_
			var json = $.collectFilterData($("#filterField"));
			setProperty("parentId", this.orgId, json);
			$("#orgListGrid").jqGrid("setGridParam", {
						postData : json
					}).trigger("reloadGrid");
		}
	}
});