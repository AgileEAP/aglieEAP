<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- <input id="btnSubimt" class="btn btn-primary" onclick="save()" type="button" value="提交"/> -->
<form id="inputForm" action="${ctx}/security/resource/${action}" method="post" class="form-horizontal">
<input type="hidden" id='id' name="id" value="${resource.id}"/>
    <fieldset>
    <div class="row-fluid">
        <div class="span5">        
        <div class="control-group">
    		<label for="name" class="control-label">资源名称:</label>
    		<div class="controls">
    		    <input type="text" id="name" name="name" placeholder="请输入资源名称"  value="${resource.name}" class="required k-textbox"/>
            </div>
    	</div>
    	</div>
        <div class="span5">  
    	<div class="control-group">
    		<label for="parentID" class="control-label">父资源:</label>
    		<div class="controls">
    		    <input type="hidden" id="parentID" name="parentID"  value="${resource.parentID}" class="required" />
    		    <input type="text" id="parentName" name="parentName"  value="${resource.parentName}" placeholder="请选择父资源" style="cursor:pointer;background:#fff url(${ctx}/themes/default/images/chooseBox.png) no-repeat right;background-color:#fff;" class="k-textbox" onclick="openChooseBoxDialog('parentID','parentName','选择资源', '${ctx}/security/resource/tree?action=choose', '450','400')"/>
            </div>
    	</div>
    	</div>
    </div>
    <div class="row-fluid">
        <div class="span5">  
            <div class="control-group">
        		<label for="sortOrder" class="control-label">序号:</label>
        		<div class="controls">
                    <input type="text" id="sortOrder" name="sortOrder"  value="${resource.sortOrder}" placeholder="请输入整数" value="1" min="1" max="100" step="1" class="required"/>
                </div>
        	</div>
    	</div>
    	<div class="span5">  
        <div class="control-group">
    		<label for="icon" class="control-label">图标:</label>
    		<div class="controls">
    		    <input type="text" id="icon" name="icon"  value="${resource.icon}" placeholder="图标地址相对根路径" class="k-textbox" />
            </div>
    	</div>
    	</div>
    </div>
     <div class="row-fluid">
        <div class="span5">  
        <div class="control-group">
    		<label for="url" class="control-label">URL地址:</label>
    		<div class="controls">
    		    <input type="text" id="url" name="url"  value="${resource.url}" placeholder="请输入URL地址" class="k-textbox" />
            </div>
    	</div>
    	</div>
        <div class="span5">   
            <div class="control-group">
        		<label for="entry" class="control-label">入口:</label>
        		<div class="controls">
        		    <input type="text" id="entry" name="entry"  value="${resource.entry}" placeholder="用来区分URL" class="k-textbox" />
                </div>
        	</div>
    	</div>
    </div>
     <div class="row-fluid">
    	<div class="span5">        
            <div class="control-group">
        		<label for="type" class="control-label">资源类型:</label>
        		<div class="controls">
                <select id="type" name="type">
                	<option value="1" selected="selected">菜单</option>
                	<option value="2">页面</option>
                </select>   
                </div>
        	</div>
    	</div>
        <div class="span5">  
        <div class="control-group">
    		<label for="openMode" class="control-label">显示模式:</label>
    		<div class="controls">
                <select id="openMode" name="openMode">
                	<option value="1" selected="selected">导航区</option>
                	<option value="2">主内容区</option>
                </select>                
            </div>
    	</div>
    	</div>
    </div>
     <div class="row-fluid">
        <div class="span5">  
        <div class="control-group">
    		<label for="showNavigation" class="control-label">显示导航:</label>
    		<div class="controls">                
                <select id="showNavigation" name="showNavigation" >
                	<option value="0" selected="selected">否</option>
                	<option value="1">是</option>
                </select>
            </div>
    	</div>
    	</div>
       <div class="span5">  
        <div class="control-group">
    		<label for="showToolBar" class="control-label">显示工具栏:</label>
    		<div class="controls">
                <select id="showToolBar" name="showToolBar">
                	<option value="0" >否</option>
                	<option value="1" selected="selected">是</option>
                </select>
            </div>
    	</div>
    	</div>
    </div>   
<h4>按钮</h4>
<hr style="margin:0px">
    <div class="row-fluid">
       	<div class="span12">
			<table id="tblOperate" class="table table-hover table-condensed">
                <thead>
                    <tr>
                        <th style="width:40px">
                            	序号
                        </th>
                        <th style="width:100px">
                            	名称
                        </th>
                        <th style="width:100px">
                            	命令
                        </th>
                       <th style="width:100px">
                           	 参数
                        </th>
                        <th style="width:50px">
                            	验证
                        </th>
                        <th>
<%--                     	<span id="add" style="width:180px;cursor: pointer;" title='新增' onclick="addOperate()">
						<img src="${ctx }/images/index/right23.png" width="14" height="29px" border="0" />新增
						</span>| --%>
						<span id="choose" style="width:180px;cursor: pointer;" title='选择' onclick="chooseOperates();">
						<img src="${ctx }/themes/default/images/operate/choose.png" width="14" height="29px" border="0" />选择
						</span>
                        </th>
                    </tr>
                </thead>
				<tbody>
				</tbody>
			</table>
		</div>
        </div>
</fieldset>
</form>
<script type="text/javascript">
$(document).ready(function() {
	$("#type").kendoComboBox();
	$("#openMode").kendoComboBox();
	$("#showNavigation").kendoComboBox();
	$("#showToolBar").kendoComboBox();
    $("#sortOrder").kendoNumericTextBox({
        format: "#"
    }); 
	
	//聚焦第一个输入框
	$("#name").focus();
	//为inputForm注册validate函数
	var inputForm=$("#inputForm");
	inputForm.validate({
		rules : {
    				name : {
    					required: true,
    					
    					maxlength: 16    
    				},
    				text : {
    					required: true,
    					
    					maxlength: 16    
    				}
		},
		messages : {
				name : {
                required: "<font color='red' size='2' >资源名称不能为空！</font>",
    				
					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
				}, 
				text : {
                required: "<font color='red' size='2' >资源显示名不能为空！</font>",
    				
					maxlength:jQuery.validator.format("<font color='red' size='2' >请输入一个 长度最多是 {0} 的字符串</font>")   
				}
		},
		errorPlacement: function (error, element) {  
            error.appendTo(element.parent());  
        },  
        submitHandler: function (form) {  
            form.submit();  
        },  
        errorClass: "error",  
        focusCleanup: true, //被验证的元素获得焦点时移除错误信息  
        success: function (label) {  
           // label.html("<span style=\"color:green\"><img alt='合法' src=${ctx}/themes/default/images/correct.png></img></span>").addClass("success");  
        }  
	});
});

function save(){
	var inputForm=$("#inputForm");
	if(!inputForm.valid()) return false;     

    var operateItems = new Array();
    $("#tblOperate").find("tr").each(function (i) {
        if (i > 0) {
            var tds = $(this).find("td");
            tds.each(function (i) {
                var $input = $(this).find("input").eq(0);
                if ($input[0]) {
                    $(this).html($input.attr("type")=="checkbox"?($input.prop("checked")?"是":"否"):$input.val());
                }
            });
            operateItems[i - 1] ={
	            id:this.id,
            	sortOrder:i,
            	name: $.trim(tds.eq(1).text()),
                command:$.trim(tds.eq(2).text()),
                argument:$.trim(tds.eq(3).text()),
                isVerify:$.trim(tds.eq(4).text()) == "是" ? 1 : 0              		
            };
        }
    });

    var resource ={};
    $("#inputForm").find(":input").each(function(){
    	if(this.id&&this.id!="")
    		resource[this.id] = $(this).val();
    });
    
    resource.operates = operateItems;
    var processbar = $("#processbar");
    processbar.processbar({ message: '正在提交...' });
    $.post("${ctx}/security/resource/update", { jsonValue: kendo.stringify(resource) }, function (retValue) {
        processbar.complete();
        if(retValue.id&&$("#id").val()=="")
        {
				var treeview = $("#treeview").data("kendoTreeView");
	            var selectNode= treeview.select();
	            if(selectNode.length>0)
	           	{
	 				treeview.append({
	                    text: $("#name").val(),
	                    id:retValue.id
	                },selectNode);
	           	}

        	$("#id").val(retValue.id);
        }
        alertMessage(retValue.message);
     });
}

function addOperate(operate) {
	operate=operate||{id:"",name:"新增",command:"add",argument:"",isVerify:0};
    var tblOperate = $("#tblOperate");
    var sortOrder = tblOperate.find("tr").length;

    var newRow = "<tr id=\""+operate.id+"\" ondblclick=\"rowDbClick(this,'')\"  style='cursor: hand' action='add'>"
                  + "<td>" + sortOrder + "</td><td>" + operate.name + "</td><td>" + operate.command + "</td><td>" + operate.argument + "</td>" +
                    "<td flag='combox'>" + (operate.isVerify==0?"否":"是") + "</td><td flag='operate'><span title='删除' onclick=\"executeOperate(this,'delete')\"><img src=\"${ctx }/themes/default/images/button/delete.png\" width=\"16px\" height=\"16px\" border=\"0\" />&nbsp;</span> ｜ <span class='btn_up' title='上移' onclick=\"executeOperate(this,'up')\"><img src=\"${ctx }/themes/default/images/button/moveup.png\" width=\"16px\" height=\"16px\" border=\"0\" />&nbsp;</span> ｜ <span class='btn_down' title='下移' onclick=\"executeOperate(this,'down')\"><img src=\"${ctx }/themes/default/images/button/movedown.png\" width=\"16px\" height=\"16px\" border=\"0\" />&nbsp;</span></td></tr>";
    tblOperate.append(newRow);
}

function chooseOperates()
{
	openDialog('${ctx }/security/operate?entry=choose&resourceId='+$("#id").val(), '选择按钮',550,450);	
}

function swapRow(srcRow, destRow) {
    if (srcRow[0] && destRow[0]) {    	
    	var id=destRow.attr("id");
    	destRow.attr("id",srcRow.attr("id"));
    	srcRow.attr("id",id);
        var destCells = destRow.find("td[flag!='operate']");
        srcRow.find("td[flag!='operate']").each(function (i) {
            if (i > 0) {
                var tmp = $.trim($(this).text());
                $(this).text($.trim(destCells.eq(i).text()));
                destCells.eq(i).text(tmp);
            }
        });
    }
}

function executeOperate(me, operate) {
    var currentRow = $(me).parent().parent();
    if (operate == "delete") {
        currentRow.remove();
    } else if (operate == "up") {
        var prev = currentRow.prev();
        swapRow(prev, currentRow);
    } else if (operate == "down") {
        var next = currentRow.next();
        swapRow(next, currentRow);
    }

    return false;
}

function rowDbClick(me, id) {
    if ($(":input", $(me)).eq(0)[0]) return;

    $("td", me).each(function (i) {
        var flag = $(this).attr("flag");
        var value = $.trim(this.innerText);
        if (i > 0 && flag != "operate" && flag != "combox") {
            $(this).html("<input type='text' value='" + value + "' style='width:70px;'/>");
        }
        if (i == 4)//是否验证
        {
        	$(this).html("<input type=\"checkbox\""+ (value == "是" ? "checked" : "") +"/>");            
        }
    });
}
</script>