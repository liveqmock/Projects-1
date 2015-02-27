<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="iconpath" type="java.lang.String" required="false"%><%-- 图标父路径 --%>
<%@ attribute name="menuwidth" type="Integer" required="false"%><%-- 下拉菜单的最小宽度 --%>
<%@ attribute name="fadeInTime" type="Integer" required="false"%><%-- 显示的淡入时间，单位为毫秒 --%>
<%@ attribute name="fadeOutTime" type="Integer" required="false"%><%-- 显示的淡出时间，单位为毫秒 --%>
<%@ attribute name="openOnClick" type="Boolean" required="false"%><%-- 是否点击时弹出菜单 --%>
<%@ attribute name="closeOnMouseOut" type="Boolean" required="false"%><%-- 是否鼠标移开后关闭菜单 --%>
<%@ attribute name="closeAfter" type="Integer" required="false"%><%-- 移开后菜单关闭的时间 --%>

<%@ attribute name="openOnRight" type="Boolean" required="false"%><%-- 下拉菜单是否在右边现实 --%>
<%@ attribute name="hasImages" type="Boolean" required="false"%><%-- 下拉菜单是否显示左边的图标 --%>
<%@ attribute name="minZindex" type="java.lang.String" required="false"%><%-- 下拉菜单的层叠顺序 --%>
<%@ attribute name="opacity" type="java.lang.String" required="false"%><%-- 下拉菜单的透明度 --%>
<%@ attribute name="menuVertical" type="Boolean" required="false"%><%-- 菜单是横向还是纵向 --%>


<c:if test="${empty menuwidth}">
	<c:set var="menuwidth" value="300" />
</c:if>
<c:if test="${empty fadeInTime}">
	<c:set var="fadeInTime" value="100" />
</c:if>
<c:if test="${empty fadeOutTime}">
	<c:set var="fadeOutTime" value="100" />
</c:if>
<c:if test="${empty openOnClick}">
	<c:set var="openOnClick" value="true" />
</c:if>
<c:if test="${empty closeOnMouseOut}">
	<c:set var="closeOnMouseOut" value="true" />
</c:if>
<c:if test="${empty closeAfter}">
	<c:set var="closeAfter" value="100" />
</c:if>
<c:if test="${empty openOnRight}">
	<c:set var="openOnRight" value="false" />
</c:if>
<c:if test="${empty hasImages}">
	<c:set var="hasImages" value="false" />
</c:if>
<c:if test="${empty minZindex}">
	<c:set var="minZindex" value="auto" />
</c:if>
<c:if test="${empty opacity}">
	<c:set var="opacity" value="1" />
</c:if>
<c:if test="${empty menuVertical}">
	<c:set var="menuVertical" value="false" />
</c:if>

<c:choose>
	<c:when test="${menuVertical==true}">
		<c:set var="openOnRight" value="true" />
		<table class="menubar" style="width:auto;">
			<tr>
				<td valign="bottom">
					<table border="0" cellpadding="0" cellspacing="0" class="container">
						<tr>
							<td class="${id}">
								<table class="rootVoices" cellspacing='0' cellpadding='0' border='0'>
									<jsp:doBody></jsp:doBody>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		<table class="menubar" style="width:100%;">
			<tr>
				<td valign="bottom">
					<table border="0" cellpadding="0" cellspacing="0" class="container">
						<tr>
							<td class="${id}">
								<table class="rootVoices" cellspacing='0' cellpadding='0' border='0'>
									<tr><jsp:doBody></jsp:doBody><tr>
								</table>
							</td> 
						</tr>
					</table> 
				</td>
				<td style='margin-top:7px;float:right;display:none;'>当前公司：
				</td>
			</tr>
		</table>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
	$(function(){
		$(".${id}").buildMenu(
	    {
	      menuWidth:${menuwidth},
	      iconPath:"${iconpath}",
	      fadeInTime:${fadeInTime},
	      fadeOutTime:${fadeOutTime},
	      openOnClick:${openOnClick},
	      closeOnMouseOut:${closeOnMouseOut},
	      closeAfter:${closeAfter},
	      openOnRight:${openOnRight},
	      hasImages:${hasImages},
	      minZindex:"${minZindex}",
	      opacity:${opacity}
	    });

	    $(document).buildContextualMenu(
	    {
	      menuWidth:${menuwidth},
	      iconPath:"${iconpath}",
	      fadeInTime:${fadeInTime},
	      fadeOutTime:${fadeOutTime},
	      openOnClick:${openOnClick},
	      closeOnMouseOut:${closeOnMouseOut},
	      closeAfter:${closeAfter},
	      openOnRight:${openOnRight},
	      hasImages:${hasImages},
	      minZindex:"${minZindex}",
	      opacity:${opacity},
	      onContextualMenu:function(o,e){} //params: o = the contextual menu,e = the event
	    });
	});
</script>