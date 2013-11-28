<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>资源管理</title>
<link href="${ctx }/js/contextMenu/jquery.contextMenu.css" rel="stylesheet"
	type="text/css" />
<script src="${ctx }/js/contextMenu/jquery.contextMenu.js"
	type="text/javascript"></script>

<style type="text/css">
html, body {
    margin: 0px 1px;
    height: 100%;
}
#splitBar {
    float: left;
    z-index: 20;
    cursor: col-resize;
    display: inline;
    overflow: hidden;
    width: 4px;
    background: #E5EDEF;
    height: 100%;
}
</style>
<script type="text/javascript" language="javascript">
   $(window).resize(function () {
       resizeWindow();
   });

   $(function () {
      resizeWindow();
   });

   function resizeWindow() {
       var height=$("body").height()-45;
	   $("#splitBar").height(height);
	   $("#treeview").height(height);
   }   
</script>
<script id="treeview-template" type="text/kendo-ui-template">
     	<span id="#:item.id#">#:item.text#</span>
</script>
<script>
  $(document).ready(function () {
	  	var dataSource = new kendo.data.HierarchicalDataSource({
		transport : {
			 read : {
		            type : "post",
		            url : "${ctx}/security/resource/treeData",
		            dataType : "json",
		            contentType : "application/json"
		        }			        
		},
		schema: {
	        model: {
	            id: 'id',
	            text: 'text',
	            imageUrl:'icon',
	            children: "items"
	        }
	    }
	});
  	
   	$("#treeview").kendoTreeView({
       	template: kendo.template($("#treeview-template").html()), 
    	dataSource : dataSource,
        dataValueField: 'id',
        dataTextField: 'text',
        dataImageUrlField:'icon',
        select: function(e)
        {
        	var nodeid=$("#treeview").data("kendoTreeView").dataItem((e.node)).id;
            $.get("${ctx}/security/resource/"+nodeid, function (resource) {
                var $formValues = $("#inputForm").find(":input");
                $formValues.each(function () {
                	$this=$(this);
                	if(this.id&&this.id!=""){
                		if($this.attr("data-role")=="combobox")
               			{
                			var combobox = $("#"+this.id).data("kendoComboBox");
                			combobox.value(resource[this.id]);
               			}
                		else if($this.attr("data-role")=="numerictextbox")
                		{
                			  var numerictextbox = $("#"+this.id).data("kendoNumericTextBox");
                              numerictextbox.value(resource[this.id]);
                		}
                		else
               			{
                			$this.val(resource[this.id]);
               			}
                	}
                });
                $("#tblOperate").find("tbody").empty();
                for(var i=0;i<resource.operates.length;i++){
                	resource.operates[i].sortOrder=i+1;
                	addOperate(resource.operates[i]);
                }                
            });
        }
    });
       	
	$.contextMenu({
		selector: '.k-in', 
		callback: function(key, options) {
			var node={id: $(this).find("span").attr("id"),text:$.trim($(this).text())};
			executCommand(key,node);
		},
		items: {
			"add": {name: "添加"},
			"delete": {name: "删除"}
		}
	});
});

function clearValues()
{
	var $formValues =$("#inputForm").find("input");
	$formValues.each(function () {
		var $this=$(this);
		if($this.attr("role")!="combobox")
		{
			$(this).val("");
		}
	});
	$("#tblOperate").find("tbody").empty();	
}

function executCommand(key,node){
	if(key == "delete"){
		var processbar = $("#processbar");
        processbar.processbar({ message: '正在提交...' });
        var url = "${ctx}/security/resource/delete?id="+node.id;
        $.post(url, function (result) {
            processbar.complete();
            clearValues();
            var treeview = $("#treeview").data("kendoTreeView");
            var selectNode= treeview.select();
            if(selectNode.length>0)
           	{
           		treeview.remove(selectNode);
           	}
            alertMessage(result);
        });
	}else if(key=="add"){
		clearValues();
 	   	var currentNode=$("#"+node.id).parent().parent().parent();
		var numerictextbox = $("#sortOrder").data("kendoNumericTextBox");
        numerictextbox.value(currentNode.find("ul:first").children("li").length+1);
        $("#parentID").val(node.id);
        $("#parentName").val(node.text);
	}
}
</script>
</head>
<body>
<jsp:include page="../../share/command.jsp"></jsp:include>
<div class="container-fluid" style="height:100%">
	<div style="float:left;width:20%;height:100%">
	<div id="treeview" style=" padding:3px;" ></div>
	</div>
	<div id="splitBar"></div>
	<div style="float:left;width:76%;height:100%;padding:5px;">
			<jsp:include page="resourceDetail.jsp"></jsp:include>
	</div>
</div>
</body>
</html>