<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>资源管理</title>
<link href="${ctx }/js/contextMenu/jquery.contextMenu.css"
	rel="stylesheet" type="text/css" />
<script src="${ctx }/js/contextMenu/jquery.contextMenu.js"
	type="text/javascript"></script>
<script src="${ctx }/js/drag.js" type="text/javascript"></script>

<style type="text/css">
html,body {
	margin: 0px 1px;
	height: 100%;
	overflow: hidden;
}

#navTitle {
	margin: 2px 2px 0px 2px;
	color: #434343;
	position: relative;
	bottom: 4px;
}

#navRegion #navContent {
	overflow: auto;
}

#navRegion {
	float: left;
	width: 19%;
	height: 100%
}

#contentRegion {
	padding-left: 3px;
	overflow: hidden;
}

#treeview {
	float: left;
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
	$(window).resize(function() {
		resizeWindow();
	});

	$(function() {
		resizeWindow();

		$("#splitBar").mousedown(function() {
			var e = window.event || arguments[0];
			$("#splitBar").startMove({
				evt : e
			});
		});
	});

	function resizeWindow() {
		var height = $("body").height() - 25;
		$("#splitBar").height(height);
		//$("#treeview").height(height);
		$("#navRegion").height(height);
		//$("#navbg").height(height);
		$("#navContent").height(height);
	}
</script>
<script id="treeview-template" type="text/kendo-ui-template">
     	<span id="#:item.id#">#:item.text#</span>
</script>
<script>
	$(document).ready(
			function() {
				var dataSource = new kendo.data.HierarchicalDataSource({
					transport : {
						read : {
							type : "post",
							url : "${ctx}/security/organization/data",
							dataType : "json",
							contentType : "application/json"
						}
					},
					schema : {
						model : {
							id : 'id',
							text : 'text',
							hasChildren : "hasChildren"
						}
					}
				});

				$("#treeview").kendoTreeView(
						{
							template : kendo.template($("#treeview-template")
									.html()),
							dataSource : dataSource,
							dataValueField : 'id',
							dataTextField : 'text',
							select : function(e) {
								var dateitem = $("#treeview").data(
										"kendoTreeView").dataItem((e.node));
								$("#orgID").val(dateitem.id);
								$("#orgName").val(dateitem.text);
								/*             $.get("${ctx}/security/resource/"+nodeid, function (resource) {
								
								 }); */
								//kGrid.data("kendoGrid").dataSource.read();
								$("#searchFlags").val("1");
								$("#operatorGrid").data("kendoGrid").dataSource
										.read();
								$("#searchFlags").val("");
							}
						});

				$.contextMenu({
					selector : '.k-in',
					callback : function(command, options) {
						var node = {
							id : $(this).find("span").attr("id"),
							text : $.trim($(this).text())
						};
						executCommand(command, node);
					},
					items : {
						"addOrganization" : {
							name : "添加机构"
						},
						"updateOrganization" : {
							name : "修改机构"
						},
						"addOperator" : {
							name : "添加用户"
						},
						"delete" : {
							name : "删除机构"
						}
					}
				});
			});

	function refreshTreeNode(id, text) {
		var node = $("#" + id);
		if (node.length > 0) {
			node.text(text);
		} else {
			var treeview = $("#treeview").data("kendoTreeView");
			var selectNode = treeview.select();
			if (selectNode && selectNode.length > 0) {
				treeview.append({
					text : text,
					id : id
				}, selectNode);
			}
		}
	}

	function update(id, text) {
		$("#" + id).val(text);
	}

	function executCommand(command, node) {
		if (command == "addOrganization") {
			window.parent.openActionDialog(
					"${ctx}/security/organization/add?parentId=" + node.id
							+ "&parentName=" + node.text, "添加机构", 650, 370);
		} else if (command == "updateOrganization") {
			window.parent.openActionDialog(
					"${ctx}/security/organization/update/" + node.id, "修改机构",
					650, 370);
		} else if (command == "addOperator") {
			window.parent.openActionDialog(
					"${ctx}/security/operator/add?orgId=" + node.id
							+ "&orgName=" + node.text, "添加用户", 650, 570);
		} else if (command == "delete") {
			var processbar = $("#processbar");
			processbar.processbar({
				message : '正在提交...'
			});
			var url = "${ctx}/security/organization/delete?id=" + node.id;
			$.post(url, function(result) {
				processbar.complete();
				var treeview = $("#treeview").data("kendoTreeView");
				var selectNode = treeview.select();
				if (selectNode && selectNode.length > 0) {
					treeview.remove(selectNode);
				}
				alertMessage(result);
			});
		}
	}
</script>
</head>
<body>
	<div class="container-fluid" style="height: 100%">
		<div id="navRegion">
			<div id="navTop">
				<label id="navTitle" style="float: left; margin: 0 0 0 5px;">
					 </label>
			</div>
			<div id="navbg">
				<div id="treeview" style="padding: 2px;"></div>
			</div>
		</div>
		<div id="splitBar"></div>
		<div id="contentRegion"
			style="float: left; width: 80%; height: 100%; padding: 3px;">
			<jsp:include page="operatorList.jsp"></jsp:include>
		</div>
	</div>
</body>
</html>