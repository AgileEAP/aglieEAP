﻿@inherits AgileEAP.MVC.ViewEngines.Razor.WebViewPage
@{
    Layout = "../Shared/_Kendo.cshtml";
}
@using AgileEAP.MVC
@using AgileEAP.Core
@using AgileEAP.Core.Extensions
@using AgileEAP.Workflow.Enums
@using AgileEAP.Plugin.Workflow.Models
@using AgileEAP.Workflow.Domain
@using Kendo.Mvc.UI

@Html.BuilderToolbar()
@(Html.Kendo().Grid<ProcessDef>()
    .Name("Grid")
    .Columns(columns =>
    {
        columns.Bound(o => o.ID).Title("ID").Hidden();
        columns.Bound(o => o.Text).Title("显示名");
        columns.Bound(o => o.Name).Title("名称");
        columns.Bound(o => o.Version).Title("版本");
    })
    .Selectable(selectable => selectable.Mode(GridSelectionMode.Single))
    .Pageable(o =>
    {
        o.PageSizes(new int[] { 10, 15, 20 }); o.Messages(m =>
        {
            m.ItemsPerPage("项每页");
        });
    }).Sortable().Filterable().DataSource(dataSource => dataSource
        .Ajax()
        .Read(read => read.Action("StartProcess_Filter", "Engine")).PageSize(Configure.Get<int>("PageSize", 15)))
        )
<script type="text/javascript">
    function startProcess() {
        var row = $("#Grid").find(".k-state-selected");
        var processDefID = row.find("td").eq(0).text()
        if (processDefID == null || processDefID == undefined || processDefID == "") {
            alert("请选择要启动的流程");
            return;
        }
        $.post("/Workflow/Engine/StartProcessInst", { processDefID: processDefID }, function (value) {
            if (value) {
                var url = value.StartURL + '?processDefID=' + processDefID + '&processInstID=' + value.ProcessInstID + '&workItemID=' + value.WorkItemID + '&Entry=WaitProcess';
                window.parent.openOperateDialog('流程', url, 950, 600, 1, 0, 50);
            }
            else {
                alert("启动流程失败,请联系管理员");
            }
        });
    }
</script>
