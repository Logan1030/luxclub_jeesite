<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员卡信息历史管理</title>
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
	<style type="text/css">
	th{text-align:center;}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/luxclub/memberInfo/">会员卡信息列表</a></li>
		<li class="active"><a href="#">会员卡信息历史列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="memberInfoHis" action="${ctx}/luxclub/memberInfo/hisList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>会员卡号：</label>
				<form:input path="memberCardno" htmlEscape="false" maxlength="64" class="input-mini" readonly="true"/>
			</li>
			<li><label>发放日期：</label>
				<input name="issuingDate" type="text" readonly="readonly" maxlength="20" class="input-mini"
					value="<fmt:formatDate value="${memberInfoHis.issuingDate}" pattern="yyyy-MM-dd"/>"/>
			</li>
			<li><label>过期日期：</label>
				<input name="expireDate" type="text" readonly="readonly" maxlength="20" class="input-mini"
					value="<fmt:formatDate value="${memberInfoHis.expireDate}" pattern="yyyy-MM-dd"/>"	/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>操作日期：</label>
				<input name="beginOperationDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${memberInfoHis.beginOperationDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endOperationDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${memberInfoHis.endOperationDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>操作类型：</label>
				<form:select path="operationType" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getDictList('luxclub_member_his_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="state" class="input-mini">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('luxclub_member_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>级别：</label>
				<form:select path="memberLevel" class="input-mini">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('luxclub_card_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th rowspan="2">级别</th>
				<th rowspan="2">状态</th>
				<th colspan="2"  style="text-align:center">会员卡</th>
				<th colspan="2"  style="text-align:center">零钱包</th>
				<th rowspan="2">操作类型</th>
				<th rowspan="2">操作时间</th>
				<th rowspan="2">卡主姓名</th>
				<th rowspan="2">卡主电话</th>
				<th rowspan="2">设备ID</th>
				<th rowspan="2">备注信息</th>
				<shiro:hasPermission name="luxclub:memberInfoHis:edit"><th rowspan="2">操作</th></shiro:hasPermission>
			</tr>
			<tr>
				<th>余额</th>
				<th>交易金额</th>
				<th>余额</th>
				<th>交易金额</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memberInfoHis">
			<tr>
				<td>
					${fns:getDictLabel(memberInfoHis.memberLevel, 'luxclub_card_type', '')}
				</td>
				<td>
					${fns:getDictLabel(memberInfoHis.state, 'luxclub_member_state', '')}
				</td>
				<td>
					${memberInfoHis.memberBalance}
				</td>
				<td>
					${memberInfoHis.memberAmount}
				</td>
				<td>
					${memberInfoHis.walletBalance}
				</td>
				<td>
					${memberInfoHis.walletAmount}
				</td>
				<td>
					${fns:getDictLabel(memberInfoHis.operationType, 'luxclub_member_his_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${memberInfoHis.operationDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${memberInfoHis.memberName}
				</td>
				<td>
					${memberInfoHis.memberMobile}
				</td>
				<td>
					${memberInfoHis.deviceId}
				</td>
				<td>
					${memberInfoHis.remarks}
				</td>
				<shiro:hasPermission name="luxclub:memberInfoHis:edit"><td>
    				<a href="${ctx}/luxclub/memberInfoHis/form?id=${memberInfoHis.id}">修改</a>
					<a href="${ctx}/luxclub/memberInfoHis/delete?id=${memberInfoHis.id}" onclick="return confirmx('确认要删除该会员卡信息历史吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>