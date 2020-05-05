<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门禁管理</title>
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
		<li class="active"><a href="${ctx}/biz/accessDevice/">门禁列表</a></li>
		<shiro:hasPermission name="biz:accessDevice:edit"><li><a href="${ctx}/biz/accessDevice/form">门禁添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="accessDevice" action="${ctx}/biz/accessDevice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>门禁名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>门禁名称</th>
				<th>出入类型</th>
				<th>门锁类型</th>
				<th>连接类型</th>
				<th>生产商</th>
				<th>集成商</th>
				<th>IP地址</th>
				<th>MC地址</th>
				<th>安装日期</th>
				<th>备注</th>
				<shiro:hasPermission name="biz:accessDevice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="accessDevice">
			<tr>
				<td><a href="${ctx}/biz/accessDevice/form?id=${accessDevice.id}">
					${accessDevice.name}
					</a>
				</td>
				<td>${accessDevice.accessType}</td>
				<td>${accessDevice.lockType}</td>
				<td>${accessDevice.connectType}</td>
				<td>${accessDevice.manufactor}</td>
				<td>${accessDevice.assemblor}</td>
				<td>${accessDevice.accIpAddr}</td>
				<td>${accessDevice.macAddr}</td>
				<td>${accessDevice.installDate}</td>
				<td>${accessDevice.memo}</td>
				<shiro:hasPermission name="biz:accessDevice:edit"><td>
    				<a href="${ctx}/biz/accessDevice/form?id=${accessDevice.id}">修改</a>
					<a href="${ctx}/biz/accessDevice/delete?id=${accessDevice.id}" onclick="return confirmx('确认要删除该门禁吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>