<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>水泵故障信息管理</title>
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
		<li class="active"><a href="${ctx}/biz/pumpFaultInfo/">水泵故障信息列表</a></li>
		<shiro:hasPermission name="biz:pumpFaultInfo:edit"><li><a href="${ctx}/biz/pumpFaultInfo/form">水泵故障信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="pumpFaultInfo" action="${ctx}/biz/pumpFaultInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>报警编码</th>
				<th>设备编码</th>
				<th>故障级别</th>
				<th>故障信息</th>
				<th>故障状态</th>
				<shiro:hasPermission name="biz:pumpFaultInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pumpFaultInfo">
			<tr>
				<td>${pumpFaultInfo.alarm.id}</td>
				<td>${pumpFaultInfo.device.id}</td>
				<td>${pumpFaultInfo.faultLevel}</td>
				<td>${pumpFaultInfo.faultMessage}</td>
				<td>${pumpFaultInfo.faultState}</td>
				<shiro:hasPermission name="biz:pumpFaultInfo:edit"><td>
    				<a href="${ctx}/biz/pumpFaultInfo/form?id=${pumpFaultInfo.id}">修改</a>
					<a href="${ctx}/biz/pumpFaultInfo/delete?id=${pumpFaultInfo.id}" onclick="return confirmx('确认要删除该水泵故障信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>