<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
<title>操作管理</title>
</head>
<body>
    <jsp:include page="../../share/command.jsp"></jsp:include>
 <!--    <input id="btnSubimt" class="btn btn-primary" onclick="choose()" type="button" value="选择"/>&nbsp; -->
    <div id="operateGrid"></div>    
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
            		<label for="command" class="control-label">命令:</label>
            		<div class="controls">
            			<input type="text" id="command" name="command" value=""/>
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
    	setDetailWindowHeight(370);
    	setFilterWindowHeight(210);
        var dataSource = new kendo.data.DataSource({
            transport : {
                read : {
                    type : "post",
                    url : "${ctx}/security/operate/ajaxList",
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
        
        var columns=[];
        if($.query.get("entry")=="choose")
       	{
            columns=[ 
                     {
                         field : "id",
                         template:"<input id='#=id#' type='checkbox' />",
                         title : "<input type='checkbox' onclick='toggleCheckbox(this);' />选择",
                         width: 60
                     }
                     ,{
                         field : "name",
                         title : "名称"
                     }
                             ,{
                         field : "command",
                         title : "命令"
                     }
                             ,{
                         field : "argument",
                         title : "命令参数"
                     }
                     ,{
                         field : "isVerify",
                         title : "是否验证",
                         template:"#=(isVerify==1?'是':'否')#"                  
                     }                                
                     ];	
       	}
        else
        {
            columns=[ 
                     {
                         field : "id",
                         title : "主键",
                         hidden : true
                     }
                     ,{
                         field : "sortOrder",
                         title : "序号",
                         width: 60
                     }
                     ,{
                         field : "name",
                         title : "名称"
                     }
                     ,{
                         field : "command",
                         title : "命令"
                     }
                     ,{
                         field : "argument",
                         title : "命令参数"
                     }
                     ,{
                         field : "isVerify",
                         template:"#=(isVerify==1?'是':'否')#",
                         title : "是否验证"                    
                     }                  
                     ];	
        }
        
        var operateGrid=$("#operateGrid");
        var kGrid=operateGrid.kendoGrid({
            dataSource : dataSource,
            selectable: "multiple",
            change: function(){
           	 this.select().find(":checkbox").click();
           },
           dataBound: function(e){
               if($.query.get("entry")=="choose")
               {
               	var roles="${operateIdString}".split(",");
               	for(var i in roles)
              		{
              			$("#"+roles[i]).prop("checked",true);
              		}
               }
           },
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
            columns : columns
        });

        $("#btnSearch").click(function (e) {
        	$("#searchFlags").val("1");
        	kGrid.data("kendoGrid").dataSource.read();
        	$("#searchFlags").val("");
        	$("#filterDialog").data("kendoWindow").close();  
        });

        operateGrid.delegate("tbody>tr", "dblclick", function(){
        	commandExecute("view","查看");
        });
    });
    
    
    function toggleCheckbox(me)
    {
    	$("#operateGrid").find(":checkbox").prop("checked",me.checked);
    }
    
    
	function choose()
	{ 
		var operates=[];
		$("input:checked").each(function(i)
		{
			 tds=$(this).parent().parent().find("td");
			 operates.push({
 	            id:this.id,
            	sortOrder:i,
            	name: $.trim(tds.eq(1).text()),
                command:$.trim(tds.eq(2).text()),
                argument:$.trim(tds.eq(3).text()),
                isVerify:$.trim(tds.eq(4).text()) == "是" ? 1 : 0      
			 });
		});
		
		if (operates.length==0) {
			alertMessage("请先选择操作按钮");
			return false;
		}
		
       	var ifrContent=window.parent.frames["ifrContent"];
        for(var key in operates){
        	ifrContent.addOperate(operates[key]);
        } 
		window.parent.$("#actionDialog").data("kendoWindow").close();
	}
    </script>
</body>
</html>