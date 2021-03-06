﻿@inherits AgileEAP.MVC.ViewEngines.Razor.WebViewPage
@{
    Layout = "../Shared/_Kendo.cshtml";
}
@using AgileEAP.MVC
@using AgileEAP.Core
@using AgileEAP.Core.Extensions
@using AgileEAP.Workflow.Enums
@using AgileEAP.Plugin.Workflow.Models
@using Kendo.Mvc.UI
@Html.BuilderToolbar()
@(Html.Kendo().Grid<ProcessInstModel>()
    .Name("Grid")
    .Columns(columns =>
    {
        columns.Bound(o => o.ID).Title("ID").Hidden();
        columns.Bound(o => o.Name).Title("名称");
        columns.Bound(o => o.CurrentState).Title("当前状态").Filterable(false);
        columns.Bound(o => o.ProcessVersion).Title("流程版本");
        columns.Bound(o => o.StartTime).Title("启动时间").Format("{0:yyyy-MM-dd HH:mm:ss}");
        columns.Bound(o => o.EndTime).Title("结束时间").Format("{0:yyyy-MM-dd HH:mm:ss}");
    })
        //.RowAction(row=>row.HtmlAttributes.Add("processInstID",row.DataItem.ID))
        //.ClientRowTemplate(
        //    "<tr processInstID=\"#=ID#\">"+
        //        "<td>#=Name#</td>" +
        //        "<td>#=CurrentState#</td>" +
        //        "<td>#=ProcessVersion#</td>" +
        //         "<td>#=StartTime#</td>" +
        //        "<td>#=EndTime#</td>" +
        //     "</tr>"        
        //   )
    .Selectable(selectable => selectable.Mode(GridSelectionMode.Single))
    .Pageable(o =>
    {
        o.PageSizes(new int[] { 10, 15, 20 }); o.Messages(m =>
        {
            m.ItemsPerPage("项每页");
        });
    }).Sortable().Filterable().DataSource(dataSource => dataSource
        .Ajax()
        .Read(read => read.Action("ProcessInst_Filter", "Monitor")).PageSize(Configure.Get<int>("PageSize", 15)))
        )
<script type="text/javascript">

    function execEngineCommand(cmd) {
        var row = $("#Grid").find(".k-state-selected");
        var processInstID = row.find("td").eq(0).text()
        if (processInstID == null || processInstID == undefined || processInstID == "") {
            alert("请先选择流程实例");
            return;
        }
        var processbar = window.parent.$("#processbar");
        processbar.processbar({ message: '正在处理...' });
        $.post("/Workflow/Engine/" + cmd, { processInstID: processInstID }, function (ajaxResult) {
            processbar.complete();
            if (ajaxResult && ajaxResult.Result == 1) {
                if (cmd == "DeleteProcessInst") {
                    row.remove();
                }
                else {
                    row.find("td").eq(2).text(ajaxResult.RetValue);
                }
            }
            alert(ajaxResult.PromptMsg);
        });
    }

    function proessTracing() {
        var processInstID = $("#Grid").find(".k-state-selected").find("td").eq(0).text();
        if (processInstID == null || processInstID == undefined || processInstID == "") {
            alert("请先选择流程实例");
            return;
        }
        $.post("/Workflow/Engine/GetProcessDefID", { processInstID: processInstID }, function (ajaxResult) {
            if (ajaxResult && ajaxResult.Result == 1) {
                window.parent.openOperateDialog("流程跟踪", "/Workflow/Process/Tracking?ProcessInstID=" + processInstID + "&processDefID=" + ajaxResult.RetValue, 600, 550);
            }
        });
    }
</script>
