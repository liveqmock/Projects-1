<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/mediaplay/mediaplay_list.js"></script>
<nui:grid id="mediaplayListGrid" title="播放明细列表" offsetHeight="-10"
	url="${ctx}/mediaplay/load_list" pageId="mediaplayListPageId"
	pkName="id" 
	delOperator="MediaPlay_Manager" 
	delClick="MediaplayManager.deleteMediaplay"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center" hidden="true"></nui:gridCell>
    <nui:gridCell name="mediaid" title="mediaid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="memberid" title="memberid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="membername" title="会员名"  align="center"></nui:gridCell>
  	<nui:gridCell name="playtime" title="播放时间"  align="center"></nui:gridCell>
  	<nui:gridCell name="medianame" title="歌曲名称"  align="center"></nui:gridCell>
  	<nui:gridCell name="authorid" title="authorid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="authorname" title="作者"  align="center"></nui:gridCell>
 </nui:grid>