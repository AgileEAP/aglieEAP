<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>人员信息表管理</title>
</head>
<body>
<jsp:include page="../../share/command.jsp"></jsp:include>
<div id="employeeGrid"></div>
<script>
$(document).ready(function () {
var dataSource = new kendo.data.DataSource({
    transport : {
        read : {
            type : "post",
            url : "${ctx}/security/employee/ajaxList",
            dataType : "json",
            contentType : "application/json"
        },
        parameterMap : function(options, operation) {
            if (operation == "read") {               
                var $form = $("#frmSearch");
                var formData = $form.serialize().split('&');
                
                if($("#searchFlags").val()=="1")options.page=1;
                
                var requestPageData = {
                        page : options.page,    //当前页
                        pageSize : options.pageSize,//每页显示个数,
                        data:{}
                    };
                $(formData).each(function () {
                    var nvp = this.split('=');
                    var nvpvalue=$form.find('*[name=' + nvp[0] + ']').first().val();
                    if(nvpvalue!="")
                    {
                    	requestPageData.data[nvp[0]] =nvpvalue; 
                    }
                });	                
                
                return kendo.stringify(requestPageData);
            }
        }
    },
    batch : true,
    pageSize : 10, //每页显示个数
    schema : {
        data : function(d) {
            return d.data;  //响应到页面的数据
        },
        total : function(d) {
            return d.total;   //总条数
        }
    },
    serverPaging : true,
    serverFiltering : true,
    serverSorting : true

});
var employeeGrid=$("#employeeGrid");
var kGrid=employeeGrid.kendoGrid({
    dataSource : dataSource,
    selectable: "multiple",
    height : $(document).height()-55,
    pageable : {
        messages : {
            display : "{0} - {1} 共 {2} 条数据",
            empty : "没有要显示的数据",
            page : "Page",
            of : "of {0}", // {0} is total amount of pages
            itemsPerPage : "项 每页",
            first : "首页",
            previous : "前一页",
            next : "下一页",
            last : "最后一页",
            refresh : "刷新"
        }
    },
    columns : [ 
    {
        field : "id",
        title : "主键",
        hidden : true
    }
    ,{
        field : "code",
        title : "员工编号",
    }
    ,{
        field : "loginName",
        title : "登陆名",
    }
    ,{
        field : "name",
        title : "人员姓名",
    }
    ,{
        field : "operatorID",
        title : "操作员ID",
    }
    ,{
        field : "nation",
        title : "民族",
    }
    ,{
        field : "birthplace",
        title : "出生地",
    }
    ,{
        field : "nativeplace",
        title : "籍贯",
    }
    ,{
        field : "politicsStatus",
        title : "政治面貌",
    }
    ,{
        field : "healthStatus",
        title : "健康状况",
    }
    ,{
        field : "industrialGrade",
        title : "专业技术职务",
    }
    ,{
        field : "speciality",
        title : "特长",
    }
    ,{
        field : "positionName",
        title : "岗位名称",
    }
    ,{
        field : "position",
        title : "基本岗位",
    }
    ,{
        field : "postGrade",
        title : "岗位等级",
    }
    ,{
        field : "cardNo",
        title : "证件号码",
    }
    ,{
        field : "zipCode",
        title : "邮编",
    }
    ,{
        field : "email",
        title : "Email",
    }
    ,{
        field : "fax",
        title : "传真号码",
    }
    ,{
        field : "mobile",
        title : "手机号码",
    }
    ,{
        field : "msn",
        title : "MSN号码",
    }
    ,{
        field : "officePhone",
        title : "办公电话",
    }
    ,{
        field : "address",
        title : "住址",
    }
    ,{
        field : "director",
        title : "直接主管",
    }
    ,{
        field : "majorOrgID",
        title : "主机构ID",
    }
    ,{
        field : "photo",
        title : "照片",
    }
    ,{
        field : "creator",
        title : "创建者",
    }
    ,{
        field : "corpID",
        title : "公司ID",
    }
    ,{
        field : "ownerOrg",
        title : "归属组织",
    }
 ]
});

$("#btnSearch").click(function (e) {
	$("#searchFlags").val("1");
	kGrid.data("kendoGrid").dataSource.read();
	$("#searchFlags").val("");
	$("#filterDialog").data("kendoWindow").close();  
});

employeeGrid.delegate("tbody>tr", "dblclick", function(){
	commandExecute("view","查看");
});
});
</script>

<div id="filterDialog" >
<form id="frmSearch" class="form-horizontal">
<input id="searchFlags" type="hidden" />
<fieldset>
	<div class="control-group">
		<label for="code" class="control-label">员工编号:</label>
		<div class="controls">
			<input type="text" id="code" name="code"  value="" class=" required" minlength="1"  maxlength="16"/>
         </div>
	</div>
	<div class="control-group">
		<label for="loginName" class="control-label">登陆名:</label>
		<div class="controls">
			<input type="text" id="loginName" name="loginName"  value="" class=" required" minlength="1"  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="name" class="control-label">人员姓名:</label>
		<div class="controls">
			<input type="text" id="name" name="name"  value="" class=" required" minlength="1"  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="operatorID" class="control-label">操作员ID:</label>
		<div class="controls">
			<input type="text" id="operatorID" name="operatorID"  value="" class=""  maxlength="36"/>
         </div>
	</div>
	<div class="control-group">
		<label for="nation" class="control-label">民族:</label>
		<div class="controls">
			<input type="text" id="nation" name="nation"  value="" class=" required" minlength="1"  maxlength="36"/>
         </div>
	</div>
	<div class="control-group">
		<label for="birthplace" class="control-label">出生地:</label>
		<div class="controls">
			<input type="text" id="birthplace" name="birthplace"  value="" class=" required" minlength="1"  maxlength="64"/>
         </div>
	</div>
	<div class="control-group">
		<label for="nativeplace" class="control-label">籍贯:</label>
		<div class="controls">
			<input type="text" id="nativeplace" name="nativeplace"  value="" class=" required" minlength="1"  maxlength="64"/>
         </div>
	</div>
	<div class="control-group">
		<label for="politicsStatus" class="control-label">政治面貌:</label>
		<div class="controls">
			<input type="text" id="politicsStatus" name="politicsStatus"  value="" class=" required" minlength="1"  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="healthStatus" class="control-label">健康状况:</label>
		<div class="controls">
			<input type="text" id="healthStatus" name="healthStatus"  value="" class=" required" minlength="1"  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="industrialGrade" class="control-label">专业技术职务:</label>
		<div class="controls">
			<input type="text" id="industrialGrade" name="industrialGrade"  value="" class=" required" minlength="1"  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="speciality" class="control-label">特长:</label>
		<div class="controls">
			<input type="text" id="speciality" name="speciality"  value="" class=" required" minlength="1"  maxlength="256"/>
         </div>
	</div>
	<div class="control-group">
		<label for="positionName" class="control-label">岗位名称:</label>
		<div class="controls">
			<input type="text" id="positionName" name="positionName"  value="" class=" required" minlength="1"  maxlength="64"/>
         </div>
	</div>
	<div class="control-group">
		<label for="position" class="control-label">基本岗位:</label>
		<div class="controls">
			<input type="text" id="position" name="position"  value="" class=" required" minlength="1"  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="postGrade" class="control-label">岗位等级:</label>
		<div class="controls">
			<input type="text" id="postGrade" name="postGrade"  value="" class=" required" minlength="1"  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="cardNo" class="control-label">证件号码:</label>
		<div class="controls">
			<input type="text" id="cardNo" name="cardNo"  value="" class=" required" minlength="1"  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="zipCode" class="control-label">邮编:</label>
		<div class="controls">
			<input type="text" id="zipCode" name="zipCode"  value="" class=" required" minlength="1"  maxlength="12"/>
         </div>
	</div>
	<div class="control-group">
		<label for="email" class="control-label">Email:</label>
		<div class="controls">
			<input type="text" id="email" name="email"  value="" class=" required" minlength="1"  maxlength="128"/>
         </div>
	</div>
	<div class="control-group">
		<label for="fax" class="control-label">传真号码:</label>
		<div class="controls">
			<input type="text" id="fax" name="fax"  value="" class=" required" minlength="1"  maxlength="12"/>
         </div>
	</div>
	<div class="control-group">
		<label for="mobile" class="control-label">手机号码:</label>
		<div class="controls">
			<input type="text" id="mobile" name="mobile"  value="" class=" required" minlength="1"  maxlength="11"/>
         </div>
	</div>
	<div class="control-group">
		<label for="msn" class="control-label">MSN号码:</label>
		<div class="controls">
			<input type="text" id="msn" name="msn"  value="" class=" required" minlength="1"  maxlength="12"/>
         </div>
	</div>
	<div class="control-group">
		<label for="officePhone" class="control-label">办公电话:</label>
		<div class="controls">
			<input type="text" id="officePhone" name="officePhone"  value="" class=" required" minlength="1"  maxlength="11"/>
         </div>
	</div>
	<div class="control-group">
		<label for="address" class="control-label">住址:</label>
		<div class="controls">
			<input type="text" id="address" name="address"  value="" class=" required" minlength="1"  maxlength="128"/>
         </div>
	</div>
	<div class="control-group">
		<label for="director" class="control-label">直接主管:</label>
		<div class="controls">
			<input type="text" id="director" name="director"  value="" class=" required" minlength="1"  maxlength="36"/>
         </div>
	</div>
	<div class="control-group">
		<label for="majorOrgID" class="control-label">主机构ID:</label>
		<div class="controls">
			<input type="text" id="majorOrgID" name="majorOrgID"  value="" class=" required" minlength="1"  maxlength="36"/>
         </div>
	</div>
	<div class="control-group">
		<label for="photo" class="control-label">照片:</label>
		<div class="controls">
			<input type="text" id="photo" name="photo"  value="" class=" required" minlength="1"  maxlength="128"/>
         </div>
	</div>
	<div class="control-group">
		<label for="creator" class="control-label">创建者:</label>
		<div class="controls">
			<input type="text" id="creator" name="creator"  value="" class=" required" minlength="1"  maxlength="36"/>
         </div>
	</div>
	<div class="control-group">
		<label for="corpID" class="control-label">公司ID:</label>
		<div class="controls">
			<input type="text" id="corpID" name="corpID"  value="" class=""  maxlength="36"/>
         </div>
	</div>
	<div class="control-group">
		<label for="ownerOrg" class="control-label">归属组织:</label>
		<div class="controls">
			<input type="text" id="ownerOrg" name="ownerOrg"  value="" class=""  maxlength="512"/>
         </div>
	</div>
	<div class="form-actions">
		<input id="btnSearch" class="btn btn-primary" type="button" value="查询"/>&nbsp;	
		<input id="btnCancel" class="btn" type="button" value="清除" onclick="clearFilterValues()"/>
	</div>
</fieldset>
</form>
</div>
</body>
</html>