<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/frame/rankcomment/rankcomment_list.js"></script>
<nui:grid id="rankcommentListGrid" title="rankcomment列表" height="400"
	url="${ctx}/rankcomment/load_list" pageId="rankcommentListPageId"
	pkName="rankcommentId" addCheck="RankcommentManager.addCheck" addClick="RankcommentManager.addRankcomment" 
	addOperator="Frame_Rankcomment_Add" editOperator="Frame_Rankcomment_Edit"
	delOperator="Frame_Rankcomment_Delete" 
	editClick="RankcommentManager.editRankcomment" 
	delClick="RankcommentManager.deleteRankcomment"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center"
		formatter="RankcommentManager.formatViewLink"></nui:gridCell>
      	<nui:gridCell name="mediaid" title="mediaid"  align="center"></nui:gridCell>
  	<nui:gridCell name="memberid" title="memberid"  align="center"></nui:gridCell>
  	<nui:gridCell name="membername" title="membername"  align="center"></nui:gridCell>
  	<nui:gridCell name="medianame" title="medianame"  align="center"></nui:gridCell>
  	<nui:gridCell name="authorid" title="authorid"  align="center"></nui:gridCell>
  	<nui:gridCell name="authorname" title="authorname"  align="center"></nui:gridCell>
  	<nui:gridCell name="commentcount" title="commentcount"  align="center"></nui:gridCell>
 </nui:grid>