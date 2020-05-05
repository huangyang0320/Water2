<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>电气设备标牌管理</title>
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
		<li class="active"><a href="${ctx}/biz/deviceElectricalLabel/">电气设备标牌列表</a></li>
		<shiro:hasPermission name="biz:deviceElectricalLabel:edit"><li><a href="${ctx}/biz/deviceElectricalLabel/form">电气设备标牌添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="deviceElectricalLabel" action="${ctx}/biz/deviceElectricalLabel/" method="post" class="breadcrumb form-search">
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
				<th>设备型号</th>
				<th>额定电压</th>
				<th>额定功率</th>
				<th>执行标准</th>
				<th>控制台数</th>
				<th>额定电流</th>
				<th>电气设备标牌</th>
				<shiro:hasPermission name="biz:deviceElectricalLabel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="deviceElectricalLabel">
			<tr>
				<td>${deviceElectricalLabel.device.name}</td>
				<td>${deviceElectricalLabel.modelNo}</td>
				<td>${deviceElectricalLabel.ratedVoltage}</td>
				<td>${deviceElectricalLabel.ratedVoltage}</td>
				<td>${deviceElectricalLabel.ratedPower}</td>
				<td>${deviceElectricalLabel.standards}</td>
				<td>${deviceElectricalLabel.controlQuantity}</td>
				<td>${deviceElectricalLabel.ratedCurrent}</td>
				<td>${deviceElectricalLabel.ratedFrequency}</td>
				<shiro:hasPermission name="biz:deviceElectricalLabel:edit"><td>
    				<a href="${ctx}/biz/deviceElectricalLabel/form?id=${deviceElectricalLabel.id}">修改</a>
					<a href="${ctx}/biz/deviceElectricalLabel/delete?id=${deviceElectricalLabel.id}" onclick="return confirmx('确认要删除该电气设备标牌吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>