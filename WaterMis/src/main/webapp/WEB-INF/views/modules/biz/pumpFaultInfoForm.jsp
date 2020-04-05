<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>水泵故障信息管理</title>
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
		<li><a href="${ctx}/biz/pumpFaultInfo/">水泵故障信息列表</a></li>
		<li class="active"><a href="${ctx}/biz/pumpFaultInfo/form?id=${pumpFaultInfo.id}">水泵故障信息<shiro:hasPermission name="biz:pumpFaultInfo:edit">${not empty pumpFaultInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="biz:pumpFaultInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="pumpFaultInfo" action="${ctx}/biz/pumpFaultInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">报警编码：</label>
			<div class="controls">
				<form:input path="alarm.id" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备编码：</label>
			<div class="controls">
				<form:input path="device.id" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">故障级别：</label>
			<div class="controls">
				<form:select path="faultLevel" class="input-medium">
					<form:options items="${fns:getDictList('fault_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">故障信息：</label>
			<div class="controls">
				<form:input path="faultMessage" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">故障状态：</label>
			<div class="controls">
				<form:input path="faultState" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="biz:pumpFaultInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>