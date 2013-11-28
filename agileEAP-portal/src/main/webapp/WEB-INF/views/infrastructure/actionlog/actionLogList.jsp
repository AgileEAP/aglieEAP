<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>登录用户操作日志管理</title>
</head>
<body>
<jsp:include page="../../share/command.jsp"></jsp:include>
<div id="actionLogGrid"></div>
<script>
$(document).ready(function () {
	setDetailWindowHeight(400);
	setFilterWindowHeight(300);
var dataSource = new kendo.data.DataSource({
    transport : {
        read : {
            type : "post",
            url : "${ctx}/infrastructure/actionlog/ajaxList",
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
var actionLogGrid=$("#actionLogGrid");
var kGrid=actionLogGrid.kendoGrid({
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
        field : "message",
        title : "操作信息",
    }    
    ,{
        field : "userName",
        title : "操作员",
    }
    ,{
        field : "createTime",
        title : "操作时间",
        template: '#= kendo.toString(new Date(createTime),"yyyy-MM-dd HH:mm:ss") #'
    }
 ]
});

$("#btnSearch").click(function (e) {
	$("#searchFlags").val("1");
	kGrid.data("kendoGrid").dataSource.read();
	$("#searchFlags").val("");
	$("#filterDialog").data("kendoWindow").close();  
});

actionLogGrid.delegate("tbody>tr", "dblclick", function(){
	commandExecute("view","查看");
});
});
</script>

<div id="filterDialog" >
<form id="frmSearch" class="form-horizontal">
<input id="searchFlags" type="hidden" />
<fieldset>
	<div class="control-group">
		<label for="userName" class="control-label">操作员:</label>
		<div class="controls">
			<input type="text" id="userName" name="userName"  value="" class=" required" minlength="1"  maxlength="16"/>
         </div>
	</div>
	<div class="control-group">
		<label for="clientIP" class="control-label">客户IP:</label>
		<div class="controls">
			<input type="text" id="clientIP" name="clientIP"  value="" class=" required" minlength="1"  maxlength="16"/>
         </div>
	</div>
	<div class="control-group">
		<label for="message" class="control-label">操作信息:</label>
		<div class="controls">
			<input type="text" id="message" name="message"  value="" class=""  maxlength="128"/>
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