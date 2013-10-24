<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
<title>流程定义管理</title>
</head>
<body>
    <jsp:include page="../../share/command.jsp"></jsp:include>
    <div id="processDefGrid"></div>    
    <div id="filterDialog" >
        <form id="frmSearch" class="form-horizontal">
            <input id="searchFlags" type="hidden" />
            <fieldset>
            	<div class="control-group">
            		<label for="name" class="control-label">名称:</label>
            		<div class="controls">
            			<input type="text" id="name" name="name" value=""/>
                    </div>
            	</div>
            	<div class="control-group">
            		<label for="text" class="control-label">显示名:</label>
            		<div class="controls">
            			<input type="text" id="text" name="text" value=""/>
                    </div>
            	</div>
            	<div class="control-group">
            		<label for="content" class="control-label">流程内容:</label>
            		<div class="controls">
            			<input type="text" id="content" name="content" value=""/>
                    </div>
            	</div>
            	<div class="control-group">
            		<label for="categoryID" class="control-label">所属分类:</label>
            		<div class="controls">
            			<input type="text" id="categoryID" name="categoryID" value=""/>
                    </div>
            	</div>
            	<div class="control-group">
            		<label for="startor" class="control-label">流程启动者:</label>
            		<div class="controls">
            			<input type="text" id="startor" name="startor" value=""/>
                    </div>
            	</div>
            	<div class="control-group">
            		<label for="version" class="control-label">版本:</label>
            		<div class="controls">
            			<input type="text" id="version" name="version" value=""/>
                    </div>
            	</div>
            	<div class="control-group">
            		<label for="creator" class="control-label">创建者:</label>
            		<div class="controls">
            			<input type="text" id="creator" name="creator" value=""/>
                    </div>
            	</div>
            	<div class="control-group">
            		<label for="modifier" class="control-label">修改人:</label>
            		<div class="controls">
            			<input type="text" id="modifier" name="modifier" value=""/>
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
                    url : "${ctx}/workflow/processdef/ajaxList",
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
        var processDefGrid=$("#processDefGrid");
        var kGrid=processDefGrid.kendoGrid({
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
                title : "名称"
            }
                    ,{
                field : "text",
                title : "显示名"
            }
                    ,{
                field : "content",
                title : "流程内容"
            }
                    ,{
                field : "categoryID",
                title : "所属分类"
            }
                                    ,{
                field : "startor",
                title : "流程启动者"
            }
                            ,{
                field : "version",
                title : "版本"
            }
                    ,{
                field : "description",
                title : "描述"
            }
                    ,{
                field : "createTime",
                title : "创建时间",
                template: '#= kendo.toString(new Date(createTime),"yyyy-MM-dd HH:mm:ss")#'
            }
                    ,{
                field : "creator",
                title : "创建者"
            }
                    ,{
                field : "modifyTime",
                title : "修改时间",
                template: '#= kendo.toString(new Date(modifyTime),"yyyy-MM-dd HH:mm:ss")#'
            }
                    ,{
                field : "modifier",
                title : "修改人"
            }
         ]
        });

        $("#btnSearch").click(function (e) {
        	$("#searchFlags").val("1");
        	kGrid.data("kendoGrid").dataSource.read();
        	$("#searchFlags").val("");
        	$("#filterDialog").data("kendoWindow").close();  
        });

        processDefGrid.delegate("tbody>tr", "dblclick", function(){
        	commandExecute("view","查看");
        });
    });
    </script>
</body>
</html>