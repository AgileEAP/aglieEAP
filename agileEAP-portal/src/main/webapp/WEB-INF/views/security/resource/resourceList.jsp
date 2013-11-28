<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>资源管理</title>
</head>
<body>
<jsp:include page="../../share/command.jsp"></jsp:include>
<div id="resourceGrid"></div>
<script>
$(document).ready(function () {
var dataSource = new kendo.data.DataSource({
    transport : {
        read : {
            type : "post",
            url : "${ctx}/security/resource/ajaxList",
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
var resourceGrid=$("#resourceGrid");
var kGrid=resourceGrid.kendoGrid({
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
        field : "name",
        title : "资源名称",
    }
    ,{
        field : "text",
        title : "资源显示名",
    }
    ,{
        field : "parentID",
        title : "父资源",
    }
    ,{
        field : "entry",
        title : "调用入口",
    }
    ,{
        field : "argument",
        title : "输入参数",
    }
    ,{
        field : "url",
        title : "链接地址",
    }
    ,{
        field : "icon",
        title : "闭合图标",
    }
    ,{
        field : "expandIcon",
        title : "展开图标",
    }
    ,{
        field : "appID",
        title : "所属应用",
    }
    ,{
        field : "moduleID",
        title : "所属模块",
    }
    ,{
        field : "description",
        title : "描述",
    }
    ,{
        field : "ownerOrg",
        title : "归属组织",
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

resourceGrid.delegate("tbody>tr", "dblclick", function(){
	commandExecute("view","查看");
});
});
</script>

<div id="filterDialog" >
<form id="frmSearch" class="form-horizontal">
<input id="searchFlags" type="hidden" />
<fieldset>
	<div class="control-group">
		<label for="name" class="control-label">资源名称:</label>
		<div class="controls">
			<input type="text" id="name" name="name"  value="" class=""  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="text" class="control-label">资源显示名:</label>
		<div class="controls">
			<input type="text" id="text" name="text"  value="" class=""  maxlength="32"/>
         </div>
	</div>
	<div class="control-group">
		<label for="parentID" class="control-label">父资源:</label>
		<div class="controls">
			<input type="text" id="parentID" name="parentID"  value="" class=" required" minlength="1"  maxlength="36"/>
         </div>
	</div>
	<div class="control-group">
		<label for="entry" class="control-label">调用入口:</label>
		<div class="controls">
			<input type="text" id="entry" name="entry"  value="" class=" required" minlength="1"  maxlength="64"/>
         </div>
	</div>
	<div class="control-group">
		<label for="argument" class="control-label">输入参数:</label>
		<div class="controls">
			<input type="text" id="argument" name="argument"  value="" class=" required" minlength="1"  maxlength="128"/>
         </div>
	</div>
	<div class="control-group">
		<label for="url" class="control-label">链接地址:</label>
		<div class="controls">
			<input type="text" id="url" name="url"  value="" class=" required" minlength="1"  maxlength="128"/>
         </div>
	</div>
	<div class="control-group">
		<label for="icon" class="control-label">闭合图标:</label>
		<div class="controls">
			<input type="text" id="icon" name="icon"  value="" class=" required" minlength="1"  maxlength="128"/>
         </div>
	</div>
	<div class="control-group">
		<label for="expandIcon" class="control-label">展开图标:</label>
		<div class="controls">
			<input type="text" id="expandIcon" name="expandIcon"  value="" class=" required" minlength="1"  maxlength="128"/>
         </div>
	</div>
	<div class="control-group">
		<label for="appID" class="control-label">所属应用:</label>
		<div class="controls">
			<input type="text" id="appID" name="appID"  value="" class=""  maxlength="36"/>
         </div>
	</div>
	<div class="control-group">
		<label for="moduleID" class="control-label">所属模块:</label>
		<div class="controls">
			<input type="text" id="moduleID" name="moduleID"  value="" class=""  maxlength="36"/>
         </div>
	</div>
	<div class="control-group">
		<label for="description" class="control-label">描述:</label>
		<div class="controls">
			<input type="text" id="description" name="description"  value="" class=" required" minlength="1"  maxlength="256"/>
         </div>
	</div>
	<div class="control-group">
		<label for="ownerOrg" class="control-label">归属组织:</label>
		<div class="controls">
			<input type="text" id="ownerOrg" name="ownerOrg"  value="" class=""  maxlength="512"/>
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