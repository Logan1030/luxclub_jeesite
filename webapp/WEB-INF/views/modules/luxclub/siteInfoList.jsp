<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>场所信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/luxclub/siteInfo/">场所信息列表</a></li>
		<shiro:hasPermission name="luxclub:siteInfo:edit"><li><a href="${ctx}/luxclub/siteInfo/form">场所信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="siteInfo" action="${ctx}/luxclub/siteInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>联系人：</label>
				<form:input path="contactMan" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="contactPhone" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>邮件：</label>
				<form:input path="email" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>场所类型：</label>
				<form:select path="siteType" class="input-medium">
					<form:option value="" label="全部类型"/>
					<form:options items="${fns:getDictList('site_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>场所名称：</label>
				<form:input path="siteName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>联系人</th>
				<th>联系电话</th>
				<th>邮件</th>
				<th>场所类型</th>
				<th>场所名称</th>
				<th>场所地址</th>
				<th>是否开业</th>
				<th>排序</th>
				<th>备注信息</th>
				<shiro:hasPermission name="luxclub:siteInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="siteInfo">
			<tr>
				<td><a href="${ctx}/luxclub/siteInfo/form?id=${siteInfo.id}">
					${siteInfo.contactMan}
				</a></td>
				<td>
					${siteInfo.contactPhone}
				</td>
				<td>
					${siteInfo.email}
				</td>
				<td>
					${fns:getDictLabel(siteInfo.siteType, 'site_type', '')}
				</td>
				<td>
					${siteInfo.siteName}
				</td>
				<td>
					${siteInfo.siteAddr}
				</td>
			    <td>
					${fns:getDictLabel(siteInfo.isOpen, 'luxclub_is_open', '')}
				</td>
				<th>${siteInfo.sort}</th>
				<td>
					${siteInfo.remarks}
				</td>
				<shiro:hasPermission name="luxclub:siteInfo:edit">
					<td>
	    				<a href="${ctx}/luxclub/siteInfo/form?id=${siteInfo.id}">修改</a>
						<a href="${ctx}/luxclub/siteInfo/delete?id=${siteInfo.id}" onclick="return confirmx('确认要删除该场所信息吗？', this.href)">删除</a>
						<a href="${ctx}/luxclub/siteInfo/stick?id=${siteInfo.id}" onclick="return confirmx('确认要将此场所置顶吗？', this.href)">置顶</a>
						 
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>