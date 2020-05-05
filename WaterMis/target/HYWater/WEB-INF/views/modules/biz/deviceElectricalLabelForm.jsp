<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>电气设备标牌管理</title>
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
		<li><a href="${ctx}/biz/deviceElectricalLabel/">电气设备标牌列表</a></li>
		<li class="active"><a href="${ctx}/biz/deviceElectricalLabel/form?id=${deviceElectricalLabel.id}">电气设备标牌<shiro:hasPermission name="biz:deviceElectricalLabel:edit">${not empty deviceElectricalLabel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="biz:deviceElectricalLabel:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="deviceElectricalLabel" action="${ctx}/biz/deviceElectricalLabel/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">设备名称：</label>
			<div class="controls">
				<form:input path="device.name" htmlEscape="false" maxlength="50" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备型号：</label>
			<div class="controls">
				<form:input path="modelNo" htmlEscape="false" maxlength="200" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">额定电压：</label>
			<div class="controls">
				<form:input path="ratedVoltage" htmlEscape="false" maxlength="200" class="input-xlarge required "/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">额定功率：</label>
			<div class="controls">
				<form:input path="ratedPower" htmlEscape="false" maxlength="200" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执行标准：</label>
			<div class="controls">
				<form:input path="standards" htmlEscape="false" maxlength="20" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">控制台数：</label>
			<div class="controls">
				<form:input path="controlQuantity" htmlEscape="false" maxlength="200" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">额定电流：</label>
			<div class="controls">
				<form:input path="ratedCurrent" htmlEscape="false" maxlength="200" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电气设备标牌：</label>
			<div class="controls">
				<form:input path="ratedFrequency" htmlEscape="false" maxlength="200" class="input-xlarge required "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="biz:deviceElectricalLabel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>