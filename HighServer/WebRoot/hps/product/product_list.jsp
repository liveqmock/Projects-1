<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/product/product_list.js"></script>
<nui:grid id="productListGrid" title="product列表" height="400"
	url="${ctx}/product/load_list" pageId="productListPageId"
	pkName="productId" addCheck="ProductManager.addCheck" addClick="ProductManager.addProduct" 
	addOperator="Frame_Product_Add" editOperator="Frame_Product_Edit"
	delOperator="Frame_Product_Delete" 
	editClick="ProductManager.editProduct" 
	delClick="ProductManager.deleteProduct"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center"
		formatter="ProductManager.formatViewLink"></nui:gridCell>
      	<nui:gridCell name="title" title="title"  align="center"></nui:gridCell>
  	<nui:gridCell name="protype" title="protype"  align="center"></nui:gridCell>
  	<nui:gridCell name="fileid" title="fileid"  align="center"></nui:gridCell>
  	<nui:gridCell name="score" title="score"  align="center"></nui:gridCell>
  	<nui:gridCell name="creator" title="creator"  align="center"></nui:gridCell>
  	<nui:gridCell name="creatorname" title="creatorname"  align="center"></nui:gridCell>
  	<nui:gridCell name="createtime" title="createtime"  align="center"></nui:gridCell>
 </nui:grid>