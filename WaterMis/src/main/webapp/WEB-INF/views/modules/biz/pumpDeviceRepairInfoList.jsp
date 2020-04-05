<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>水泵故障维护信息管理</title>
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
		<li class="active"><a href="${ctx}/biz/pumpDeviceRepairInfo/">水泵故障维护信息列表</a></li>
		<shiro:hasPermission name="biz:pumpDeviceRepairInfo:edit"><li><a href="${ctx}/biz/pumpDeviceRepairInfo/form">水泵故障维护信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="pumpDeviceRepairInfo" action="${ctx}/biz/pumpDeviceRepairInfo/" method="post" class="breadcrumb form-search">
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
				<th>项目名称</th>
				<th>设备名称</th>
				<th>泵房名称</th>
				<th>用户名称</th>
				<th>维修时间</th>
				<th>维修内容</th>
				<th>故障配件</th>
				<th>故障原因</th>
				<th>解决方法</th>
				<th>入库时间</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="biz:pumpDeviceRepairInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pumpDeviceRepairInfo">
			<tr>
				<td>${pumpDeviceRepairInfo.project.id}</td>
				<td>${pumpDeviceRepairInfo.device.id}</td>
				<td>${pumpDeviceRepairInfo.pumpHouse.id}</td>
				<td>${pumpDeviceRepairInfo.user.id}</td>
				<td>${pumpDeviceRepairInfo.repairTime}</td>
				<td>${pumpDeviceRepairInfo.repairContent}</td>
				<td>${pumpDeviceRepairInfo.accessoriesXh}</td>
				<td>${pumpDeviceRepairInfo.faultReason}</td>
				<td>${pumpDeviceRepairInfo.solution}</td>
				<td>${pumpDeviceRepairInfo.createtime}</td>
				<td>${pumpDeviceRepairInfo.updatetime}</td>
				<td>${pumpDeviceRepairInfo.memo}</td>
				<shiro:hasPermission name="biz:pumpDeviceRepairInfo:edit"><td>
    				<a href="${ctx}/biz/pumpDeviceRepairInfo/form?id=${pumpDeviceRepairInfo.id}">修改</a>
					<a href="${ctx}/biz/pumpDeviceRepairInfo/delete?id=${pumpDeviceRepairInfo.id}" onclick="return confirmx('确认要删除该水泵故障维护信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>