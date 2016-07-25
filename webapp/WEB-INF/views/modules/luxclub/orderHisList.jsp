<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单历史表管理</title>
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
		<li class="active"><a href="${ctx}/luxclub/orderHis/">订单历史表列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="orderHis" action="${ctx}/luxclub/orderHis/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单编号：</label>
				<form:input path="orderCode" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>订单时间：</label>
				<input name="beginReserveDate"   type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.beginReserveDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endReserveDate"   type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.endReserveDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>消费人数：</label>
				<form:select path="reserveNumber" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('reserve_number')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>费用范围：</label>
				<form:select path="reserveCost" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cost_range')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>订单状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>会员卡号：</label>
				<form:input path="memberCardno" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="contactMobile" htmlEscape="false" maxlength="16" class="input-medium"/>
			</li>
			<li><label>客户经理：</label>
				<form:select path="businessId" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getUserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
				<th>订单编号</th>
				<th>会员卡号</th>
				<th>客户经理</th>
				<th>联系电话</th>
				<th>消费人数</th>
				<th>消费金额</th>
				<th>实际人数</th>
				<th>消费时间</th>
				<th>派单时间</th>
				<th>操作时间</th>
				<th>订单状态</th>
				<th>备注信息</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderHis">
			<tr>
				<td><a href="${ctx}/luxclub/orderHis/form?id=${orderHis.id}">
					${orderHis.orderCode}
				</a></td>
				<td>
					${orderHis.memberCardno}
				</td>
				<td>
					${orderHis.businessName}
				</td>
				<td>
					${orderHis.contactMobile}
				</td>
				<td>
					${fns:getDictLabel(orderHis.reserveNumber, 'reserve_number', '')}
				</td>

				
				<td>
					${orderHis.consumerMoney}
				</td>
				
				<td>
					${orderHis.realNumber}
				</td>
				<td>
					<fmt:formatDate value="${orderHis.reserveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${orderHis.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${orderHis.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(orderHis.state, 'order_state', '')}
				</td>
				<td>
					${orderHis.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>