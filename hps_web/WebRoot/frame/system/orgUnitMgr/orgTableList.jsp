<%@ page language="java" pageEncoding="UTF-8"%>
<nui:thead>
	<tr>
		<th nowrap="nowrap" group="check" width="40">
			<input type="checkbox" id="selectAll">
		</th>
		<th nowrap="nowrap" group="orgName">
			机构名称
		</th>
		<td nowrap="nowrap" group="orgLevelCode" width="150">
			层级码
		</td>
		<td nowrap="nowrap" group="orgType" width="200">
			机构类型
		</td>
		<td nowrap="nowrap" group="managers" width="200">
			负责人
		</td>
		<td nowrap="nowrap" width="80">
			排序
		</td>
	</tr>
</nui:thead>
<nui:tbody>
	<c:forEach items="${page.result}" var="orgUnit">
		<tr>
			<td nowrap="nowrap" group="check">
				<input type="checkbox" id="orgId" name="orgId"
					value="${orgUnit.orgId}">
			</td>
			<td group="orgName">
				${orgUnit.orgName}
			</td>
			<td group="orgLevelCode">
				${orgUnit.orgLevelCode}
			</td>
			<td group="orgType">
				${orgUnit.orgType?'公司':'部门'}
			</td>
			<td group="managers">
				${orgUnit.managers}
			</td>
			<td>
				<div style="width: 40px; margin: 0 auto;">
					<div style="float: left;">
						<a class="ui-icon ui-icon-arrowthick-1-n" title="向上移动"
							href="javascript:OrgManager.moveUpDown('${orgUnit.orgId}','up')"></a>
					</div>
					<div style="margin-left: 20px;">
						<a class="ui-icon ui-icon-arrowthick-1-s" title="向下移动"
							href="javascript:OrgManager.moveUpDown('${orgUnit.orgId}','down')"></a>
					</div>
				</div>
			</td>
		</tr>
	</c:forEach>
</nui:tbody>