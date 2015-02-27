<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/rankrecommend/rankrecommend_list.js"></script>
<nui:grid id="rankrecommendListGrid" title="推荐排行" offsetHeight="-10"
	url="${ctx}/rankrecommend/load_list" pageId="rankrecommendListPageId"
	pkName="id" addCheck="RankrecommendManager.addCheck" addClick="RankrecommendManager.addRankrecommend" 
	addOperator="RankRecommend_Manager" editOperator="RankRecommend_Manager"
	delOperator="RankRecommend_Manager" 
	editClick="RankrecommendManager.editRankrecommend" 
	delClick="RankrecommendManager.deleteRankrecommend"
	otherButtons="">
	
    <nui:gridCell name="id" title="id" align="center" hidden="true"></nui:gridCell>
    <nui:gridCell name="mediaid" title="mediaid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="memberid" title="memberid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="membername" title="membername"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="medianame" title="歌曲名"  align="center"></nui:gridCell>
  	<nui:gridCell name="authorid" title="authorid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="authorname" title="作者"  align="center"></nui:gridCell>
  	<nui:gridCell name="recommendcount" title="推荐次数"  align="center"></nui:gridCell>
  	
 </nui:grid>