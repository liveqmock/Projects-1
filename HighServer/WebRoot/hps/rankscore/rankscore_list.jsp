<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/rankscore/rankscore_list.js"></script>
<nui:grid id="rankscoreListGrid" title="评分排行" offsetHeight="-10"
	url="${ctx}/rankscore/load_list" pageId="rankscoreListPageId"
	pkName="id" addCheck="RankscoreManager.addCheck" addClick="RankscoreManager.addRankscore" 
	addOperator="RankScore_Manager" editOperator="RankScore_Manager"
	delOperator="RankScore_Manager" 
	editClick="RankscoreManager.editRankscore" 
	delClick="RankscoreManager.deleteRankscore"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center" hidden="true"></nui:gridCell>
    <nui:gridCell name="mediaid" title="mediaid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="memberid" title="memberid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="membername" title="membername"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="medianame" title="歌曲名"  align="center"></nui:gridCell>
  	<nui:gridCell name="authorid" title="authorid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="authorname" title="作者"  align="center"></nui:gridCell>
  	<nui:gridCell name="score" title="分数"  align="center"></nui:gridCell>
 </nui:grid>