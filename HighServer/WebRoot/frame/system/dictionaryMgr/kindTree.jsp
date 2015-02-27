<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/frame/system/dictionaryMgr/kindTree.js"></script>
<nui:tree id="kindTree" checkbox="false"
	url="${ctx}/dictionaryMgr/loadKindTree?defaultOrgId=${defaultOrgId}"
	showroot="false" onactivate="KindTree.active"
	buttons="{id:'btn_addKind', name:'添加',title:'添加子类别',className:'ui-icon ui-icon-plus',onclick:'KindTree.addKind'},
	{id:'btn_editKind', name:'修改',title:'修改子类别',className:'ui-icon ui-icon-pencil',onclick:'KindTree.editKind'},
			{id:'btn_deleteKind',name:'删除',title:'删除子类别',className:'ui-icon ui-icon-trash',onclick:'KindTree.deleteKind'}"></nui:tree>
<nui:dialog id="kindMgrWindow" title="类别信息" height="220" width="400"
	saveClick="checkKindForm">
	<nui:validate formId='kindFormId' onclick="checkKindForm"
		callback="KindTree.submitForm"></nui:validate>
	<form id="kindFormId">
		<nui:field type="input" paddingLeft="-40">
			<nui:input title="父类别：" size="x3">
				<input type="text" id="parentKindName" name="parentKindName"
					value="" readonly="readonly" />
				<input type="hidden" id="parentKindId" name="parentId" value="" />
				<input type="hidden" id="kindId" name="kindId" value="" />
			</nui:input>
		</nui:field>
		<nui:field type="input" paddingLeft="-40">
			<nui:input title="类别名称：" size="x3" require="true">
				<input type="text" id="kindName" name="kindName" value=""
					required="true" maxlength="20" />
			</nui:input>
		</nui:field>
		<nui:field type="input" paddingLeft="-40">
			<nui:input title="类别描述：" size="x3">
				<input type="text" id="kindDes" name="kindDes" value=""
					maxlength="100" />
			</nui:input>
		</nui:field>
	</form>
</nui:dialog>