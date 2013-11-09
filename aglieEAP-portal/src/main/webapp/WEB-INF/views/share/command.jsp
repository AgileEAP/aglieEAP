<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
<!--
body{
font-size:12px;
}
#navpos
{
background: url('${ctx}/themes/default/images/navpos_bg.png');
height: 23px;
line-height:23px;
border: 1px solid #a3d567;
}
#toolbar
{
background: url('${ctx}/themes/default/images/toolbar_bg.png');
height:25px;
margin:0px;
}
    #toolbar li {
        float: left;
        list-style-type: none;
        padding-left: 12px;
        padding-top:3px;
        cursor: pointer;
        }
-->
</style>
<script type="text/javascript">
<!--
	var dialogSetting = {
		detailWidth : 650,
		detailHeight : 540,
		filterWidth : 550,
		filterHeight : 400
	};

	function setDetailWindowHeight(height){
		dialogSetting.detailHeight=height;
	}
	function setFilterWindowHeight(height){
		dialogSetting.filterHeight=height;
		var kendoWindow=$("#filterDialog").data("kendoWindow");
		kendoWindow.wrapper.css({
	        height: height,
	        position: 'fixed',
	        margin: 'auto',
	        top: '15%'
	    });		
	}

	$(function() {
		//初始化查询窗体
		var filterDialog = $("#filterDialog");
		if (filterDialog[0]) {
			filterDialog.kendoWindow({
				width : dialogSetting.filterWidth + "px",
				height : dialogSetting.filterHeight + "px",
				title : "查询",
				visible : false,
				modal : false,
				actions : [ "Close" ],
				close : function() {
					$("#searchFlags").val("");
				}
			});
			var kendoWindow=$("#filterDialog").data("kendoWindow");
			kendoWindow.wrapper.css({
		        height: dialogSetting.filterHeight,
		        position: 'fixed',
		        margin: 'auto',
		        top: '15%'
		    });
			kendoWindow.center();
		}
	});

	function clearFilterValues() {
		$('#frmSearch').find(":input").each(function() {
			if($(this).attr("type")!="button" && $(this).attr("type")!="submit"){
				if($(this).attr("type")!="hidden"){
					$(this).val("");
				}
			}
				
		});
	}

	function del() {
		if (!confirm("是否确定删除记录？")) {
			return false;
		}

		var id = "";
		$(".k-state-selected").each(function() {
			id += $(this).find("td:first").text() + ",";
		});

		var processbar = $("#processbar");
		processbar.processbar({
			message : '正在执行...'
		});
		$.post(document.URL.split("#")[0] + "/delete", {
			id : id
		}, function(result) {
			processbar.complete();
			alertMessage(result);
			if (window.parent && result&&result.indexOf("成功") > 0) {
				window.parent.$("#actionDialog").data("kendoWindow").close();
				window.parent.frames["ifrContent"].$("#btnSearch").click();
			}
		});
	}

	function commandExecute(cmdName, title, argument) {
		var url = document.URL.split("#")[0] + "/" + cmdName;
		if (cmdName == "update" || cmdName == "delete" || cmdName == 'view' || cmdName == 'check' ) {
			var id = $(".k-state-selected:first").find("td:first").text();
			if (id == null || id == undefined || id == "") {
				alertMessage("请先选择一行数据");
				return false;
			}
			url += "/" + id;
		}

		if (cmdName == "search")
			$('#filterDialog').data("kendoWindow").open();
		else if (cmdName == "add" || cmdName == "update"
				|| cmdName == "view" || cmdName == "check") {
			if (window.parent && window.parent.openActionDialog) {
				window.parent.openActionDialog("actionDialog",url, title, dialogSetting.detailWidth, dialogSetting.detailHeight);
			} else {
				openActionDialog("actionDialog",url, title,dialogSetting.detailWidth, dialogSetting.detailHeight);
			}
		} else {
			if (cmdName == "delete") {
				del();
			}
			var actionName = cmdName.substring(0, 1).toLowerCase()
					+ cmdName.substr(1);
			eval(actionName + "('" + title + "','" +argument  + "');");
		}		
	}
	function choose()
	{
		var tds= $(".k-state-selected:first").find("td");
		var target=window.parent.$("#actionDialog").find("#bg_div_iframe")[0].contentWindow;
		target.$("#"+$.query.get("code")).val(tds[0].innerText);
		target.$("#"+$.query.get("name")).val(tds[1].innerText); 
	    var chooseDialog = window.parent.$("#chooseDialog");
	    chooseDialog.data("kendoWindow").close();
	}
	
	function chooseItem()
	{
		var tds= $(".k-state-selected:first").find("td");
		var target=window.parent.$("#actionDialog").find("#bg_div_iframe")[0].contentWindow;
		target.$("#"+$.query.get("code")).val(tds[0].innerText);
		target.$("#"+$.query.get("name")).val(tds[1].innerText); 
	    var chooseDialog = window.parent.$("#chooseDialog");
	    chooseDialog.data("kendoWindow").close();
	}
	
//-->
</script>
<c:if test="${showNavigation==1}">
<div id="navpos">
	<img alt="" src="${ctx}/themes/default/images/navpos.png"><span>当前位置：${navigationTitle}</span>
</div> 
</c:if>

<c:if test="${showToolBar==1}">
<ul id="toolbar" >
 	<c:forEach items="${operates }" var="operate">
 	<c:if test="${fn:length(operate.name)>2 }">
 		<li id="${operate.id }" onclick="commandExecute('${operate.command }','${operate.name }')" style="width: 80px;">
			<img src="${ctx }/themes/default/images/operate/${operate.command }.png" width="14" height="29px" border="0" />&nbsp;${operate.name }
		</li>
 	</c:if>
 	<c:if test="${fn:length(operate.name)<=2 }">
 		<li id="${operate.id }" onclick="commandExecute('${operate.command }','${operate.name }')" style="width: 50px;">
			<img src="${ctx }/themes/default/images/operate/${operate.command }.png" width="14" height="29px" border="0" />&nbsp;${operate.name }
		</li>
 	</c:if>
	</c:forEach>				
</ul>
</c:if>
