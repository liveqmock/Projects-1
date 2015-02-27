<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/adphoto/adphoto_list.js"></script>
<nui:grid id="adphotoListGrid" title="广告列表" offsetHeight="-15"
	url="${ctx}/adphoto/load_list" pageId="adphotoListPageId"
	pkName="id" addCheck="AdphotoManager.addCheck" addClick="AdphotoManager.addAdphoto" 
	addOperator="Frame_Adphoto_Add" editOperator="Frame_Adphoto_Edit"
	delOperator="Frame_Adphoto_Delete" 
	editClick="AdphotoManager.editAdphoto" 
	delClick="AdphotoManager.deleteAdphoto"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center" hidden="true"></nui:gridCell>
    <nui:gridCell name="fileId" title="fileId" align="center" hidden="true"></nui:gridCell>
    <nui:gridCell name="title" title="标题"  align="center"></nui:gridCell>
  	<nui:gridCell name="adtype" title="类型"  align="center" formatter="AdphotoManager.formatType"></nui:gridCell>
  	<nui:gridCell name="url" title="链接地址"  align="center"></nui:gridCell>
  	<nui:gridCell name="addesc" title="描述信息"  align="center"></nui:gridCell>
  	<nui:gridCell name="finish" title="是否结束"  align="center" formatter="AdphotoManager.formatFinish"></nui:gridCell>
  	<nui:gridCell name="creator" title="creator"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="creatorname" title="创建者"  align="center"></nui:gridCell>
  	<nui:gridCell name="operate" title="结束广告" align="center" width="80" formatter="AdphotoManager.formatOperate"></nui:gridCell>
  	<nui:gridCell name="createtime" title="创建时间"  align="center" formatter="AdphotoManager.formatCreateTime"></nui:gridCell>
  	<nui:gridCell name="tuxiang" title="图片"  align="center" formatter="AdphotoManager.showtupian"></nui:gridCell>
 </nui:grid>