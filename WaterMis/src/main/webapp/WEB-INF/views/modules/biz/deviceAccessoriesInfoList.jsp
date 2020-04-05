<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备配件信息管理</title>
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
		<li class="active"><a href="${ctx}/biz/deviceAccessoriesInfo/">设备配件信息列表</a></li>
		<shiro:hasPermission name="biz:deviceAccessoriesInfo:edit"><li><a href="${ctx}/biz/deviceAccessoriesInfo/form">设备配件信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="deviceAccessoriesInfo" action="${ctx}/biz/deviceAccessoriesInfo/" method="post" class="breadcrumb form-search">
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
				<th>配件名称</th>
				<th>设备名称</th>
				<th>配件型号</th>
				<th>配件类型</th>
				<th>所属单元</th>
				<th>所属条目</th>
				<th>插入时间</th>
				<th>最后更新时间</th>
				<shiro:hasPermission name="biz:deviceAccessoriesInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="deviceAccessoriesInfo">
			<tr>
				<td>${deviceAccessoriesInfo.accessoriesName}</td>
				<td>${deviceAccessoriesInfo.device.name}</td>
				<td>${deviceAccessoriesInfo.accessoriesNo}</td>
				<td>${deviceAccessoriesInfo.accessoriesType}</td>
				<td>${deviceAccessoriesInfo.accessoriesUnits}</td>
				<td>${deviceAccessoriesInfo.accessoriesItemid}</td>
				<td>${deviceAccessoriesInfo.insertTime}</td>
				<td>${deviceAccessoriesInfo.lastUpdateTime}</td>
				<shiro:hasPermission name="biz:deviceAccessoriesInfo:edit"><td>
    				<a href="${ctx}/biz/deviceAccessoriesInfo/form?id=${deviceAccessoriesInfo.id}">修改</a>
					<a href="${ctx}/biz/deviceAccessoriesInfo/delete?id=${deviceAccessoriesInfo.id}" onclick="return confirmx('确认要删除该设备配件信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>