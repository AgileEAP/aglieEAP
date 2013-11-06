<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html class="ng-app" id="ng-app"  ng-app >
<html>
<head>
<title><sitemesh:title /></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/themes/default/images/favicon.ico"	rel="shortcut icon">
<link href="${ctx}/themes/default/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/themes/default/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
<link href="${ctx}/themes/default/kendoui/kendo.common.min.css" rel="stylesheet">
<link href="${ctx}/themes/default/kendoui/kendo.metro.min.css" rel="stylesheet">
<link href="${ctx}/themes/default/site.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.query.js"></script>
<script type="text/javascript" src="${ctx}/themes/default/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/js/kendo.web.min.js"></script>
<script type="text/javascript" src="${ctx}/js/processbar/processbar.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.diy.js"></script>
<script type="text/javascript" src="${ctx}/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/js/utils.js"></script>
<!--[if lt IE 9]>
    <script type="text/javascript" src="${ctx}/js/json2.js"></script>
<![endif]-->
<sitemesh:head />
</head>
<body>
	<%-- <div class="container" style="width: 100%">
		<%@ include file="/WEB-INF/layouts/header.jsp"%> 
		<div id="content">--%>
			<sitemesh:body />
	<%-- 		</div>
	<%@ include file="/WEB-INF/layouts/footer.jsp"%> 
	</div>--%>
  	<div id="processbar"></div>
  	<div id="actionDialog"></div>
  	<div id="actionDialog2"></div>
  	<div id="actionDialog3"></div>
  	<div id="chooseDialog"></div>   
</body>
</html>