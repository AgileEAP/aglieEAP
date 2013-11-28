<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
    $(document).ready(function () {
        $("#modifyPwd").click(function () {
            //openOperateDialog('修改密码', "/Account/ModifyPassword", 450, 220, false, false, 100);
            var url = "${ctx}/profile/modifyPwd";
            window.parent.openActionDialog(url, '修改密码', 600, 350);
        });
    });
</script>

<div id="header">
    <div id="logo">
    </div>
    <div id="syslogo">
    </div>
    <ul id="info" style="width: 350px;">
        <li style="width: 120px;">
            <label>
                <img src="${ctx}/themes/default/images/female.png" width="16" height="16" alt="" />
                ${currentUser}
            </label>
        </li>
        <li style="width: 80px;">
            <img src="${ctx}/themes/default/images/password.png" width="16" height="16" alt=""  />
            <a id="modifyPwd">修改密码</a></li>
        <li style="width: 50px;">
            <img src="${ctx}/themes/default/images/logout.png" width="16" height="16"alt=""  />
            <a href="${ctx}/logout">退出</a></li>
        <li style="width: 50px;">
            <img src="${ctx}/themes/default/images/help.png" width="16" height="16" alt="" />
            <a target="ifrContentPage" href="/help">帮助</a></li>
    </ul>
</div>