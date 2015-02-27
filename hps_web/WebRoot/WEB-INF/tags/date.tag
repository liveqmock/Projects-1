<%@ tag pageEncoding="UTF-8"%>
<%@tag import="com.newsoft.utils.DateTool"%>
<%@ attribute name="date" type="java.util.Date" required="true"%>
<%@ attribute name="isShort" type="java.lang.Boolean" required="false"%>
<%@ attribute name="formatter" type="java.lang.String" required="false"%>
<%
	try {
		if (null == date) {
			out.print("");
			return;
		}
		String pattern = formatter;
		if (pattern == null || "".equals(pattern)) {
			pattern = DateTool.PATTERN_DATETIME;
		}
		if (isShort != null && isShort == true)
			pattern = DateTool.PATTERN_DEFAULT;
		out.print(DateTool.formatDate(date, pattern));
	} catch (Exception e) {
		out.print(date);
	}
%>