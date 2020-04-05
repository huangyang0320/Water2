<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备管理</title>
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
		<li class="active"><a href="${ctx}/biz/device/">设备列表</a></li>
		<shiro:hasPermission name="biz:device:edit"><li><a href="${ctx}/biz/device/form">设备添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="device" action="${ctx}/biz/device/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>类型</th>
				<th>泵房名称</th>
				<th>项目名称</th>
				<th>购买金额</th>
				<th>购买日期</th>
				<th>出厂日期</th>
				<th>开始使用时间</th>
				<th>信息更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="biz:device:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="device">
			<tr>
				<td><a href="${ctx}/biz/device/form?id=${device.id}">
					${device.name}
					</a>
				</td>
				<td>${device.type}</td>
				<td>${device.pumpHouse.name}</td>
				<td>${device.project.name}</td>
				<td>${device.purchaseAmount}</td>
				<td>${device.datePurchase}</td>
				<td>${device.dateManufacture}</td>
				<td>${device.createtime}</td>
				<td>${device.updatetime}</td>
				<td>${device.memo}</td>
				<shiro:hasPermission name="biz:device:edit"><td>
    				<a href="${ctx}/biz/device/form?id=${device.id}">修改</a>
					<a href="${ctx}/biz/device/delete?id=${device.id}" onclick="return confirmx('确认要删除该设备吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>