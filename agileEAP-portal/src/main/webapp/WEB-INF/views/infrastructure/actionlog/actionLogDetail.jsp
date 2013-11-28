<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>登录用户操作日志信息</title>
</head>
<body>
<form id="inputForm" action="${ctx}/infrastructure/actionlog/${action}" method="post" class="form-horizontal">
<input type="hidden" id='id' name="id" value="${actionLog.id}"/>
<fieldset>
 <div class="control-group">
		<label for="message" class="control-label">操作信息:</label>
		<div class="controls">
             <textarea id="message" name="message" rows="8" >${actionLog.message}</textarea>
        </div>
	</div>
	 <div class="control-group">
		<label for="clientIP" class="control-label">机器IP:</label>
		<div class="controls">
			<input type="text" id="clientIP" name="clientIP"  value="${actionLog.clientIP}" class=" required" minlength="1"  maxlength="16"/>
        </div>
	</div>
	<div class="control-group">
		<label for="userName" class="control-label">操作员:</label>
		<div class="controls">
			<input type="text" id="userName" name="userName"  value="${actionLog.userName}" class=" required" minlength="1"  maxlength="16"/>
        </div>
	</div>
	<div class="control-group">
		<label for="createTime" class="control-label">创建时间:</label>
		<div class="controls">
        <input type="text" id="createTime" name="createTime" class="readonly" minlength="1"
			value='<fmt:formatDate value="${actionLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />' />
        </div>
	</div>
</fieldset>
</form>
</body>
</html>