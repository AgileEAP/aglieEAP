<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>岗位定义表信息</title>
</head>
<body>
<form id="inputForm" action="${ctx}/security/position/${action}" method="post" class="form-horizontal">
<input type="hidden" id='id' name="id" value="${position.id}"/>
<fieldset>
    <div class="control-group">
		<label for="code" class="control-label">岗位代码:</label>
		<div class="controls">
			<input type="text" id="code" name="code"  value="${position.code}" class=" required" minlength="1"  maxlength="32"/>
        </div>
	</div>
    <div class="control-group">
		<label for="name" class="control-label">岗位名称:</label>
		<div class="controls">
			<input type="text" id="name" name="name"  value="${position.name}" class=""  maxlength="32"/>
        </div>
	</div>
    <div class="control-group">
		<label for="dutyID" class="control-label">职务ID:</label>
		<div class="controls">
			<input type="text" id="dutyID" name="dutyID"  value="${position.dutyID}" class=""  maxlength="36"/>
        </div>
	</div>
    <div class="control-group">
		<label for="appID" class="control-label">应用ID:</label>
		<div class="controls">
			<input type="text" id="appID" name="appID"  value="${position.appID}" class=""  maxlength="36"/>
        </div>
	</div>
    <c:if test="${action=='view'}">
	<div class="control-group">
		<label for="creator" class="control-label">创建者:</label>
		<div class="controls">
			<input type="text" id="creator" name="creator"  value="${role.creator}" class=""  maxlength="36"/>
        </div>
	</div>
    </c:if>
    <c:if test="${action=='view'}">
	<div class="control-group">
		<label for="createTime" class="control-label">创建时间:</label>
		<div class="controls">
        <input type="text" id="createTime" name="createTime" value='${role.createTime}' class="readonly" minlength="1" />
        </div>
	</div>
    </c:if>
	<c:if test="${action!='view'}">
	<div class="form-actions">
		<input id="btnSubimt" class="btn btn-primary" type="button" value="提交"/>&nbsp;	
		<input id="btnCancel" class="btn" type="button" value="取消"/>
	</div>
	</c:if>
</fieldset>
</form>
<script>
$(document).ready(function() {
	//聚焦第一个输入框
	$("#name").focus();
	//为inputForm注册validate函数
	var inputForm=$("#inputForm");
	inputForm.validate({
		rules : {
    				name : {
    					required: true,
    					
    					maxlength: 32    
    				},
    				dutyID : {
    					required: true,
    					
    					maxlength: 36    
    				},
    				appID : {
    					required: true,
    					
    					maxlength: 36    
    				},
    				creator : {
    					required: true,
    					
    					maxlength: 36    
    				},
		},
		messages : {
				name : {
required: "<font color=red size=2 >岗位名称不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
				dutyID : {
required: "<font color=red size=2 >职务ID不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
				appID : {
required: "<font color=red size=2 >应用ID不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
				creator : {
required: "<font color=red size=2 >创建者不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
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
				window.parent.$("#btnSearch").click();                    
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