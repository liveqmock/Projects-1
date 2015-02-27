<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--工具脚本库--%>
<script type="text/javascript" src="${ctx}/frame/js/AjaxSubmit.js"></script>
<%-- 引入common库 --%>
<script type="text/javascript" src="${ctx}/frame/js/common.js"></script>
<script type="text/javascript" src="${ctx}/frame/js/jquery-extend.js"></script>
<link href="${ctx}/frame/js/plugin/biaoqing/css/smohan.face.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/frame/js/plugin/biaoqing/js/smohan.face.js" charset="utf-8"></script>
<script type="text/javascript">
$(function() {
	$(".submitComment").click(function(){
		var content = $(this).parent().find("#comment").val();
		var mediaId = $(this).parent().find(".mediaIdHidden").val();
		var outerDiv = $(this).parent();
		var url = "${ctx}/h/comment/submit_form?page=1"+ "&rows=10&operation=add&mediaid=" + mediaId + "&content=" + content;
		AjaxSubmit(url, function(html) {
			$("a.face").die('click');
			outerDiv.html(html);
		}, null, null, 'html');
		
	});
	
	$(".pageSplit").click(function(){
		var page = $(this).attr("title");
		var outDiv = $(this).parent().parent().parent().parent().parent();
		var url = "${ctx}/h/comment/commentPage?page="
			+ page + "&rows=10&mediaId=" + $("#mediaIdHidden").val();
		AjaxSubmit(url, function(html) {
		outDiv.find(".commentLine").hide();
		outDiv.find(".commentLine").html(html);
		outDiv.find(".commentLine").fadeIn(1500);
	}, null, null, 'html');
	});
	
	$("a.face").smohanfacebox({
		Event : "click",	//触发事件	
		divid : "commentDiv", //外层DIV ID
		textid : "comment" //文本框 ID
	});
	
	$('#commentDiv .commentTop').each(function(){
		$(this).replaceface($(this).html());//替换表情
	});
	
});

function LimitTextArea(field){
    maxlimit=100;
    if (field.value.length > maxlimit)
     field.value = field.value.substring(0, maxlimit);
      
   }
   
</script>
<style>
.submitComment{
	margin-top:5px;
	cursor:pointer;
	border-radius:2px;
	float:right;
	background-color:rgb(65, 16, 19);
	border:1px solid #df1b22;
	color:#ffffff;
	font-size:13px;
	height:24px;
	letter-spacing:0.2em;
	line-height:24px;
	text-align:center;
	min-width:40px;
}
.submitComment:hover{
	cursor:pointer;
	background-color:#c51b21;
}
.commentDiv{
	width:98%;
	margin-bottom:10px;
	float:left;
	border-bottom:1px solid gray;
}
.commentTop{
	color:white;
	width:98%;
	line-height:23px;
	word-wrap:break-word;
}
.commentMember{
	color:#7fc3d6;
	font-size:13px;
	font-family:宋体;
}
.commentBottom{
	width:98%;
	height:23px;
	line-height:23px;
	color:#808080;
}
</style>
	<div id="commentDiv" style="position:relative;margin-top:15px;">
	<input type="hidden" id="mediaIdHidden" value="${mediaId}" name="mediaIdHidden" class="mediaIdHidden"></input>
	<textarea   title="100个字符以内" 
	onKeyDown="LimitTextArea(this)" onKeyUp="LimitTextArea(this)" onkeypress="LimitTextArea(this)"
	onpropertychange="this.style.height=this.scrollHeight + 'px'" oninput="this.style.height=this.scrollHeight + 'px'" 
	id="comment" name="comment" style="resize:both;color: rgb(51, 51, 51); overflow-y: scroll; width:99%;height:23px;line-height:18px;border:1px solid #01b9ce;"></textarea>
	<a class="submitComment" title="提交" href="javascript:void(0);">评论</a>
	<a href="javascript:void(0)" class="face" title="表情"></a>100个字符以内
	<div style="clear:both;"></div>
	<c:forEach items="${commentList}" var="comment" varStatus="commentStatus">
		<div class="commentDiv">
			<div class="commentTop">
				<a class="commentMember">${comment.membername}:</a> &nbsp;&nbsp;${comment.content}
			</div>
			<div class="commentBottom">
				<fmt:formatDate value="${comment.cratetime}" type="both"
									pattern="yyyy年MM月dd日 " var="commentTime" />
				时间：${commentTime}
			</div>
		</div>
	</c:forEach>
	<div style="clear:both;"></div>
<div id="pageLine">
<div id="pageNo"><a class="pageSplit" title="${prePage}">上一页</a>
<c:set var="page" value="${currentPage+1}"></c:set>
<c:set var="prepage" value="${currentPage-1}"></c:set>
<c:forEach items="${linkPage}" var="pageNo" varStatus="playstatus">
<c:if test="${currentPage==pageNo}"><span style="color:red;">${pageNo}&nbsp;</span></c:if>
<c:if test="${(pageNo<=3 || pageNo>(fn:length(linkPage)-3)) && currentPage!=pageNo}">
	<c:if test="${pageNo>currentPage}">
	<a  class="pageSplit" title="${page}">${pageNo}&nbsp;</a>
	<c:set var="page" value="${page+1}"></c:set>
	</c:if>
	<c:if test="${pageNo<currentPage}">
	<a class="pageSplit" title="${prepage}">${pageNo}&nbsp;</a>
	<c:set var="prepage" value="${prepage-1}"></c:set>
	</c:if>
</c:if>
<c:if test="${pageNo>3 && pageNo<=(fn:length(linkPage)-3) && currentPage!=pageNo}">
<c:if test="${pageNo==(fn:length(linkPage)-3)}">
	...
</c:if>
<c:if test="${pageNo>currentPage}">
	<c:set var="page" value="${page+1}"></c:set>
</c:if>
<c:if test="${pageNo<currentPage}">
	<c:set var="prepage" value="${prepage-1}"></c:set>
</c:if>
</c:if>
</c:forEach>
<a class="pageSplit" title="${nextPage}">下一页</a></div>
</div>
