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
		<li><a href="${ctx}/luxclub/orderstatistics/">统计客户经理业绩</a></li>
		<li class="active"><a href="${ctx}/luxclub/orderstatistics/listInfo">统计账务信息</a></li>
		<li style="display: none;"><a href="${ctx}/luxclub/orderstatistics/flot">图表分析图</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/luxclub/orderstatistics/listInfo" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>日期：</label>
				<input name="beginReserveDate"   type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orderCnd.beginReserveDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endReserveDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${orderCnd.endReserveDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width: 40%;">
		<thead>
			<tr>
				<th>已结账</th>
				<th>未结账</th>
			</tr>
		</thead>
		<tbody>
		   <tr>
				<td> ￥<fmt:formatNumber value="${queryPayAmount}" type="currency" pattern="#,##0.##"/>元</td>
				<td> ￥<fmt:formatNumber value="${queryUnpayAmount}" type="currency" pattern="#,##0.##"/>元</td>
			</tr>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>