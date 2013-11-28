<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>岗位定义表管理</title>
</head>
<body>
<jsp:include page="../../share/command.jsp"></jsp:include>
<div id="positionGrid"></div>
<script>
$(document).ready(function () {
var dataSource = new kendo.data.DataSource({
    transport : {
        read : {
            type : "post",
            url : "${ctx}/security/position/ajaxList",
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
var positionGrid=$("#positionGrid");
var kGrid=positionGrid.kendoGrid({
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
        title : "岗位代码",
    }
    ,{
        field : "name",
        title : "岗位名称",
    }
    ,{
        field : "dutyID",
        title : "职务ID",
    }
    ,{
        field : "appID",
        title : "应用ID",
    }
    ,{
        field : "creator",
        title : "创建者",
    }
 ]
});

$("#btnSearch").click(function (e) {
	$("#searchFlags").val("1");
	kGrid.data("kendoGrid").dataSource.read();
	$("#searchFlags").val("");
	$("#filterDialog").data("kendoWindow").close();  
});

positionGrid.delegate("tbody>tr", "dblclick", function(){
	commandExecute("view","查看");
});
});
</script>

<div id="filterDialog" >
<form id="frmSearch" class="form-horizontal">
<input id="searchFlags" type="hidden" />
<fieldset>
	<div class="control-group">
		<label for="code" class="control-label">岗位代码:</label>
		<div class="controls">
			<input type="text" id="code" name="code"  value="" class=" required" minlength="1"  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="name" class="control-label">岗位名称:</label>
		<div class="controls">
			<input type="text" id="name" name="name"  value="" class=""  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="dutyID" class="control-label">职务ID:</label>
		<div class="controls">
			<input type="text" id="dutyID" name="dutyID"  value="" class=""  maxlength="36"/>
         </div>
	</div>
	<div class="control-group">
		<label for="appID" class="control-label">应用ID:</label>
		<div class="controls">
			<input type="text" id="appID" name="appID"  value="" class=""  maxlength="36"/>
         </div>
	</div>
	<div class="control-group">
		<label for="creator" class="control-label">创建者:</label>
		<div class="controls">
			<input type="text" id="creator" name="creator"  value="" class=""  maxlength="36"/>
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