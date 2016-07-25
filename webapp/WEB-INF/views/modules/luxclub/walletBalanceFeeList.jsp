<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>零钱包充值/消费信息</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/lightbox2/css/lightbox.css" rel="stylesheet">
</head>
<body>
	<ul class="nav nav-tabs">
	    <li><a href="${ctx}/luxclub/walletBalance/">零钱包信息列表</a></li>
		<li class="active"><a href="#">零钱包充值/消费信息</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="walletFeeRecord" action="${ctx}/luxclub/walletBalance/feeList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员卡号：</label>
				<form:input path="memberCardno" htmlEscape="false" maxlength="64" class="input-medium" readonly="true"/>
			</li>
			<li><label>费用类型：</label>
				<form:select path="feeType" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getDictList('luxclub_wallet_fee_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>订单编号：</label>
				<form:input path="orderCode" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>场所：</label>
				<form:select path="siteId" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${siteList}" itemLabel="siteName" itemValue="id" htmlEscape="false"/>
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
				<th>零钱包余额</th>
				<th>金额</th>
				<th>费用类型</th>
				<th>订单编号</th>
				<th>场所</th>
				<th>更新时间</th>
				<th>凭证</th>
				<th>备注信息</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="walletFeeRecord">
			<tr>
				<td>
					${walletFeeRecord.balance}
				</td>
				<td>
					${walletFeeRecord.amount}
				</td>
				<td>
					${fns:getDictLabel(walletFeeRecord.feeType, 'luxclub_wallet_fee_type', '')}
				</td>
				<td>
					${walletFeeRecord.orderCode}
				</td>
				<td>
					${walletFeeRecord.siteInfo.siteName}
				</td>
				<td>
					<fmt:formatDate value="${walletFeeRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:forEach items="${walletFeeRecord.voucherArray}" var="pic" varStatus="status">
						<a href="${pic}" data-lightbox="walletVucher-${walletFeeRecord.id}" data-title="凭证">
							<img src="${pic}" width="70px" height="60px" alt=""/>
						</a>
					</c:forEach>
				</td>
				<td>
					${walletFeeRecord.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
    <script src="${ctxStatic}/lightbox2/js/lightbox.js"></script>
	<script type="text/javascript">
			function page(n,s){
				$("#pageNo").val(n);
				$("#pageSize").val(s);
				$("#searchForm").submit();
	        	return false;
	        }
	</script>
</body>
</html>