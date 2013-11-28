<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="../../share/command.jsp"></jsp:include>
<div id="treeview"></div>
 <script>
   	function singleCheck(me)
   	{
   		$("input[type='checkbox']").prop("checked",false);
   		$(me).prop("checked", true);
   	}
   	
    $(document).ready(function () {
    	var dataSource = new kendo.data.HierarchicalDataSource({
			transport : {
				 read : {
			            type : "post",
			            url : "${ctx}/security/organization/data",
			            dataType : "json",
			            contentType : "application/json"
			        }			        
			},
			schema: {
		        model: {
		            id: 'id',
		            text: 'text',
		            imageUrl:'icon',
		            hasChildren: "hasChildren"
		        }
		    }
		});
    	
       	$("#treeview").kendoTreeView({
        	dataSource : dataSource,
            dataValueField: 'id',
            dataTextField: 'text',
            dataImageUrlField:'icon',
            checkboxes: {
                checkChildren: false,
                template: "<input type='checkbox' name='#= item.id #' title='#= item.text #' onclick=\"singleCheck(this);\" />"
            }, 
            select: function(e)
            {
            	$("input[type='checkbox']").prop("checked",false);
            	var checkbox = $("input[type='checkbox']", e.node)[0];
            	checkbox.click();
            } 
        });      	
    });
	
	function choose()
	{
		var checkbox=$("#treeview").find("input:checked").eq(0);
		var entry=$.query.get("flags");
		var target;
		if(entry=="search"){
			target=window.parent.$("#ifrContent")[0].contentWindow;	
		}
		else{
			target=window.parent.$("#actionDialog").find("iframe")[0].contentWindow;
		}
		
		target.$("#"+$.query.get("code")).val(checkbox.attr("name"));
		target.$("#"+$.query.get("name")).val(checkbox.attr("title")); 
	    var chooseDialog = window.parent.$("#chooseDialog");
	    chooseDialog.data("kendoWindow").close();
	}
	</script>   
</body>
</html>