// jQuery.validate 自定义扩展方法
// 1.修改默认提示
	jQuery.extend(jQuery.validator.messages, {
		required : "<font color=red size=2 >必选字段</font>",
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
		var reg = /^(\(\d{3,4}\)|\d{3,4}-*)?\d{7,8}$/;
		return this.optional(element) || reg.test(value);
	}, $.validator.format("<font color=red size=2 >请输入正确的电话号码</font>"));
	// 3.非中文判定
	jQuery.validator.addMethod("notZH_CN", function(value, element) {
		var reg = /^[\u4e00-\u9fa5],{0,}$/;
		return this.optional(element) || !reg.test(value);
	}, $.validator.format("<font color=red size=2 >请输入非中文字符</font>"));
	// 4.中文判定
	jQuery.validator.addMethod("isZH_CN", function(value, element) {
		var reg = /^[\u4e00-\u9fa5],{0,}$/;
		return this.optional(element) || reg.test(value);
	}, $.validator.format("<font color=red size=2 >请输入中文字符</font>"));
	// 5.日期比较 (yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd): 若调用该函数的对象的日期大于$(id).val()的日期值，返回true;否则，返回false.
	// 调用形式: rules:{ compareDate:"#xId" } ，与id="xId"的输入框中的日期值对比。若事件主体的日期值较晚，返回true;若id='xId'的值较晚，返回false
	jQuery.validator.addMethod("compareDate", function(value, element, id) {
		if ($(id).val().length > 0 && value.length > 0) {
			var reg = /-|:+|\s|$/;
			var arr1 = $(id).val().split(reg);
			var arr2 = value.split(reg);
			var startDate = null;
			var endDate = null;
			if (arr1.length == 3) {
				startDate = new Date(arr1[0], arr1[1], arr1[2]);
			} else if (arr1.length == 6) {
				startDate = new Date(arr1[0], arr1[1], arr1[2], arr1[3], arr1[4], arr1[5]);
			}
			if (arr2.length == 3) {
				startDate = new Date(arr2[0], arr2[1], arr2[2]);
			} else if (arr2.length == 6) {
				startDate = new Date(arr2[0], arr2[1], arr2[2], arr2[3], arr2[4], arr2[5]);
			}
			return startDate <= endDate;
		}
		return true;
	}, $.validator.format("<font color=red size=2 >请确保结束日期晚于开始日期</font>"));
	// 6.小于或等于 ;
	/*
	 * 调用示例：noMoreThan : ["#inweight","进场重量"], 如果<=$("#inweight").val(),return true; 否则，提示: 请输入一个小于或等于“进场重量” 的值
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
	// 9.MAC地址判定
	jQuery.validator.addMethod("isMac", function(value, element) {
		var reg = /^([0-9a-fA-F]{2}[-:]){4}(([0-9a-fA-F]){2})$/;
		return this.optional(element) || reg.test(value);
	}, $.validator.format("<font color=red size=2 >请输入正确的MAC地址</font>"));
