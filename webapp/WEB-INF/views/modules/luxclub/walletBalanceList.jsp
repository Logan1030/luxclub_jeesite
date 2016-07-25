<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>零钱包余额管理</title>
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
		<li class="active"><a href="${ctx}/luxclub/walletBalance/">零钱包信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="memberInfo" action="${ctx}/luxclub/walletBalance/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员卡号：</label>
				<form:input path="memberCardno" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>级别：</label>
				<form:select path="memberLevel" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getDictList('luxclub_card_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getDictList('luxclub_member_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>卡主姓名：</label>
				<form:input path="memberName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>卡主电话：</label>
				<form:input path="memberMobile" htmlEscape="false" maxlength="13" class="input-medium"/>
			</li>
			<li><label>发放日期：</label>
				<input name="beginIssuingDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${memberInfo.beginIssuingDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endIssuingDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${memberInfo.endIssuingDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>会员卡号</th>
				<th>级别</th>
				<th>零钱包余额</th>
				<th>零钱包待付款</th>
				<th>状态</th>
				<th>卡主姓名</th>
				<th>卡主电话</th>
				<th>发放日期</th>
				<th>过期日期</th>
				<th>备注信息</th>
				<shiro:hasPermission name="luxclub:walletBalance:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memberInfo">
			<tr>
				<td><a href="${ctx}/luxclub/walletBalance/feeList?memberCardno=${memberInfo.memberCardno}">
					${memberInfo.memberCardno}
				</a></td>
				<td>
					${fns:getDictLabel(memberInfo.memberLevel, 'luxclub_card_type', '')}
				</td>
				<td>
					${memberInfo.walletBalance}
				</td>
				<td>
					${memberInfo.walletPrepay}
				</td>
				<td>
					${fns:getDictLabel(memberInfo.state, 'luxclub_member_state', '')}
				</td>
				<td>
					${memberInfo.memberName}
				</td>
				<td>
					${memberInfo.memberMobile}
				</td>
				<td>
					<fmt:formatDate value="${memberInfo.issuingDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${memberInfo.expireDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${memberInfo.remarks}
				</td>
				<shiro:hasPermission name="luxclub:walletBalance:edit">
				<td>
					<c:if test="${memberInfo.state != 3 && memberInfo.state != 4}">
						<a href="${ctx}/luxclub/walletBalance/rechargeForm?id=${memberInfo.id}">充值</a>
						<a href="${ctx}/luxclub/walletBalance/deductForm?id=${memberInfo.id}">扣费</a>
					</c:if>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>