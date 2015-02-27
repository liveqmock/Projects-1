<%@ include file="css.jsp"%>
<!-- 这里设置主题样式 -->
<c:set var="frameTheme" scope="application" value="default" />
<link rel="stylesheet" type="text/css" media="all"
		href="${ctx}/frame/theme/${frameTheme}/css/frame_home.css" />
<link rel="stylesheet" type="text/css" media="all"
	href="${ctx}/frame/js/plugin/ol.loading/loading.css" />
<%-- jquery核心库 --%>
<script type="text/javascript"
	src="${ctx}/frame/js/jquery/jquery-1.7.min.js"></script>
<%-- jquery UI核心库 --%>
<link rel="stylesheet" type="text/css" media="all"
	href="${ctx}/frame/js/plugin/jquery-ui-1.8.16.custom/css/${frameTheme}/jquery-ui-1.8.16.custom.css" />
<%--<script type="text/javascript"
	src="${ctx}/frame/js/plugin/jquery-ui-1.8.16.custom/js/jquery-ui-1.8.16.custom.min.js"></script>--%>
<%-- 提示框 --合并到loading.css
<link rel="stylesheet" type="text/css" media="all"
	href="${ctx}/frame/js/plugin/msgAlert/msgAlert.css" />--%>
<script type="text/javascript"
	src="${ctx}/frame/js/plugin/msgAlert/msgAlert.js"></script>
<%--工具脚本库--%>
<script type="text/javascript" src="${ctx}/frame/js/AjaxSubmit.js"></script>
<%-- 引入common库 --%>
<script type="text/javascript" src="${ctx}/frame/js/common.js"></script>
<script type="text/javascript" src="${ctx}/frame/js/jquery-extend.js"></script>
<%-- 引入日期控件库 
<link rel="stylesheet" type="text/css" media="all"
	href="${ctx}/frame/js/plugin/jquery-calendar/calendar.css" />
<script type="text/javascript"
	src="${ctx}/frame/js/plugin/jquery-calendar/calendar.js"></script>--%>
<%-- 引入时间控件库 --%>
<link rel="stylesheet" type="text/css" media="all"
	href="${ctx}/frame/js/plugin/jquery-timePicker/jquery-timePicker.css" />
<script type="text/javascript"
	src="${ctx}/frame/js/plugin/jquery-timePicker/jquery-timePicker.js"></script>
<%--引入marsk loading--%>
<script type="text/javascript"
	src="${ctx}/frame/js/plugin/ol.loading/jquery.bgiframe.min.js"></script>
<script type="text/javascript"
	src="${ctx}/frame/js/plugin/ol.loading/loading.js"></script>