<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备配置标牌管理</title>
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
		<li class="active"><a href="${ctx}/biz/deviceSetLabel/">设备配置标牌列表</a></li>
		<shiro:hasPermission name="biz:deviceSetLabel:edit"><li><a href="${ctx}/biz/deviceSetLabel/form">设备配置标牌添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="deviceSetLabel" action="${ctx}/biz/deviceSetLabel/" method="post" class="breadcrumb form-search">
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
				<th>水泵型号</th>
				<th>设备额定供水流量</th>
				<th>设备额定功率</th>
				<th>稳流罐规格</th>
				<th>设备外形尺寸</th>
				<th>流量控制器</th>
				<th>气压罐规格</th>
				<th>水箱规格</th>
				<th>设备重量</th>
				<th>环境温度</th>
				<th>水泵台数</th>
				<th>控制柜型号</th>
				<th>配套电源电压</th>
				<th>设备额定供水扬程</th>
				<th>设备总功率</th>
				<th>增压装置规格</th>
				<th>双向补偿器规格</th>
				<th>整机设备序号</th>
				<th>智能引水装置</th>
				<th>副泵型号</th>
				<th>腔体规格</th>
				<th>综合水力控制单元</th>
				<th>稳压补偿罐规格</th>
				<th>出厂日期</th>
				<shiro:hasPermission name="biz:deviceSetLabel:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="deviceSetLabel">
			<tr>
				<td>${deviceSetLabel.device.name}</td>
				<td>${deviceSetLabel.equipmentModelNo}</td>
				<td>${deviceSetLabel.pupmpModelNo}</td>
				<td>${deviceSetLabel.sbedgsll}</td>
				<td>${deviceSetLabel.powerRating}</td>
				<td>${deviceSetLabel.wlgGg}</td>
				<td>${deviceSetLabel.shapgeSize}</td>
				<td>${deviceSetLabel.llkzq}</td>
				<td>${deviceSetLabel.qygGg}</td>
				<td>${deviceSetLabel.sxGg}</td>
				<td>${deviceSetLabel.weight}</td>
				<td>${deviceSetLabel.environmentalTemperature}</td>
				<td>${deviceSetLabel.pumpQuantity}</td>
				<td>${deviceSetLabel.cabinetModelNo}</td>
				<td>${deviceSetLabel.powerVoltage}</td>
				<td>${deviceSetLabel.sbedgsyc}</td>
				<td>${deviceSetLabel.totalPower}</td>
				<td>${deviceSetLabel.zyzzGg}</td>
				<td>${deviceSetLabel.scbcq}</td>
				<td>${deviceSetLabel.exFactoryDate}</td>
				<td>${deviceSetLabel.fullDeviceXh}</td>
				<td>${deviceSetLabel.znYinshui}</td>
				<td>${deviceSetLabel.fbXinghao}</td>
				<td>${deviceSetLabel.qtGuige}</td>
				<td>${deviceSetLabel.slKongzhi}</td>
				<td>${deviceSetLabel.wyBuchang}</td>
				<shiro:hasPermission name="biz:deviceSetLabel:edit"><td>
    				<a href="${ctx}/biz/deviceSetLabel/form?id=${deviceSetLabel.id}">修改</a>
					<a href="${ctx}/biz/deviceSetLabel/delete?id=${deviceSetLabel.id}" onclick="return confirmx('确认要删除该设备配置标牌吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>