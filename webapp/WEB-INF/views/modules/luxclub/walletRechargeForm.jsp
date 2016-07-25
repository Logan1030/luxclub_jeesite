<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>零钱包余额管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#amount").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					confirmx("确认要充值吗？",function(){
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/luxclub/walletBalance/">零钱包余额列表</a></li>
		<li class="active">
			<a href="${ctx}/luxclub/walletBalance/rechargeForm?id=${memberInfo.id}">零钱包充值</a>
		</li>
	</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="memberInfo" action="${ctx}/luxclub/walletBalance/recharge" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会员卡号：</label>
			<div class="controls">
				<form:input path="memberCardno" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">零钱包余额：</label>
			<div class="controls">
				<form:input path="walletBalance" htmlEscape="false" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">充值金额：</label>
			<div class="controls">
				<form:input id="amount" path="amount" htmlEscape="false" min="1" maxlength="10" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">充值凭证：</label>
			<div class="controls">
				<form:hidden id="voucherPhoto" name="voucherPhoto" path="voucherPhoto" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="voucherPhoto" type="images" uploadPath="/luxclub/walletFeeInfo" maxWidth="100" maxHeight="100" selectMultiple="true" maxSelect="10"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="luxclub:walletBalance:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="充值"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>