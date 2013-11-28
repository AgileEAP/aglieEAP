<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
    $(function () {
        var activeMenuID = $("#headerMenu li:eq(0)").attr("id");
        $("#headerMenu a").mouseover(function () {
            $(this).attr("class", "currentmenu_a");
        });

        $("#headerMenu a").mouseout(function () {
            $(this).removeAttr("class");
        });

        $("#headerMenu a").click(function () {
            if (activeMenuID != $(this).parent().attr("id")) {
                $("#" + activeMenuID).removeAttr("class");
                activeMenuID = $(this).parent().attr("id");
                $("#" + activeMenuID).attr("class", "currentmenu");
            }
        });

        $("#headerMenu li").mouseover(function () {
        	if($(this).attr("class")!="disablemenu")
            	$(this).attr("class", "currentmenu_li");
        });

        $("#headerMenu li").mouseout(function () {
            if (activeMenuID == $(this).attr("id")) {
                $("#" + activeMenuID).attr("class", "currentmenu");
            }
            else if($(this).attr("class")!="disablemenu"){
                $(this).removeAttr("class");
            }
        });

        var activeMenu= $("#" + activeMenuID);
        activeMenu.attr("class", "currentmenu");
        $("#ifrNav").attr("src",activeMenu.find("a:first").attr("href"));        
    });

</script>
<ul id="headerMenu">
 	<c:forEach items="${headerMenus }" var="headerMenu">
<%--  	<c:if test="${headerMenu.authorized==false}">
 	 	<li id='${headerMenu.id}' class='disablemenu' style="border-radius: 2px;cursor: default;">${headerMenu.name }</li>
 	</c:if> --%>
 	<c:if test="${headerMenu.authorized==true}">
 		<li id='${headerMenu.id}' class=''><a href="${ctx }/home/menu?id=${headerMenu.id}" target="ifrNav">${headerMenu.name }</a></li>
 		<li style="color: #EEEEEE;">
            <img width="2" height="28" style="float: left;" src="${ctx }/themes/default/images/menu/header_menu_split.png" alt=""/>
        </li>
 	</c:if>
 	</c:forEach>
</ul>
