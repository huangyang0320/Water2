<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>水泵设备标牌管理</title>
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
		<li class="active"><a href="${ctx}/biz/devicePumpLabel/">水泵设备标牌列表</a></li>
		<shiro:hasPermission name="biz:devicePumpLabel:edit"><li><a href="${ctx}/biz/devicePumpLabel/form">水泵设备标牌添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="devicePumpLabel" action="${ctx}/biz/devicePumpLabel/" method="post" class="breadcrumb form-search">
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
				<th>设备名称</th>
				<th>水泵型号</th>
				<th>额定供水流量</th>
				<th>额定功率</th>
				<th>额定供水扬程</th>
				<th>转速</th>
				<shiro:hasPermission name="biz:devicePumpLabel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="devicePumpLabel">
			<tr>
				<td>${devicePumpLabel.device.name}</td>
				<td>${devicePumpLabel.modelNo}</td>
				<td>${devicePumpLabel.sbedgsll}</td>
				<td>${devicePumpLabel.ratedPower}</td>
				<td>${devicePumpLabel.sbedgsyc}</td>
				<td>${devicePumpLabel.speed}</td>
				<shiro:hasPermission name="biz:devicePumpLabel:edit"><td>
    				<a href="${ctx}/biz/devicePumpLabel/form?id=${devicePumpLabel.id}">修改</a>
					<a href="${ctx}/biz/devicePumpLabel/delete?id=${devicePumpLabel.id}" onclick="return confirmx('确认要删除该水泵设备标牌吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>