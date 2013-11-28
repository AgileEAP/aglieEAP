<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script language="javascript" type="text/javascript"
	src="../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
//显示操作信息。
var operatorMsg="${operatorMsg}";
if(operatorMsg!=null && operatorMsg!=""){
	alert(operatorMsg);
	}
	
	//检查开始建立日期必须小于等于结果日期。
	function checkSubmit() {
		var beginDate=document.getElementsByName("beginUploadDate")[0].value;
		var endDate=document.getElementsByName("endUploadDate")[0].value;
		if(beginDate<=endDate){
			document.forms[0].submit();
		}else{
			alert("开始建立日期必须小于等于结果日期!请重新选择后再查询!");
			return false;
		}
		}
	//清空查询条件 。
	function emptyQuery(){
		document.getElementById("uploadPerson").value="";
		document.getElementById("uploadProject").value="";
		document.getElementById("beginUploadDate").value="";
		document.getElementById("endUploadDate").value="";
	}

</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>种植上传查询列表</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	font-size: 12px;
}

.STYLE3 {
	font-size: 12px;
	font-weight: bold;
}

.STYLE4 {
	color: #03515d;
	font-size: 12px;
}
-->
</style>
<script>
var  highlightcolor='#c1ebff';
//此处clickcolor只能用win系统颜色代码才能成功,如果用#xxxxxx的代码就不行,还没搞清楚为什么:(
var  clickcolor='#51b2f6';
function  changeto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=highlightcolor&&source.id!="nc"&&cs[1].style.backgroundColor!=clickcolor)
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor=highlightcolor;
}
}

function  changeback(){
if  (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="nc")
return
if  (event.toElement!=source&&cs[1].style.backgroundColor!=clickcolor)
//source.style.backgroundColor=originalcolor
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}

function  clickto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=clickcolor&&source.id!="nc")
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor=clickcolor;
}
else
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}
</script>
</head>
<body>
	<div>
		<table width="100%" border="0" cellpadding="0" cellspacing="1"
			bgcolor="b5d6e6">
			<tr>
				<td width="8"><div align="center">
						<img src="../images/tb.gif" width="16" height="16" align="left" />
					</div></td>
				<td class="STYLE1"><span class="STYLE3">你当前的位置</span>：[种植上传记录]-[查询]</td>
			</tr>
		</table>
	</div>
	<div>
		<form action="" method="post" onsubmit=" return checkSubmit()">
			<table width="100%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="b5d6e6">
				<tr>
					<td width="8" background="../images/tab_03.gif">&nbsp;</td>
					<td class="STYLE1">上传项目<input id="uploadProject" name="uploadProject"
						type="text" value="${uploadProject}" /></td>
					<td class="STYLE1">上传人员<input id="uploadPerson" name="uploadPerson"
						type="text" value="${uploadPerson}" /></td>
					<td class="STYLE1">开始上传日期<input id="beginUploadDate"
						name="beginUploadDate" type="text" value="${beginUploadDate}"
						onClick="WdatePicker()" /></td>
					<td class="STYLE1">结束上传日期<input id="endBuildDate"
						name="endUploadDate" type="text" value="${endUploadDate}"
						onClick="WdatePicker()" /></td>
					<td class="STYLE1"><input id="empty" type="button" value="清空"
						onclick="emptyQuery()" /></td>
					<td class="STYLE1"><input id="query" type="submit" value="查询" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="STYLE1">
		<table width="100%" border="0.5" cellpadding="0" cellspacing="1"
			bgcolor="b5d6e6">
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
		<table width="100%" cellspacing="1" bgcolor="b5d6e6">
			<tr>
				<td width="3%" height="30" background="../images/bg.gif"
					bgcolor="#FFFFFF"><div align="center">
						<input type="checkbox" name="checkbox" value="checkbox" />
					</div></td>
				<td width="10%" height="30" background="../images/bg.gif"
					bgcolor="#FFFFFF"><div align="center">上传项目</div></td>
				<td width="10%" height="30" background="../images/bg.gif"
					bgcolor="#FFFFFF"><div align="center">上传时间</div></td>
					<td width="10%" height="30" background="../images/bg.gif"
					bgcolor="#FFFFFF"><div align="center">上传条数</div></td>
				<td width="10%" height="30" background="../images/bg.gif"
					bgcolor="#FFFFFF"><div align="center">上传人员</div></td>
				<td width="10%" height="30" background="../images/bg.gif"
					bgcolor="#FFFFFF"><div align="center">上传IP</div></td>
			</tr>
			<c:forEach items="${uploadDataList}" var="uploadData">
				<tr>
					<td height="20" bgcolor="#FFFFFF"><div align="center">
							<input type="checkbox" name="checkbox2" value="checkbox" />
						</div></td>
					<td height="20" bgcolor="#FFFFFF"><div align="center">
							${uploadData.uploadProject}</div></td>
					<td height="20" bgcolor="#FFFFFF"><div align="center">
							<fmt:formatDate value="${uploadData.uploadTime}"
								pattern="yyyy-MM-dd" />
						</div></td>
						<td bgcolor="#FFFFFF"><div align="center">
							${uploadData.uploadCount}</div></td>
					<td bgcolor="#FFFFFF"><div align="center">
							${uploadData.uploadPerson}</div></td>
					<td height="20" bgcolor="#FFFFFF"><div align="center">
							${uploadData.loginIp}</div></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
