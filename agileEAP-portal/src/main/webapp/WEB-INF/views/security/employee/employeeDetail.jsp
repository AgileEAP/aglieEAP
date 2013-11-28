<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>人员信息表信息</title>
</head>
<body>
<form id="inputForm" action="${ctx}/security/employee/${action}" method="post" class="form-horizontal">
<input type="hidden" id='id' name="id" value="${employee.id}"/>
<fieldset>
    <div class="control-group">
		<label for="code" class="control-label">员工编号:</label>
		<div class="controls">
			<input type="text" id="code" name="code"  value="${employee.code}" class=" required" minlength="1"  maxlength="16"/>
        </div>
	</div>
    <div class="control-group">
		<label for="loginName" class="control-label">登陆名:</label>
		<div class="controls">
			<input type="text" id="loginName" name="loginName"  value="${employee.loginName}" class=" required" minlength="1"  maxlength="32"/>
        </div>
	</div>
    <div class="control-group">
		<label for="name" class="control-label">人员姓名:</label>
		<div class="controls">
			<input type="text" id="name" name="name"  value="${employee.name}" class=" required" minlength="1"  maxlength="32"/>
        </div>
	</div>
    <div class="control-group">
		<label for="operatorID" class="control-label">操作员ID:</label>
		<div class="controls">
			<input type="text" id="operatorID" name="operatorID"  value="${employee.operatorID}" class=""  maxlength="36"/>
        </div>
	</div>
    <div class="control-group">
		<label for="gender" class="control-label">性别:</label>
		<div class="controls">
        <input type="text" id="gender" name="gender"  value="${employee.gender}" class=" required" minlength="1" />
        </div>
	</div>
    <div class="control-group">
		<label for="birthday" class="control-label">出生日期:</label>
		<div class="controls">
        <input type="text" id="birthday" name="birthday" onClick="WdatePicker()" value="${employee.birthday}" class=" required" minlength="1" />
        </div>
	</div>
    <div class="control-group">
		<label for="nation" class="control-label">民族:</label>
		<div class="controls">
			<input type="text" id="nation" name="nation"  value="${employee.nation}" class=" required" minlength="1"  maxlength="36"/>
        </div>
	</div>
    <div class="control-group">
		<label for="birthplace" class="control-label">出生地:</label>
		<div class="controls">
			<input type="text" id="birthplace" name="birthplace"  value="${employee.birthplace}" class=" required" minlength="1"  maxlength="64"/>
        </div>
	</div>
    <div class="control-group">
		<label for="nativeplace" class="control-label">籍贯:</label>
		<div class="controls">
			<input type="text" id="nativeplace" name="nativeplace"  value="${employee.nativeplace}" class=" required" minlength="1"  maxlength="64"/>
        </div>
	</div>
    <div class="control-group">
		<label for="politicsStatus" class="control-label">政治面貌:</label>
		<div class="controls">
			<input type="text" id="politicsStatus" name="politicsStatus"  value="${employee.politicsStatus}" class=" required" minlength="1"  maxlength="32"/>
        </div>
	</div>
    <div class="control-group">
		<label for="workFromDate" class="control-label">参加工作时间:</label>
		<div class="controls">
        <input type="text" id="workFromDate" name="workFromDate" onClick="WdatePicker()" value="${employee.workFromDate}" class=" required" minlength="1" />
        </div>
	</div>
    <div class="control-group">
		<label for="healthStatus" class="control-label">健康状况:</label>
		<div class="controls">
			<input type="text" id="healthStatus" name="healthStatus"  value="${employee.healthStatus}" class=" required" minlength="1"  maxlength="32"/>
        </div>
	</div>
    <div class="control-group">
		<label for="industrialGrade" class="control-label">专业技术职务:</label>
		<div class="controls">
			<input type="text" id="industrialGrade" name="industrialGrade"  value="${employee.industrialGrade}" class=" required" minlength="1"  maxlength="32"/>
        </div>
	</div>
    <div class="control-group">
		<label for="speciality" class="control-label">特长:</label>
		<div class="controls">
             <textarea id="speciality" name="speciality" class=" required" minlength="1"  maxlength="256">${employee.speciality}</textarea>
        </div>
	</div>
    <div class="control-group">
		<label for="positionName" class="control-label">岗位名称:</label>
		<div class="controls">
			<input type="text" id="positionName" name="positionName"  value="${employee.positionName}" class=" required" minlength="1"  maxlength="64"/>
        </div>
	</div>
    <div class="control-group">
		<label for="position" class="control-label">基本岗位:</label>
		<div class="controls">
			<input type="text" id="position" name="position"  value="${employee.position}" class=" required" minlength="1"  maxlength="32"/>
        </div>
	</div>
    <div class="control-group">
		<label for="postGrade" class="control-label">岗位等级:</label>
		<div class="controls">
			<input type="text" id="postGrade" name="postGrade"  value="${employee.postGrade}" class=" required" minlength="1"  maxlength="32"/>
        </div>
	</div>
    <div class="control-group">
		<label for="status" class="control-label">状态:</label>
		<div class="controls">
        <input type="text" id="status" name="status"  value="${employee.status}" class=" required" minlength="1" />
        </div>
	</div>
    <div class="control-group">
		<label for="cardType" class="control-label">证件类型:</label>
		<div class="controls">
        <input type="text" id="cardType" name="cardType"  value="${employee.cardType}" class=" required" minlength="1" />
        </div>
	</div>
    <div class="control-group">
		<label for="cardNo" class="control-label">证件号码:</label>
		<div class="controls">
			<input type="text" id="cardNo" name="cardNo"  value="${employee.cardNo}" class=" required" minlength="1"  maxlength="32"/>
        </div>
	</div>
    <div class="control-group">
		<label for="inDate" class="control-label">入职日期:</label>
		<div class="controls">
        <input type="text" id="inDate" name="inDate" onClick="WdatePicker()" value="${employee.inDate}" class=" required" minlength="1" />
        </div>
	</div>
    <div class="control-group">
		<label for="outDate" class="control-label">离职日期:</label>
		<div class="controls">
        <input type="text" id="outDate" name="outDate" onClick="WdatePicker()" value="${employee.outDate}" class=" required" minlength="1" />
        </div>
	</div>
    <div class="control-group">
		<label for="zipCode" class="control-label">邮编:</label>
		<div class="controls">
			<input type="text" id="zipCode" name="zipCode"  value="${employee.zipCode}" class=" required" minlength="1"  maxlength="12"/>
        </div>
	</div>
    <div class="control-group">
		<label for="email" class="control-label">Email:</label>
		<div class="controls">
             <textarea id="email" name="email" class=" required" minlength="1"  maxlength="128">${employee.email}</textarea>
        </div>
	</div>
    <div class="control-group">
		<label for="fax" class="control-label">传真号码:</label>
		<div class="controls">
			<input type="text" id="fax" name="fax"  value="${employee.fax}" class=" required" minlength="1"  maxlength="12"/>
        </div>
	</div>
    <div class="control-group">
		<label for="mobile" class="control-label">手机号码:</label>
		<div class="controls">
			<input type="text" id="mobile" name="mobile"  value="${employee.mobile}" class=" required" minlength="1"  maxlength="11"/>
        </div>
	</div>
    <div class="control-group">
		<label for="msn" class="control-label">MSN号码:</label>
		<div class="controls">
			<input type="text" id="msn" name="msn"  value="${employee.msn}" class=" required" minlength="1"  maxlength="12"/>
        </div>
	</div>
    <div class="control-group">
		<label for="officePhone" class="control-label">办公电话:</label>
		<div class="controls">
			<input type="text" id="officePhone" name="officePhone"  value="${employee.officePhone}" class=" required" minlength="1"  maxlength="11"/>
        </div>
	</div>
    <div class="control-group">
		<label for="address" class="control-label">住址:</label>
		<div class="controls">
             <textarea id="address" name="address" class=" required" minlength="1"  maxlength="128">${employee.address}</textarea>
        </div>
	</div>
    <div class="control-group">
		<label for="director" class="control-label">直接主管:</label>
		<div class="controls">
			<input type="text" id="director" name="director"  value="${employee.director}" class=" required" minlength="1"  maxlength="36"/>
        </div>
	</div>
    <div class="control-group">
		<label for="majorOrgID" class="control-label">主机构ID:</label>
		<div class="controls">
			<input type="text" id="majorOrgID" name="majorOrgID"  value="${employee.majorOrgID}" class=" required" minlength="1"  maxlength="36"/>
        </div>
	</div>
    <div class="control-group">
		<label for="photo" class="control-label">照片:</label>
		<div class="controls">
             <textarea id="photo" name="photo" class=" required" minlength="1"  maxlength="128">${employee.photo}</textarea>
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
    <div class="control-group">
		<label for="corpID" class="control-label">公司ID:</label>
		<div class="controls">
			<input type="text" id="corpID" name="corpID"  value="${employee.corpID}" class=""  maxlength="36"/>
        </div>
	</div>
    <div class="control-group">
		<label for="sortOrder" class="control-label">序号:</label>
		<div class="controls">
        <input type="text" id="sortOrder" name="sortOrder"  value="${employee.sortOrder}" class=" required" minlength="1" />
        </div>
	</div>
    <div class="control-group">
		<label for="ownerOrg" class="control-label">归属组织:</label>
		<div class="controls">
             <textarea id="ownerOrg" name="ownerOrg" class=""  maxlength="512">${employee.ownerOrg}</textarea>
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
<script>
$(document).ready(function() {
	//聚焦第一个输入框
	$("#name").focus();
	//为inputForm注册validate函数
	var inputForm=$("#inputForm");
	inputForm.validate({
		rules : {
    				operatorID : {
    					required: true,
    					
    					maxlength: 36    
    				},
    				createTime : {
required: true,    
    					dateISO:true
    				},
    				corpID : {
    					required: true,
    					
    					maxlength: 36    
    				},
    				ownerOrg : {
    					required: true,
    					
    					maxlength: 512    
    				},
		},
		messages : {
				operatorID : {
required: "<font color=red size=2 >操作员ID不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
				createTime : {
required: "<font color=red size=2 >创建时间不能为空！</font>",    
					dateISO:"请输入合法的日期"
				}, 
				corpID : {
required: "<font color=red size=2 >公司ID不能为空！</font>",
    				
					maxlength:jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串")   
				}, 
				ownerOrg : {
required: "<font color=red size=2 >归属组织不能为空！</font>",
    				
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