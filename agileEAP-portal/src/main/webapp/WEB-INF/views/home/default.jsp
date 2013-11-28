<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<head>
	<title>肉类蔬菜追溯平台</title>
	<link href="${ctx}/themes/default/layout.css" rel="stylesheet">
    <script type="text/javascript" language="javascript">
	$(document).ready(function () {
	    $("#mainbody").height($("#masterpage").height() - $("#header").height()-$("#headerMenu").height() - $("#footer").height()-10);
	 }); 
    </script>
    <!--Powered by trh --Copyright (c) 2010-@DateTime.Now.Year-->
</head>
<body>
    <div id="masterpage">
    	<jsp:include page="header.jsp"></jsp:include>
    	<jsp:include page="headerMenu.jsp"></jsp:include>
        <div class="clear">
        </div>
        <div id="mainbody">
			<div id="navRegion">
				<div id="navTop">
				<img id="navIcon" alt="" src="${ctx }/themes/default/images/navigate/navbar_top_bg.png"/>
				<label id="navTitle" ></label>
				</div>
			<div id="navbg">
			 <div id="navContent">
			 	<iframe id="ifrNav" name="ifrNav" frameborder="0" width="100%" scrolling="no" height="100%"></iframe>
			 </div>
			</div>
			</div>
            <div id="splitBar"></div>
            <div id="contentRegion">
            		<iframe id="ifrContent" name="ifrContent" src="${ctx }/home/main" frameborder="0" scrolling="auto" height="100%" width="100%"></iframe>
            </div>
        </div>
    </div>
</body>
</html>
