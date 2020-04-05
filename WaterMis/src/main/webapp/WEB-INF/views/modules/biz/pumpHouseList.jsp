<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>泵房管理</title>
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
		<li class="active"><a href="${ctx}/biz/pumpHouse/">泵房列表</a></li>
		<shiro:hasPermission name="biz:pumpHouse:edit"><li><a href="${ctx}/biz/pumpHouse/form">泵房添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="pumpHouse" action="${ctx}/biz/pumpHouse/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>泵房名称：</label>
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
				<th>项目名称</th>
				<th>泵房名称</th>
				<th>泵房地址</th>
				<th>经度</th>
				<th>纬度</th>
				<th>门禁IP地址</th>
				<th>门禁端口号</th>
				<th>全景图片地址</th>
				<th>所属地区</th>
				<th>备注</th>
				<shiro:hasPermission name="biz:pumpHouse:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="pumpHouse">
			<tr>
				<td>${pumpHouse.project.id}</td>
				<td><a href="${ctx}/biz/pumpHouse/form?id=${pumpHouse.id}">
					${pumpHouse.name}
					</a>
				</td>
				<td>${pumpHouse.region.id}</td>
				<td>${pumpHouse.address}</td>
				<td>${pumpHouse.longi}</td>
				<td>${pumpHouse.lati}</td>
				<td>${pumpHouse.accessCtrlIpAddr}</td>
				<td>${pumpHouse.accessCtrlPort}</td>
				<td>${pumpHouse.allPicUrl}</td>
				<td>${pumpHouse.memo}</td>
				<shiro:hasPermission name="biz:pumpHouse:edit"><td>
    				<a href="${ctx}/biz/pumpHouse/form?id=${pumpHouse.id}">修改</a>
					<a href="${ctx}/biz/pumpHouse/delete?id=${pumpHouse.id}" onclick="return confirmx('确认要删除该泵房吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>