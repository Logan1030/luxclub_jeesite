<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员卡批次管理</title>
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
		<li class="active"><a href="${ctx}/luxclub/cardBatch/">会员卡批次列表</a></li>
		<shiro:hasPermission name="luxclub:cardBatch:edit"><li><a href="${ctx}/luxclub/cardBatch/form">会员卡批次添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="cardBatch" action="${ctx}/luxclub/cardBatch/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>批次名称：</label>
				<form:input path="batichName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>生成卡类型：</label>
				<form:select path="cardType" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getDictList('luxclub_card_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>批次名称</th>
				<th>生成卡类型</th>
				<th>生成数量</th>
				<th>生成时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="luxclub:cardBatch:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="cardBatch">
			<tr>
				<td>
					<a href="${ctx}/luxclub/memberInfo/list?batchId=${cardBatch.id}">
						${cardBatch.batichName}
					</a>
				</td>
				<td>
					${fns:getDictLabel(cardBatch.cardType, 'luxclub_card_type', '')}
				</td>
				<td>
					${cardBatch.createCount}
				</td>
				<td>
					<fmt:formatDate value="${cardBatch.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${cardBatch.remarks}
				</td>
				<shiro:hasPermission name="luxclub:cardBatch:edit"><td>
					<a href="${ctx}/luxclub/cardBatch/export?id=${cardBatch.id}" onclick="return confirmx('确认要导出该批次的会员卡吗？', this.href)">导出</a>
    				<a href="${ctx}/luxclub/cardBatch/form?id=${cardBatch.id}">修改</a>
					<a href="${ctx}/luxclub/cardBatch/delete?id=${cardBatch.id}" onclick="return confirmx('确认要删除该会员卡批次吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>