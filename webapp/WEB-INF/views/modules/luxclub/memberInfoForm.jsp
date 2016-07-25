<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员信息管理</title>
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
		<li><a href="${ctx}/luxclub/memberInfo/">会员卡信息列表</a></li>
		<li class="active"><a href="${ctx}/luxclub/memberInfo/form?id=${memberInfo.id}">会员卡信息<shiro:hasPermission name="luxclub:memberInfo:edit">${not empty memberInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="luxclub:memberInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="memberInfo" action="${ctx}/luxclub/memberInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">会员卡号：</label>
			<div class="controls">
				<form:input path="memberCardno" htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">级别：</label>
			<div class="controls">
				<form:select path="memberLevel" class="input-xlarge ">
					<form:options items="${fns:getDictList('luxclub_card_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡主姓名：</label>
			<div class="controls">
				<form:input path="memberName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡主电话：</label>
			<div class="controls">
				<form:input path="memberMobile" htmlEscape="false" maxlength="13" class="input-xlarge "/>
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
				<form:input path="obligation" htmlEscape="false" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发放日期：</label>
			<div class="controls">
				<input name="issuingDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${memberInfo.issuingDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">过期日期：</label>
			<div class="controls">
				<input name="expireDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${memberInfo.expireDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="state" class="input-xlarge" disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('luxclub_member_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="luxclub:memberInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>