<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>设备配置标牌管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/biz/deviceSetLabel/">设备配置标牌列表</a></li>
		<li class="active"><a href="${ctx}/biz/deviceSetLabel/form?id=${deviceSetLabel.id}">设备配置标牌<shiro:hasPermission name="biz:deviceSetLabel:edit">${not empty deviceSetLabel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="biz:deviceSetLabel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="deviceSetLabel" action="${ctx}/biz/deviceSetLabel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">设备名称：</label>
			<div class="controls">
				<form:input path="device.id" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备型号：</label>
			<div class="controls">
				<form:input path="equipmentModelNo" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">水泵型号：</label>
			<div class="controls">
				<form:input path="pupmpModelNo" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备额定供水流量：</label>
			<div class="controls">
				<form:input path="sbedgsll" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备额定功率：</label>
			<div class="controls">
				<form:input path="powerRating" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">稳流罐规格：</label>
			<div class="controls">
				<form:input path="wlgGg" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备外形尺寸：</label>
			<div class="controls">
				<form:input path="shapgeSize" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">流量控制器：</label>
			<div class="controls">
				<form:input path="llkzq" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">气压罐规格：</label>
			<div class="controls">
				<form:input path="qygGg" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">水箱规格：</label>
			<div class="controls">
				<form:input path="sxGg" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备重量：</label>
			<div class="controls">
				<form:input path="weight" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">环境温度：</label>
			<div class="controls">
				<form:input path="environmentalTemperature" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">水泵台数：</label>
			<div class="controls">
				<form:input path="pumpQuantity" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">控制柜型号：</label>
			<div class="controls">
				<form:input path="cabinetModelNo" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配套电源电压：</label>
			<div class="controls">
				<form:input path="powerVoltage" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备额定供水扬程：</label>
			<div class="controls">
				<form:input path="sbedgsyc" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备总功率：</label>
			<div class="controls">
				<form:input path="totalPower" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">增压装置规格：</label>
			<div class="controls">
				<form:input path="zyzzGg" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">双向补偿器规格：</label>
			<div class="controls">
				<form:input path="scbcq" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出厂日期：</label>
			<div class="controls">
				<input name="exFactoryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${deviceSetLabel.exFactoryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">整机设备序号：</label>
			<div class="controls">
				<form:input path="fullDeviceXh" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">智能引水装置：</label>
			<div class="controls">
				<form:input path="znYinshui" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">副泵型号：</label>
			<div class="controls">
				<form:input path="fbXinghao" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">腔体规格：</label>
			<div class="controls">
				<form:input path="qtGuige" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">综合水力控制单元：</label>
			<div class="controls">
				<form:input path="slKongzhi" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">稳压补偿罐规格：</label>
			<div class="controls">
				<form:input path="wyBuchang" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="biz:deviceSetLabel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>