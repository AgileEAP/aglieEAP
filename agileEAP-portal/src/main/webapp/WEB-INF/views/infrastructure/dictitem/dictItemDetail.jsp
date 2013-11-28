<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>字典项</title>
</head>
<body>
<form id="inputForm" action="${ctx}/infrastructure/dictitem/${action}" method="post" class="form-horizontal">
    <input type="hidden" id='id' name="id" value="${dictItem.id}"/>
        <fieldset>
            <div class="control-group">
        		<label for="dictID" class="control-label">所属字典:</label>
        		<div class="controls">
        		    <input type="text" id="dictID" name="dictID"  value="${dictItem.dictID}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="value" class="control-label">字典项值:</label>
        		<div class="controls">
        		    <input type="text" id="value" name="value"  value="${dictItem.value}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="text" class="control-label">字典项显示值:</label>
        		<div class="controls">
        		    <input type="text" id="text" name="text"  value="${dictItem.text}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="sortOrder" class="control-label">序号:</label>
        		<div class="controls">
                    <input type="text" id="sortOrder" name="sortOrder"  value="${dictItem.sortOrder}" placeholder="请输入序号" class="required"/>
                </div>
        	</div>
            <div class="control-group">
        		<label for="description" class="control-label">描述:</label>
        		<div class="controls">
        		    <input type="text" id="description" name="description"  value="${dictItem.description}" placeholder="请输入描述" class="required" />
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
    	//为inputForm注册validate函数
    	var inputForm=$("#inputForm");
    	inputForm.validate({
    		rules : {
        				dictID : {
        					required: true,
        					
        					maxlength: 18    
        				},
        				value : {
        					required: true,
        					
        					maxlength: 32    
        				},
        				text : {
        					required: true,
        					
        					maxlength: 32    
        				},
    		},
    		messages : {
    				dictID : {
                    required: "<font color='red' size='2' >所属字典不能为空！</font>",
        				
    					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
    				}, 
    				value : {
                    required: "<font color='red' size='2' >字典项值不能为空！</font>",
        				
    					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
    				}, 
    				text : {
                    required: "<font color='red' size='2' >字典项显示值不能为空！</font>",
        				
    					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
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