<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息通知管理</title>
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/luxclub/messageNotify/">消息通知列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="messageNotify" action="${ctx}/luxclub/messageNotify/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>接收人：</label>
				<form:input path="receiverName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>消息标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="messageType" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getDictList('luxclub_message_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>信息渠道：</label>
				<form:select path="messageChannel" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getDictList('luxclub_message_channel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		    <li><label>状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label="==请选择=="/>
					<form:options items="${fns:getDictList('luxclub_message_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>返回代码：</label>
				<form:input path="returnCode" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>创建日期：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${messageNotify.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${messageNotify.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>接收人</th>
				<th>消息标题</th>
				<th>内容</th>
				<th>类型</th>
				<th>消息渠道</th>
				<th>状态</th>
				<th>返回代码</th>
				<th>错误信息</th>
				<th>发送时间</th>
				<th>创建日期</th>
				<th>电话</th>
				<th>会员卡号</th>
				<th>订单编号</th>
				<th>备注信息</th>
				<shiro:hasPermission name="luxclub:messageNotify:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="messageNotify">
			<tr>
				<td>
					${messageNotify.receiverName}
				</td>
				<td>
					${messageNotify.title}
				</td>
				<td>
					${messageNotify.sendContent}
				</td>
				<td>
					${fns:getDictLabel(messageNotify.messageType, 'luxclub_message_type', '')}
				</td>
				<td>
					${fns:getDictLabel(messageNotify.messageChannel, 'luxclub_message_channel', '')}
				</td>
				<td>
					${fns:getDictLabel(messageNotify.state, 'luxclub_message_state', '')}
				</td>
				<td>
					${messageNotify.returnCode}
				</td>
				<td>
					${messageNotify.errorMessage}
				</td>
				<td>
					<fmt:formatDate value="${messageNotify.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${messageNotify.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${messageNotify.memberPhone}
				</td>
				<td>
					${messageNotify.memberCardno}
				</td>
				<td>
					${messageNotify.orderCode}
				</td>
				<td>
					${messageNotify.remarks}
				</td>
				<shiro:hasPermission name="luxclub:messageNotify:edit"><td>
					<a href="${ctx}/luxclub/messageNotify/delete?id=${messageNotify.id}" onclick="return confirmx('确认要删除该消息通知吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>