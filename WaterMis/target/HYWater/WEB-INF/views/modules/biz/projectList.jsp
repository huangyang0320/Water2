<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理</title>
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
		<li class="active"><a href="${ctx}/biz/project/">项目列表</a></li>
		<shiro:hasPermission name="biz:project:edit"><li><a href="${ctx}/biz/project/form">项目添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="project" action="${ctx}/biz/project/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称：</label>
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
				<th>所属区域</th>
				<th>项目地址</th>
				<th>项目经理</th>
				<th>业务员</th>
				<th>合同类型</th>
				<th>合同签订单位</th>
				<th>购买方名称</th>
				<th>购买方联系人</th>
				<th>签订日期</th>
				<th>到货日期</th>
				<th>预计调试日期</th>
				<th>备注</th>
				<shiro:hasPermission name="biz:project:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="project">
			<tr>
				<td><a href="${ctx}/biz/project/form?id=${project.id}">
					${project.name}
					</a>
				</td>
				<td>${project.region.id}</td>
				<td>${project.address}</td>
				<td>${project.projectManager}</td>
				<td>${project.salesman}</td>
				<td>${project.contractType}</td>
				<td>${project.htqdDw}</td>
				<td>${project.buyerName}</td>
				<td>${project.buyerContactsName}</td>
				<td>${project.signDate}</td>
				<td>${project.arrivalDate}</td>
				<td>${project.yjtsDate}</td>
				<td>${project.memo}</td>
				<shiro:hasPermission name="biz:project:edit"><td>
    				<a href="${ctx}/biz/project/form?id=${project.id}">修改</a>
					<a href="${ctx}/biz/project/delete?id=${project.id}" onclick="return confirmx('确认要删除该项目吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>