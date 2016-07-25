<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>场所信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$('.lightbox').css("display","none");
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
			// 电话号码验证     
			jQuery.validator.addMethod("mobiePhone", function(value, element) {     
				var tel = /(^0[1-9]{1}\d{9,10}$)|(^1[3,5,8]\d{9}$)/g;     
				return this.optional(element) || (tel.test(value));     
			}, "请正确填写您的手机号码或电话号码");
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/luxclub/siteInfo/">场所信息列表</a></li>
		<li class="active"><a href="${ctx}/luxclub/siteInfo/form?id=${siteInfo.id}">场所信息<shiro:hasPermission name="luxclub:siteInfo:edit">${not empty siteInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="luxclub:siteInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="siteInfo" action="${ctx}/luxclub/siteInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="contactMan" htmlEscape="false" maxlength="16" class="input-xlarge required realName"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="contactPhone" htmlEscape="false" maxlength="16" class="input-xlarge required mobiePhone"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮件：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="25" class="input-xlarge email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场所类型：</label>
			<div class="controls">
				<form:select path="siteType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('site_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否开业：</label>
			<div class="controls">
				<form:radiobuttons path="isOpen" items="${fns:getDictList('luxclub_is_open')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场所区域:</label>
			<div class="controls">
                <sys:treeselect id="area" name="area.id" value="${siteInfo.area.id}" labelName="area.name" labelValue="${siteInfo.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场所名称：</label>
			<div class="controls">
				<form:input path="siteName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场所地址：</label>
			<div class="controls">
				<form:input path="siteAddr" htmlEscape="false" maxlength="40" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场所描述：</label>
			<div class="controls">
			    <form:textarea path="siteDescription" htmlEscape="false" rows="2" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">店家主图：</label>
			<div class="controls">
				<form:hidden id="shopPhoto" path="shopPhoto" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="shopPhoto" type="images" uploadPath="/luxclub/siteInfo" selectMultiple="false"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">店家相册：</label>
			<div class="controls">
				<form:hidden id="shopAlbum" path="shopAlbum" htmlEscape="false" class="input-xlarge"/>
				<sys:ckfinder input="shopAlbum" type="images" uploadPath="/luxclub/siteInfo" maxHeight="100" maxWidth="100" selectMultiple="true" maxSelect="4"/>
				<span class="help-inline"><font color="red">374*220 *</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="2" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">活动信息：</label>
			<form:textarea id="activeInfo"  htmlEscape="false" path="activeInfo"  rows="2" maxlength="100" class="input-xlarge"/>
		    <sys:ckeditor replace="activeInfo" uploadPath="/luxclub/siteInfo" />

		</div>
		<div class="form-actions">
			<shiro:hasPermission name="luxclub:siteInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>