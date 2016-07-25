<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员卡余额管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#amount").focus();
			$("#memberMobile").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
				    
					confirmx("确认要激活吗？",function(){
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
		// 手机号码验证
		jQuery.validator.addMethod("mobile1", function(value, element) {
		    var length = value.length;
		    return this.optional(element) || (length == 11 && /^1[3|4|5|7|8]\d{9}$/.test(value));
		}, "请正确填写您的手机号码");
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/luxclub/memberInfo/">会员卡信息列表</a></li>
		<li class="active">
			<a href="${ctx}/luxclub/memberInfo/active?id=${memberInfo.id}">会员卡激活</a>
		</li>
	</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="memberInfo" action="${ctx}/luxclub/memberInfo/doActive" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会员卡号：</label>
			<div class="controls">
				<form:input path="memberCardno" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码：</label>
			<div class="controls">
				<form:input id="memberMobile" path="memberMobile" htmlEscape="false" min="1" maxlength="11" class="input-xlarge required mobile1"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认手机号码：</label>
			<div class="controls">
				<form:input id="amount" path="amount" htmlEscape="false" min="1" maxlength="11" class="input-xlarge required mobile1" equalTo="#memberMobile"/>
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
			<shiro:hasPermission name="luxclub:memberInfo:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="激活"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>