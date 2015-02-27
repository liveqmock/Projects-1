<%@ tag pageEncoding="UTF-8"  %> 
<%@ taglib tagdir="/WEB-INF/tags" prefix="nui"%>
<%@ attribute name="displaypage" type="java.lang.String" required="false"  %>
<tbody class="ui-accordion-content ui-helper-reset ui-widget-content ui-accordion-content-active">
	<jsp:doBody></jsp:doBody>
</tbody>
</table>
<nui:pageTag displaypage="${displaypage}" />