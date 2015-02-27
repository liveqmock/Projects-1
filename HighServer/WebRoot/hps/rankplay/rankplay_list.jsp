<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/rankplay/rankplay_list.js"></script>
<nui:grid id="rankplayListGrid" title="播放排行" offsetHeight="-10"
	url="${ctx}/rankplay/load_list" pageId="rankplayListPageId"
	pkName="rankplayId" addCheck="RankplayManager.addCheck" addClick="RankplayManager.addRankplay" 
	addOperator="RankPlay_Manager" editOperator="RankPlay_Manager"
	delOperator="RankPlay_Manager" 
	editClick="RankplayManager.editRankplay" 
	delClick="RankplayManager.deleteRankplay"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center" hidden="true"></nui:gridCell>
    <nui:gridCell name="mediaid" title="mediaid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="memberid" title="memberid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="membername" title="membername"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="medianame" title="歌曲名称"  align="center"></nui:gridCell>
  	<nui:gridCell name="authorid" title="authorid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="authorname" title="作者"  align="center"></nui:gridCell>
  	<nui:gridCell name="playcount" title="播放次数"  align="center"></nui:gridCell>
 </nui:grid>