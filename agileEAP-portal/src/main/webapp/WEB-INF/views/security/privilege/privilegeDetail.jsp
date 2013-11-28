<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>权限信息</title>
</head>
<body>
<form id="inputForm" action="${ctx}/security/privilege/${action}" method="post" class="form-horizontal">
<input type="hidden" id='id' name="id" value="${privilege.id}"/>
<fieldset>
    <div class="control-group">
		<label for="name" class="control-label">权限名称:</label>
		<div class="controls">
			<input type="text" id="name" name="name"  value="${privilege.name}" class=""  maxlength="32"/>
        </div>
	</div>
    <div class="control-group">
		<label for="type" class="control-label">权限类型:</label>
		<div class="controls">
        <input type="text" id="type" name="type"  value="${privilege.type}" class="" />
        </div>
	</div>
    <div class="control-group">
		<label for="resourceID" class="control-label">资源ID:</label>
		<div class="controls">
			<input type="text" id="resourceID" name="resourceID"  value="${privilege.resourceID}" class=""  maxlength="36"/>
        </div>
	</div>
    <div class="control-group">
		<label for="operateID" class="control-label">操作ID:</label>
		<div class="controls">
			<input type="text" id="operateID" name="operateID"  value="${privilege.operateID}" class=""  maxlength="36"/>
        </div>
	</div>
    <div class="control-group">
		<label for="metaDataID" class="control-label">元数据ID:</label>
		<div class="controls">
			<input type="text" id="metaDataID" name="metaDataID"  value="${privilege.metaDataID}" class=""  maxlength="36"/>
        </div>
	</div>
    <div class="control-group">
		<label for="moduleID" class="control-label">模块ID:</label>
		<div class="controls">
			<input type="text" id="moduleID" name="moduleID"  value="${privilege.moduleID}" class=""  maxlength="36"/>
        </div>
	</div>
    <div class="control-group">
		<label for="appID" class="control-label">应用ID:</label>
		<div class="controls">
			<input type="text" id="appID" name="appID"  value="${privilege.appID}" class=""  maxlength="36"/>
        </div>
	</div>
    <div class="control-group">
		<label for="sortOrder" class="control-label">序号:</label>
		<div class="controls">
        <input type="text" id="sortOrder" name="sortOrder"  value="${privilege.sortOrder}" class="" />
        </div>
	</div>
    <div class="control-group">
		<label for="description" class="control-label">描述:</label>
		<div class="controls">
             <textarea id="description" name="description" class=" required" minlength="1"  maxlength="128">${privilege.description}</textarea>
        </div>
	</div>
    <div class="control-group">
		<label for="ownerOrg" class="control-label">归属组织:</label>
		<div class="controls">
             <textarea id="ownerOrg" name="ownerOrg" class=""  maxlength="512">${privilege.ownerOrg}</textarea>
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
    				resourceID : {
    					required: true,
    					
    					maxlength: 36    
    				},
    				operateID : {
    					required: true,
    					
    					maxlength: 36    
    				},
    				metaDataID : {
    					required: true,
    					
    					maxlength: 36    
    				},
    				moduleID : {
    					required: true,
    					
    					maxlength: 36    
    				},
    				appID : {
    					required: true,
    					
    					maxlength: 36    
    				},
    				sortOrder : {
required: true,    
    					digits:true
    				},
    				ownerOrg : {
    					required: true,
    					
    					maxlength: 512    
    				},
    				creator : {
    					required: true,
    					
    					maxlength: 36    
    				},
		},
		messages : {
				name : {
required: "<font color=red size=2 >权限名称不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
				resourceID : {
required: "<font color=red size=2 >资源ID不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
				operateID : {
required: "<font color=red size=2 >操作ID不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
				metaDataID : {
required: "<font color=red size=2 >元数据ID不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
				moduleID : {
required: "<font color=red size=2 >模块ID不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
				appID : {
required: "<font color=red size=2 >应用ID不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
				sortOrder : {
required: "<font color=red size=2 >序号不能为空！</font>",    
					digits:"只能输入整数"
				}, 
				ownerOrg : {
required: "<font color=red size=2 >归属组织不能为空！</font>",
    				
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