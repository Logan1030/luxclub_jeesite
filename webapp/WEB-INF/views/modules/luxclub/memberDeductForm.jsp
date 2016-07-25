<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员卡余额管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#amount").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					confirmx("确认要扣费吗？",function(){
						loading('正在提交，请稍等...');
						form.submit();
					});
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
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
		<li><a href="${ctx}/luxclub/cardBalance/">会员卡余额列表</a></li>
		<li class="active">
			<a href="${ctx}/luxclub/cardBalance/deductForm?id=${memberInfo.id}">会员卡扣费</a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="memberInfo" action="${ctx}/luxclub/cardBalance/deduct" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">会员卡号：</label>
			<div class="controls">
				<form:input path="memberCardno" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额：</label>
			<div class="controls">
				<form:input path="memberBalance" htmlEscape="false" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">待付款：</label>
			<div class="controls">
				<form:input path="obligation" htmlEscape="false" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扣费金额：</label>
			<div class="controls">
				<form:input id="amount" path="amount" htmlEscape="false" min="0.01" maxlength="10" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扣费凭证：</label>
			<div class="controls">
				<form:hidden id="voucherPhoto" path="voucherPhoto" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="voucherPhoto" type="images" uploadPath="/luxclub/feeInfo" maxWidth="100" maxHeight="100" selectMultiple="true" maxSelect="10"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>订单编号</th>
					<th>消费金额</th>
					<th>消费时间</th>
					<th>订单状态</th>
					<th>客户经理</th>
					<th>消费人数</th>
					<th>实际时间</th>
					<th>下单时间</th>
					<th>费用范围</th>
					<th>备注信息</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="order">
					<tr>
						<td>
							${order.orderCode}
						</td>
						<td>
							${order.consumerMoney}
						</td>
						<td>
							<fmt:formatDate value="${order.reserveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${fns:getDictLabel(order.state, 'order_state', '')}
						</td>
						<td>
							${order.businessName}
						</td>
						<td>
							${fns:getDictLabel(order.reserveNumber, 'reserve_number', '')}
						</td>
						<td>
							${fns:getDictLabel(order.reserveCost, 'cost_range', '')}
						</td>
						<td>
							<fmt:formatDate value="${order.realDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${order.remarks}
						</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
	<div class="pagination">${page}</div>
		<div class="form-actions">
			<shiro:hasPermission name="luxclub:cardBalance:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="扣费"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>