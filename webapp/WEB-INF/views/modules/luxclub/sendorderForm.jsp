<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					confirmx("确认要派单吗？",function(){
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
		<li><a href="${ctx}/luxclub/sendorder/">派单订单列表</a></li>
		<li class="active"><a href="${ctx}/luxclub/sendorder/form?id=${order.id}">派单订单<shiro:hasPermission name="luxclub:sendorder:edit">${not empty order.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="luxclub:sendorder:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="order" action="${ctx}/luxclub/sendorder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">业务员编号：</label>
			<div class="controls">
			    <form:select path="businessId" class="input-medium required">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getUserList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				 
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">消费时间：</label>
			<div class="controls">
				<input name="reserveDate" type="text" disabled="disabled" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${order.reserveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消费人数：</label>
			<div class="controls">
				<form:select path="reserveNumber" class="input-xlarge "  disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('reserve_number')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费用范围：</label>
			<div class="controls">
				<form:select path="reserveCost" class="input-xlarge " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('cost_range')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他要求：</label>
			<div class="controls">
				<form:input path="reserveRequire" htmlEscape="false" readonly="true" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场所名称：</label>
			<div class="controls">
			    <form:select path="reserveSiteId" class="input-medium" disabled="true">
					<form:option value="" label="==请选择=="/>
					<form:options items="${siteList}" itemLabel="siteName" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		 
		<div class="control-group">
			<label class="control-label">会员卡号：</label>
			<div class="controls">
				<form:input path="memberCardno" htmlEscape="false" readonly="true"  maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="contactMobile" htmlEscape="false" readonly="true"   maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false"  rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="luxclub:sendorder:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="派单"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>