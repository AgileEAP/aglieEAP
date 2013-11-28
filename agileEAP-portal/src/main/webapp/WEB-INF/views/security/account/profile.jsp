<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>资料修改</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/profile/update" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${id}" />
		<fieldset>
		<div class="control-group">
				<label for="name" class="control-label">真实名:</label>
				<div class="controls">
					<input type="text" id="name" name="name" value="${name}" readonly="readonly"/>
				</div>
			</div>
			<div class="control-group">
				<label for="loginName" class="control-label">用户名:</label>
				<div class="controls">
					<input type="text" id="loginName" name="loginName" value="${loginName}"
						class="required" />
				</div>
			</div>
			<div class="control-group">
				<label for="password" class="control-label">密码:</label>
				<div class="controls">
					<input type="password" id="password" name="password"
						class="required" placeholder="新密码" />
				</div>
			</div>
			<div class="control-group">
				<label for="confirmPassword" class="control-label">确认密码:</label>
				<div class="controls">
					<input type="password" id="confirmPassword" name="confirmPassword"
						class="required" placeholder="确认新密码" />
				</div>
			</div>
			<div class="form-actions">
					<input id="btnSubimt" class="btn btn-primary" type="button" value="提交" />&nbsp; <input
						id="btnCancel" class="btn" type="button" value="取消" />
				</div>
		</fieldset>
	</form>
<script type="text/javascript">
	$(document).ready(function() {
		//登录密码和新登录密码不能相同
		jQuery.validator.addMethod("notEqualTo", function (value, element, param) {
		    return value != $(param).val();
		}, $.validator.format("两次输入不能相同!"));
		
    	//聚焦第一个输入框
    	$("#name").focus();
    	//为inputForm注册validate函数
    	var inputForm=$("#inputForm");
    	inputForm.validate({
    		rules : {
    			password : {
					required: true
				},
				confirmPassword : {
					required: true,
					equalTo: "#password"
				}
    		},
    		messages : {
    			password : {
					required: "<font color=red size=2 >密码不能为空！</font>"
				},
				confirmPassword : {
					required: "<font color=red size=2 >确认密码不能为空！</font>",
					equalTo: "<font color=red size=2 >两次输入密码不相同！</font>"
				}
			},
    		
    		errorPlacement: function (error, element) {  
                error.appendTo(element.parent());  
            },  
            submitHandler: function (form) {  
                form.submit();  
            },  
            errorClass: "error",  
            focusCleanup: true, //被验证的元素获得焦点时移除错误信息  
            success: function (label) {  
                label.html("<span style=\"color:green\"><img alt='合法' src=${ctx}/themes/default/images/correct.png></img></span>").addClass("success");  
            }  
    	});
    	
    	$("#btnSubimt").click(function(){
        	if(!inputForm.valid()) return false;
            
            var processbar = $("#processbar");
            processbar.processbar({ message: '正在提交...' });
             $.post(inputForm.attr("action"), inputForm.serialize(), function (result) {
                processbar.complete();
                alertMessage(result);
    			if (window.parent&&result.indexOf("成功")>0) {
    				window.parent.$("#actionDialog").data("kendoWindow").close();              
    			}
            }); 
    	}); 
    	
    	$("#btnCancel").click(function(){
    		if (window.parent) {
    			window.parent.$("#actionDialog").data("kendoWindow").close();
    		}
    	});
    });
</script>
</body>
</html>
