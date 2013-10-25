<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
<title>扩展属性管理</title>
</head>
<body>
    <jsp:include page="../../share/command.jsp"></jsp:include>
    <div id="extendAttrGrid"></div>    
    <div id="filterDialog" >
        <form id="frmSearch" class="form-horizontal">
            <input id="searchFlags" type="hidden" />
            <fieldset>
            	<div class="control-group">
            		<label for="entity" class="control-label">扩展实体:</label>
            		<div class="controls">
            			<input type="text" id="entity" name="entity" value=""/>
                    </div>
            	</div>
            	<div class="control-group">
            		<label for="entityID" class="control-label">实例ID:</label>
            		<div class="controls">
            			<input type="text" id="entityID" name="entityID" value=""/>
                    </div>
            	</div>
            	<div class="control-group">
            		<label for="name" class="control-label">属性名:</label>
            		<div class="controls">
            			<input type="text" id="name" name="name" value=""/>
                    </div>
            	</div>
            	<div class="control-group">
            		<label for="value" class="control-label">属性值:</label>
            		<div class="controls">
            			<input type="text" id="value" name="value" value=""/>
                    </div>
            	</div>
            	<div class="form-actions">
            		<input id="btnSearch" class="btn btn-primary" type="button" value="查询"/>&nbsp;	
            		<input id="btnCancel" class="btn" type="button" value="清除" onclick="clearFilterValues()"/>
            	</div>
            </fieldset>
        </form>
    </div>
    <script type="text/javascript">
    $(document).ready(function () {
        var dataSource = new kendo.data.DataSource({
            transport : {
                read : {
                    type : "post",
                    url : "${ctx}/workflow/extendattr/ajaxList",
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
        var extendAttrGrid=$("#extendAttrGrid");
        var kGrid=extendAttrGrid.kendoGrid({
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
                field : "entity",
                title : "扩展实体"
            }
                    ,{
                field : "entityID",
                title : "实例ID"
            }
                    ,{
                field : "name",
                title : "属性名"
            }
                    ,{
                field : "value",
                title : "属性值"
            }
         ]
        });

        $("#btnSearch").click(function (e) {
        	$("#searchFlags").val("1");
        	kGrid.data("kendoGrid").dataSource.read();
        	$("#searchFlags").val("");
        	$("#filterDialog").data("kendoWindow").close();  
        });

        extendAttrGrid.delegate("tbody>tr", "dblclick", function(){
        	commandExecute("view","查看");
        });
    });
    </script>
</body>
</html>