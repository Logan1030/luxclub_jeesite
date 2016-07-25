<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$('.lightbox').css("display","none");
			$("#inputForm").validate({
				submitHandler: function(form){
					confirmx("确认要结账吗？",function(){
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
		<li><a href="${ctx}/luxclub/accountorder/">结账订单列表</a></li>
		<li class="active"><a href="${ctx}/luxclub/accountorder/form?id=${order.id}">结账订单<shiro:hasPermission name="luxclub:accountorder:edit">${not empty order.id?'核算':'添加'}</shiro:hasPermission><shiro:lacksPermission name="luxclub:accountorder:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="order" action="${ctx}/luxclub/accountorder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
		  <tbody>
		    <tr>
		      <td>
				<label class="control-label">会员卡号：</label>
			    <form:input path="memberCardno" disabled="true" htmlEscape="false" maxlength="20" />
		      </td>
		      <td>
				<label class="control-label">会员手机号：</label>
				<div class="controls">
				    <form:input path="contactMobile" disabled="true"  readonly="readonly" htmlEscape="false" maxlength="16" class="input-medium"/>
			    </div>
		      </td>
		    </tr>
		    <tr>
		      <td>
				<label class="control-label">会员卡号余额：</label>
				<div class="controls">
				     ￥<fmt:formatNumber value="${memberInfo.memberBalance }" type="currency" pattern="#,##0.##"/>元
			    </div>
		      </td>
		      <td>
				<label class="control-label">零钱包余额：</label>
				<div class="controls">
				   ￥<fmt:formatNumber value="${memberInfo.walletBalance }" type="currency" pattern="#,##0.##"/>元
				</div>
		      </td>
		    </tr>
		    <tr>
		      <td>
				<label class="control-label">会员卡待付款：</label>
				<div class="controls">
				    ￥<fmt:formatNumber value="${memberInfo.obligation }" type="currency" pattern="#,##0.##"/>元
			    </div>
		      </td>
		      <td>
				<label class="control-label">零钱包待付款：</label>
				<div class="controls">
				  ￥<fmt:formatNumber value="${memberInfo.walletPrepay }" type="currency" pattern="#,##0.##"/>元
				</div>
		      </td>
		    </tr>
		    <tr>
		      <td>
				<label class="control-label">消费金额：</label>
				<div class="controls">
				    <form:input path="consumerMoney"   htmlEscape="false" class="input-medium required number"/>元
			    </div>
		      </td>
		      <td>
				<label class="control-label">零钱包消费额：</label>
				<div class="controls">
				 <form:input path="walletAmount"  disabled="true"  htmlEscape="false" class="input-medium"/>元
				</div>
		      </td>
		    </tr>
		    <tr>
		      <td>
				<label class="control-label">业务员：</label>
				<div class="controls">
				  ${user.name }
				</div>
		      </td>
		      <td>
				<label class="control-label">业务员手机号码：</label>
				<div class="controls">
				  ${user.mobile }
				</div>
		      </td>
		    </tr>
		    <tr>
		      <td>
				<label class="control-label">消费凭证：</label>
				<div class="controls">
				    <form:hidden id="consumerVouchers" path="consumerVouchers" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="consumerVouchers" type="images" uploadPath="/luxclub/accountorder" readonly="true" />
			    </div>
		      </td>
		      <td>
				<label class="control-label">零钱包凭证：</label>
				<div class="controls">
				    <form:hidden id="walletVoucher" path="walletVoucher" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="walletVoucher" type="images" uploadPath="/luxclub/accountorder" readonly="true" />
			    </div>
		      </td>
		    </tr>
		    <tr>
		      <td>
				<label class="control-label">场所名称：</label>
				<div class="controls">
				    <form:select path="reserveSiteId" disabled="true" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${siteList}" itemLabel="siteName" itemValue="id" htmlEscape="false"/>
				    </form:select>
			    </div>
		      </td>
		      <td>
				<label class="control-label">消费时间：</label>
				<div class="controls">
				 <input name="reserveDate" type="text" readonly="readonly" maxlength="20" class="input-medium "
					value="<fmt:formatDate value="${order.reserveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
				</div>
		      </td>
		    </tr>
		    <tr>
		      <td>
				<label class="control-label">其他要求：</label>
				<div class="controls">
				     <form:textarea path="reserveRequire" disabled="true" htmlEscape="false" rows="2" maxlength="255" class="input-xlarge "/>
			    </div>
		      </td>
		      <td>
				<label class="control-label">实际要求：</label>
				<div class="controls">
				 <form:textarea path="realRequire" disabled="true" htmlEscape="false" rows="2" maxlength="255" class="input-xlarge "/>
				</div>
		      </td>
		    </tr>
		  </tbody>
		</table> 
		
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks"   htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="luxclub:accountorder:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="结账"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>