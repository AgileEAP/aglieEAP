<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>文件目录</title>
</head>
<body>
<form id="inputForm" action="${ctx}/infrastructure/catalog/${action}" method="post" class="form-horizontal">
    <input type="hidden" id='id' name="id" value="${catalog.id}"/>
        <fieldset>
            <div class="control-group">
        		<label for="catalogName" class="control-label">目录名称:</label>
        		<div class="controls">
        		    <input type="text" id="catalogName" name="catalogName"  value="${catalog.catalogName}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="catalogType" class="control-label">目录类型:</label>
        		<div class="controls">
                    <input type="text" id="catalogType" name="catalogType"  value="${catalog.catalogType}" placeholder="请输入目录类型" class="required"/>
                </div>
        	</div>
            <div class="control-group">
        		<label for="parentID" class="control-label">上级目录:</label>
        		<div class="controls">
        		    <input type="text" id="parentID" name="parentID"  value="${catalog.parentID}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="path" class="control-label">目录路径:</label>
        		<div class="controls">
                    <textarea type="text" id="path" name="path" placeholder="请输入目录路径" class="required" >${catalog.path}</textarea>
                </div>
        	</div>
            <div class="control-group">
        		<label for="sortOrder" class="control-label">序号:</label>
        		<div class="controls">
                    <input type="text" id="sortOrder" name="sortOrder"  value="${catalog.sortOrder}" placeholder="请输入序号" class="required"/>
                </div>
        	</div>
            <div class="control-group">
        		<label for="description" class="control-label">描述:</label>
        		<div class="controls">
                    <textarea type="text" id="description" name="description" placeholder="请输入描述" class="required" >${catalog.description}</textarea>
                </div>
        	</div>
            <c:if test="${action=='view'}">
        	<div class="control-group">
        		<label for="creator" class="control-label">创建者:</label>
        		<div class="controls">
        			<input type="text" id="creator" name="creator"  value="${catalog.creator}" class="readonly" readonly="readonly"/>
                </div>
        	</div>
            </c:if>
            <c:if test="${action=='view'}">
        	<div class="control-group">
        		<label for="createTime" class="control-label">创建时间:</label>
        		<div class="controls">
                    <input type="text" id="createTime" name="createTime" value='${catalog.createTime}' class="readonly" readonly="readonly"/>
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
<script type="text/javascript">
$(document).ready(function() {
    	//聚焦第一个输入框
    	$("#name").focus();
    	//为inputForm注册validate函数
    	var inputForm=$("#inputForm");
    	inputForm.validate({
    		rules : {
        				catalogName : {
        					required: true,
        					
        					maxlength: 32    
        				},
        				parentID : {
        					required: true,
        					
        					maxlength: 18    
        				},
        				creator : {
        					required: true,
        					
        					maxlength: 18    
        				},
    		},
    		messages : {
    				catalogName : {
                    required: "<font color='red' size='2' >目录名称不能为空！</font>",
        				
    					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
    				}, 
    				parentID : {
                    required: "<font color='red' size='2' >上级目录不能为空！</font>",
        				
    					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
    				}, 
    				creator : {
                    required: "<font color='red' size='2' >创建者不能为空！</font>",
        				
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