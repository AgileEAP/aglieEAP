<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>组织机构 </title>
</head>
<body>
<%-- <jsp:include page="../../share/command.jsp"></jsp:include> --%>
<form id="inputForm" action="${ctx}/security/organization/${action}" method="post" class="form-horizontal">
    <input type="hidden" id='id' name="id" value="${organization.id}"/>
        <fieldset>
            <div class="control-group">
        		<label for="code" class="control-label">机构编码:</label>
        		<div class="controls">
        		    <input type="text" id="code" name="code"  value="${organization.code}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="name" class="control-label">机构名称:</label>
        		<div class="controls">
        		    <input type="text" id="name" name="name"  value="${organization.name}" placeholder="请输入机构名称" class="required" />
                </div>
        	</div>
        	<div class="control-group">
        		<label for="parentID" class="control-label">上级组织:</label>
        		<div class="controls">
        		    <input type="hidden" id="parentID" name="parentID"  value="${organization.parentID}" placeholder="请输入上级组织" class="required" />
        		    <input type="text" id="parentName" name="parentName"  value="${organization.parentName}" placeholder="请输入上级组织" class="required choosebox" onclick="openChooseBoxDialog('parentID','parentName','选择上级部门', '${ctx}/security/organization/tree?action=choose', '450','400')"/>
                </div>
        	</div>
        	<div class="control-group">
        		<label for="sortOrder" class="control-label">序号:</label>
        		<div class="controls">
                    <input type="text" id="sortOrder" name="sortOrder"  value="${organization.sortOrder}" placeholder="请输入序号" class="required"/>
                </div>
        	</div>
        	<div class="control-group">
        		<label for="grade" class="control-label">机构等级:</label>
        		<div class="controls">
                 <select id="grade" name="grade" >
                	<option value="1" <c:if test="${organization.grade==1}"> selected="selected" </c:if>>总公司</option>
                	<option value="2" <c:if test="${organization.grade==2}"> selected="selected" </c:if>>总部部门</option>
                	<option value="3" <c:if test="${organization.grade==3}"> selected="selected" </c:if>>分公司</option>
                	<option value="4" <c:if test="${organization.grade==4}"> selected="selected" </c:if>>分公司部门</option>
                </select>
                </div>
        	</div>
     <%--        <div class="control-group">
        		<label for="address" class="control-label">机构地址:</label>
        		<div class="controls">
        		    <input type="text" id="address" name="address"  value="${organization.address}" placeholder="请输入机构地址" class="required" />
                </div>
        	</div>        	
            <div class="control-group">
        		<label for="zipCode" class="control-label">邮编:</label>
        		<div class="controls">
        		    <input type="text" id="zipCode" name="zipCode"  value="${organization.zipCode}" placeholder="请输入邮编" class="required" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="contactMan" class="control-label">联系人:</label>
        		<div class="controls">
        		    <input type="text" id="contactMan" name="contactMan"  value="${organization.contactMan}" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="contactPhone" class="control-label">联系电话:</label>
        		<div class="controls">
        		    <input type="text" id="contactPhone" name="contactPhone"  value="${organization.contactPhone}" placeholder="请输入联系电话" class="required" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="email" class="control-label">电子邮件:</label>
        		<div class="controls">
        		    <input type="text" id="email" name="email"  value="${organization.email}" placeholder="请输入电子邮件" class="required" />
                </div>
        	</div>
            <div class="control-group">
        		<label for="city" class="control-label">所属城市:</label>
        		<div class="controls">
        		    <input type="text" id="city" name="city"  value="${organization.city}" />
                </div>
        	</div> --%>
            <c:if test="${action=='view'}">
        	<div class="control-group">
        		<label for="creator" class="control-label">创建者:</label>
        		<div class="controls">
        			<input type="text" id="creator" name="creator"  value="${organization.creator}" class="readonly" readonly="readonly"/>
                </div>
        	</div>
        	<div class="control-group">
        		<label for="createTime" class="control-label">创建时间:</label>
        		<div class="controls">
                    <input type="text" id="createTime" name="createTime" value='${organization.createTime}' class="readonly" readonly="readonly"/>
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
  		$("#grade").kendoComboBox();
  	    $("#sortOrder").kendoNumericTextBox({
  	        format: "#"
  	    }); 
    	//聚焦第一个输入框
    	$("#name").focus(); 
    	var inputForm=$("#inputForm");
    	inputForm.validate({
    		rules : {
        				code : {
        					required: true,        					
        					maxlength: 16    
        				},
        				name : {
        					required: true,        					
        					maxlength: 18    
        				}
    		},
    		messages : {
    				code : {
                    required: "<font color='red' size='2' >机构编码不能为空！</font>",        				
    					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
    				},
    				name : {
                    required: "<font color='red' size='2' >机构名称不能为空！</font>",        				
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
            $.post(inputForm.attr("action"), inputForm.serialize(), function (retValue) {
                processbar.complete();
                alertMessage(retValue.message);
    			if (window.parent&&retValue.message.indexOf("成功")>0) {
    				window.parent.frames["ifrContent"].refreshTreeNode(retValue.id,$("#name").val());
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