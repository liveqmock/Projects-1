<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/hps/cmscontent/cmscontent_list.js"></script>
<nui:grid id="cmscontentListGrid" title="文章列表" offsetHeight="-15"
	url="${ctx}/cmscontent/load_list" pageId="cmscontentListPageId"
	pkName="id" addCheck="CmscontentManager.addCheck" addClick="CmscontentManager.addCmscontent" 
	addOperator="CMS_Mgr_Add" editOperator="CMS_Mgr_Edit"
	delOperator="CMS_Mgr_Delete" 
	editClick="CmscontentManager.editCmscontent" 
	delClick="CmscontentManager.deleteCmscontent"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center" hidden="true"></nui:gridCell>
    <nui:gridCell name="categoryid" title="categoryid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="title" title="标题"  align="center" formatter="CmscontentManager.formatTitle"></nui:gridCell>
  	<nui:gridCell name="authorid" title="authorid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="authorname" title="作者"  align="center"></nui:gridCell>
  	<nui:gridCell name="cratetime" title="发布时间"  align="center" formatter="CmscontentManager.formatCreateTime"></nui:gridCell>
 </nui:grid>