<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>登录页</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link type="image/x-icon" href="${ctx}/themes/default/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/themes/default/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/themes/default/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
<link href="${ctx}/themes/default/site.css" rel="stylesheet">
<link href="${ctx }/themes/default/login_slideshow.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx }/themes/default/login_css.css" type="text/css">

<script src="${ctx }/js/login/slideshow.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/themes/default/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery.validate.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		//聚焦第一个输入框
		$("#username").focus();
		//为inputForm注册validate函数
		$("#loginForm").validate({
			rules : {
				username : {
					required : true
				},
				password : {
					required : true
				},
				confirmCode : {
					required : true,
					remote : "${ctx}/validcode/validate"
				}
			},
			messages : {
				username : {
					required : "<font color=red size=2 >请输入用户名</font>"
				},
				password : {
					required : "<font color=red size=2 >请输入密码</font>"
				},
				confirmCode : {
					required : "<font color=red size=2 >请输入验证码</font>",
					remote : "<font color=red size=2 >验证码错误！</font>"
				}
			},
			errorPlacement : function(error, element) {
				error.appendTo(element.parent());
			},
			submitHandler : function(form) {
				form.submit();
			},
			errorClass : "error",
			focusCleanup : true
		//被验证的元素获得焦点时移除错误信息  
		});
	
	});
	function generateValidcode(obj) {
		var timeNow = new Date().getTime();
		obj.src = "${ctx }/validcode/generate?time=" + timeNow;
	}
</script>
</head>
<body>
<div class="login">
   <div class="login_head">
      <div class="login_head1"></div>
	  <div class="login_head2"></div> 
   </div>
   
   <div class="login_content">
      <div class="login_contentbg">
	  
       	<div class="login_datu">
		   <div class="comiis_wrapad" id="slideContainer">
				<div id="frameHlicAe" class="frame cl">
					<div class="temp"></div>
					<div class="block">
						<div class="cl">
							<ul class="slideshow" id="slidesImgs">
								<li><img src="${ctx }/themes/default/images/login/1.png" width="691" height="417" alt="" /></li>
								<li><img src="${ctx }/themes/default/images/login/2.png" width="691" height="417" alt="" /></li>
								<li><img src="${ctx }/themes/default/images/login/3.png" width="691" height="417" alt="" /></li>
							</ul>
						</div>
						<div class="slidebar" id="slideBar">
							<ul>
								<li class="on"></li>
								<li></li>
								<li></li>
							</ul>
						</div>
					</div>
				</div>
		  	</div>
			<script type="text/javascript">
				SlideShow(2000);
			</script>
		 </div> 
		 <form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal">
			 <div class="login_contentright">
			    <div class="login_contentright1" style="height: 12px;"><img src="${ctx }/themes/default/images/login/login_contentright1.png" width="315" height="5" border="0" /></div>
				<div class="login_contentright2"></div>
				<div class="login_contentrightbg">
					<div style="margin-top: 10px"></div>
					<%
						String error = (String) request
								.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
						if (error != null) {
					%>
					<div class="alert alert-error input-medium controls" 
						style="margin-left: 70px;line-height: 15px;font-size: 12px;
							   margin-bottom: 10px;padding-bottom: 5px;width:118px;
							   padding-top: 0px;margin-top: 0px;padding-right: 25px;">
						<button class="close" data-dismiss="alert">×</button>
						登录失败，请重试.
					</div>
					<%
						}else{
					%>
						<div style="margin-top: 45px"></div>
					<%
						}
					%>
				    <div class="login_contentusera" style="margin-top: 0px;">
				    	<div class="control-group" style="margin-bottom: 5px;">
							<label for="username" class="control-label" style="width: 70px;padding-top: 0px;font-size: 12px;">用户名：</label>
							<div class="controls" style="margin-left: 1px;">
								<input type="text" id="username" name="username" style="height: 12px;width: 145px;font-size: 12px; 
										vertical-align: middle;" placeholder="输入用户名" value="admin" class="input-medium required" />
							</div>
						</div>
					</div>
					<div class="login_contentuserb">
						<div class="control-group" style="margin-bottom: 5px;">
							<label for="password" class="control-label" style="width: 70px;padding-top: 0px;font-size: 12px;">密 &nbsp;&nbsp;&nbsp;码：</label>
							<div class="controls" style="margin-left: 1px;">
								<input type="password" id="password" name="password" value="admin" style="height: 12px;width: 145px;font-size: 12px;"
									placeholder="输入密码" class="input-medium required" />
							</div>
						</div>
					</div>
					<div class="login_contentuserc">
						<div class="control-group" style="margin-bottom: 5px;">
							<label for="confirmCode" class="control-label" style="width: 70px;padding-top: 0px;font-size: 12px;">验证码：</label>
							<div class="controls" style="margin-left: 1px;">
								<input type="text" id="confirmCode" name="confirmCode" class="input-medium required" 
									style="width: 80px;height: 12px;font-size: 12px;" placeholder="输入验证码"/> 
									<img src="${ctx }/validcode/generate" alt="验证码" border=0 onclick="generateValidcode(this)" />
							</div>
						</div>
					</div>
					
					<div class="login_contentbtn" style="margin-top: 20px;">
						<div class="control-group" style="margin-bottom: 5px;">
							<div class="controls" style="margin-left: 70px;">
								<input id="submit_btn" class="btn btn-primary" type="submit" 
								style="background: url(${ctx }/themes/default/images/login/login_btn.png);border: 0;padding: 0;width: 83px;height: 31px;" value="" />
							</div>
						</div>
				    </div>
					<div class="login_contentxw">
					    <div class="login_contenx" style="height: 30px;"></div>
						<div class="login_contenw">版权所有：广州市彩讯计算机科技有限公司</div>
				    </div>
				</div>
				<div class="login_contentright3"></div>
			  </div>
		  </form>
	  </div>
   </div>
   
   <div class="login_foot">
       <div class="login_foot1"></div>
	   <div class="login_foot2"></div>
	   <div class="login_foot3"></div>
   </div>
</div>
</body>
</html>
