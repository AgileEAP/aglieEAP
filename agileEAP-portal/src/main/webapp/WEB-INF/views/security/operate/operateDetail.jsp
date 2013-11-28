<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>操作</title>
</head>
<body>
<form id="inputForm" action="${ctx}/security/operate/${action}" method="post" class="form-horizontal">
    <input type="hidden" id='id' name="id" value="${operate.id}"/>
        <fieldset>
            <div class="control-group">
        		<label for="name" class="control-label">操作名称:</label>
        		<div class="controls">
        		    <input type="text" id="name" name="name"  value="${operate.name}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="command" class="control-label">命令:</label>
        		<div class="controls">
        		    <input type="text" id="command" name="command"  value="${operate.command}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="argument" class="control-label">命令参数:</label>
        		<div class="controls">
        		    <input type="text" id="argument" name="argument"  value="${operate.argument}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="isVerify" class="control-label">是否验证:</label>
        		<div class="controls">
                <select id="isVerify" name="isVerify" >
                	<option value="0" <c:if test="${operate.isVerify==0}"> selected="selected" </c:if>>否</option>
                	<option value="1" <c:if test="${operate.isVerify==1}"> selected="selected" </c:if>>是</option>
                </select>
                </div>
        	</div>
        	<div class="control-group">
        		<label for="sortOrder" class="control-label">序号:</label>
        		<div class="controls">
        		    <input type="text" id="sortOrder" name="sortOrder"  value="${operate.sortOrder}" />
                </div>
        	</div>
    	<c:if test="${action!='view'}">
    	<div class="form-actions">
    		<input id="btnSubimt" class="btn btn-primary" type="button" value="提交"/>&nbsp;	
    		<input id="btnCancel" class="btn" type="button" value="取消"/>
    	</div>
    	</c:if>
 </fieldset>
</form>
<script type="text/javascript">
$(document).ready(function() {
    	//聚焦第一个输入框
    	$("#name").focus();
    	$("#isVerify").kendoComboBox();
  	    $("#sortOrder").kendoNumericTextBox({
  	        format: "#"
  	    }); 
  	    
    	//为inputForm注册validate函数
    	var inputForm=$("#inputForm");
    	inputForm.validate({
    		rules : {
        				name : {
        					required: true,        					
        					maxlength: 16    
        				},
        				command : {
        					required: true,        					
        					maxlength: 16    
        				}
    		},
    		messages : {
    				name : {
                    required: "<font color='red' size='2' >操作名称不能为空！</font>",        				
    					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
    				}, 
    				command : {
                    required: "<font color='red' size='2' >命令不能为空！</font>",        				
    					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
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
    				window.parent.frames["ifrContent"].$("#btnSearch").click();                    
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