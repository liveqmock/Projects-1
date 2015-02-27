<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="container" type="java.lang.String" required="true"%>
<%@ attribute name="northSize" type="java.lang.String" required="false"%>
<%@ attribute name="southSize" type="java.lang.String" required="false"%>
<%@ attribute name="westSize" type="java.lang.String" required="false"%>
<%@ attribute name="eastSize" type="java.lang.String" required="false"%>
<%@ attribute name="paneClass" type="java.lang.String" required="false"%>


<c:if test="${empty northSize}">
	<c:set var="northSize" value="0" />
</c:if>
<c:if test="${empty southSize}">
	<c:set var="southSize" value="0" />
</c:if>
<c:if test="${empty westSize}">
	<c:set var="westSize" value="250" />
</c:if>
<c:if test="${empty eastSize}">
	<c:set var="eastSize" value="0" />
</c:if>
<c:if test="${empty paneClass}">
	<c:set var="paneClass" value="ui-layout-pane" />
</c:if>
<SCRIPT type="text/javascript">
/***var stateResetSettings = {
			north__size:		${northSize}
		,	north__initClosed:	false
		,	north__initHidden:	false
		,	south__size:		${southSize}
		,	south__initClosed:	false
		,	south__initHidden:	false
		,	west__size:			${westSize}
		,	west__initClosed:	false
		,	west__initHidden:	false
		,	east__size:			${eastSize}
		,	east__initClosed:	false
		,	east__initHidden:	false
	};**/
	var myLayout;
	$(document).ready(function () {
		/**myLayout = $('${container}').layout({
			west__showOverflowOnHover: false
		,	closable:				false	// pane can open & close
		,	resizable:				false	// when open, pane can be resized 
		,	slidable:				true	// when closed, pane can 'slide' open over other panes - closes on mouse-out
		,	north__size:		${northSize}
		,	north__slidable:		false	// OVERRIDE the pane-default of 'slidable=true'
		,	north__togglerLength_closed: '100%'	// toggle-button is full-width of resizer-bar
		,	north__spacing_closed:	20		// big resizer-bar when open (zero height)
		,	south__size:		${southSize}
		,	south__resizable:		false	// OVERRIDE the pane-default of 'resizable=true'
		,	south__spacing_open:	0		// no resizer-bar when open (zero height)
		,	south__spacing_closed:	20		// big resizer-bar when open (zero height)
		,	west__size:			${westSize}
		,	west__minSize:			100
		,	east__size:			${eastSize}
		,	east__minSize:			10
		,	east__maxSize:			Math.floor(screen.availWidth / 2) // 1/2 screen width
		,	center__minWidth:		100
		,	useStateCookie:			false
		,   paneClass:              '${paneClass}'
		}); **/
		
		$("body").css("overflow","hidden");
		$('${container} .ui-layout-west').width(${westSize}).css("float","left").css("clear","left").css("overflow","auto");
		$('${container} .ui-layout-center').css("clear","right").css("float","left").width("500");
		
		layoutResize = function(){
			var height = document.documentElement.clientHeight - ${northSize}-105;
			var width = document.documentElement.clientWidth - ${westSize}-2;
			$('${container} .ui-layout-center').height(height).width(width);
			$('${container} .ui-layout-west').height(height);
		}
		layoutResize();
		$(window).bind('resize', function() {
		   layoutResize();
		});
 	});
</SCRIPT>
<jsp:doBody></jsp:doBody>