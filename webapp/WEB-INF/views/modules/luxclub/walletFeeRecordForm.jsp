<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>零钱包充值/消费管理</title>
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
		<li><a href="${ctx}/luxclub/walletFeeRecord/">零钱包充值/消费列表</a></li>
		<li class="active"><a href="${ctx}/luxclub/walletFeeRecord/form?id=${feeRecord.id}">零钱包充值/消费记录<shiro:hasPermission name="luxclub:walletFeeRecord:edit">${not empty feeRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="luxclub:walletFeeRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="walletFeeRecord" action="${ctx}/luxclub/walletFeeRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会员卡号：</label>
			<div class="controls">
				<form:input path="memberCardno" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费用类型：</label>
			<div class="controls">
				<form:select path="feeType" class="input-xlarge " disabled="true">
					<form:options items="${fns:getDictList('luxclub_fee_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">金额：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">余额：</label>
			<div class="controls">
				<form:input path="balance" htmlEscape="false" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单编号：</label>
			<div class="controls">
				<form:input path="orderCode" htmlEscape="false" maxlength="20" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场所编号：</label>
			<div class="controls">
				<form:input path="siteInfo.siteName" htmlEscape="false" maxlength="20" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">凭证：</label>
				<div class="controls">
					<img src="${walletFeeRecord.voucher}" width="400px" height="300px" alt="">
				</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge" readonly="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="luxclub:walletFeeRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>