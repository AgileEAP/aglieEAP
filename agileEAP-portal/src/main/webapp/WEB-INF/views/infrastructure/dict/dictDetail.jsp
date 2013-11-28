<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>数据字典</title>
</head>
<body>
<form id="inputForm" action="${ctx}/infrastructure/dict/${action}" method="post" class="form-horizontal">
    <input type="hidden" id='id' name="id" value="${dict.id}"/>
        <fieldset>
            <div class="control-group">
        		<label for="name" class="control-label">字典名:</label>
        		<div class="controls">
        		    <input type="text" id="name" name="name"  value="${dict.name}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="text" class="control-label">字典显示名:</label>
        		<div class="controls">
        		    <input type="text" id="text" name="text"  value="${dict.text}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="parentID" class="control-label">父字典:</label>
        		<div class="controls">
        		    <input type="text" id="parentID" name="parentID"  value="${dict.parentID}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="sortOrder" class="control-label">序号:</label>
        		<div class="controls">
                    <input type="text" id="sortOrder" name="sortOrder"  value="${dict.sortOrder}" placeholder="请输入序号" class="required"/>
                </div>
        	</div>
            <div class="control-group">
        		<label for="description" class="control-label">描述:</label>
        		<div class="controls">
        		    <input type="text" id="description" name="description"  value="${dict.description}" placeholder="请输入描述" class="required" />
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
        				name : {
        					required: true,
        					
        					maxlength: 32    
        				},
        				text : {
        					required: true,
        					
        					maxlength: 32    
        				},
        				parentID : {
        					required: true,
        					
        					maxlength: 18    
        				},
    		},
    		messages : {
    				name : {
                    required: "<font color='red' size='2' >字典名不能为空！</font>",
        				
    					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
    				}, 
    				text : {
                    required: "<font color='red' size='2' >字典显示名不能为空！</font>",
        				
    					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
    				}, 
    				parentID : {
                    required: "<font color='red' size='2' >父字典不能为空！</font>",
        				
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