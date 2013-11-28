﻿function alertMessage(msg) {
	alert(msg);
}

function openTopDialog(container,url, title,width,height,showModal) {
	if (window.parent && window.parent.openActionDialog) {
		window.parent.openActionDialog(container,url, title, width, height,showModal);
	} else {
		openActionDialog(container,url, title,width, height,showModal);
	}
}

function openDialog(url, title,width,height) {
	openTopDialog("actionDialog",url, title,width, height,false);
}

function openActionDialog(container,url,title,width,height,showModal) {
	var actionDialog = $("#"+container);
	actionDialog.html('<iframe id="bg_div_iframe" scrolling="auto"  width="100%" height="98%" allowTransparency="true" frameborder="0"></iframe>');
	actionDialog.find('#bg_div_iframe').attr('src', url);
	var kendoWindow=actionDialog.kendoWindow({
	    width : width + "px",
		height : height + "px",
		title : title,
		modal : showModal || false,
		actions : [ "Maximize", "Close" ]
	}).data("kendoWindow");
	$("#"+container+"_wnd_title").text(title);
	kendoWindow.wrapper.css({
			width: width,
	        height: height,
	        position: 'fixed',
	        margin: 'auto',
	        top: '20%'
	    });
	kendoWindow.center();
	kendoWindow.open();
}

function openChooseBoxDialog(code, name, title, url, width, height) {
	var chooseDialog = window.parent.$("#chooseDialog");
	chooseDialog.html('<iframe id="bg_div_iframe" scrolling="no"  width="100%" height="98%" allowTransparency="true" frameborder="0"></iframe>');
	chooseDialog.find('#bg_div_iframe').attr('src', url + "&code=" + code + "&name=" + name);
	var kendoWindow=chooseDialog.kendoWindow({
		width : width + "px",
		height : height + "px",
		title : title,
		modal : true,
		actions : [ "Maximize", "Close" ]
	}).data("kendoWindow");
	$("#actionDialog_wnd_title").text(title);	
	kendoWindow.wrapper.css({
		width: width,
        height: height,
        position: 'fixed',
        margin: 'auto',
        top: '20%'
    });
	kendoWindow.center();
	kendoWindow.open();
}
function getWindowHeight() {
	var windowHeight = 0;
	if (typeof (window.innerHeight) == 'number') {
		windowHeight = window.innerHeight;
	} else {
		if (document.documentElement && document.documentElement.clientHeight) {
			windowHeight = document.documentElement.clientHeight;
		} else {
			if (document.body && document.body.clientHeight) {
				windowHeight = document.body.clientHeight;
			}
		}
	}
	return windowHeight;
}

$(function($) {
	//获取当前网址，如： http://localhost:8080/eTrace-portal/retail/meun.jsp  
	var curWwwPath=window.document.location.href;
	//获取主机地址之后的目录，如： eTrace-portal/retail/meun.jsp
	var pathName=window.document.location.pathname;
	var pos=curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8080    
	var localhostPaht=curWwwPath.substring(0,pos); 
	//获取带"/"的项目名，如：/eTrace-portal    
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	var path=localhostPaht+projectName;
	$("#btnSubimt").bind({
		mouseover : function() {
			$("#btnSubimt").css("background", "url("+path+"/themes/default/images/button/submit_change.png)");
		},
		mouseout : function() {
			$("#btnSubimt").css("background", "url("+path+"/themes/default/images/button/submit_init.png)");
		}
	});
	$("#btnCancel").bind({
		mouseover : function() {
			$("#btnCancel").css("background", "url("+path+"/themes/default/images/button/cancel_change.png)");
		},
		mouseout : function() {
			$("#btnCancel").css("background", "url("+path+"/themes/default/images/button/cancel_init.png)");
		}
	});
	$("#btnSearch").bind({
		mouseover : function() {
			$("#btnSearch").css("background", "url("+path+"/themes/default/images/button/submit_change.png)");
		},
		mouseout : function() {
			$("#btnSearch").css("background", "url("+path+"/themes/default/images/button/submit_init.png)");
		}
	});
	// jQuery.validate 自定义扩展方法
	
	// 1.修改默认提示
	jQuery.extend(jQuery.validator.messages, {
		required : "<font color=red size=2 >不能为空</font>",
		remote : "<font color=red size=2 >请修正该字段</font>",
		email : "<font color=red size=2 >请输入正确格式的电子邮件</font>",
		url : "<font color=red size=2 >请输入合法的网址</font>",
		date : "<font color=red size=2 >请输入合法的日期</font>",
		dateISO : "<font color=red size=2 >请输入合法的日期 (ISO).</font>",
		number : "<font color=red size=2 >请输入合法的数字</font>",
		digits : "<font color=red size=2 >只能输入整数</font>",
		creditcard : "<font color=red size=2 >请输入合法的信用卡号</font>",
		equalTo : "<font color=red size=2 >请再次输入相同的值</font>",
		accept : "<font color=red size=2 >请输入拥有合法后缀名的字符串</font>",
		maxlength : jQuery.validator.format("<font color=red size=2 >请输入一个 长度最多是 {0} 的字符串</font>"),
		minlength : jQuery.validator.format("<font color=red size=2 >请输入一个 长度最少是 {0} 的字符串</font>"),
		rangelength : jQuery.validator.format("<font color=red size=2 >请输入 一个长度介于 {0} 和 {1} 之间的字符串</font>"),
		range : jQuery.validator.format("<font color=red size=2 >请输入一个介于 {0} 和 {1} 之间的值</font>"),
		max : jQuery.validator.format("<font color=red size=2 >请输入一个最大为{0} 的值</font>"),
		min : jQuery.validator.format("<font color=red size=2 >请输入一个最小为{0} 的值</font>")
	});
	
	// 2.电话号码
	jQuery.validator.addMethod("isTel", function(value, element) {
		var reg = /^\d{11}$/;
		return this.optional(element) || reg.test(value);
	}, "<font color='red' size='2' >请填写正确11位手机号码</font>");
	
	// 3.非中文判定
	jQuery.validator.addMethod("notZH_CN", function(value, element) {
		var reg = /^[\u4e00-\u9fa5],{0,}$/;
		return this.optional(element) || !reg.test(value);
	});
	
	// 4.中文判定
	jQuery.validator.addMethod("isZH_CN", function(value, element) {
		var reg = /^[\u4e00-\u9fa5],{0,}$/;
		return this.optional(element) || reg.test(value);
	}, "<font color='red' size='2' >请填写中文!</font>");
	
	// 5.日期比较 (yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd):
	/*
	 * 在endDateId一端添加规则 调用形式: rules:{ compareDate:"#beginDateId" } 事件主体(endDateId)的日期值与id="beginDateId"的输入框中的日期值对比。
	 * 若endDate较晚(较大)，返回true; 若id='beginDateId'的值(beginDate)较晚(较大)，返回false;
	 */
	// 传入的参数的形式："#"+beginDateId ；即 beginDateId_1 = "#"+beginDateId
	jQuery.validator.addMethod("compareDate", function(value, element, beginDateId_1) {
		var earlyVal = $(beginDateId_1).val();
		var laterVal = value;
		if (earlyVal.length > 0 && laterVal.length > 0) {
			var reg = /-|:+|\s|$/;
			var arr1 = earlyVal.split(reg);
			var arr2 = laterVal.split(reg);
			var startDate = null;
			var endDate = null;
			// startDate
			if (arr1.length == 3) {
				startDate = new Date(arr1[0], arr1[1], arr1[2]);
			} else if (arr1.length == 6) {
				startDate = new Date(arr1[0], arr1[1], arr1[2], arr1[3], arr1[4], arr1[5]);
			}
			// endDate
			if (arr2.length == 3) {
				endDate = new Date(arr2[0], arr2[1], arr2[2]);
			} else if (arr2.length == 6) {
				endDate = new Date(arr2[0], arr2[1], arr2[2], arr2[3], arr2[4], arr2[5]);
			}
			return startDate <= endDate;
		}
		return true;
	});
	
	// 6.小于或等于 ;
	/*
	 * 调用示例：noMoreThan : ["#inweight","进场重量"], 如果<=$("#inweight").val(),return true; 否则，提示请输入一个小于或等于进场重量 的值
	 */
	jQuery.validator.addMethod("noMoreThan", function(value, element, arr) {
		var otherVal = $(arr[0]).val();
		if (value.length > 0 && otherVal.length > 0) {
			return new Number(value) <= new Number(otherVal);
		}
		return true;
	}, $.validator.format("<font color=red size=2 >请输入一个小于或等于“{1}” 的值</font>"));
	
	// 7.大于或等于
	jQuery.validator.addMethod("noLessThan", function(value, element, arr) {
		var otherVal = $(arr[0]).val();
		if (value.length > 0 && otherVal.length > 0) {
			return new Number(value) >= new Number(otherVal);
		}
		return true;
	}, $.validator.format("<font color=red size=2 >请输入一个大于或等于“{1}”的值 </font>"));
	
	// 8.身份证
	jQuery.validator.addMethod("isCardNo", function(value, element) {
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
		var reg = /(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		return reg.test(value);
	}, $.validator.format("<font color=red size=2 >请输入正确的身份证号 </font>"));
});
	// 建议使用onchange事件；示例：onchange="compareDate('beginMeatindate','endMeatindate')"
	function compareDate(earlyDateId, laterDateId) {
		$('#errormsg').remove();
		var earlyVal = $("#" + earlyDateId).val();
		var laterVal = $("#" + laterDateId).val();
		if (earlyVal.length > 0 && laterVal.length > 0) {
			var reg = /-|:+|\s|$/;
			var arr1 = earlyVal.split(reg);
			var arr2 = laterVal.split(reg);
			var startDate = null;
			var endDate = null;
			// startDate
			if (arr1.length == 3) {
				startDate = new Date(arr1[0], arr1[1], arr1[2]);
			} else if (arr1.length == 6) {
				startDate = new Date(arr1[0], arr1[1], arr1[2], arr1[3], arr1[4], arr1[5]);
			}
			// endDate
			if (arr2.length == 3) {
				endDate = new Date(arr2[0], arr2[1], arr2[2]);
			} else if (arr2.length == 6) {
				endDate = new Date(arr2[0], arr2[1], arr2[2], arr2[3], arr2[4], arr2[5]);
			}
			if (startDate > endDate) {
				// alert(laterVal+' 应晚于 '+earlyVal);
				$("#" + laterDateId).val(null);
				// $("#"+laterDateId).focus();
				$("#" + laterDateId).parent().append("<font id='errormsg' color='red' size='2' ><br>" + laterVal + ' 应晚于 ' + earlyVal + "</font>");
			}
		}
	}
