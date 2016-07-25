<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单表管理</title>
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
		<li class="active"><a href="${ctx}/luxclub/orderstatistics/">统计客户经理业绩</a></li>
		<li><a href="${ctx}/luxclub/orderstatistics/listInfo">统计账务信息</a></li>
		<li style="display: none;"><a href="${ctx}/luxclub/orderstatistics/flot">图表分析图</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/luxclub/orderstatistics"  method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			 
			<li><label>客户经理：</label>
				<form:select path="businessId" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getUserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>场所：</label>
				<form:select path="reserveSiteId" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${siteList}" itemLabel="siteName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>日期：</label>
				<input name="beginReserveDate"   type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.beginReserveDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endReserveDate"   type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.endReserveDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width: 60%;">
		<thead>
			<tr>
			    <th width="40%">场所</th>
				<th>客户经理</th>
				<th>金额</th>
			</tr>
		</thead>
		<tbody>
		<c:set var="sum" value="0"/>
		<c:forEach items="${page.list}" var="order">
			<tr>
			    <td>
					${order.siteName}
				</td>
				<td>
					${order.businessName}
				</td>
				<td>
					￥<fmt:formatNumber value="${-order.consumerMoney}" type="currency" pattern="#,##0.##"/>元
				</td>
			</tr>
			<c:set var="sum" value="${sum+order.consumerMoney}"></c:set>
		</c:forEach>
		</tbody>
		<tfoot>
		   <tr>
				<td>合计</td>
				<td colspan="2">￥<fmt:formatNumber value="${-sum}" type="currency" pattern="#,##0.##"/>元</td>
			</tr>
		</tfoot>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>