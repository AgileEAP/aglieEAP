<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>系统登录用户表</title>
</head>
<body>
	<form id="inputForm" action="${ctx}/security/operator/${action}" method="post"
		class="form-horizontal">
		<input type="hidden" id='id' name="id" value="${operator.id}" />
		<fieldset>
			<div class="control-group">
				<label for="loginName" class="control-label">登录名:</label>
				<div class="controls">
					<input type="text" id="loginName" name="loginName" value="${operator.loginName}" />
				</div>
			</div>
			<div class="control-group">
				<label for="name" class="control-label">姓名:</label>
				<div class="controls">
					<input type="text" id="name" name="name" value="${operator.name}" />
				</div>
			</div>
<%-- 			<div class="control-group">
				<label for="userType" class="control-label">用户类型:</label>
				<div class="controls">
					<select id="userType" name="userType">
						<option value="0" <c:if test="${operator.userType==0}"> selected="selected"</c:if>>管理员</option>
						<option value="1" <c:if test="${operator.userType==1}"> selected="selected"</c:if>>用户</option>
					</select>
				</div>
			</div> --%>
			<div class="control-group">
				<label for="password" class="control-label">密码:</label>
				<div class="controls">
					<input type="password" id="password" name="password" value="${operator.password}" />
				</div>
			</div>
			<div class="control-group">
				<label for="orgID" class="control-label">所属部门:</label>
				<div class="controls">
					<input type="hidden" id="orgID" name="orgID" value="${operator.orgID}" class="required" />
					<input	type="text" id="orgName" name="orgName" value="${operator.orgName}" class="required choosebox" onclick="openChooseBoxDialog('orgID','orgName','选择部门', '${ctx}/security/organization/tree?action=choose', '450','400')"/>
				</div>
			</div>
			<div class="control-group">
				<label for="corpName" class="control-label">所属公司:</label>
				<div class="controls">
					<input type="hidden" id="corpID" name="corpID" value="${operator.corpID}" />
					<input type="text" id="corpName" name="corpName" value="${operator.corpName}"
						class="required choosebox" />
				</div>
			</div>
			<div class="control-group">
				<label for="theme" class="control-label">主题:</label>
				<div class="controls">
					<select id="theme" name="theme">
						<option value="default" <c:if test="${operator.theme=='default'}"> selected="selected"</c:if>>默认</option>
						<option value="green" <c:if test="${operator.theme=='green'}"> selected="selected"</c:if>>绿色</option>
						<option value="blue" <c:if test="${operator.theme=='blue'}"> selected="selected"</c:if>>蓝色</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label for="email" class="control-label">邮箱:</label>
				<div class="controls">
					<input type="text" id="email" name="email" value="${operator.email}" />
				</div>
			</div>
			<div class="control-group">
				<label for="phone" class="control-label">联系电话:</label>
				<div class="controls">
					<input type="text" id="phone" name="phone" value="${operator.phone}" />
				</div>
			</div>
			<c:if test="${action=='view'}">
				<div class="control-group">
					<label for="creator" class="control-label">创建者:</label>
					<div class="controls">
						<input type="text" id="creator" name="creator" value="${operator.creator}" class="readonly"
							readonly="readonly" />
					</div>
				</div>
				<div class="control-group">
					<label for="createTime" class="control-label">创建时间:</label>
					<div class="controls">
						<input type="text" id="createTime" name="createTime" class="readonly" readonly="readonly"
							value='<fmt:formatDate value="${operator.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />' />
					</div>
				</div>
			</c:if>
			<c:if test="${action!='view'}">
				<div class="form-actions">
					<input id="btnSubimt" class="btn btn-primary" type="button" value="提交" />&nbsp; <input
						id="btnCancel" class="btn" type="button" value="取消" />
				</div>
			</c:if>
		</fieldset>
	</form>
	<script type="text/javascript">
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#name").focus();
			//为inputForm注册validate函数
			var inputForm = $("#inputForm");
			inputForm.validate({
				rules : {
					loginName : {
						required : true,
						maxlength : 16
					},
					name : {
						required : true,
						maxlength : 16
					},
					password : {
						required : true,
						maxlength : 128
					},
					theme : {
						required : true,
						maxlength : 8
					}
				},
				messages : {
					loginName : {
						required : "<font color='red' size='2' >登录名不能为空！</font>",
						maxlength : jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")
					},
					name : {
						required : "<font color='red' size='2' >姓名不能为空！</font>",
						maxlength : jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")
					},
					password : {
						required : "<font color='red' size='2' >密码不能为空！</font>",
						maxlength : jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")
					},
					theme : {
						required : "<font color='red' size='2' >主题不能为空！</font>",
						maxlength : jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")
					}
				},
				errorPlacement : function(error, element) {
					error.appendTo(element.parent());
				},
				submitHandler : function(form) {
					form.submit();
				},
				errorClass : "error",
				focusCleanup : true, //被验证的元素获得焦点时移除错误信息  
				success : function(label) {
					label.html("<span style=\"color:green\"><img alt='合法' src=${ctx}/themes/default/images/correct.png></img></span>").addClass("success");
				}
			});
			$("#btnSubimt").click(function() {
				if (!inputForm.valid())
					return false;
				var processbar = $("#processbar");
				processbar.processbar({
					message : '正在提交...'
				});
				$.post(inputForm.attr("action"), inputForm.serialize(), function(result) {
					processbar.complete();
					alertMessage(result);
					if (window.parent && result.indexOf("成功") > 0) {
						window.parent.$("#actionDialog").data("kendoWindow").close();
						window.parent.frames["ifrContent"].$("#btnSearch").click();
					}
				});
			});
			$("#btnCancel").click(function() {
				if (window.parent) {
					window.parent.$("#actionDialog").data("kendoWindow").close();
				}
			});
		});
	</script>
</body>
</html>