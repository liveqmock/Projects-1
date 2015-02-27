<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/goods/goods_list.js"></script>
<nui:grid id="goodsListGrid" title="goods列表" height="400"
	url="${ctx}/goods/load_list" pageId="goodsListPageId"
	pkName="goodsId" addCheck="GoodsManager.addCheck" addClick="GoodsManager.addGoods" 
	addOperator="Frame_Goods_Add" editOperator="Frame_Goods_Edit"
	delOperator="Frame_Goods_Delete" 
	editClick="GoodsManager.editGoods" 
	delClick="GoodsManager.deleteGoods"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center"
		formatter="GoodsManager.formatViewLink"></nui:gridCell>
      	<nui:gridCell name="image" title="image"  align="center"></nui:gridCell>
  	<nui:gridCell name="largeimage" title="largeimage"  align="center"></nui:gridCell>
  	<nui:gridCell name="title" title="title"  align="center"></nui:gridCell>
  	<nui:gridCell name="score" title="score"  align="center"></nui:gridCell>
  	<nui:gridCell name="type" title="type"  align="center"></nui:gridCell>
  	<nui:gridCell name="detail" title="detail"  align="center"></nui:gridCell>
 </nui:grid>