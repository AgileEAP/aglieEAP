
(function (window, undefined) {
    var selectactivity = new Object();
    var ActivityType=
    {
        StartActivity : "StartActivity",
        ManualActivity :  "ManualActivity",
        RouterActivity :  "RouterActivity",
        SubflowActivity :  "SubflowActivity",
        AutoActivity :  "AutoActivity",
        EndActivity :  "EndActivity",
        ProcessActivity: "ProcessActivity"
    }
    var connectionLabel = "";
    var startConnection = 0;
    var CurrentStatus =
        {
            waitPublish: 0,
            Published: 1,
            stopPublish: 2
        };
    var operate = new Array();//记录操作历史
    var temporaryObject = new Array();
    var temporaryDrop = new Array();
    var lineStyle = "Straight";
    var ProcessDefine = function () {
        this.version = "1.0";
        this.name = "processDefine";
        this.author = "agileEAP";
        this.transitions = [];
        this.activities = [];
    };
    var processDefine = new ProcessDefine();
    jsPlumb.importDefaults({
        Connector: ["Straight"],
        ConnectionOverlays: [
					["Arrow", { location: 1, foldback: 0.1, width: 7, direction: -1, length: 5 }],
					["Label", {
					    location: 0.5,
					    id: "label",
					    cssClass: "aLabel",
					    LabelStyle: "z-index: 21"
					}]
        ],
        ConnectorZIndex: 50
    });
    document.oncontextmenu = function (e) { return false };
    var containerment;
    var connectorPaintStyle = {
        lineWidth: 2,
        strokeStyle: "Green",
        joinstyle: "round",
        outlineColor: "white",
        outlineWidth: 2
    };
    var connectorHoverStyle = {
        lineWidth: 7,
        strokeStyle: "#528E21"
    };
    var exampleGreyEndpointOptions = {
        isSource: true,
        isTarget: true,
        DragOptions: { cursor: 'pointer', zIndex: 2000 },
        Endpoints: [["Dot", { radius: 7 }], ["Dot", { radius: 7 }]],
        EndpointStyles: [{ fillStyle: "#528E21" }, { fillStyle: "#528E21" }],
        connector: [lineStyle, { stub: [40, 60], gap: 10 }],
        paintStyle: { fillStyle: "#528E21", radius: 7 },
        connectorStyle: connectorPaintStyle,
        hoverPaintStyle: connectorHoverStyle,
        maxConnections: 5,
        scope: "process",
        connectorHoverStyle: connectorHoverStyle
    };
    Array.prototype.indexOf = function (o) {
        var _self = this;
        for (var i = 0; i < _self.length; i++) {
            if (_self[i] == o) {
                return i;
            }
        }
        return -1;
    }
    function delControl(id, cutElement) {
        if (id) {
            jsPlumb.removeAllEndpoints(id);
            var ActivitiesCount = processDefine.activities.length;
            for (var j = 0; j < ActivitiesCount; j++) {
                if (id == processDefine.activities[j].id) {
                    var position = processDefine.activities.indexOf(processDefine.activities[j]);
                    processDefine.activities[j].style.left = $("#" + id).offset().left;
                    processDefine.activities[j].style.top = $("#" + id).offset().top;
                    processDefine.activities[j].style.width = $("#" + id).width();
                    processDefine.activities[j].style.height = $("#" + id).height();
                    processDefine.activities[j].style.zIndex = 0;
                    if (operate.length < 10) {
                        operate.push({ bindEvent: "activitycontextmenu", bindObject: processDefine.activities[j], bind: "designerActivity", result: "delActivity" });
                    }
                    else {
                        operate.shift();
                        operate.push({ bindEvent: "activitycontextmenu", bindObject: processDefine.activities[j], bind: "designerActivity", result: "delActivity" });
                    }
                    if (cutElement) {
                        selectactivity = processDefine.activities[j];
                    }
                    processDefine.activities.splice(position, 1);
                    ActivitiesCount--;
                    j = -1;

                }
            }
            var connectionCount = processDefine.transitions.length;
            for (var i = 0 ; i < connectionCount; i++) {
                if (processDefine.transitions[i].srcActivity == id || processDefine.transitions[i].destActivity == id) {
                    var conPosition = processDefine.transitions.indexOf(processDefine.transitions[i]);
                    processDefine.transitions.splice(conPosition, 1);
                    connectionCount--;
                    i = -1;
                }
            }
            if (operate.length < 10) {
                operate.push({ bindEvent: "conncontextmenu", bindObject: processDefine.transitions[i], bind: "connection", result: "delconnection" });
            }
            else {
                operate.shift();
                operate.push({ bindEvent: "conncontextmenu", bindObject: processDefine.transitions[i], bind: "connection", result: "delconnection" });
            }
            $("#" + id).remove();
        }
    }
    function cutControl(id) {
        delControl(id, true);
    }
    function copyControl(id) {
        for (var j = 0; j < processDefine.activities.length; j++) {
            if (id == processDefine.activities[j].id) {
                var position = processDefine.activities.indexOf(processDefine.activities[j]);
                processDefine.activities[j].style.left = $("#" + id).offset().left;
                processDefine.activities[j].style.top = $("#" + id).offset().top;
                processDefine.activities[j].style.width = $("#" + id).width();
                processDefine.activities[j].style.height = $("#" + id).height();
                processDefine.activities[j].style.zIndex = 0;
                var newID = id + new Date().getTime();
                for (var ActivitieElement in processDefine.activities[j]) {
                    if (!selectactivity) {
                        selectactivity = new Object();
                    }
                    selectactivity[ActivitieElement] = processDefine.activities[j][ActivitieElement];
                }
                selectactivity.id = newID;
            }
        }
    }
    function initContextMenu() {
        $(".designeractivity").contextMenu({
            menu: 'ulcontrol'
        }, function (action, el, pos) {
            var id = $(el.context).attr("id");
            if (action == "delControl") {
                if (confirm("您确定要删除该活动吗？")) {
                    delControl(id);
                }
                return;
            }
            if (action == "cutControl") {
                cutControl(id);
                return;
            }
            if (action == "copyControl") {
                copyControl(id);
                return;
            }
            if (action == "attrControl") {
                var activityType = $("#" + id).attr("activitytype");
                openTopDialog("actionDialog2", "workflow/designer/activity?ProcessDefID=" + $.query.get("processDefID") + "&activityID=" + id + "&activityType=" + activityType,'活动配置', 850, 580, true);
                return;
            }
            if (action == "formControl") {
              
                var activityType = $("#" + id).attr("activitytype");
                if (activityType == ActivityType.ManualActivity && processDefine.activities) {
                    // window.parent.parent.openTopDialog("actionDialog2", '设计表单', "/FormDesigner/Home/FormDesigner?ProcessDefID=" + $.query.get("processDefID") + "&ActivityID=" + id + "&ActivityType=" + activityType, 850, 580, true);
                    for (var i = 0; i < processDefine.activities.length; i++) {
                        if (id == processDefine.activities[i].id) {
                            processDefine.activities[i].eForm = processDefine.activities[i].eForm ? processDefine.activities[i].eForm : new Date().getTime();

                            openDialog2({ title: "表单设计器", url: "/FormDesigner/Home/FormDesigner?eFormID=" + processDefine.activities[i].eForm, dialogType: 1, showModal: true, height: screen.height - 80, width: screen.width - 10, windowStyle: { style: "dialogLeft:0;dialogTop:-100;edge: Raised; center: Yes; resizable: Yes; status: no;scrollbars=no;", auguments: window } });
                        }
                    }
                }
                return;
            }
            
        });
    }
    function initDocumentContextMunu() {
        $(document).bind("contextmenu", function () {
            var e = window.event ? window.event : arguments[0];
            var x; var y;
            var o = { menu: "uldocument", inSpeed: 150, outSpeed: 75, position: document };
            var x = (e.clientX + $(document).scrollLeft()) || e.offsetX;
            var y = (e.clientY + $(document).scrollTop()) || e.offsetY;
            $('#' + o.menu).addClass('contextMenu');
            var menu = $('#' + o.menu, o.position);
            $(menu).css({ top: y, left: x }).fadeIn(o.inSpeed);
            $(menu).find('A').mouseover(function () {
                $(menu).find('LI.hover').removeClass('hover');
                $(this).parent().addClass('hover');
            }).mouseout(function () {
                $(menu).find('LI.hover').removeClass('hover');
            });
            $('#' + o.menu, o.position).find('A').unbind('click');
            $('#' + o.menu, o.position).find('LI:not(.disabled) A').click(function () {
                $(document).unbind('click').unbind('keypress');
                $(".contextMenu", o.position).hide();
                var action = $(this).attr('href').substr(1);
                switch (action) {
                    case "pasteControl":
                        if (selectactivity && selectactivity.id) {
                            var id = selectactivity.id;
                            var name = selectactivity.name;
                            var activityType = selectactivity.activityType;
                            var left = x; //selectactivity.style.left;
                            var top = y; //selectactivity.style.top;
                            var img = "/Plugins/WorkflowDesigner/Content/Themes/Default/images/" + selectactivity.activityType + ".png";
                            var activityResource = "<div id=\"" + id + "\"  class=\"designeractivity\" name=\"" + name + "\" ActivityType=\"" + activityType + "\" style=\"left:" + left + "px;top:" + top + "px;width:40px;height:40px;position:absolute\"><img style=\"width:40px;height:40px;\" src=\"" + img + "\" /><label style=\"width:100px;position:absolute;Top:40px;left:-30px\">" + name + "</label></div>";
                            $("#" + containerment).append(activityResource);
                            $("#" + id).bind("mouseover", function () { jsPlumb.show(id, $("#" + id)); });
                            $("#" + id).bind("dblclick", function () { openTopDialog("actionDialog2", "workflow/designer/activity?processDefID=" + processDefine.id + "&activityID=" + id + "&activityType=" + activityType,'活动配置', 850, 580, true);});
                            initContextMenu();
                            var activity = selectactivity;
                            processDefine.activities.push(activity);
                            var exampleGreyEndpointOptions = {
                                isSource: true,
                                isTarget: true,
                                DragOptions: { cursor: 'pointer', zIndex: 2000 },
                                Endpoints: [["Dot", { radius: 7 }], ["Dot", { radius: 7 }]],
                                EndpointStyles: [{ fillStyle: "#528E21" }, { fillStyle: "#528E21" }],
                                connector: [lineStyle, { stub: [40, 60], gap: 10 }],
                                paintStyle: { fillStyle: "#528E21", radius: 7 },
                                connectorStyle: connectorPaintStyle,
                                hoverPaintStyle: connectorHoverStyle,
                                maxConnections: 5,
                                scope: "process",
                                connectorHoverStyle: connectorHoverStyle
                            };
                            jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "BottomCenter" });
                            jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "TopCenter" });
                            jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "LeftMiddle" });
                            jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "RightMiddle" });
                            jsPlumb.draggable(jsPlumb.getSelector($("#" + id)),
                       {
                           start: function (event, ui) {
                               console.log("开始拖动");
                               if (operate.length < 10) {
                                   operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                               }
                               else {
                                   operate.shift();
                                   operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                               }
                           },
                           containment: $("#" + containerment)
                       });
                            $(".designeractivity").each(function () {
                                jsPlumb.hide(this.id, true);
                            });
                            $(".designeractivity").each(function () {
                                jsPlumb.show(this.id);
                            });
                            if (operate.length < 10) {
                                operate.push({ bindEvent: "addActivity", bindObject: activity, bind: "designerActivity", result: "addActivity" });
                            }
                            else {
                                operate.shift();
                                operate.push({ bindEvent: "addActivity", bindObject: activity, bind: "designerActivity", result: "addActivity" });
                            }
                            selectactivity = null;
                        }
                        break;
                }
                return false;
            });
            setTimeout(function () { // Delay for Mozilla
                $(document).click(function () {
                    $(menu).fadeOut(o.outSpeed);
                    return false;
                });
            }, 0);
        });
    }
    function initUI() {
        $("#tabs").tabs();
        // fix the classes
        $(".tabs-bottom .ui-tabs-nav, .tabs-bottom .ui-tabs-nav > *")
                .removeClass("ui-corner-all ui-corner-top")
                .addClass("ui-corner-bottom");
        $(".tabs-bottom .ui-tabs-nav").appendTo(".tabs-bottom");
    }
    //初始化鼠标事件
    function init() {
        $("#main_designercontent").bind("click", function () {
            // $("#contexmenu").hide();
            $(".designeractivity").each(function () {
                jsPlumb.hide(this.id, true);
            });
            $(".designeractivity").each(function () {
                jsPlumb.show(this.id);
                e = window.event || argument[0];
                if (!e.ctrlKey) {
                    this.selectedActivity = false;
                }
                if ($(this).attr("standardactivity")) {
                    $(this).removeAttr("standardactivity");
                }
                if ($(this).attr("selectedactivity")) {
                    $(this).removeAttr("selectedactivity");
                }
            });
        });
        $("#sel_zoomscale").change(function () {
            var zoomscale = $("#sel_zoomscale").val();
            zoomscale = zoomscale.substring(0, zoomscale.indexOf("%")) / 100;
            $(".designeractivity").each(function () {
                $(this).css("width", $(this).width() * zoomscale + "px");
                $(this).css("height", $(this).height() * zoomscale + "px");
                $(this).find("img").css("width", $(this).find("img").width() * zoomscale + "px");
                $(this).find("img").css("height", $(this).find("img").height() * zoomscale + "px");
                $(this).find("label").css("top", $(this).find("img").height() + "px");
                jsPlumb.repaintEverything();
            });
        });
        //删除活动
        //$("#contexmenu_del").click(function () {
        //    if (selectactivity != null) {
        //        jsPlumb.removeAllEndpoints(selectactivity.id);
        //        var ActivitiesCount = processDefine.activities.length;
        //        for (var j = 0; j < ActivitiesCount; j++) {
        //            if (selectactivity.id == processDefine.activities[j].id) {
        //                var position = processDefine.activities.indexOf(processDefine.activities[j]);
        //                processDefine.activities[j].style.left = $("#" + selectactivity.id).offset().left;
        //                processDefine.activities[j].style.top = $("#" + selectactivity.id).offset().top;
        //                processDefine.activities[j].style.width = $("#" + selectactivity.id).width();
        //                processDefine.activities[j].style.height = $("#" + selectactivity.id).height();
        //                processDefine.activities[j].style.zIndex = 0;
        //                if (operate.length < 10) {
        //                    operate.push({ bindEvent: "activitycontextmenu", bindObject: processDefine.activities[j], bind: "designerActivity", result: "delActivity" });
        //                }
        //                else {
        //                    operate.shift();
        //                    operate.push({ bindEvent: "activitycontextmenu", bindObject: processDefine.activities[j], bind: "designerActivity", result: "delActivity" });
        //                }
        //                processDefine.activities.splice(position, 1);
        //                ActivitiesCount--;
        //                j = -1;

        //            }
        //        }
        //        var connectionCount = processDefine.transitions.length;
        //        for (var i = 0 ; i < connectionCount; i++) {
        //            if (processDefine.transitions[i].srcActivity == selectactivity.id || processDefine.transitions[i].destActivity == selectactivity.id) {
        //                var conPosition = processDefine.transitions.indexOf(processDefine.transitions[i]);
        //                processDefine.transitions.splice(conPosition, 1);
        //                connectionCount--;
        //                i = -1;
        //            }
        //        }
        //        $(selectactivity).remove();
        //    }

        //});

        $(".menuedit").mouseleave(function () {
            $(this).hide();
        });
        $("#linestyle").mouseleave(function () {
            $("#linestyle").hide();
        });
        $(".line").click(function () {
            switch (this.id) {
                case "linestyle_straight": lineStyle = "Straight"; break;
                case "linestyle_flowchart": lineStyle = "Flowchart"; break;
                case "linestyle_bezier": lineStyle = "Bezier"; break;
            };
            $("#linestyle").hide();
        });
        $("#toolboxshow").click(function () {
            if ($("#toolbox_content").css("display") == "block") {
                $("#toolbox_content").hide();
                $("#toolboxshow").attr("src", "../../Plugins/WorkflowDesigner/Content/Themes/Default/images/hide.png");
            }
            else {
                $("#toolbox_content").show();
                $("#toolboxshow").attr("src", "../../Plugins/WorkflowDesigner/Content/Themes/Default/images/Show.jpg");

            }

        });

    }
    function changePosition(currentObject) {
        for (var i = 0; i < currentObject.length; i++) {
            var left = currentObject[i].left;
            var top = currentObject[i].top;
            currentObject[i].left = $("#" + currentObject[i].id).css("left");
            currentObject[i].top = $("#" + currentObject[i].id).css("top");
            $("#" + currentObject[i].id).css("left", left);
            $("#" + currentObject[i].id).css("top", top);
        }
    }
    //前进一步
    function redo() {
        if (temporaryObject.length > 0) {
            var currentOperate = temporaryObject.pop();
            var currentObject = currentOperate.bindObject;
            switch (currentOperate.bindEvent) {
                case "drag":
                    if (currentObject) {
                        $("#" + currentObject.helper[0].id).css("left", currentObject.position.left);
                        $("#" + currentObject.helper[0].id).css("top", currentObject.position.top);
                        console.log("前进到回退前，回到原来位置" + currentOperate.result);
                        operate.push({ bindEvent: "drag", bindObject: temporaryDrop.shift(), bind: currentOperate.bind, result: currentOperate.result });
                        temporaryDrop.push(currentObject);
                    } break;
                case "addActivity":
                    var id = currentObject.id;
                    var name = currentObject.name;
                    var activityType = currentObject.activityType;
                    var left = currentObject.style.left;
                    var top = currentObject.style.top;
                    var img = "/Plugins/WorkflowDesigner/Content/Themes/Default/images/" + currentObject.activityType + ".png";
                    var activityResource = "<div id=\"" + id + "\"  class=\"designeractivity\" name=\"" + name + "\" ActivityType=\"" + activityType + "\" style=\"left:" + left + "px;top:" + top + "px;width:40px;height:40px;position:absolute\"><img style=\"width:40px;height:40px;\" src=\"" + img + "\" /><label style=\"width:100px;position:absolute;Top:40px;left:-30px\">" + name + "</label></div>"
                    $("#" + containerment).append(activityResource);
                    $("#" + id).bind("mouseover", function () { jsPlumb.show(id, $("#" + id)); });
                    $("#" + id).bind("dblclick", function () { openTopDialog("actionDialog2", "workflow/designer/activity?processDefID=" + processDefine.id + "&activityID=" + id + "&activityType=" + activityType,'活动配置', 850, 580, true); });
                    initContextMenu();
                    var activity = currentObject;
                    processDefine.activities.push(activity);
                    var exampleGreyEndpointOptions = {
                        isSource: true,
                        isTarget: true,
                        DragOptions: { cursor: 'pointer', zIndex: 2000 },
                        Endpoints: [["Dot", { radius: 7 }], ["Dot", { radius: 7 }]],
                        EndpointStyles: [{ fillStyle: "#528E21" }, { fillStyle: "#528E21" }],
                        connector: [lineStyle, { stub: [40, 60], gap: 10 }],
                        paintStyle: { fillStyle: "#528E21", radius: 7 },
                        connectorStyle: connectorPaintStyle,
                        hoverPaintStyle: connectorHoverStyle,
                        maxConnections: 5,
                        scope: "process",
                        connectorHoverStyle: connectorHoverStyle
                    };
                    jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "BottomCenter" });
                    jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "TopCenter" });
                    jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "LeftMiddle" });
                    jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "RightMiddle" });
                    jsPlumb.draggable(jsPlumb.getSelector($("#" + id)),
                       {
                           start: function (event, ui) {
                               console.log("开始拖动");
                               if (operate.length < 10) {
                                   operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                               }
                               else {
                                   operate.shift();
                                   operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                               }
                           },
                           containment: $("#" + containerment)
                       });
                    $(".designeractivity").each(function () {
                        jsPlumb.hide(this.id, true);
                    });
                    $(".designeractivity").each(function () {
                        jsPlumb.show(this.id);
                    });
                    operate.push(currentOperate);
                    break;
                case "activitycontextmenu":
                    var id = currentObject.id;
                    jsPlumb.removeAllEndpoints(id);
                    var ActivitiesCount = processDefine.activities.length;
                    for (var j = 0; j < ActivitiesCount; j++) {
                        if (id == processDefine.activities[j].id) {
                            var position = processDefine.activities.indexOf(processDefine.activities[j]);
                            processDefine.activities[j].style.left = $("#" + id).offset().left;
                            processDefine.activities[j].style.top = $("#" + id).offset().top;
                            processDefine.activities[j].style.width = $("#" + id).width();
                            processDefine.activities[j].style.height = $("#" + id).height();
                            processDefine.activities[j].style.zIndex = 0;
                            currentOperate = { bindEvent: "activitycontextmenu", bindObject: processDefine.activities[j], bind: currentOperate.bind, result: currentOperate.result };
                            processDefine.activities.splice(position, 1);
                            ActivitiesCount--;
                            j = -1;
                        }
                    }
                    var connectionCount = processDefine.transitions.length;
                    for (var i = 0 ; i < connectionCount; i++) {
                        if (processDefine.transitions[i].srcActivity == id || processDefine.transitions[i].destActivity == id) {
                            var conPosition = processDefine.transitions.indexOf(processDefine.transitions[i]);
                            processDefine.transitions.splice(conPosition, 1);
                            connectionCount--;
                            i = -1;
                        }
                    }
                    $("#" + id).remove();
                    console.log("回退到加入活动前，删除活动");
                    operate.push(currentOperate);
                    break;
                case "connection":
                    var anchors;
                    startConnection = 3;
                    if (currentObject.sourceEndpoint) {
                        if (currentObject.sourceEndpoint.anchor.type != "Perimeter") {
                            anchors = [currentObject.sourceEndpoint.anchor.type, currentObject.targetEndpoint.anchor.type];
                        }
                        else {
                            anchors = [["Perimeter", { shape: "rectangle" }], ["Perimeter", { shape: "rectangle" }]];
                        }
                    }
                    else {
                        anchors = [currentObject.sourceOrientation, currentObject.sinkOrientation];
                    }
                    jsPlumb.show(currentObject.srcActivity, $("#" + currentObject.srcActivity));
                    jsPlumb.show(currentObject.destActivity, $("#" + currentObject.destActivity));
                    jsPlumb.connect({
                        paintStyle: { fillStyle: "Green", strokeStyle: "Green", lineWidth: 2, radius: 7 },
                        source: currentObject.srcActivity,
                        target: currentObject.destActivity,
                        anchors: anchors,
                        connector: lineStyle,
                        hoverPaintStyle: connectorHoverStyle,
                        connectorHoverStyle: connectorHoverStyle,
                        endpoints: [["Dot", { radius: 7 }], ["Dot", { radius: 7 }]],
                        scope: "process",
                        endpointStyles: [{ fillStyle: "#528E21" }, { fillStyle: "#528E21" }]
                    });
                    processDefine.transitions.push(currentObject);
                    console.log("前进到删除连接前，重连对象");

                    break;
                case "conncontextmenu":
                    for (var i = 0; i < processDefine.transitions.length; i++) {
                        var sourceAnchor = processDefine.transitions[i].sourceOrientation;
                        var sinkAnchor = processDefine.transitions[i].sinkOrientation;
                        if (sourceAnchor == currentObject.sourceEndpoint.anchor.type && sinkAnchor == currentObject.targetEndpoint.anchor.type && processDefine.transitions[i].srcActivity == currentObject.sourceId && processDefine.transitions[i].destActivity == currentObject.targetId) {
                            operate.push({ bindEvent: "conncontextmenu", bindObject: processDefine.transitions[i], bind: "connection", result: "connection" });
                            processDefine.transitions.splice(i, 1);
                        }
                    }
                    jsPlumb.detach(currentObject);
                    console.log("前进到连接前，删除连接");
                    break;
                case "zoomenlarge": zoomenlarge(); operate.push(currentOperate); break;
                case "zoomdecrease": zoomdecrease(); operate.push(currentOperate); break;
                case "leftActivity":
                    changePosition(currentObject);
                    operate.push(currentOperate);
                    break;
                case "centerActivity":
                    changePosition(currentObject);
                    operate.push(currentOperate);
                    break;
                case "rightActivity":
                    changePosition(currentObject);
                    operate.push(currentOperate);
                    break;
            }
            startConnection = 1;
            jsPlumb.repaintEverything();
        }
    }

    //回退一步
    function undo() {
        if (operate.length > 0) {
            var currentOperate = operate.pop();
            var currentObject = currentOperate.bindObject;
            switch (currentOperate.bindEvent) {
                case "drag":
                    if (currentObject) {
                        $("#" + currentObject.helper[0].id).css("left", currentObject.position.left);
                        $("#" + currentObject.helper[0].id).css("top", currentObject.position.top);
                        jsPlumb.repaintEverything();
                        console.log("回退到拖拽前，回到原来位置" + currentOperate.result);
                        temporaryObject.push({ bindEvent: "drag", bindObject: temporaryDrop.pop(), bind: currentOperate.bind, result: currentOperate.result });
                        temporaryDrop.unshift(currentObject);
                    } break;
                case "addActivity":
                    var id = currentObject.id;
                    jsPlumb.removeAllEndpoints(id);
                   // jsPlumb.detachAllConnections(id);
                    var ActivitiesCount = processDefine.activities.length;
                    for (var j = 0; j < ActivitiesCount; j++) {
                        if (id == processDefine.activities[j].id) {
                            var position = processDefine.activities.indexOf(processDefine.activities[j]);
                            processDefine.activities[j].style.left = $("#" + id).offset().left;
                            processDefine.activities[j].style.top = $("#" + id).offset().top;
                            processDefine.activities[j].style.width = $("#" + id).width();
                            processDefine.activities[j].style.height = $("#" + id).height();
                            processDefine.activities[j].style.zIndex = 0;
                            currentOperate = { bindEvent: "addActivity", bindObject: processDefine.activities[j], bind: currentOperate.bind, result: currentOperate.result };
                            processDefine.activities.splice(position, 1);
                            ActivitiesCount--;
                            j = -1;
                        }
                    }
                    var connectionCount = processDefine.transitions.length;
                    for (var i = 0 ; i < connectionCount; i++) {
                        if (processDefine.transitions[i].srcActivity == id || processDefine.transitions[i].destActivity == id) {
                            var conPosition = processDefine.transitions.indexOf(processDefine.transitions[i]);
                            processDefine.transitions.splice(conPosition, 1);
                            connectionCount--;
                            i = -1;
                        }
                    }
                    $("#" + id).remove();
                    console.log("回退到加入活动前，删除活动");
                    temporaryObject.push(currentOperate);
                    break;
                case "activitycontextmenu":
                    var id = currentObject.id;
                    var name = currentObject.name;
                    var activityType = currentObject.activityType;
                    var left = currentObject.style.left;
                    var top = currentObject.style.top;
                    var img = "/Plugins/WorkflowDesigner/Content/Themes/Default/images/" + currentObject.activityType + ".png";
                    var activityResource = "<div id=\"" + id + "\"  class=\"designeractivity\" name=\"" + name + "\" ActivityType=\"" + activityType + "\" style=\"left:" + left + "px;top:" + top + "px;width:40px;height:40px;position:absolute\"><img style=\"width:40px;height:40px;\" src=\"" + img + "\" /><label style=\"width:100px;position:absolute;Top:40px;left:-30px\">" + name + "</label></div>"
                    $("#" + containerment).append(activityResource);
                    $("#" + id).bind("mouseover", function () { jsPlumb.show(id, $("#" + id)); });
                    $("#" + id).bind("dblclick", function () { openTopDialog("actionDialog2", "workflow/designer/activity?processDefID=" + processDefine.id + "&activityID=" + id + "&activityType=" + activityType,'活动配置', 850, 580, true);});
                    //$("#" + id).bind("contextmenu", function () {
                    //    selectactivity = $("#" + id)[0];
                    //    e = window.event || arguments[0];
                    //    var pointx = (e.clientX + $(document).find("#" + containerment).scrollLeft()) || e.offsetX;
                    //    var pointy = (e.clientY + $(document).find("#" + containerment).scrollTop()) || e.offsetY;
                    //    $("#contexmenu").css("left", pointx);
                    //    $("#contexmenu").css("top", pointy);
                    //    $("#contexmenu").show();
                    //});
                    initContextMenu();
                    var activity = currentObject;
                    processDefine.activities.push(activity);
                    var exampleGreyEndpointOptions = {
                        isSource: true,
                        isTarget: true,
                        DragOptions: { cursor: 'pointer', zIndex: 2000 },
                        Endpoints: [["Dot", { radius: 7 }], ["Dot", { radius: 7 }]],
                        EndpointStyles: [{ fillStyle: "#528E21" }, { fillStyle: "#528E21" }],
                        connector: [lineStyle, { stub: [40, 60], gap: 10 }],
                        paintStyle: { fillStyle: "#528E21", radius: 7 },
                        connectorStyle: connectorPaintStyle,
                        hoverPaintStyle: connectorHoverStyle,
                        maxConnections: 5,
                        scope: "process",
                        connectorHoverStyle: connectorHoverStyle
                    };
                    jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "BottomCenter" });
                    jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "TopCenter" });
                    jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "LeftMiddle" });
                    jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "RightMiddle" });
                    jsPlumb.draggable(jsPlumb.getSelector($("#" + id)),
                       {
                           start: function (event, ui) {
                               console.log("开始拖动");
                               if (operate.length < 10) {
                                   operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                               }
                               else {
                                   operate.shift();
                                   operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                               }
                           },
                           containment: $("#" + containerment)
                       });
                    $(".designeractivity").each(function () {
                        jsPlumb.hide(this.id, true);
                    });
                    $(".designeractivity").each(function () {
                        jsPlumb.show(this.id);
                    });
                    temporaryObject.push(currentOperate);
                    break;
                case "connection":
                    for (var i = 0; i < processDefine.transitions.length; i++) {
                        var sourceAnchor = processDefine.transitions[i].sourceOrientation;
                        var sinkAnchor = processDefine.transitions[i].sinkOrientation;
                        if (sourceAnchor == currentObject.sourceEndpoint.anchor.type && sinkAnchor == currentObject.targetEndpoint.anchor.type && processDefine.transitions[i].srcActivity == currentObject.sourceId && processDefine.transitions[i].destActivity == currentObject.targetId) {
                            temporaryObject.push({ bindEvent: "connection", bindObject: processDefine.transitions[i], bind: "connection", result: "lineConnection" });
                            processDefine.transitions.splice(i, 1);
                        }
                    }
                    //for (var i = 0; i < processDefine.transitions.length; i++) {
                    //    var sourceAnchor = processDefine.transitions[i].sourceOrientation;
                    //    var sinkAnchor = processDefine.transitions[i].sinkOrientation;
                    //    if (sourceAnchor == currentObject.sourceOrientation && sinkAnchor == currentObject.sinkOrientation && processDefine.transitions[i].srcActivity == currentObject.srcActivity && processDefine.transitions[i].destActivity == currentObject.destActivity) {
                    //        processDefine.transitions.splice(i, 1);
                    //    }
                    //}
                    jsPlumb.detach(currentObject);
                    console.log("回退到连接前，删除连接");
                    //temporaryObject.push(currentOperate);
                    break;
                case "conncontextmenu":
                    startConnection = 2;
                    var anchors;
                    if (currentObject.endpoints) {
                        if (currentObject.endpoints[0].anchor.type != "Perimeter") {
                            anchors = [currentObject.endpoints[0].anchor.type, currentObject.endpoints[1].anchor.type];
                        }
                        else {
                            anchors = [["Perimeter", { shape: "rectangle" }], ["Perimeter", { shape: "rectangle" }]];
                        }
                    }
                    else if (currentObject.sourceEndpoint) {
                        if (currentObject.sourceEndpoint.anchor.type != "Perimeter") {
                            anchors = [currentObject.sourceEndpoint.anchor.type, currentObject.targetEndpoint.anchor.type];
                        }
                        else {
                            anchors = [["Perimeter", { shape: "rectangle" }], ["Perimeter", { shape: "rectangle" }]];
                        }
                    }
                    else {
                        anchors = [currentObject.sourceOrientation, currentObject.sinkOrientation];
                    }

                    jsPlumb.show(currentObject.srcActivity, $("#" + currentObject.srcActivity));
                    jsPlumb.show(currentObject.destActivity, $("#" + currentObject.destActivity));
                    jsPlumb.connect({
                        paintStyle: { fillStyle: "Green", strokeStyle: "Green", lineWidth: 2, radius: 7 },
                        source: currentObject.srcActivity,
                        target: currentObject.destActivity,
                        anchors: anchors,
                        connector: lineStyle,
                        hoverPaintStyle: connectorHoverStyle,
                        connectorHoverStyle: connectorHoverStyle,
                        endpoints: [["Dot", { radius: 7 }], ["Dot", { radius: 7 }]],
                        scope: "process",
                        endpointStyles: [{ fillStyle: "#528E21" }, { fillStyle: "#528E21" }]
                    });
                    processDefine.transitions.push(currentObject);
                    //temporaryObject.push(currentOperate);
                    console.log("回退到删除活动前，重连对象");
                    break;
                case "zoomenlarge":
                    zoomdecrease();
                    temporaryObject.push(currentOperate);
                    break;
                case "zoomdecrease":
                    zoomenlarge();
                    temporaryObject.push(currentOperate);
                    break;
                case "leftActivity":
                    changePosition(currentObject);
                    temporaryObject.push(currentOperate);
                    break;
                case "centerActivity":
                    changePosition(currentObject);
                    temporaryObject.push(currentOperate);
                    break;
                case "rightActivity":
                    changePosition(currentObject);
                    temporaryObject.push(currentOperate);
                    break;

            }
            startConnection = 1;
            jsPlumb.repaintEverything();
        }
    }
    function zoomdecrease() {
        $(".designeractivity").each(function () {
            if ($(this).width() > 20) {
                $(this).css("width", $(this).width() * 0.5 + "px");
                $(this).css("height", $(this).height() * 0.5 + "px");
                $(this).find("img").css("width", $(this).find("img").width() * 0.5 + "px");
                $(this).find("img").css("height", $(this).find("img").height() * 0.5 + "px");
                $(this).find("label").css("top", $(this).find("img").height() + "px");

            }
        });
    }
    function zoomenlarge() {
        $(".designeractivity").each(function () {
            if ($(this).width() < 200) {
                $(this).css("width", $(this).width() * 2 + "px");
                $(this).css("height", $(this).height() * 2 + "px");
                $(this).find("img").css("width", $(this).find("img").width() * 2 + "px");
                $(this).find("img").css("height", $(this).find("img").height() * 2 + "px");
                $(this).find("label").css("top", $(this).find("img").height() + "px");

            }
        });
    }
    //初始化工具栏
    function initToolbar() {
        $(".toolbar").click(function () {
            switch (this.id) {
                case "header_toolbar_new":
                    if (confirm("是否保存该流程图")) {
                        saveProcessDefine();
                    }
                    processDefine.id = null;
                    jsPlumb.reset();
                    $(".designeractivity").each(function () {
                        $(this).remove();
                    });
                    break;
                case "header_toolbar_open": break;
                case "header_toolbar_save":
                    saveProcessDefine();
                    break;
                case "header_toolbar_back": undo(); break;
                case "header_toolbar_redo": redo(); break;
                case "header_toolbar_zoomenlarge":
                    $(".designeractivity").each(function () {
                        if ($(this).width() < 200) {
                            $(this).css("width", $(this).width() * 2 + "px");
                            $(this).css("height", $(this).height() * 2 + "px");
                            $(this).find("img").css("width", $(this).find("img").width() * 2 + "px");
                            $(this).find("img").css("height", $(this).find("img").height() * 2 + "px");
                            $(this).find("label").css("top", $(this).find("img").height() + "px");

                        }
                    });
                    if (operate.length < 10) {
                        operate.push({ bindEvent: "zoomenlarge", bindObject: null, bind: "point", result: "editPoint" });
                    }
                    else {
                        operate.shift();
                        operate.push({ bindEvent: "zoomenlarge", bindObject: null, bind: "point", result: "editPoint" });
                    }
                    jsPlumb.repaintEverything();
                    break;
                case "header_toolbar_zoomdecrease":
                    $(".designeractivity").each(function () {
                        if ($(this).width() > 20) {
                            $(this).css("width", $(this).width() * 0.5 + "px");
                            $(this).css("height", $(this).height() * 0.5 + "px");
                            $(this).find("img").css("width", $(this).find("img").width() * 0.5 + "px");
                            $(this).find("img").css("height", $(this).find("img").height() * 0.5 + "px");
                            $(this).find("label").css("top", $(this).find("img").height() + "px");

                        }
                    });
                    if (operate.length < 10) {
                        operate.push({ bindEvent: "zoomdecrease", bindObject: null, bind: "point", result: "editPoint" });
                    }
                    else {
                        operate.shift();
                        operate.push({ bindEvent: "zoomdecrease", bindObject: null, bind: "point", result: "editPoint" });
                    }
                    jsPlumb.repaintEverything();
                    break;
                case "header_toolbar_drawLine":
                    e = window.event || arguments[0];
                    var pointx = (e.clientX + $(document).find("#designer_content").scrollLeft()) || e.offsetX;
                    var pointy = (e.clientY + $(document).find("#designer_content").scrollTop()) || e.offsetY;
                    $("#linestyle").css("left", pointx);
                    $("#linestyle").css("top", pointy);
                    $("#linestyle").show();
                    break;
                case "header_toolbar_left":
                    var field = new Object();
                    var selectItems = new Array();
                    $(".designeractivity").each(function () {
                        if ($(this).attr("standardActivity")) {
                            field.standard = this;
                            var left = $(this).css("left");
                            var top = $(this).css("top");
                            var width = $(this).width();
                            field.left = parseInt(left.substring(0, left.lastIndexOf("px")));
                            field.top = parseInt(top.substring(0, top.lastIndexOf("px")));
                            field.width = width;
                        }
                        else if ($(this).attr("selectedActivity")) {
                            selectItems.push({ ID: $(this).attr("id"), left: $(this).css("left"), top: $(this).css("top") });
                        }
                    });
                    if (field.standard) {
                        for (var i = 0; i < selectItems.length; i++) {
                            var left = field.left + "px";
                            $("#" + selectItems[i].id).css("left", left);
                        }
                        jsPlumb.repaintEverything();
                        if (operate.length < 10) {
                            operate.push({ bindEvent: "leftActivity", bindObject: selectItems, bind: "activity", result: "leftActivity" });
                        }
                        else {
                            operate.shift();
                            operate.push({ bindEvent: "leftActivity", bindObject: selectItems, bind: "activity", result: "leftActivity" });
                        }
                    }
                    else {
                        $(".designeractivity").each(function () {
                            if ($(this).attr("standardactivity")) {
                                $(this).removeAttr("standardactivity");
                            }
                            if ($(this).attr("selectedactivity")) {
                                $(this).removeAttr("selectedactivity");
                            }
                        });
                    }
                    break;
                case "header_toolbar_center":
                    var field = new Object();
                    var selectItems = new Array();
                    $(".designeractivity").each(function () {
                        if ($(this).attr("standardActivity")) {
                            field.standard = this;
                            var left = $(this).css("left");
                            var top = $(this).css("top");
                            var width = $(this).width();
                            field.left = parseInt(left.substring(0, left.lastIndexOf("px")));
                            field.top = parseInt(top.substring(0, top.lastIndexOf("px")));
                            field.width = width
                        }
                        else if ($(this).attr("selectedActivity")) {
                            // selectItems.push(this);
                            selectItems.push({ ID: $(this).attr("id"), left: $(this).css("left"), top: $(this).css("top") });
                        }
                    });
                    if (field.standard) {
                        for (var i = 0; i < selectItems.length; i++) {
                            var left = field.left + field.width / 2 - $("#" + selectItems[i].id).width() / 2 + "px";
                            $("#" + selectItems[i].id).css("left", left);
                        }
                        jsPlumb.repaintEverything();
                        if (operate.length < 10) {
                            operate.push({ bindEvent: "centerActivity", bindObject: selectItems, bind: "activity", result: "centerActivity" });
                        }
                        else {
                            operate.shift();
                            operate.push({ bindEvent: "centerActivity", bindObject: selectItems, bind: "activity", result: "centerActivity" });
                        }
                    }
                    else {
                        $(".designeractivity").each(function () {
                            if ($(this).attr("standardactivity")) {
                                $(this).removeAttr("standardactivity");
                            }
                            if ($(this).attr("selectedactivity")) {
                                $(this).removeAttr("selectedactivity");
                            }
                        });
                    }
                    break;
                case "header_toolbar_right":
                    var field = new Object();
                    var selectItems = new Array();
                    $(".designeractivity").each(function () {
                        if ($(this).attr("standardActivity")) {
                            field.standard = this;
                            var left = $(this).css("left");
                            var top = $(this).css("top");
                            var width = $(this).width();
                            field.left = parseInt(left.substring(0, left.lastIndexOf("px")));
                            field.top = parseInt(top.substring(0, top.lastIndexOf("px")));
                            field.width = width
                        }
                        else if ($(this).attr("selectedActivity")) {
                            //selectItems.push(this);
                            selectItems.push({ ID: $(this).attr("id"), left: $(this).css("left"), top: $(this).css("top") });
                        }
                    });
                    if (field.standard) {
                        for (var i = 0; i < selectItems.length; i++) {
                            var standardleft = field.left + field.width;
                            var width = $("#" + selectItems[i].id).width();
                            var left = standardleft - width + "px";
                            $("#" + selectItems[i].id).css("left", left);
                        }
                        jsPlumb.repaintEverything();
                        if (operate.length < 10) {
                            operate.push({ bindEvent: "rightActivity", bindObject: selectItems, bind: "activity", result: "rightActivity" });
                        }
                        else {
                            operate.shift();
                            operate.push({ bindEvent: "rightActivity", bindObject: selectItems, bind: "activity", result: "rightActivity" });
                        }
                    }
                    else {
                        $(".designeractivity").each(function () {
                            if ($(this).attr("standardactivity")) {
                                $(this).removeAttr("standardactivity");
                            }
                            if ($(this).attr("selectedactivity")) {
                                $(this).removeAttr("selectedactivity");
                            }
                        });
                    }
                    break;
            };
        });
    }

    //初始化菜单
    function initMenu() {
        $(".menu").click(function () {
            e = window.event || arguments[0];
            var pointx = (e.clientX + $(document).find("#designer_content").scrollLeft()) || e.offsetX;
            var pointy = (e.clientY + $(document).find("#designer_content").scrollTop()) || e.offsetY;
            $("#" + $(this).attr("id") + "_list").css("left", pointx);
            $("#" + $(this).attr("id") + "_list").css("top", pointy);
            $("#" + $(this).attr("id") + "_list").show();
        });
    }

    //初始化视图
    function initView() {
        $(".viewmenu").click(function () {
            switch (this.id) {
                case "viewmenu_toolbar":
                    if ($("#toolbars").is(":hidden")) {
                        $("#toolbars").show();
                    }
                    else {
                        $("#toolbars").hide();
                    }
                    resizeWindow();
                    break;
                case "viewmenu_toolbox":
                    if ($("#main_leftcontent").is(":hidden")) {
                        //  $("#main_designercontent").css("clear", "both");
                        $("#main_leftcontent").show();
                    }
                    else {
                        $("#main_leftcontent").hide();
                        //$("#main_designercontent").css("clear", "none");
                    }
                    resizeWindow();
                    break;
                case "viewmenu_designer": if ($("#main_designercontent").is(":hidden")) {
                    $("#main_designercontent").show();
                }
                else {
                    $("#main_designercontent").hide();
                }
                    resizeWindow();
                    break;
                case "editmenu_attribute": if ($("#main_attributecontent").is(":hidden")) {
                    $("#main_attributecontent").show();
                }
                else {
                    $("#main_attributecontent").hide();
                }
                    resizeWindow();
                    break;
            };
        });
    }

    //初始化流程图结束，同步流程信息到变量processDefine中
    function initProcessDefine(proDefine) {
        startConnection = 1;
        processDefine.activities = proDefine.activities;
        processDefine.transitions = proDefine.transitions;
        var connections = jsPlumb.getConnections({ scope: "process" });
        for (var j = 0; j < connections.length; j++) {
            for (var i = 0; i < processDefine.transitions.length; i++) {
                var sourceAnchor = processDefine.transitions[i].sourceOrientation;
                var sinkAnchor = processDefine.transitions[i].sinkOrientation;
                switch (sourceAnchor) {
                    case "Left": sourceAnchor = "LeftMiddle"; break;
                    case "Top": sourceAnchor = "TopCenter"; break;
                    case "Right": sourceAnchor = "RightMiddle"; break;
                    case "Bottom": sourceAnchor = "BottomCenter"; break;
                }
                switch (sinkAnchor) {
                    case "Left": sinkAnchor = "LeftMiddle"; break;
                    case "Top": sinkAnchor = "TopCenter"; break;
                    case "Right": sinkAnchor = "RightMiddle"; break;
                    case "Bottom": sinkAnchor = "BottomCenter"; break;
                }
                if (sourceAnchor == connections[j].endpoints[0].anchor.type && sinkAnchor == connections[j].endpoints[1].anchor.type && processDefine.transitions[i].srcActivity == connections[j].sourceId && processDefine.transitions[i].destActivity == connections[j].targetId) {
                    connectionLabel = connections[j];
                    setConnectionLabel(processDefine.transitions[i].name);
                }
            }
        }
    }

    //保存流程图
    function saveProcessDefine() {
        if ($.trim($("#currentStatus").val()) == CurrentStatus.Published) {
            alert("流程为发布状态，无法保存，修改发布状态的流程可能会影响该流程实例");
            return;
        }
        var checkActivities = new Array();
        for (var j = 0; j < processDefine.activities.length; j++) {
            if (processDefine.activities[j].newID) {
                for (var i = 0; i < checkActivities.length; i++) {
                    if (processDefine.activities[j].newID == checkActivities[i]) {
                        alert("活动存在相同ID,相同ID为" + processDefine.activities[j].newID + "，请检查后在保存!");
                        return false;
                    }
                }
                checkActivities.push(processDefine.activities[j].newID);
            }
            else {
                for (var i = 0; i < checkActivities.length; i++) {
                    if (processDefine.activities[j].id == checkActivities[i]) {
                        alert("活动存在相同ID,相同ID为" + processDefine.activities[j].id + "，请检查后在保存!");
                        return false;
                    }
                }
                checkActivities.push(processDefine.activities[j].id);
            }

        }


        for (var i = 0; i < processDefine.transitions.length; i++) {
            for (var j = 0; j < processDefine.activities.length; j++) {
                if (processDefine.transitions[i].srcActivity == processDefine.activities[j].id && processDefine.activities[j].newID) {
                    processDefine.transitions[i].srcActivity = processDefine.activities[j].newID;
                }
                if (processDefine.transitions[i].destActivity == processDefine.activities[j].id && processDefine.activities[j].newID) {
                    processDefine.transitions[i].destActivity = processDefine.activities[j].newID;
                }
            }
        }
        $(processDefine.activities).each(function () {
            var me = this;
            $(".designeractivity").each(function () {
                if (this.id == me.id) {
                    me.style.left = $(this).offset().left - $("#main_leftcontent").width();
                    me.style.top = $(this).offset().top - $("#header").height() - $("#tabcontainer").height() - $("#designer_title").height();
                    me.style.width = $(this).width();
                    me.style.height = $(this).height();
                    me.style.zIndex = 0;
                }
            });
        });
        for (var j = 0; j < processDefine.activities.length; j++) {
            if (processDefine.activities[j].newID) {
                processDefine.activities[j].id = processDefine.activities[j].newID;
            }
        }


        var name = $.trim($("#processName").val());
        var text = $.trim($("#processText").val());
        var version = $.trim($("#version").val());
        var description = $.trim($("#description").val());
        var startor = $.trim($("#startor").val());
        var isActive = $.trim($("#isActive").val());
        var currentFlag = $.trim($("#currentFlag").val());
        var currentStatus = $.trim($("#currentStatus").val());
        var categoryID = $.trim($("#categoryID").val());
        processDefine.name = text;
        processDefine.version = version;
        processDefine.author = startor;
        processDefine.startURL = $.trim($("#starturl").val());
        processDefine.id = name;//2012.11.16
        var processDefContent = JSON.stringify(processDefine);
        var processDefID = $.query.get("processDefID");
        if ($.query.get("action") == 'cloneProcess' && processDefID) {//代码bug，如果对方传过来的参数为&
            processDefID = null;
        }
        $.post("../workflow/designer/processdefine/save", { processDefID: processDefID, categoryID: categoryID, processDefContent: processDefContent, name: name, text: text, version: version, description: description, startor: startor, isActive: isActive, currentFlag: currentFlag, currentStatus: currentStatus }, function (message) {
            alert(message);
        });
    }


    //开始绘制流程图
    function startdraw(container) {
        $(".ativity").draggable({
            helper: "clone"
        });
        $("#" + container).droppable({
            drop: function (event, ui) {
                if (ui.draggable[0].className.indexOf("ativity ui-draggable") >= 0) {
                    var text = $.trim($(ui.draggable[0]).text());
                    var id = $.trim($(ui.draggable[0]).attr("id")) + new Date().getTime();
                    if ($.trim($(ui.draggable[0]).attr("id")) == "StartActivity" && $("#" + container).find("div[activitytype='StartActivity']")[0] != undefined) {
                        alert("流程图只能有一个开始活动");
                        return false;
                    }
                    else if ($.trim($(ui.draggable[0]).attr("id")) == "EndActivity" && $("#" + container).find("div[activitytype='EndActivity']")[0] != undefined) {
                        alert("流程图只能有一个结束活动");
                        return false;
                    }
                    else {
                        var img = $(ui.draggable[0]).children()[0];
                        var pointx = (event.clientX + $(document).find("#" + container).scrollLeft() - 20) || event.offsetX;
                        var pointy = (event.clientY + $(document).find("#" + container).scrollTop() - 20) || event.offsetY;
                        var activityResource = "<div id=\"" + id + "\"  class=\"designeractivity\" name=\"" + text + "\" ActivityType=\"" + $.trim($(ui.draggable[0]).attr("id")) + "\" style=\"left:" + pointx + "px;top:" + pointy + "px;width:40px;height:40px;position:absolute\"><img style=\"width:40px;height:40px;\" src=\"" + $(img).attr("src") + "\" /><label style=\"width:100px;position:absolute;Top:40px;left:-30px\">" + text + "</label></div>"
                        $("#" + container).append(activityResource);
                        $("#" + id).bind("mouseover", function () { jsPlumb.show(id, $("#" + id)); });
                        $("#" + id).bind("dblclick", function () { openTopDialog("actionDialog2", "workflow/designer/activity?processDefID=" + processDefine.id + "&activityID=" + id + "&activityType=" + $.trim($(ui.draggable[0]).attr("id")),'活动配置', 850, 580, true); });
                        //$("#" + id).bind("contextmenu", function () {
                        //    selectactivity = $("#" + id)[0];
                        //    e = window.event || arguments[0];
                        //    var pointx = (e.clientX + $(document).find("#" + container).scrollLeft()) || e.offsetX;
                        //    var pointy = (e.clientY + $(document).find("#" + container).scrollTop()) || e.offsetY;
                        //    $("#contexmenu").css("left", pointx);
                        //    $("#contexmenu").css("top", pointy);
                        //    $("#contexmenu").show();
                        //});
                        $("#" + id).bind("click", function () {
                          
                            e = window.event || arguments[0];
                            if (window.event) //停止事件向下传播
                                window.event.cancelBubble = true;
                            else {
                                e.stopPropagation();
                            }
                        });
                        initContextMenu();
                        $("#" + id).bind("mousedown", function () {
                            e = window.event || arguments[0];
                            if (window.event) //停止事件向下传播
                                window.event.cancelBubble = true;
                            else {
                                e.stopPropagation();
                            }
                            var me = this;
                            if (e.ctrlKey) {
                                this.selectedActivity = true;
                                $(this).attr("selectedActivity", "true");
                            }
                            else {
                                if (!this.selectedActivity) {
                                    $(".designeractivity").each(function () {
                                        jsPlumb.hide(this.id, true);
                                        jsPlumb.show(this.id);
                                    });
                                    jsPlumb.show(id, $("#" + id));
                                    $(".designeractivity").each(function () {
                                        $(this).removeAttr("standardActivity")
                                    });
                                    $(this).attr("standardActivity", "true");
                                }
                                else {

                                    $(".designeractivity").draggable("disable");
                                    // $(this).bind('dragstart', function (event, ui) { console.log("1")});
                                    startmove = 0;
                                    e = window.event || arguments[0];
                                    preleft = (e.clientX + $(document).scrollLeft()) || e.offsetX;
                                    pretop = (e.clientY + $(document).scrollTop()) || e.offsetY;
                                    //preleft = $(this).offset().left;
                                    //pretop = $(this).offset().top;
                                    var top = $(this).offset().top;
                                    var left = $(this).offset().left;
                                    var buttom = 0;
                                    var right = 0;
                                    $(".designeractivity").each(function () {
                                        if (this.selectedActivity) {
                                            if ($(this).offset().left < left) {
                                                left = $(this).offset().left;
                                            }
                                            if ($(this).offset().left > right) {
                                                right = $(this).offset().left;
                                            }
                                            if ($(this).offset().top < top) {
                                                top = $(this).offset().top;
                                            }
                                            if ($(this).offset().top > buttom) {
                                                buttom = $(this).offset().top;
                                            }
                                        }
                                    });
                                    var width = right - left + $(this).width();
                                    var height = buttom - top + $(this).height();
                                    var rect = "<div id=\"rect\" style=\"border:1px dashed #B6B6B6; position:absolute;z-index:999999999999;width:" + width + "px;height:" + height + "px;left:" + left + "px;top:" + top + "px\"></div>"
                                    $("#" + containerment).append(rect);
                                    //$("#rect").draggable();
                                    //$("#rect").bind("mousedown",function () {
                                    //    //alert("1");
                                    //    $("#rect").bind("mousemove", function () {
                                    //        e = window.event || arguments[0];
                                    //        var endleft = (e.clientX + $(document).scrollLeft()) || e.offsetX;
                                    //        var endtop = (e.clientY + $(document).scrollTop()) || e.offsetY;
                                    //        var left = endleft - preleft + $("#rect").offset().left;
                                    //        var top = endtop - pretop + $("#rect").offset().top;
                                    //        $("#rect").css("left", left);
                                    //        $("#rect").css("top", top);
                                    //    });
                                    //});
                                    document.onmousemove = null;
                                    $(document).unbind("mousemove");
                                    // document.onmouseup = null;
                                    //  document.onmouseup = activityMouseup;
                                    $(document).bind("mouseup", function () {
                                        $(document).unbind("mousemove");
                                        $("#rect").remove();
                                        $(".designeractivity").draggable("enable");
                                        if (startmove == 1) {
                                            e = window.event || arguments[0];
                                            var endleft = (e.clientX + $(document).scrollLeft()) || e.offsetX;
                                            var endtop = (e.clientY + $(document).scrollTop()) || e.offsetY;
                                            var left = endleft - preleft;
                                            var top = endtop - pretop;
                                            // var me = this;
                                            $(".designeractivity").each(function () {
                                                if (this.selectedActivity) {
                                                    this.style.left = left + $(this).offset().left + "px";
                                                    this.style.top = top + $(this).offset().top + "px";
                                                }
                                                // $(this).css("left", endleft);
                                                // $(this).css("top", endtop);
                                                // alert(this.style.left);
                                            });
                                            jsPlumb.repaintEverything();

                                        }
                                        startmove = 0;
                                        document.onmouseup = null;
                                        document.onmousemove = null;
                                    });
                                    // document.onmousemove = rectMouseMove;
                                    $(document).bind("mousemove", function () {
                                        startmove = 1;
                                    });
                                    document.onmousemove = null;
                                }
                            }
                        });
                        var activity = { name: text, id: id, activityType: $.trim($(ui.draggable[0]).attr("id")), style: {} };
                        processDefine.activities.push(activity);
                        var exampleGreyEndpointOptions = {
                            isSource: true,
                            isTarget: true,
                            DragOptions: { cursor: 'pointer', zIndex: 2000 },
                            Endpoints: [["Dot", { radius: 7 }], ["Dot", { radius: 7 }]],
                            EndpointStyles: [{ fillStyle: "#528E21" }, { fillStyle: "#528E21" }],
                            connector: [lineStyle, { stub: [40, 60], gap: 10 }],
                            paintStyle: { fillStyle: "#528E21", radius: 7 },
                            connectorStyle: connectorPaintStyle,
                            hoverPaintStyle: connectorHoverStyle,
                            maxConnections: 5,
                            scope: "process",
                            connectorHoverStyle: connectorHoverStyle
                        };
                        jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "BottomCenter" });
                        jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "TopCenter" });
                        jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "LeftMiddle" });
                        jsPlumb.addEndpoint(id, exampleGreyEndpointOptions, { anchor: "RightMiddle" });
                        jsPlumb.draggable(jsPlumb.getSelector($("#" + id)),
                       {
                           start: function (event, ui) {
                               console.log("开始拖动");
                               if (operate.length < 10) {
                                   operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                               }
                               else {
                                   operate.shift();
                                   operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                               }
                           },
                           containment: $("#" + container)
                       });
                        $(".designeractivity").each(function () {
                            jsPlumb.hide(this.id, true);
                        });
                        $(".designeractivity").each(function () {
                            jsPlumb.show(this.id);
                        });
                        if (operate.length < 10) {
                            operate.push({ bindEvent: "addActivity", bindObject: activity, bind: "designerActivity", result: "addActivity" });
                        }
                        else {
                            operate.shift();
                            operate.push({ bindEvent: "addActivity", bindObject: activity, bind: "designerActivity", result: "addActivity" });
                        }
                    }
                }
                else {
                    temporaryDrop.push(ui);
                }
                startConnection = 1;
                //  alert("123");
            }
        });
        initUI();
        init();
        initMenu();
        initToolbar();
        initView();

    }


    //初始化流程活动
    function drawActivityInst(activity, currentState, container) {
        var imgPath = "/Plugins/WorkflowDesigner/Content/Themes/Default/images/";
        var resource = "/Plugins/WorkflowDesigner/Content/Themes/Default/images/" + activity.activityType + ".png";
        switch (currentState) {
            case -1: resource = imgPath + activity.activityType + "4.png";
                break;
            case 0: resource = imgPath + activity.activityType + ".png";
                break;
            case 1: resource = imgPath + activity.activityType + "2.png";
                break;
            case 2: break;
            case 3: resource = imgPath + activity.activityType + "2.png";
                break;
            case 4: resource = imgPath + activity.activityType + "3.png";
                break;
            case 5: resource = imgPath + activity.activityType + "5.png";
                break;
            case 8: resource = imgPath + activity.activityType + "6.png";
                break;
        }
        if ($("#" + container).find($("#" + activity.id))[0] != undefined) {
            $("#" + container).find($("#" + activity.id)).remove();
        }
        var left = activity.style.left + $("#main_leftcontent").width();
        var height = activity.style.top + $("#header").height() + $("#tabcontainer").height() + $("#designer_title").height();
        var activityResource = "<div id=\"" + activity.id + "\"  class=\"designeractivity\" name=\"" + activity.name + "\" ActivityType=\"" + activity.activityType + "\"  style=\"left:" + left + "px;top:" + height + "px;width:40px;height:40px;position:absolute\"><img style=\"width:40px;height:40px;\" src=\"" + resource + "\" /><label style=\"width:100px;position:absolute;Top:40px;left:-30px\">" + activity.name + "</label></div>"
        $("#" + container).append(activityResource);
        $("#" + activity.id).bind("mouseover", function () { jsPlumb.show(activity.id, $("#" + activity.id)); });
        $("#" + activity.id).bind("dblclick", function () { openTopDialog("actionDialog2", "workflow/designer/activity?processDefID=" + $.query.get("processDefID") + "&activityID=" + activity.id + "&activityType=" + $(activity).attr("ActivityType"),'活动配置', 850, 580, true); });
        //$("#" + activity.id).bind("contextmenu", function () {
        //    selectactivity = $("#" + activity.id)[0];
        //    e = window.event || arguments[0];
        //    var pointx = (e.clientX + $(document).find("#" + container).scrollLeft()) || e.offsetX;
        //    var pointy = (e.clientY + $(document).find("#" + container).scrollTop()) || e.offsetY;
        //    $("#contexmenu").css("left", pointx);
        //    $("#contexmenu").css("top", pointy);
        //    $("#contexmenu").show();
        //});
        initContextMenu();
        $("#" + activity.id).bind("click", function () {
           
            e = window.event || arguments[0];
            if (window.event) //停止事件向下传播
                window.event.cancelBubble = true;
            else {
                e.stopPropagation();
            }
        });
        $("#" + activity.id).bind("mousedown", function () {
            e = window.event || arguments[0];
            if (window.event) //停止事件向下传播
                window.event.cancelBubble = true;
            else {
                e.stopPropagation();
            }
            var me = this;
            if (e.ctrlKey) {
                this.selectedActivity = true;
                $(this).attr("selectedActivity", "true");
            }
            else {
                if (!this.selectedActivity) {
                    $(".designeractivity").each(function () {
                        jsPlumb.hide(this.id, true);
                        jsPlumb.show(this.id);
                        $(this).removeAttr("standardActivity")
                    });
                    jsPlumb.show(activity.id, $("#" + activity.id));
                    $(this).attr("standardActivity", "true");
                }
                else {
                    $(".designeractivity").draggable("disable");
                    // $(this).bind('dragstart', function (event, ui) { console.log("1")});
                    startmove = 0;
                    e = window.event || arguments[0];
                    preleft = (e.clientX + $(document).scrollLeft()) || e.offsetX;
                    pretop = (e.clientY + $(document).scrollTop()) || e.offsetY;
                    //preleft = $(this).offset().left;
                    //pretop = $(this).offset().top;
                    var top = $(this).offset().top;
                    var left = $(this).offset().left;
                    var buttom = 0;
                    var right = 0;
                    $(".designeractivity").each(function () {
                        if (this.selectedActivity) {
                            if ($(this).offset().left < left) {
                                left = $(this).offset().left;
                            }
                            if ($(this).offset().left > right) {
                                right = $(this).offset().left;
                            }
                            if ($(this).offset().top < top) {
                                top = $(this).offset().top;
                            }
                            if ($(this).offset().top > buttom) {
                                buttom = $(this).offset().top;
                            }
                        }
                    });
                    var width = right - left + $(this).width();
                    var height = buttom - top + $(this).height();
                    var rect = "<div id=\"rect\" style=\"border:1px dashed #B6B6B6; position:absolute;z-index:999999999999;width:" + width + "px;height:" + height + "px;left:" + left + "px;top:" + top + "px\"></div>"
                    $("#" + containerment).append(rect);
                    //$("#rect").draggable();
                    //$("#rect").bind("mousedown",function () {
                    //    //alert("1");
                    //    $("#rect").bind("mousemove", function () {
                    //        e = window.event || arguments[0];
                    //        var endleft = (e.clientX + $(document).scrollLeft()) || e.offsetX;
                    //        var endtop = (e.clientY + $(document).scrollTop()) || e.offsetY;
                    //        var left = endleft - preleft + $("#rect").offset().left;
                    //        var top = endtop - pretop + $("#rect").offset().top;
                    //        $("#rect").css("left", left);
                    //        $("#rect").css("top", top);
                    //    });
                    //});
                    document.onmousemove = null;
                    $(document).unbind("mousemove");
                    // document.onmouseup = null;
                    //  document.onmouseup = activityMouseup;
                   
                    // document.onmousemove = rectMouseMove;
                    $(document).bind("mousemove", function () {
                        startmove = 1;
                        //alert("2");
                        //$(document).unbind("mouseup");
                        $(document).bind("mouseup", function () {
                           // alert("1");
                            $(document).unbind("mousemove");
                            //$(document).unbind("mouseup");
                            $("#rect").remove();
                            $(".designeractivity").draggable("enable");
                            if (startmove == 1) {
                                e = window.event || arguments[0];
                                var endleft = (e.clientX + $(document).scrollLeft()) || e.offsetX;
                                var endtop = (e.clientY + $(document).scrollTop()) || e.offsetY;
                                var left = endleft - preleft;
                                var top = endtop - pretop;
                                // var me = this;
                                $(".designeractivity").each(function () {
                                    if (this.selectedActivity) {
                                        this.style.left = left + $(this).offset().left + "px";
                                        this.style.top = top + $(this).offset().top + "px";
                                    }
                                    // $(this).css("left", endleft);
                                    // $(this).css("top", endtop);
                                    // alert(this.style.left);
                                });
                                jsPlumb.repaintEverything();

                            }
                            startmove = 0;
                            document.onmouseup = null;
                            document.onmousemove = null;
                        });
                    });
                    document.onmousemove = null;
                }
            }
        });
    }
    function rectMouseMove() {
        startmove = 1;
        e = window.event || arguments[0];
        var endleft = (e.clientX + $(document).scrollLeft()) || e.offsetX;
        var endtop = (e.clientY + $(document).scrollTop()) || e.offsetY;
        var left = endleft - preleft + $("#rect").offset().left;
        var top = endtop - pretop + $("#rect").offset().top;
        $("#rect").css("left", left);
        $("#rect").css("top", top);
        console.log("移动");
        //startmove = 1;
        //e = window.event || arguments[0];
        //var endleft = (e.clientX + $(document).scrollLeft()) || e.offsetX;
        //var endtop = (e.clientY+ $(document).scrollTop()) || e.offsetY;
        //var left = endleft - preleft + $("#rect").offset().left;
        //var top = endtop - pretop + $("#rect").offset().top;
        //$("#rect").css("left", left);
        //$("#rect").css("top", top);
        //var left = endleft - preleft;
        //var top = endtop - pretop;
        //$(".designeractivity").each(function () {
        //    if (this.selectedActivity) {
        //        //$(this).mousedown();
        //        //$(this).mousemove();
        //       // $(this).mousem();
        //    }

        //});
    }
    function activityMouseup() {
        $(document).unbind("mousemove");
        $("#rect").remove();
        $(".designeractivity").draggable("enable");
        if (startmove == 1) {
            e = window.event || arguments[0];
            var endleft = (e.clientX + $(document).scrollLeft()) || e.offsetX;
            var endtop = (e.clientY + $(document).scrollTop()) || e.offsetY;
            var left = endleft - preleft;
            var top = endtop - pretop;
            $(".designeractivity").each(function () {
                if (this.selectedActivity) {
                    this.style.left = left + $(this).offset().left + "px";
                    this.style.top = top + $(this).offset().top + "px";
                }
            });
            jsPlumb.repaintEverything();

        }
        startmove = 0;
        document.onmouseup = null;
        document.onmousemove = null;
    }

    //初始化流程线
    function drawConnection(transition, activityInstList, transitionControlList, container) {
        var currentStatus = 0;
        var status = 0;
        if (activityInstList != null && activityInstList.length > 0) {
            for (var i = 0; i < activityInstList.length; i++) {
                if (transition.destActivity == activityInstList[i].ActivityDefID)//查找目标
                {
                    switch (activityInstList[i].CurrentState) {
                        case -1: currentStatus = -1; break;//未运行到
                        case 0: currentStatus = 0; break;//初始状态
                        case 1: currentStatus = 1; break;//将运行
                        case 2: currentStatus = 2; break;//运行出错
                        case 3: currentStatus = 3; break;// 挂起
                        case 4: currentStatus = 4; break;//已运行
                        case 5: currentStatus = 5; break;//回退
                    }
                    for (var j = 0; j < activityInstList.length; j++) {
                        if (transition.srcActivity == activityInstList[j].ActivityDefID && activityInstList[i].CurrentState == 4) {
                            for (var n = 0; n < transitionControlList.length; n++) {
                                if (transitionControlList[n].DestActID == activityInstList[i].ActivityDefID && transitionControlList[n].SrcActID == transition.srcActivity) {
                                    switch (activityInstList[j].CurrentState) {
                                        case -1: currentStatus = -1; break;
                                        case 0: currentStatus = -1; break;
                                        case 3: currentStatus = -1; break;
                                        case 4: currentStatus = 4; break;
                                        case 5: currentStatus = 0; break;
                                    }
                                    break;
                                }
                                else {
                                    currentStatus = -1;
                                }
                            }
                            status = 1;
                        }

                        if (transition.srcActivity == activityInstList[j].ActivityDefID && currentStatus != 4) {
                            for (var m = 0; m < transitionControlList.length; m++) {
                                if (transitionControlList[m].DestActID == activityInstList[i].ActivityDefID && transitionControlList[m].SrcActID == transition.srcActivity) {

                                    if (currentStatus == 5) {
                                        for (var k = 0; k < transitionControlList.length; k++) {
                                            if (transitionControlList[k].DestActID == transition.srcActivity && transitionControlList[k].SrcActID == activityInstList[i].ActivityDefID) {
                                                currentStatus = 5;
                                                break;
                                            }
                                            else {
                                                currentStatus = 0;
                                            }
                                        }

                                    }
                                    else {
                                        if (currentStatus == -1)
                                            currentStatus = 1;
                                        if (currentStatus == 3)
                                            currentStatus = 1;


                                    }
                                    break;

                                }
                                else {
                                    if (currentStatus != 5 && currentStatus != 0 && currentStatus != 4) {
                                        currentStatus = -1;
                                    }

                                }
                            }

                            status = 1;
                        }
                    }
                    if (status != 1 && currentStatus != 5) {
                        currentStatus = -1;
                        drawActivityInst(transition.srcActivity, -1);
                    }
                    else if (status != 1 && currentStatus == 5) {
                        currentStatus = 0;
                    }

                }
                if (transition.srcActivity == activityInstList[i].ActivityDefID) {
                    for (var p = 0; p < activityInstList.length; p++) {

                        if (currentStatus != 1 && currentStatus != 4 && activityInstList[i].CurrentState == 4 || activityInstList[p].CurrentState == -1) {
                            currentStatus = -1;
                        }
                    }
                }
            }
        }
        else {
            currentStatus = 0;
        }

        var connectionStyle = "Green";
        switch (currentStatus) {
            case -1: connectionStyle = "Yellow"; break;
            case 0: connectionStyle = "Green"; break;
            case 1: connectionStyle = "Red"; break;
            case 2:; break;
            case 4: connectionStyle = "Gray"; break;
            case 3:; break;
            case 5: connectionStyle = "Blue"; break;
        }
        var sourceAnchor = transition.sourceOrientation;
        var sinkAnchor = transition.sinkOrientation;
        switch (transition.sourceOrientation) {
            case "Left": sourceAnchor = "LeftMiddle"; break;
            case "Top": sourceAnchor = "TopCenter"; break;
            case "Right": sourceAnchor = "RightMiddle"; break;
            case "Bottom": sourceAnchor = "BottomCenter"; break;
        }
        switch (transition.sinkOrientation) {
            case "Left": sinkAnchor = "LeftMiddle"; break;
            case "Top": sinkAnchor = "TopCenter"; break;
            case "Right": sinkAnchor = "RightMiddle"; break;
            case "Bottom": sinkAnchor = "BottomCenter"; break;
        }

        jsPlumb.addEndpoint(transition.srcActivity, exampleGreyEndpointOptions, { anchor: "BottomCenter" });
        jsPlumb.addEndpoint(transition.srcActivity, exampleGreyEndpointOptions, { anchor: "TopCenter" });
        jsPlumb.addEndpoint(transition.srcActivity, exampleGreyEndpointOptions, { anchor: "LeftMiddle" });
        jsPlumb.addEndpoint(transition.srcActivity, exampleGreyEndpointOptions, { anchor: "RightMiddle" });
        jsPlumb.addEndpoint(transition.destActivity, exampleGreyEndpointOptions, { anchor: "BottomCenter" });
        jsPlumb.addEndpoint(transition.destActivity, exampleGreyEndpointOptions, { anchor: "TopCenter" });
        jsPlumb.addEndpoint(transition.destActivity, exampleGreyEndpointOptions, { anchor: "LeftMiddle" });
        jsPlumb.addEndpoint(transition.destActivity, exampleGreyEndpointOptions, { anchor: "RightMiddle" });
        jsPlumb.connect({
            paintStyle: { fillStyle: connectionStyle, strokeStyle: connectionStyle, lineWidth: 2, radius: 7 },
            source: transition.srcActivity,
            target: transition.destActivity,
            anchors: [sourceAnchor, sinkAnchor],
            connector: lineStyle,
            hoverPaintStyle: connectorHoverStyle,
            connectorHoverStyle: connectorHoverStyle,
            endpoints: [["Dot", { radius: 7 }], ["Dot", { radius: 7 }]],
            scope: "process",
            endpointStyles: [{ fillStyle: "#528E21" }, { fillStyle: "#528E21" }]
        });

    }


    //初始化流程
    var WorkflowDesigner = function () {
        this.me = this;
        this.show = 1;
    };
    
    WorkflowDesigner.draw = function (url, proDefContent, container) {
        $.post(url, { processDefContent: proDefContent }, function ($processDefine) {
            jsPlumb.removeEveryEndpoint();
            for (var q = 0; q < $processDefine.activities.length; q++) {
                if (document.getElementById("leftcontent")) {
                    $("#leftcontent").hide();
                }
                drawActivityInst($processDefine.activities[q], 0, container);
            }
/*                if (ajaxResult.RetValue.processDefine.transitions.length > 0) {
                    for (var j = 0; j < ajaxResult.RetValue.processDefine.transitions.length; j++) {
                        drawConnection(ajaxResult.RetValue.processDefine.transitions[j], ajaxResult.RetValue.activityInsts, ajaxResult.RetValue.transList, container);
                    }
                }*/
            jsPlumb.addEndpoint($(".designeractivity"), exampleGreyEndpointOptions, { anchor: "BottomCenter" });
            jsPlumb.addEndpoint($(".designeractivity"), exampleGreyEndpointOptions, { anchor: "TopCenter" });
            jsPlumb.addEndpoint($(".designeractivity"), exampleGreyEndpointOptions, { anchor: "LeftMiddle" });
            jsPlumb.addEndpoint($(".designeractivity"), exampleGreyEndpointOptions, { anchor: "RightMiddle" });
            $(".designeractivity").each(function () {
                jsPlumb.hide(this.id, true);
            });
            $(".designeractivity").each(function () {
                jsPlumb.show(this.id);
            });
            jsPlumb.draggable(jsPlumb.getSelector(".designeractivity"),
                   {
                       start: function (event, ui) {
                           console.log("开始拖动");
                           if (operate.length < 10) {
                               operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                           }
                           else {
                               operate.shift();
                               operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                           }
                       },
                       containment: $("#" + container)
                   });
            initProcessDefine($processDefine);
        });
    };

    WorkflowDesigner.reloadXML = function (url) {
        var checkActivities = new Array();
        for (var j = 0; j < processDefine.activities.length; j++) {
            if (processDefine.activities[j].newID) {
                for (var i = 0; i < checkActivities.length; i++) {
                    if (processDefine.activities[j].newID == checkActivities[i]) {
                        alert("活动存在相同ID,相同ID为" + processDefine.activities[j].newID + "，请检查后在保存!");
                        return false;
                    }
                }
                checkActivities.push(processDefine.activities[j].newID);
            }
            else {
                for (var i = 0; i < checkActivities.length; i++) {
                    if (processDefine.activities[j].id == checkActivities[i]) {
                        alert("活动存在相同ID,相同ID为" + processDefine.activities[j].id + "，请检查后在保存!");
                        return false;
                    }
                }
                checkActivities.push(processDefine.activities[j].id);
            }

        }


        for (var i = 0; i < processDefine.transitions.length; i++) {
            for (var j = 0; j < processDefine.activities.length; j++) {
                if (processDefine.transitions[i].srcActivity == processDefine.activities[j].id && processDefine.activities[j].newID) {
                    processDefine.transitions[i].srcActivity = processDefine.activities[j].newID;
                }
                if (processDefine.transitions[i].destActivity == processDefine.activities[j].id && processDefine.activities[j].newID) {
                    processDefine.transitions[i].destActivity = processDefine.activities[j].newID;
                }
            }
        }
        $(processDefine.activities).each(function () {
            var me = this;
            $(".designeractivity").each(function () {
                if (this.id == me.id) {
                    me.style.left = $(this).offset().left - $("#main_leftcontent").width();
                    me.style.top = $(this).offset().top - $("#header").height() - $("#tabcontainer").height() - $("#designer_title").height();
                    me.style.width = $(this).width();
                    me.style.height = $(this).height();
                    me.style.zIndex = 0;
                }
            });
        });
        for (var j = 0; j < processDefine.activities.length; j++) {
            if (processDefine.activities[j].newID) {
                processDefine.activities[j].id = processDefine.activities[j].newID;
            }
        }
        var name = $.trim($("#processName").val());
        var text = $.trim($("#processText").val());
        var version = $.trim($("#version").val());
        var description = $.trim($("#description").val());
        var startor = $.trim($("#startor").val());
        var isActive = $.trim($("#isActive").val());
        var currentFlag = $.trim($("#currentFlag").val());
        var currentStatus = $.trim($("#currentStatus").val());
        var categoryID = $.trim($("#categoryID").val());
        processDefine.name = text;
        processDefine.version = version;
        processDefine.author = startor;
        processDefine.startURL = $.trim($("#starturl").val());
        processDefine.id = name;//2012.11.16
        var processDefContent = JSON.stringify(processDefine);
        $.post(url, { processDefContent: processDefContent }, function (xmlContent) {
            $("#XMLdesigner_content").val(xmlContent);
        });
    };
    //流程图设计静态初始化方法
    WorkflowDesigner.init = function (url, processDefID, processInstID, container, designerDefine, defineUrl) {
        containerment = container;
        if (processDefID != null && processDefID != "" && processDefID != true) {
            if (designerDefine == 1) {
                $.post(defineUrl, { processDefID: processDefID }, function (processDef) {
	                if ($.query.get("action") == 'cloneProcess') {
	                	processDef.CurrentState = 0;
	                }
	                if (processDef.CurrentState == 1)//1表示流程为发布状态，0为未发布状态，2为终止状态
	                {
	                    alert("该流程为发布状态，无法修改保存");
	                }
	                $("#processName").val(processDef.name);
	                $("#processText").val(processDef.text);
	                $("#version").val(processDef.Version);
	                $("#description").val(processDef.description);
	                $("#startor").val(processDef.startor);
	                $("#isActive").val(processDef.isActive);
	                $("#currentFlag").val(processDef.currentFlag);
	                $("#currentStatus").val(processDef.currentState);
	                $("#categoryID").val(processDef.categoryID);
	                processDefine.id = processDef.name;//2012.11.16
	                $("#XMLdesigner_content").val(processDef.content);
                });
            }
            $.post(url, { processDefID: processDefID, processInstID: processInstID }, function (retValue) {
                var message = "操作失败";
                if (!retValue.processDefine.startURL) {
                	retValue.processDefine.startURL = "workflow / eform";
                }
                $("#starturl").val(retValue.processDefine.StartURL);
                if (retValue.processInstID != null && retValue.processInstID != "" &&retValue.processInstID != "null") {
                    if (document.getElementById("leftcontent")) {
                        $("#leftcontent").show();
                    }
                    try {
                        for (var j = 0; retValue.processDefine.activities.length > j; j++) {
                            var i = 0;
                            for (var k = 0; k < retValue.activityInsts.length; k++) {
                                var activityDefID = retValue.activityInsts[k].activityDefID;
                                if (activityDefID ==retValue.processDefine.activities[j].id) {
                                    i = 1;
                                    var currentState = retValue.activityInsts[k].currentState;
                                    drawActivityInst(retValue.processDefine.activities[j], currentState, container);
                                }
                            }
                            if (i == 0) {
                                drawActivityInst(retValue.processDefine.activities[j], 0, container);
                            }
                        }
                    }
                    catch (ex) {
                    }
                }
                else {
                    for (var q = 0; q < retValue.processDefine.activities.length; q++) {
                        if (document.getElementById("leftcontent")) {
                            $("#leftcontent").hide();
                        }
                        drawActivityInst(retValue.processDefine.activities[q], 0, container);
                    }
                }
                if (retValue.processDefine.transitions.length > 0) {
                    for (var j = 0; j < retValue.processDefine.transitions.length; j++) {
                        drawConnection(retValue.processDefine.transitions[j],retValue.activityInsts, retValue.transList, container);
                    }
                }
                jsPlumb.addEndpoint($(".designeractivity"), exampleGreyEndpointOptions, { anchor: "BottomCenter" });
                jsPlumb.addEndpoint($(".designeractivity"), exampleGreyEndpointOptions, { anchor: "TopCenter" });
                jsPlumb.addEndpoint($(".designeractivity"), exampleGreyEndpointOptions, { anchor: "LeftMiddle" });
                jsPlumb.addEndpoint($(".designeractivity"), exampleGreyEndpointOptions, { anchor: "RightMiddle" });
                $(".designeractivity").each(function () {
                    jsPlumb.hide(this.id, true);
                });
                $(".designeractivity").each(function () {
                    jsPlumb.show(this.id);
                });
                jsPlumb.draggable(jsPlumb.getSelector(".designeractivity"),
                   {
                       start: function (event, ui) {
                           console.log("开始拖动");
                           if (operate.length < 10) {
                               operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                           }
                           else {
                               operate.shift();
                               operate.push({ bindEvent: "drag", bindObject: ui, bind: "designeractivity", result: "dragActivity" });
                           }
                       },
                       containment: $("#" + container)
                   });
                initProcessDefine(retValue.processDefine);
            });
        }
        else {
            processDefID = "processDefine" + new Date().getTime();
            processDefine.id = processDefID;
        }
        //  processDefine.id = processDefID;
        startdraw(container);
        initDocumentContextMunu();

    }


    //连接线操作
    jsPlumb.bind("contextmenu", function (conn, originalEvent) {
        var o = { menu: "ulline", inSpeed: 150, outSpeed: 75, position: document };
        var e = originalEvent;
        e.preventDefault();
        if (window.event) //停止事件向下传播
            window.event.cancelBubble = true;
        else {
            e.stopPropagation();
        }
        var x;
        var y;
        (e.pageX) ? x = e.pageX : x = e.clientX + e.scrollLeft;
        (e.pageY) ? y = e.pageY : x = e.clientX + e.scrollTop;
        $('#' + o.menu).addClass('contextMenu');
        var menu = $('#' + o.menu, o.position);
        $(menu).css({ top: y, left: x }).fadeIn(o.inSpeed);
        $(menu).find('A').mouseover(function () {
            $(menu).find('LI.hover').removeClass('hover');
            $(this).parent().addClass('hover');
        }).mouseout(function () {
            $(menu).find('LI.hover').removeClass('hover');
        });
        $(document).keypress(function (e) {
            switch (e.keyCode) {
                case 38: // up
                    if ($(menu).find('LI.hover').size() == 0) {
                        $(menu).find('LI:last').addClass('hover');
                    } else {
                        $(menu).find('LI.hover').removeClass('hover').prevAll('LI:not(.disabled)').eq(0).addClass('hover');
                        if ($(menu).find('LI.hover').size() == 0) $(menu).find('LI:last').addClass('hover');
                    }
                    break;
                case 40: // down
                    if ($(menu).find('LI.hover').size() == 0) {
                        $(menu).find('LI:first').addClass('hover');
                    } else {
                        $(menu).find('LI.hover').removeClass('hover').nextAll('LI:not(.disabled)').eq(0).addClass('hover');
                        if ($(menu).find('LI.hover').size() == 0) $(menu).find('LI:first').addClass('hover');
                    }
                    break;
                case 13: // enter
                    $(menu).find('LI.hover A').trigger('click');
                    break;
                case 27: // esc
                    $(document).trigger('click');
                    break
            }
        });
        $('#' + o.menu, o.position).find('A').unbind('click');
        $('#' + o.menu, o.position).find('LI:not(.disabled) A').click(function () {
            $(document).unbind('click').unbind('keypress');
            $(".contextMenu", o.position).hide();
            var action = $(this).attr('href').substr(1);
            switch (action) {
                case "delControl":
                    if (confirm("是否删除该连接")) {
                        for (var i = 0; i < processDefine.transitions.length; i++) {
                            var sourceAnchor = processDefine.transitions[i].sourceOrientation;
                            var sinkAnchor = processDefine.transitions[i].sinkOrientation;
                            switch (sourceAnchor) {
                                case "Left": sourceAnchor = "LeftMiddle"; break;
                                case "Top": sourceAnchor = "TopCenter"; break;
                                case "Right": sourceAnchor = "RightMiddle"; break;
                                case "Bottom": sourceAnchor = "BottomCenter"; break;
                            }
                            switch (sinkAnchor) {
                                case "Left": sinkAnchor = "LeftMiddle"; break;
                                case "Top": sinkAnchor = "TopCenter"; break;
                                case "Right": sinkAnchor = "RightMiddle"; break;
                                case "Bottom": sinkAnchor = "BottomCenter"; break;
                            }
                            if (sourceAnchor == conn.endpoints[0].anchor.type && sinkAnchor == conn.endpoints[1].anchor.type && processDefine.transitions[i].srcActivity == conn.sourceId && processDefine.transitions[i].destActivity == conn.targetId) {
                                if (operate.length < 10) {
                                    operate.push({ bindEvent: "conncontextmenu", bindObject: processDefine.transitions[i], bind: "connection", result: "delconnection" });
                                }
                                else {
                                    operate.shift();
                                    operate.push({ bindEvent: "conncontextmenu", bindObject: processDefine.transitions[i], bind: "connection", result: "delconnection" });
                                }
                                processDefine.transitions.splice(i, 1);
                            }
                        }
                        jsPlumb.detach(conn);

                    }
                    break;
                case "attrControl":
                    var transitionID = "";
                    for (var i = 0; i < processDefine.transitions.length; i++) {
                        var sourceAnchor = processDefine.transitions[i].sourceOrientation;
                        var sinkAnchor = processDefine.transitions[i].sinkOrientation;
                        switch (sourceAnchor) {
                            case "Left": sourceAnchor = "LeftMiddle"; break;
                            case "Top": sourceAnchor = "TopCenter"; break;
                            case "Right": sourceAnchor = "RightMiddle"; break;
                            case "Bottom": sourceAnchor = "BottomCenter"; break;
                        }
                        switch (sinkAnchor) {
                            case "Left": sinkAnchor = "LeftMiddle"; break;
                            case "Top": sinkAnchor = "TopCenter"; break;
                            case "Right": sinkAnchor = "RightMiddle"; break;
                            case "Bottom": sinkAnchor = "BottomCenter"; break;
                        }
                        if (sourceAnchor == conn.endpoints[0].anchor.type && sinkAnchor == conn.endpoints[1].anchor.type && processDefine.transitions[i].srcActivity == conn.sourceId && processDefine.transitions[i].destActivity == conn.targetId) {
                            transitionID = processDefine.transitions[i].id;
                            connectionLabel = conn;
                        }
                    }
                    window.parent.parent.openDialog("actionDialog3", '连接线配置', "/WorkflowDesigner/Workflow/ConnectionDetail?ProcessDefID=" + processDefine.id + "&TransitionID=" + transitionID, 650, 380, true);
                    break;
            }
            return false;
        });
        setTimeout(function () { // Delay for Mozilla
            $(document).click(function () {
                $(menu).fadeOut(o.outSpeed);
                return false;
            });
        }, 0);
    });
    jsPlumb.bind("dblclick", function (conn, originalEvent) {
        var transitionID = "";
        for (var i = 0; i < processDefine.transitions.length; i++) {
            var sourceAnchor = processDefine.transitions[i].sourceOrientation;
            var sinkAnchor = processDefine.transitions[i].sinkOrientation;
            switch (sourceAnchor) {
                case "Left": sourceAnchor = "LeftMiddle"; break;
                case "Top": sourceAnchor = "TopCenter"; break;
                case "Right": sourceAnchor = "RightMiddle"; break;
                case "Bottom": sourceAnchor = "BottomCenter"; break;
            }
            switch (sinkAnchor) {
                case "Left": sinkAnchor = "LeftMiddle"; break;
                case "Top": sinkAnchor = "TopCenter"; break;
                case "Right": sinkAnchor = "RightMiddle"; break;
                case "Bottom": sinkAnchor = "BottomCenter"; break;
            }
            if (sourceAnchor == conn.endpoints[0].anchor.type && sinkAnchor == conn.endpoints[1].anchor.type && processDefine.transitions[i].srcActivity == conn.sourceId && processDefine.transitions[i].destActivity == conn.targetId) {
                transitionID = processDefine.transitions[i].id;
                connectionLabel = conn;
            }
        }
        window.parent.parent.openDialog("actionDialog3", '连接线配置', "/WorkflowDesigner/Workflow/ConnectionDetail?ProcessDefID=" + processDefine.id + "&TransitionID=" + transitionID, 650, 380, true);
    });
    jsPlumb.bind("jsPlumbConnection", function (connection) {
        if (startConnection != 0) {//1.表示流程初始化结束，开始绘制流程线
            if (startConnection == 1) {
                var label = "";
                if (connection.connection.getLabel() != null) {
                    label = connection.connection.getLabel();
                }
                if (connection.sourceId != connection.targetId) {
                    processDefine.transitions.push({ id: connection.connection.id, sourcePoint: { x: $("#" + connection.sourceId).offset().left - $("#main_leftcontent").width(), y: $("#" + connection.sourceId).offset().top - $("#header").height() - $("#tabcontainer").height() - $("#designer_title").height(), z: 0 }, sinkPoint: { x: $("#" + connection.targetId).offset().left - $("#main_leftcontent").width(), y: $("#" + connection.targetId).offset().top - $("#header").height() - $("#tabcontainer").height() - $("#designer_title").height(), z: 0 }, sourceOrientation: connection.sourceEndpoint.anchor.type, sinkOrientation: connection.targetEndpoint.anchor.type, srcActivity: connection.sourceId, destActivity: connection.targetId, name: label, priority: 3, isDefault: false, expression: "" });
                    if (operate.length < 10) {
                        operate.push({ bindEvent: "connection", bindObject: connection, bind: "connection", result: "lineConnection" });
                    }
                    else {
                        operate.shift();
                        operate.push({ bindEvent: "connection", bindObject: connection, bind: "connection", result: "lineConnection" });
                    }
                }
            }
            if (startConnection == 2) {
                temporaryObject.push({ bindEvent: "conncontextmenu", bindObject: connection, bind: "connection", result: "delconnection" });
            }
            if (startConnection == 3) {
                operate.push({ bindEvent: "connection", bindObject: connection, bind: "connection", result: "lineconnection" });
            }
        }

    });

    //同步流程图的活动名称和流程线名称
    function editActivityName(id, Name) {
        $("#" + id).find("label").text(Name);
    }
    function setConnectionLabel(text) {
        connectionLabel.getOverlay("label").setLabel(text);
    }

    window.editActivityName = editActivityName;
    window.setConnectionLabel = setConnectionLabel;
    window.WorkflowDesigner = WorkflowDesigner;
    window.processDefine = processDefine;
})(window);