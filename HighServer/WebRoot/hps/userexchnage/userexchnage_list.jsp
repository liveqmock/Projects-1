<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/hps/userexchnage/userexchnage_list.js"></script>
<nui:grid id="userexchnageListGrid" title="用户积分兑换列表" height="400"
	url="${ctx}/userexchnage/load_list" pageId="userexchnageListPageId"
	pkName="id" editOperator="User_Exchange_Handle"
	editClick="UserexchnageManager.editUserexchnage" 
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center"
		formatter="UserexchnageManager.formatViewLink"></nui:gridCell>
    <nui:gridCell name="userid" title="userid"  align="center"></nui:gridCell>
  	<nui:gridCell name="productid" title="productid"  align="center"></nui:gridCell>
  	<nui:gridCell name="producttitle" title="producttitle"  align="center"></nui:gridCell>
  	<nui:gridCell name="status" title="status"  align="center"></nui:gridCell>
  	<nui:gridCell name="score" title="score"  align="center"></nui:gridCell>
  	<nui:gridCell name="handler" title="handler"  align="center"></nui:gridCell>
  	<nui:gridCell name="handlername" title="handlername"  align="center"></nui:gridCell>
  	<nui:gridCell name="reicaddress" title="reicaddress"  align="center"></nui:gridCell>
  	<nui:gridCell name="reicphone" title="reicphone"  align="center"></nui:gridCell>
  	<nui:gridCell name="createtime" title="createtime"  align="center"></nui:gridCell>
 </nui:grid>