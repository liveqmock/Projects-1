<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<nui:thead>
	<tr>
		<th nowrap="nowrap" group="userName">
			点评者
		</th>
		<th nowrap="nowrap" group="commentContent" width="250">
			评论
		</th>
		<th nowrap="nowrap" group="score" width="100" align="center">
			评分
		</th>
		<th nowrap="nowrap" group="commentTime" width="200">
			点评时间
		</th>
	</tr>
</nui:thead>
<nui:tbody id="commentBody" gotoPage="KnowledgeManage.doQuery">
	<c:forEach items="${page.result}" var="recordComent">
		<tr>
			<td group="userName" align="center">
				<c:choose>
					<c:when test="${recordComent.annoymous==true}">
						匿名用户
					</c:when>
					<c:otherwise>
						${recordComent.userName}
					</c:otherwise>
				</c:choose>
			</td>
			<td group="commentContent">
				${recordComent.commentContent}
			</td>
			<td group="score">
				<c:choose>
					<c:when test="${recordComent.score==0}">
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
					</c:when>
					<c:when test="${empty recordComent.score}">
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
					</c:when>
					<c:when test="${recordComent.score==1}">
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
					</c:when>
					<c:when test="${recordComent.score==2}">
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
					</c:when>
					<c:when test="${recordComent.score==3}">
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointBad"></div>
						<div class="knowledgePointBad"></div>
					</c:when>
					<c:when test="${recordComent.score==4}">
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointBad"></div>
					</c:when>
					<c:when test="${recordComent.score==5}">
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointGood"></div>
						<div class="knowledgePointGood"></div>
					</c:when>
				</c:choose>

			</td>
			<td group="commentTime" align="center">
				<fmt:formatDate value="${recordComent.createTime}" type="both"
					pattern="yyyy年MM月dd日 " var="commentTime" />
				${commentTime}
			</td>
		</tr>
	</c:forEach>
</nui:tbody>
