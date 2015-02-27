<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/comment/comment_list.js"></script>
<nui:grid id="commentListGrid" title="评论列表" offsetHeight="-10"
	url="${ctx}/comment/load_list" pageId="commentListPageId"
	pkName="id"
	delOperator="Comment_Manager" 
	delClick="CommentManager.deleteComment"
	otherButtons="">
    <nui:gridCell name="id" title="id" align="center" hidden="true"></nui:gridCell>
    <nui:gridCell name="parentid" title="parentid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="mediaid" title="mediaid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="medianame" title="歌曲名称"  align="center"></nui:gridCell>
  	<nui:gridCell name="content" title="内容"  align="center"></nui:gridCell>
  	<nui:gridCell name="score" title="评分"  align="center"></nui:gridCell>
  	<nui:gridCell name="memberid" title="memberid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="membername" title="会员名"  align="center"></nui:gridCell>
  	<nui:gridCell name="anoymous" title="是否匿名"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="cratetime" title="评论时间"  align="center" formatter="CommentManager.formatTime"></nui:gridCell>
 </nui:grid>