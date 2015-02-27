<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/buyerinfo/buyerinfo_list.js"></script>
<nui:grid id="buyerinfoListGrid" title="buyerinfo列表" height="400"
	url="${ctx}/buyerinfo/load_list" pageId="buyerinfoListPageId"
	pkName="buyerinfoId" addCheck="BuyerinfoManager.addCheck" addClick="BuyerinfoManager.addBuyerinfo" 
	addOperator="Frame_Buyerinfo_Add" editOperator="Frame_Buyerinfo_Edit"
	delOperator="Frame_Buyerinfo_Delete" 
	editClick="BuyerinfoManager.editBuyerinfo" 
	delClick="BuyerinfoManager.deleteBuyerinfo"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center"
		formatter="BuyerinfoManager.formatViewLink"></nui:gridCell>
      	<nui:gridCell name="userid" title="userid"  align="center"></nui:gridCell>
  	<nui:gridCell name="goodsid" title="goodsid"  align="center"></nui:gridCell>
  	<nui:gridCell name="realname" title="realname"  align="center"></nui:gridCell>
  	<nui:gridCell name="address" title="address"  align="center"></nui:gridCell>
  	<nui:gridCell name="phone" title="phone"  align="center"></nui:gridCell>
  	<nui:gridCell name="postcode" title="postcode"  align="center"></nui:gridCell>
 </nui:grid>