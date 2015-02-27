<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="displaypage" type="java.lang.String"
	required="false"%>
<%@ attribute name="id" type="java.lang.String" required="false"%>
<%@ attribute name="gotoPage" type="java.lang.String" required="false"%>
<%@ attribute name="style" type="java.lang.String" required="false"%>
<%@ attribute name="needJumpeToPage" type="java.lang.Boolean" required="false"%>

<c:if test="${!empty page}">
	<input type="hidden" name="page.pageSize" dataType="number" value="10"
		style="display: none" />
	<input type="hidden" name="page.autoCount" value="true"
		style="display: none" />
	<c:set var="pageSize" value="${page.pageSize}"></c:set>
	<c:set var="pageNumber" value="${page.pageNumber}"></c:set>
	<c:set var="totalCount">
		<c:choose>
			<c:when test="${page.totalCount == -1}">${0}</c:when>
			<c:otherwise>${page.totalCount}</c:otherwise>
		</c:choose>
	</c:set>
	<c:set var="totalPages">
		<fmt:formatNumber type="number" maxFractionDigits="0"
			groupingUsed="false"
			value="${((totalCount / pageSize*10+9.9999) - (totalCount / pageSize*10+9.9999)%10)/10}" />
	</c:set>
	<c:set var="isHasPre" value="${pageNumber - 1 >= 1}"></c:set>
	<c:set var="isHasNext" value="${pageNumber + 1 <= totalPages}"></c:set>
	<c:set var="SHOW_LENGTH" value="${5}"></c:set>
	<c:if test="${empty needJumpeToPage}">
	<c:set var="needJumpeToPage" >${true}</c:set>
    </c:if>
	<script type="text/javascript">
	$(function() {
	//处理全选按钮事件
	$("#selectAll").unbind("click");
			$("#selectAll").bind("click",function(){
				if($(this).attr("checked")){
					$(this).parent().parent().parent().parent().find("input:checkbox").attr("checked","checked");
				}else{
					$(this).parent().parent().parent().parent().find("input:checkbox").removeAttr("checked");
				}
			});
	    });
	</script>
	<c:choose>
		<c:when test="${totalPages > 1}">
			<script type="text/javascript">
$(function(){
	//在这里重新绑定button事件，将reset加进去。
	$(".pageSlider").each(function(){
		$(this).find('#pageNumber${id}').bind('keyup',function(evt){
			//if(evt.keyCode == 13) ${gotoPage}(evt.target);
		});
	});			
});
</script>
			<div class='pageSlider' style="${style}">
				<div style="width:710px;margin:0 auto;">
				<div
					style="display:${displaypage};height:25px;line-height:25px;color:#A99999;float:left;font-size:13px;">
					共${totalCount}条/${totalPages}页
				</div>
				<%--上一页--%>
				<c:choose>
					<c:when test="${isHasPre}">
						<div class="pageFirstBtn">
							<a onclick='${gotoPage}(this)'
								onmousedown='$(this).parent().parent().find("#pageNumber${id}").val("1")'>首页
								</a>
						</div>
						<div class="pagePreBtn">
							<a onclick='${gotoPage}(this)'
								onmousedown='$(this).parent().parent().find("#pageNumber${id}").val("${page.pageNumber-1}")'>上一页
								</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="pageFirstBtn">
								<a disabled onclick='javascript:void(0);'>首页
								</a>
						</div>
						<div class="pagePreBtn">
								<a disabled onclick='javascript:void(0);'>上一页
								</a>
						</div>
					</c:otherwise>
				</c:choose>
				<div style="float: left; height: 25px; line-height: 25px;">
					<c:choose>
						<%--全部显示--%>
						<c:when test="${SHOW_LENGTH >= totalPages}">
							<c:forEach var="i" begin="1" end="${SHOW_LENGTH}" step="1">
								<c:if test="${i == pageNumber}">
									<b class="pageNumSelected">${i}</b>
								</c:if>
								<c:if test="${i != pageNumber and i <= totalPages}">
									<span class="pageNumber"
										onclick='${gotoPage}(this)'
										onmousedown='$(this).parent().parent().find("#pageNumber${id}").val("${i}")'>
										${i} </span>
								</c:if>
							</c:forEach>
						</c:when>
						<%--未能全部显示--%>
						<c:otherwise>
							<c:set var="gap">
								<fmt:formatNumber type="number" maxFractionDigits="0"
									value="${((SHOW_LENGTH / 2*10+9.9999) - (SHOW_LENGTH / 2*10+9.9999)%10)/10 - 1}" />
							</c:set>
							<%--左侧的省略符--%>
							<c:if test="${pageNumber-gap>1}">
						<span style="float:left;">...</span>
					</c:if>
							<%--翻页按钮--%>
							<c:choose>
								<%--两侧按钮都不能全部显示的处理方法--%>
								<c:when
									test="${pageNumber-1>=gap && totalPages-pageNumber>=gap}">
									<c:set var="i">${pageNumber - gap}</c:set>
								</c:when>
								<%--右侧全显示--%>
								<c:when
									test="${pageNumber-1>=gap && !(totalPages-pageNumber>=gap)}">
									<c:set var="i">${totalPages - SHOW_LENGTH + 1}</c:set>
								</c:when>
								<%--左侧全显示--%>
								<c:otherwise>
									<c:set var="i">${1}</c:set>
								</c:otherwise>
							</c:choose>
							<c:forEach var="fill" begin="1" end="${SHOW_LENGTH}" step="1">
								<c:if test="${i == pageNumber}">
									<b class="pageNumSelected">${i}</b>
								</c:if>
								<c:if test="${i != pageNumber}">
									<span class="pageNumber"
										onclick='${gotoPage}(this)'
										onmousedown='$(this).parent().parent().find("#pageNumber${id}").val("${i}")'>
										${i} </span>
								</c:if>
								<c:set var="i">${i + 1}</c:set>
							</c:forEach>
							<%--右侧的省略符--%>
							<c:if test="${pageNumber+gap<totalPages}">
						<span>...</span>
						<div class='fc'></div>
					</c:if>
						</c:otherwise>
					</c:choose>
				</div>
				<c:choose>
					<c:when test="${isHasNext}">
						<div class="pageNextBtn">
								<a onclick='${gotoPage}(this)'
								onmousedown='$(this).parent().parent().find("#pageNumber${id}").val("${pageNumber+1}")'>下一页
								</a>
						</div>
						<div class="pageLastBtn">
							<a onclick='${gotoPage}(this)'
								onmousedown='$(this).parent().parent().find("#pageNumber${id}").val("${totalPages}")'>末页
								</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="pageNextBtn">
							<a disabled onclick='javascript:void(0);'>下一页
								</a>
						</div>
						<div class="pageLastBtn">
							<a disabled onclick='javascript:void(0);'>末页
								</a>
						</div>
					</c:otherwise>
				</c:choose>
                
				<div style="float: left; height: 25px; line-height: 25px;display:<c:if test="${!needJumpeToPage}">none</c:if>;">
					<div
						style='width: 40px; margin-left: 5px; float: left; height: 25px;font-size:13px;'>
						<div id='pageUrl' onclick='${gotoPage}(this)'>
							跳转到
						</div>
					</div>
					<div style="float: left;">
						<input type='text' id='pageNumber${id}' name='page.pageNumber'
							dataType='number' value='${pageNumber}' size='2'
							onfocus='this.select()'
							onkeydown="if(event.keyCode == 13) ${gotoPage}(this);"
							onkeyup='var v=this.value.replace(/\D/gmi,"");this.value=(Number(v)>${totalPages}?${totalPages}:v);' />
						页
					</div>
					<div class='fc'></div>
				</div>
				
				<div class='fc'></div>
				</div>
				</div>
		</c:when>
		<c:otherwise>
			<!-- <div class='pageSlider' style="display:${displaypage};${style}">
				<div class='g'>
					共${totalCount}条/${totalPages}页
				</div>
			</div> -->
		</c:otherwise>
	</c:choose>

</c:if>