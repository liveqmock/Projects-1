<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/media/media_list.js"></script>
<nui:grid id="mediaListGrid" title="音乐列表" offsetHeight="-15"
	url="${ctx}/media/load_list" pageId="mediaListPageId"
	pkName="id" addCheck="MediaManager.addCheck" addClick="MediaManager.addMedia" 
	addOperator="Frame_Media_Add" editOperator="Frame_Media_Update"
	delOperator="Frame_Media_Delete" 
	editClick="MediaManager.editMedia" 
	delClick="MediaManager.deleteMedia"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center"  hidden="true"></nui:gridCell>
    <nui:gridCell name="title" title="歌名"  align="center"></nui:gridCell>
  	<nui:gridCell name="authorName" title="录音师"  align="center"></nui:gridCell>
  	<nui:gridCell name="roomname" title="录音室"  align="center"></nui:gridCell>
  	<nui:gridCell name="membername" title="会员名"  align="center"></nui:gridCell>
  	<nui:gridCell name="createtime" title="上传时间"  align="center" formatter="MediaManager.formatTime"></nui:gridCell>
  	<nui:gridCell name="tryListen" title="试听"  align="center" formatter="MediaManager.tryListen"></nui:gridCell>
 </nui:grid>