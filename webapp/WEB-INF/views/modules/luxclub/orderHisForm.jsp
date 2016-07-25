<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单历史表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/luxclub/orderHis/">订单历史表列表</a></li>
		<li class="active">
		  <a href="${ctx}/luxclub/orderHis/form?id=${orderHis.id}">订单历史表<shiro:hasPermission name="luxclub:orderHis:edit">详情</shiro:hasPermission>
		   </a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderHis" action="${ctx}/luxclub/orderHis/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">订单编号：</label>
			<div class="controls">
				<form:input path="orderCode" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消费时间：</label>
			<div class="controls">
				<input name="reserveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orderHis.reserveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消费人数：</label>
			<div class="controls">
				<form:select path="reserveNumber" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('reserve_number')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费用范围：</label>
			<div class="controls">
				<form:select path="reserveCost" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cost_range')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他要求：</label>
			<div class="controls">
				<form:input path="reserveRequire" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场所名称：</label>
			<div class="controls">
			    <form:select path="reserveSiteId" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${siteList}" itemLabel="siteName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单状态：</label>
			<div class="controls">
				<form:select path="state" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消费金额：</label>
			<div class="controls">
				<form:input path="consumerMoney" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消费凭证：</label>
			<div class="controls">
				<form:hidden id="consumerVouchers" path="consumerVouchers" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="consumerVouchers" type="files" uploadPath="/luxclub/orderHis" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员卡号：</label>
			<div class="controls">
				<form:input path="memberCardno" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="contactMobile" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">派单时间：</label>
			<div class="controls">
				<input name="sendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orderHis.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备ID：</label>
			<div class="controls">
				<form:input path="deviceId" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户经理：</label>
			<div class="controls">
			    <form:select path="businessId" class="input-xlarge ">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getUserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际时间：</label>
			<div class="controls">
				<input name="realDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${orderHis.realDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际人数：</label>
			<div class="controls">
				<form:input path="realNumber" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实际要求：</label>
			<div class="controls">
				<form:input path="realRequire" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>