<!-- @{
    Style.Require("tab_" + Theme);
    Script.Require("tab").AtHead();
    Script.Require("angularjs").AtHead();
    Style.Require("ConnectionDetail_" + Theme);
    Script.Require("ConnectionDetailjs").AtHead();
    Layout = "../Shared/_LayoutWorkflow.cshtml";
    var workContext = EngineContext.Current.Resolve<IWorkContext>();
    var currentUser = workContext.User;
    var currentSkin = workContext.Theme;
}
 -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html class="ng-app" id="ng-app"  ng-app >
<head>
<title>迁移配置</title>
<link href="${ctx}/themes/default/tab.css" rel="stylesheet">
<link href="${ctx}/themes/default/workflowDesigner/connectionDetail.css" rel="stylesheet">

<script src="${ctx}/js/tab/tab.js"	type="text/javascript"></script>
<script src="${ctx}/js/angularjs/angular.min.js" type="text/javascript"></script>
<script src="${ctx}/js/workflow/connectionDetail.js"	type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        switchTab('basic');
    });
</script>
<ul id="tabcontainer">
    <li id="basic" class="tabBg1" onclick="switchTab('basic');">基本</li>
</ul>
<div id="main_bg">
    <div id="main_bg2" ng-controller="ConnectionCtrl">
        <div id="tabbasic">
            <fieldset>
                <legend>基本属性</legend>
                <div class="left">显示名称</div>
                <div class="content">
                    <input type="text" class="rightcontent" ng-model="transition().name" />
                </div>
                <div class="left">优先级</div>
                <div class="content">
                    <select class="rightcontent" style="width: 405px;" ng-model="transition().priority">
                        <option value="High">高</option>
                        <option value="SecondaryHigh">次高</option>
                        <option value="Middle">中</option>
                        <option value="SecondaryMiddle">次中</option>
                        <option value="Low">低</option>
                        <option value="SecondaryLow">次低</option>
                    </select>
                </div>
        <!--    </fieldset>
            <fieldset style="margin-top: 30px;">
                <legend>高级属性</legend>-->
                <div id="lineContent">
                    <input type="checkbox" id="defaultline" ng-model="transition().isDefault" />默认连线
                </div>
               <!-- <div>-->
                    <!--     <div class="split">
                        <div>
                            <input type="radio" name="expression" id="simpleexpression" />简单表达式
                        </div>
                        <table style="text-align: center">
                            <thead>
                                <tr>
                                    <td>左值:</td>
                                    <td>比较操作:</td>
                                    <td>右值:</td>
                                    <td>右值类型:</td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <input type="text" /></td>
                                    <td>
                                        <select>
                                            <option value="1">==</option>
                                            <option value="2">></option>
                                            <option value="3"><</option>
                                            <option value="4"><=</option>
                                            <option value="5">>=</option>
                                        </select></td>
                                    <td>
                                        <input type="text" /></td>
                                    <td>
                                        <select>
                                            <option value="constant">常量</option>
                                            <option value="bool">布尔值</option>
                                            <option value="variable">变量</option>
                                        </select></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>-->
                 <div class="left">表达式</div>
                <div class="content">
                    <textarea title="expression" class="expressioncontent" ng-model="transition().expression"></textarea>
                </div>
                   <!-- <div class="split">
                        <div>
                            <input type="radio" name="expression" id="complexexpression" checked="checked" />复杂表达式
                        </div>
                        <div>
                            <textarea title="expression" class="rightcontent" ng-model="transition().Expression" />
                        </div>
                    </div>-->
                <!--</div>-->
            </fieldset>
        </div>
    </div>
</div>
</body>
</html>