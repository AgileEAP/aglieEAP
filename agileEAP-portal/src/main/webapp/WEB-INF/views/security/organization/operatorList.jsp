<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../../share/command.jsp"></jsp:include>
   <div id="operatorGrid"></div>    
   <div id="filterDialog" >
       <form id="frmSearch" class="form-horizontal">
           <input id="searchFlags" type="hidden" />
           <fieldset>
           	<div class="control-group">
           		<label for="loginName" class="control-label">登录名:</label>
           		<div class="controls">
           			<input type="text" id="loginName" name="loginName" value=""/>
                   </div>
           	</div>
           	<div class="control-group">
           		<label for="name" class="control-label">姓名:</label>
           		<div class="controls">
           			<input type="text" id="name" name="name" value=""/>
                   </div>
           	</div>
        	<div class="control-group">
        		<label for="parentID" class="control-label">所属部门:</label>
        		<div class="controls">
        		    <input type="hidden" id="orgID" name="orgID"  value=""  class="required" />
        		    <input type="text" id="orgName" name="orgName"  value="" placeholder="请选择所属部门" class="required choosebox" onclick="openChooseBoxDialog('orgID','orgName','选择部门', '${ctx}/security/organization/tree?action=choose&flags=search', '450','400')"/>
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
	  	setDetailWindowHeight(540);
		setFilterWindowHeight(260);
       var dataSource = new kendo.data.DataSource({
           transport : {
               read : {
                   type : "post",
                   url : "${ctx}/security/operator/ajaxList",
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
       var operatorGrid=$("#operatorGrid");
       var kGrid=operatorGrid.kendoGrid({
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
               field : "loginName",
               title : "登录名"
           }
           ,{
               field : "name",
               title : "姓名"
           }             
           ,{
               field : "createTime",
               title : "创建时间",
               template: '#= kendo.toString(new Date(createTime),"yyyy-MM-dd HH:mm:ss")#'
           }
        ]
       });

       $("#btnSearch").click(function (e) {
       	$("#searchFlags").val("1");
       	kGrid.data("kendoGrid").dataSource.read();
       	$("#searchFlags").val("");
       	$("#filterDialog").data("kendoWindow").close();  
       });

       operatorGrid.delegate("tbody>tr", "dblclick", function(){
       	commandExecute("view","查看");
       });
   });
   
   function configRole(title)
   {
	var operatorId = $("#operatorGrid").find(".k-state-selected:first").find("td:first").text();
	if (operatorId == null || operatorId == undefined || operatorId == "") {
		alertMessage("请先选择一行数据");
		return false;
	}
   	openDialog("${ctx}/security/role?entry=chooseRole&operatorId="+operatorId, title, 500,450);
   }
   
   function del(uri) {
		if (!confirm("是否确定删除记录？")) {
			return false;
		}

		var id = "";
		$("#operatorGrid").find(".k-state-selected").each(function() {
			id += $(this).find("td:first").text() + ",";
		});

		var processbar = $("#processbar");
		processbar.processbar({
			message : '正在执行...'
		});
		$.post(uri||document.URL.split("#")[0] + "/delete", {
			id : id
		}, function(result) {
			processbar.complete();
			alertMessage(result);
			if (window.parent && result.indexOf("成功") > 0) {
				window.parent.$("#actionDialog").data("kendoWindow").close();
				window.parent.frames["ifrContent"].$("#btnSearch").click();
			}
		});
	}
   
	function commandExecute(cmdName, title, argument) {
		var url = "${ctx}/security/operator/" + cmdName;
		if (cmdName == "update" || cmdName == "delete" || cmdName == 'view' || cmdName == 'check' ) {
			var id = $("#operatorGrid").find(".k-state-selected:first").find("td:first").text();
			if (id == null || id == undefined || id == "") {
				alertMessage("请先选择一行数据");
				return false;
			}
			url += "/" + id;
		}
		
		if(cmdName=="add"){
			var treeview = $("#treeview").data("kendoTreeView");
		    var selectNode= treeview.select(),node={id:"",text:""};
		    if(selectNode&&selectNode.length>0)
		   	{
	        	var dateitem=treeview.dataItem(selectNode);
				node={text: dateitem.text,id:dateitem.id};
		   	}
		    window.parent.openActionDialog("${ctx}/security/operator/add?orgId="+node.id+"&orgName="+node.text, "添加用户", 650, 570);
		    return;
		}

		if (cmdName == "search")
			$('#filterDialog').data("kendoWindow").open();
		else if (cmdName == "update"|| cmdName == "view") {
			if (window.parent && window.parent.openActionDialog) {
				window.parent.openActionDialog(url, title, dialogSetting.detailWidth, dialogSetting.detailHeight);
			} else {
				openActionDialog(url, title,dialogSetting.detailWidth, dialogSetting.detailHeight);
			}
		} else {
			if (cmdName == "delete") {
				del( "${ctx}/security/operator/" + cmdName);
			}
			var actionName = cmdName.substring(0, 1).toLowerCase()
					+ cmdName.substr(1);
			eval(actionName + "('" + title + "','" +argument  + "');");
		}		
	}
   </script>
