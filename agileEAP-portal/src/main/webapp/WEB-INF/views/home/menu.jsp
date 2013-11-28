<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="${ctx }/themes/default/css.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>导航栏</title>
    <link href="${ctx}/themes/default/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${ctx}/themes/default/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="${ctx}/themes/default/site.css" rel="stylesheet">
	<link href="${ctx}/themes/default/kendoui/kendo.common.min.css" rel="stylesheet">
	<link href="${ctx}/themes/default/kendoui/kendo.metro.min.css" rel="stylesheet">
	<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <style type="text/css">
    html, body {
	margin: 0px;
	padding: 3px 0px;
    width:100%;
    height: 100%;
}
    ul
    {
		padding: 1px 0px;
		margin: 2px 0px;
    }

        ul li
        {
            margin: 0px;
			padding: 1px 10px;
            list-style-type: none;
            vertical-align: middle;
        }

            ul li a
            {
                color: #000000;
                cursor: pointer;
                font-size: 13px;
            }

                ul li A:link
                {
                    color: #000000;
                }

                ul li A:visited
                {
                    color: #000000;
                }

    .liOver
    {
        background-color: #e1f9c0;
        border: 1px solid #E1F1FE;
    }

    .liOut
    {
        background-color: White;
    }

    .liClick
    {
        background-color: #bae384;
        border: 1px solid #d2f6a2;
    }
    
    .navMenu
    {
        cursor: pointer;
    	border-radius: 3px;
        overflow: hidden;
		width: 160px;
		height: 29px;
		padding-left: 0px;
		padding-top: 4px;
        background: url(${ctx }/themes/default/images/menu/menu_background.png) top left;
        font-family: "微软雅黑", "黑体", "宋体";
		color: white;
		font-size: 13px;
		font-weight: bold;
    }
	
	a:hover{
		text-decoration :none;
	}
</style>
    <script type="text/javascript">
     $(function () {
        var $content = $("#mainMenu");
        $content.find(".menuitem").each(function () {
            //鼠标经过样式
          $(this).hover(function () {
                $(this).removeClass("liOut");
                $(this).addClass("liOver");
            },
            function () {
                $(this).removeClass("liOver");
                $(this).addClass("liOut");
            }); 

            //单击后的样式
            $(this).click(function () {
                $(this).siblings().each(function () { $(this).removeClass("liClick"); });
                $(this).addClass("liClick");
            });
        });
    }); 
   
    function toggleMenu(id)
    {
    	$("#mainMenu").find('ul').each(function()
		{
			if(this.id!=id){
				$(this).hide();
				$("#"+this.id+"flag").removeClass("flag");
				$("#"+this.id+"flag").attr("src","${ctx }/themes/default/images/menu/fold.png");
			}else{
				if($("#"+this.id+"flag").attr("class")=="flag"){
					$("#"+this.id+"flag").removeClass("flag");
					$("#"+this.id+"flag").attr("src","${ctx }/themes/default/images/menu/fold.png");
				}else{
					$("#"+this.id+"flag").addClass("flag");
					$("#"+this.id+"flag").attr("src","${ctx }/themes/default/images/menu/unfold.png");
				}
			}
		});
    	$("#"+id).toggle();
    }
	
    </script>
</head>
<body>
<ul id="mainMenu">
<c:forEach items="${menuItems }" var="menuItem">
<li>
	<div class="navMenu" onclick="toggleMenu('${menuItem.id }')">
		<img alt="" src="${ctx }/themes/default/images/menu/${menuItem.icon }">
		${menuItem.name }
		<span style="float: right;">
			<img alt="" class="" id="${menuItem.id }flag" src="${ctx }/themes/default/images/menu/fold.png">
		</span>
	</div>
<ul id="${menuItem.id }" style="display:none;">
	<c:forEach items="${menuItem.children }" var="childMenuItem">
	<li class="menuitem">
	<a href="${ctx }/${childMenuItem.url }" target="ifrContent">
	<img src="${ctx }/themes/default/images/navigate/nav_menuitem_icon.png" style="vertical-align:text-bottom;margin-right: 4px; overflow:hidden" />${childMenuItem.name }
	</a>
	</li>
	</c:forEach>
	</ul>
</li>
</c:forEach>
</ul>
</body>
</html>
