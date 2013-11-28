<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style type="text/css">
  .container
  {
	margin: 5px;
	float: left;
	overflow: auto;
	height:100%;
	width:100%;
  }
</style>
</head>
<body>
<jsp:include page="../../share/command.jsp"></jsp:include>
<div class="container" style="">
    <div id="treeview"></div>
</div>
<script>
   	   	
    $(document).ready(function () {
    	var dataSource = new kendo.data.HierarchicalDataSource({
			transport : {
				 read : {
			            type : "post",
			            url : "${ctx}/security/role/privilege?roleId="+$.query.get("roleId"),
			            dataType : "json",
			            contentType : "application/json"
			        }			        
			},
			schema: {
		        model: {
		            id: 'id',
		            text: 'text',
		            children: "items"
		        }
		    }
		});
    	
       $("#treeview").kendoTreeView({
        	dataSource : dataSource,
            dataValueField: 'id',
            dataTextField: 'text',
            checkboxes: {
                checkChildren: false,
                template: "<input type='checkbox' name='#= item.id #' id='#= item.id #' title='#= item.text #' #=item.checked?'checked':''# onclick='nodeChecked(this)' />"
            }, 
            select: function(e)
            {          	
            	var checkbox=$(e.node).children(":first-child").find(":checkbox");
            	checkbox.click(); 
            } 
        });  
    });
	
    function nodeChecked(me) {    	
 	   var currentNode=$(me).parent().parent().parent();
	   currentNode.find("ul:first").find(":checkbox").prop("checked", me.checked);
       parentChecked(currentNode,me.checked);
    }
    
    function parentChecked(node,checked) {
        var containerNode = node.parent();  //getParentByTagName(ele, 3);
        if (containerNode[0]) {
            var parentNode=containerNode.parent();
            if(parentNode.prop("tagName")=="LI")
            {
	        	var checkbox=parentNode.children(":first-child").find(":checkbox");
	             if (checked) {
	             	checkbox.prop("checked", true);
	             }
	             else {                    
	                 var hasChecked = false;
	                 containerNode.find(":checkbox").each(function () {
	                     if (this.checked) {
	                    	 checkbox.prop("checked", true);
	                         hasChecked = true;
	                         return;
	                     }
	                 });
	
	                 if (!hasChecked)
	                 	checkbox.prop("checked", false);
	             }
	             parentChecked(parentNode,checked);
            }
        }
    }
    
	function chooseItem()
	{
		var checkbox=$("#treeview").find("input:checked").eq(0);
		var target=window.parent.frames["ifrContent"];
		target.$("#"+$.query.get("code")).val(checkbox.attr("name"));
		target.$("#"+$.query.get("name")).val(checkbox.attr("title")); 
	    var chooseDialog = window.parent.$("#chooseDialog");
	    chooseDialog.data("kendoWindow").close();
	}
	
	function save()
	{       
        var processbar = $("#processbar");
        processbar.processbar({ message: '正在提交...' });
        
        var checkedNodes=[];
        $("#treeview").find("input:checked").each(function(){
        	checkedNodes.push(this.id);
        });
    
        $.post("${ctx}/security/role/privilege/save", {roleId:$.query.get("roleId"),ids:checkedNodes.join(",")}, function (result) {
            processbar.complete();
            alertMessage(result);
			if (window.parent&&result.indexOf("成功")>0) {
				window.parent.$("#actionDialog").data("kendoWindow").close();
				window.parent.frames["ifrContent"].$("#btnSearch").click();                    
			}
        });
	}
	</script>
</body>
</html>