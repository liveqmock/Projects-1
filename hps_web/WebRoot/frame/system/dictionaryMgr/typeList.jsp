<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/frame/system/dictionaryMgr/typeList.js"></script>
<nui:grid id="dictionaryTypeList" title="字典类型列表"
	url="${ctx}/dictionaryMgr/loadTypeListData?defaultOrgId=${defaultOrgId}"
	pageId="typelistPageId" pkName="typeId" addOperator="DicMgr" editOperator="DicMgr" delOperator="DicMgr"
	addClick="DictionaryMgr.addType" editClick="DictionaryMgr.editType"
	delClick="DictionaryMgr.deleteType" offsetHeight="-10">
	<nui:gridCell name="typeId" title="类型Id" align="center" width="50"></nui:gridCell>
	<nui:gridCell name="typeName" title="类型名称" align="center"
		formatter="DictionaryMgr.formatTypeName" width="80"></nui:gridCell>
	<nui:gridCell name="typeDes" title="类型描述" align="center" width="80"></nui:gridCell>
	<nui:gridCell name="sortIndex" title="类型排序" align="center"
		hidden="true"></nui:gridCell>
	<nui:gridCell name="operation" title="操作" align="center" width="50"
		formatter="DictionaryMgr.sortIndex"></nui:gridCell>
</nui:grid>
<nui:validate formId='typeFormId' onclick="checkTypeForm"
	callback="DictionaryMgr.submitTypeForm"></nui:validate>
<nui:dialog id="typeMgrWindow" title="类型信息" height="260" width="400"
	saveClick="checkTypeForm" appendTo="iframe_div">
	<form id="typeFormId">
		<nui:field type="input" paddingLeft="-40">
			<nui:input title="所属类别：" size="x3">
				<input type="text" id="kindName_t" name="kindName" 
					readonly="readonly" />
				<input type="hidden" id="sortIndex" name="sortIndex"/>
				<input type="hidden" id="kindId_t" name="kindId"/>
			</nui:input>
		</nui:field>
		<nui:field type="input" paddingLeft="-40">
			<nui:input title="类型值：" size="x3" require="true">
				<input type="text" id="typeId" name="typeId" value="0"
					datatype="number" maxlength="3" required="true" />
			</nui:input>
		</nui:field>
		<nui:field type="input" paddingLeft="-40">
			<nui:input title="类型名称：" size="x3" require="true">
				<input type="text" id="typeName" name="typeName" value=""
					maxlength="20" required="true" />
			</nui:input>
		</nui:field>
		<nui:field type="input" paddingLeft="-40">
			<nui:input title="类型描述：" size="x3">
				<input type="text" id="typeDes" name="typeDes" value=""
					maxlength="1000" />
			</nui:input>
		</nui:field>
	</form>
</nui:dialog>