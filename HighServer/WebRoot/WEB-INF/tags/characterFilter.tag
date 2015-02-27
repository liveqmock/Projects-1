<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="value" type="java.lang.String" required="true"%>
<%
	String v = value == null ? "" : value;
	v = v.replace("<", "&lt;").replace(">", "&gt;").replace(" ","&nbsp;").replace("\n","<br>");
	out.print(v);
%>