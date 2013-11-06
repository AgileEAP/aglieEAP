function SplitType() {
    this.XOR = "XOR",
       this.OR = "OR",
       this.AND = "AND"
}
function JoinType() {
    this.XOR = "XOR",
      this.OR = "OR",
      this.AND = "AND"
}
function WorkItemNumStrategy() {
    this.ParticipantNumber = "ParticipantNumber";
    this.OperatorNumber = "OperatorNumber";
}
function FinishRule() {
    this.FinishAll = "FinishAll";
    this.SpecifyNum = "SpecifyNum";
    this.SpecifyPercent = "SpecifyPercent";
}
function ParticipantorType() {
    this.Person = "Person";
    this.Role = "Role";
    this.Org = "Org";
}
function ParticipantType() {
    this.Participantor = "Participantor";
    this.ProcessStarter = "ProcessStarter";
    this.SpecialActivity = "SpecialActivity";
    this.RelevantData = "RelevantData";
    this.CustomRegular = "CustomRegular";
    this.ProcessExecutor = "ProcessExecutor";
    this.RelateRegular = "RelateRegular";
}
function FreeRangeStrategy() {
    this.FreeWithinProcess = "FreeWithinProcess";
    this.FreeWithinActivities = "FreeWithinActivities";
    this.FreeWithinNextActivites = "FreeWithinNextActivites";
}
function TimeLimitStrategy() {
    this.LimitTime = "setLimit";
    this.RelevantLimitTime = "RelevantLimitTime";
}
function RemindStrategy() {
    this.RemindLimtTime = "RemindLimtTime";
    this.RemindRelevantLimitTime = "RemindRelevantLimitTime";
}
function InvokePattern() {
    this.Synchronous = "Synchronous";
    this.Asynchronous = "Asynchronous";
}
function TriggerEventType() {
    ActivityBeforeCreate = 1;
    ActivityBeforeStart = 2;
    ActivityAfterStart = 3;
    ActivityAfterOverTime = 4;
    ActivityAfterTerminate = 5;
    ActivityCompleted = 6;
    ActivityBeforeRemind = 7;
    WorkItemBeforeCreate = 21;
    WorkItemAtferCreate = 22;
    WorkItemExecuting = 23;
    WorkItemCompleted = 24;
    WorkItemError = 25;
    WorkItemCanncel = 26;
    WorkItemOverTime = 27;
    WorkItemSuspended = 28;
}
function ActionPattern() {
    this.Method = "Method";
    this.WebService = "WebService";
    this.BusinessOperation = "BusinessOperation";
}
function ParameterDirection() {
    this.In = "In";
    this.Out = "Out";
    this.Ref = "Ref";
}
function ActivateRuleType() {
    this.DirectRunning = "DirectRunning";
    this.WaitActivate = "WaitActivate";
    this.AutoSkip = "AutoSkip";
}
function ResetParticipant() {
    this.FirstParticipantor = 1;// "FirstParticipantor";
    this.LastParticipantor = 2;// "LastParticipantor";
}

function removeTab(remove) {
    var tabs = $('#tabcontainer').find("li");
    tabs.each(function (i) {
        if (i > 0) {
            $("#tab" + this.id).hide();
            if (remove) $(this).remove();
        }
        else {
            this.className = "tabBg7";
        }
    });
}
function initStartActivity(activity) {
    removeTab(true);
    $("#row_joinType").remove();
    $("#row_propertyandproxy").remove();
    $("#row_priority").remove();
    $("#row_businessSet").remove();
    $("#row_isSpecifyURL").remove();
    $("#row_urltype").remove();
    $("#row_CustomizeURL").remove();
    $("#row_description").css("clear", "both");
}
function initManualActivity(activity) {
    if (activity.participant && activity.participant.participantType) {
        switch (activity.participant.participantType) {
            case "Participantor":
                $("#rblOrganization").attr("checked", "checked");
                $("#txtParticipantRule").attr("disabled", "disabled");
                $("#txtspecialActivity").attr("disabled", "disabled");
                $("#txtParticipantRule").val("");
                $("#txtspecialActivity").val("");
                break;
            case "ProcessStarter":
                $("#rblProcessStarter").attr("checked", "checked");
                $("#txtParticipantRule").attr("disabled", "disabled");
                $("#txtspecialActivity").attr("disabled", "disabled");
                $("#txtParticipantRule").val("");
                $("#txtspecialActivity").val(""); break;
            case "ProcessExecutor":
                $("#rblSpecialActivity").attr("checked", "checked");
                $("#txtspecialActivity").val(activity.participant.participantValue);
                $("#txtParticipantRule").val("");
                $("#txtParticipantRule").attr("disabled", "disabled");
                $("#txtspecialActivity").removeAttr("disabled");
                $("#orgorrole").attr("disabled", "disabled");
                break;
            case "CustomRegular":
                $("#rblParticipantRule").attr("checked", "checked");
                $("#txtParticipantRule").val(activity.participant.participantValue);
                $("#txtspecialActivity").val("");
                $("#txtspecialActivity").attr("disabled", "disabled");
                $("#txtParticipantRule").removeAttr("disabled");
                $("#orgorrole").attr("disabled", "disabled");
              //  $scope.Participant().ParticipantType = Type.CustomRegular;
                break;
        }
    }
    else {
        activity.participant.participantType = "Participantor";
        $("#txtParticipantRule").attr("disabled", "disabled");
        $("#txtspecialActivity").attr("disabled", "disabled");
        $("#txtParticipantRule").val("");
        $("#txtspecialActivity").val("");
    }
    if ($("#chbIsTimeLimitSet").attr("Checked")) {
        $("#txtLimitTimeHour").removeAttr("disabled");
        $("#txtLimitTimeMinute").removeAttr("disabled");
        $("#txtRelevantData").removeAttr("disabled");
        $("#chbIsSendMessageForOvertime").removeAttr("disabled");
        $("#txtRemindLimtTimeHour").removeAttr("disabled");
        $("#txtRemindLimtTimeMinute").removeAttr("disabled");
        $("#txtRemindRelevantData").removeAttr("disabled");
        $("#chbisSendMessageForRemind").removeAttr("disabled");
        $("#rabRelevantLimitTime").removeAttr("disabled");
        $("#rabTimeLimitStrategy").removeAttr("disabled");
        $("#rabRemindLimtTime").removeAttr("disabled");
        $("#rabRemindRelevantLimitTime").removeAttr("disabled");
    }
    else {
        $("#txtLimitTimeHour").attr("disabled", "disabled");
        $("#txtLimitTimeMinute").attr("disabled", "disabled");
        $("#txtRelevantData").attr("disabled", "disabled");
        $("#chbIsSendMessageForOvertime").attr("disabled", "disabled");
        $("#txtRemindLimtTimeHour").attr("disabled", "disabled");
        $("#txtRemindLimtTimeMinute").attr("disabled", "disabled");
        $("#txtRemindRelevantData").attr("disabled", "disabled");
        $("#chbisSendMessageForRemind").attr("disabled", "disabled");
        $("#rabRelevantLimitTime").attr("disabled", "disabled");
        $("#rabTimeLimitStrategy").attr("disabled", "disabled");
        $("#rabRemindLimtTime").attr("disabled", "disabled");
        $("#rabRemindRelevantLimitTime").attr("disabled", "disabled");

    }
    if (activity.customURL) {
        if (activity.customURL.urlType == "CustomURL") {
            $("#rbCustomizeURL").attr("checked", "checked");
            $("#txtSpecifyURL").removeAttr("disabled");
            activity.customURL.urlType = "CustomURL";
        }
        else {
            $("#rbDefaultURL").attr("checked", "checked");
            $("#txtSpecifyURL").attr("disabled", "disabled");
            activity.customURL.urlType = "DefaultURL";
        }
    }
    var activateRuleType = new ActivateRuleType();
    var resetParticipant = new ResetParticipant();
    if (activity.activateRule) {
        if (activity.activateRule.activateRuleType == activateRuleType.DirectRunning) {
            $("#rblDirectRunning").attr("Checked", "checked");
        }
        else if (activity.activateRule.activateRuleType == activateRuleType.WaitActivate) {
            $("#rblDisenabled").attr("Checked", "checked");
        }
        else if (activity.activateRule.activateRuleType == activateRuleType.AutoSkip)//可选规则
        {
            $("#rblAutoAfter").attr("Checked", "checked");
        }
        if (activity.resetParticipant == resetParticipant.FirstParticipantor) {
            $("#rbFirstParticipantor").attr("Checked", "checked");
        }
        else if (activity.resetParticipant == resetParticipant.LastParticipantor)  //重新启动规则
        {
            $("#rbLastParticipantor").attr("Checked", "checked");
        }
    }
    if (activity.freeFlowRule) {
        var freeRangeStrategy = new FreeRangeStrategy();
        if (activity.freeFlowRule.freeRangeStrategy == freeRangeStrategy.FreeWithinProcess) {
            $("#rblFreeWithinProcess").attr("Checked", "checked");
        }
        else if (activity.freeFlowRule.freeRangeStrategy == freeRangeStrategy.FreeWithinActivities) {
            $("#rblFreeWithinActivities").attr("Checked", "checked");
        }
        else if (activity.freeFlowRule.freeRangeStrategy == freeRangeStrategy.FreeWithinNextActivites) {
            $("#rblFreeWithinNextActivites").attr("Checked", "checked");
        }; //自由范围设置策略
    }

    if ($("#chbIsFreeActivity").attr("checked")) {
        $(".FreeActivity").find("input").removeAttr("disabled");
    }
    else {
        $(".FreeActivity").find("input").attr("disabled", "disabled");
    }
    if ($("#chbIsMulWIValid").attr("checked")) {
        $(".MulWIValidConfigure").find("input:radio").removeAttr("disabled");
        $(".MulWIValidConfigure").find("input:checkbox").removeAttr("disabled");
    }
    else {
        $(".MulWIValidConfigure").find("input").attr("disabled", "disabled");
    }
    if (activity.multiWorkItem && activity.multiWorkItem.workItemNumStrategy) {
        var workItemNumStrategy = new WorkItemNumStrategy();
        var finishRule = new FinishRule();
        if (activity.multiWorkItem.workItemNumStrategy == workItemNumStrategy.ParticipantNumber) {
            $("#rblParticipantNumber").attr("checked", "checked");
        }
        else {
            $("#rblOperatorNumber").attr("checked", "checked");
        }
        if (activity.multiWorkItem.isSequentialExecute)//顺序执行工作项
        {
            $("#rabYIsSequentialExecute").attr("checked", "checked");
        }
        else {
            $("#rabNIsSequentialExecute").attr("checked", "checked");
        }
        if (activity.multiWorkItem.finishRule == finishRule.FinishAll)  //完成规则设定
        {
            $("#rblFinishAll").attr("checked", "checked");
        }
        else if (activity.multiWorkItem.finishRule == finishRule.SpecifyNum) {
            $("#rblSpecifyNum").attr("checked", "checked");
        }
        else {
            $("#rblSpecifyPercent").attr("checked", "checked");
        }
        if (activity.multiWorkItem.isAutoCancel)//顺序执行工作项
        {
            $("#rabYIsAutoCancel").attr("checked", "checked");
        }
        else {
            $("#rabNIsAutoCancel").attr("checked", "checked");
        }
    }
    if (activity.timeLimit.timeLimitInfo) {
        var timeLimitStrategy = new TimeLimitStrategy();
        if (activity.timeLimit.timeLimitInfo.timeLimitStrategy == timeLimitStrategy.LimitTime) {
            $("#rabTimeLimitStrategy").attr("checked", "checked");
        }
        else {
            $("#rabRelevantLimitTime").attr("checked", "checked");
        }
    }
    if (activity.timeLimit.RemindInfo) {
        var remindStrategy = new RemindStrategy();
        if (activity.timeLimit.remindInfo.remindStrategy == remindStrategy.RemindLimtTime) {
            $("#rabRemindLimtTime").attr("checked", "checked");
        }
        else {
            $("#rabRemindRelevantLimitTime").attr("checked", "checked");
        }
    }
}
function initRouterActivity() {
    removeTab(true);
    $("#row_propertyandproxy").remove();
    $("#row_priority").remove();
    $("#row_businessSet").remove();
    $("#row_isSpecifyURL").remove();
    $("#row_urltype").remove();
    $("#row_CustomizeURL").remove();
}
function initSubflowActivity(activity) {
    initManualActivity(activity);
}
function initAutoActivity(activity) {
    $("#participantor").remove();
    $("#tabparticipantor").hide();
    $("#task").remove();
    $("#tabtask").hide();
    $("#limit").remove();
    $("#tablimit").hide();
    $("#task").remove();
    $("#tabtask").hide();
    $("#event").remove();
    $("#tabevent").hide();
    $("#freeActivity").remove();
    $("#tabfreeActivity").hide();
    $("#row_propertyandproxy").remove();
    $("#row_priority").remove();
}
function initEndActivity(activity) {
    removeTab(true);
    $("#row_splitType").remove();
    $("#row_propertyandproxy").remove();
    $("#row_priority").remove();
    $("#row_businessSet").remove();
    $("#row_isSpecifyURL").remove();
    $("#row_urltype").remove();
    $("#row_CustomizeURL").remove();
    $("#row_joinType").css("clear", "both");
    $("#row_description").css("clear", "both");
}
function initProcessActivity(activity) {
    initManualActivity(activity);
}
function ActivityDetailCtrl($scope) {
    $scope.processDefine = window.parent.$("#actionDialog").find("#bg_div_iframe")[0].contentWindow.processDefine;
    $scope.activity = function () {
        var currentActivity = "";
        //var processDefID = $.query.get("processDefID");
        var activityID = $.query.get("activityID");
        angular.forEach($scope.processDefine.activities, function (activitie) {
            if (activitie.id == activityID) {//&&processDefID == $scope.processDefine.id) {
            	currentActivity= activitie;
               if (activitie.newID==null) {
            	   activitie.newID = activityID;
                }
                if (!activitie.splitType) {
                    var splitType = new SplitType();
                    activitie.splitType = splitType.XOR;
                }
                if (!activitie.joinType) {
                    var joinType = new JoinType();
                    activitie.joinType = joinType.AND;
                }
                if (activitie.multiWorkItem) {
                    if (!activitie.multiWorkItem.finishRquiredNum) {
                    	activitie.multiWorkItem.finishRquiredNum = 0;
                    }
                    if (!activitie.multiWorkItem.finishRequiredPercent) {
                    	activitie.multiWorkItem.finishRequiredPercent = 0;
                    }
                }
                    window.parent.$("#actionDialog").find("#bg_div_iframe")[0].contentWindow.editActivityName(activitie.id, activitie.name);
            }
        });
        return currentActivity;
    };
    $scope.participant = function () {
        var participant = new Object();
        if ($scope.activity().participant) {
            participant = $scope.activity().participant;
        }
        else {
            if ($scope.activity().activityType == "ManualActivity" || $scope.activity().activityType == 2) {
                $scope.activity().participant = participant;
            }
        }
        return participant;
    };
    $scope.customURL = function () {
        var customURL = new Object();
        if ($scope.activity().customURL) {
        	customURL = $scope.activity().customURL;
        }
        else {
            if ($scope.activity().activityType == "ManualActivity" || $scope.activity().activityType == 2) {
                $scope.activity().customURL = customURL;
            }
        }
        return customURL;
    }
    $scope.businessSet = function (url) {
        if (url == "rbCustomizeURL") {
            $("#rbCustomizeURL").attr("checked", "checked");
            $("#txtSpecifyURL").removeAttr("disabled");
            //  $("#rbDefaultURL").attr("disabled", "disabled");
            $scope.customURL().urlType = "CustomURL";
            
        }
        else {
            $("#rbDefaultURL").attr("checked", "checked");
            $("#txtSpecifyURL").attr("disabled", "disabled");
            $scope.customURL().urlType = "DefaultURL";
        }
    }
    $scope.addParticipant = function () {
        var participantors = openOperateDialog("添加参与者", "../../Workflow/ChooseParticipantor.aspx", 980, 700, true, 1);
        if (participantors == null) return;
        if (participantors.length != 0) {
            var tbody = $("tbody", "#gvOrgizationOrRole_container");
            var index = $("tr", tbody).length;
            var count = participantors.length;
            for (var i = 0; i < participantors.length; i++) {
                if ($("tr", tbody).find("td:contains('" + participantors[i].id + "')").length > 0)//存在的不添加
                    continue;
                var participantorType = new ParticipantorType();
                switch (participantors[i].type) {
                    case "人员": participantors[i].type = participantorType.Person; break;
                    case "角色": participantors[i].type = participantorType.Role; break;
                    case "组织": participantors[i].type = participantorType.Org; break;
                }
                if (!$scope.participant().participantors) {
                    var tmpParticipantors = new Array();
                    tmpParticipantors.push({ id: participantors[i].id, name: participantors[i].name, sortOrder: count + index - i, participantorType: participantors[i].type });
                    $scope.activity().participant = new Object();
                    $scope.activity().participant.participantors = tmpParticipantors;
                }
                else {
                    $scope.activity().participant.participantors.push({ id: participantors[i].id, name: participantors[i].name, sortOrder: count + index - i, participantorType: participantors[i].type });
                }
            }
        }
    };
    $scope.delParticipantor = function () {
        if (!confirm("是否确定删除记录？")) {
            return false;
        }
        var table = $("#gvOrgizationOrRole_container");
        var rad = table.find(":checked ").first();
        //alert($(rad).val());
        angular.forEach($scope.participant().participantors, function (participantor) {
            if ($(rad).val() == participantor.id) {
                var position = $scope.activity().participant.participantors.indexOf(participantor);
                // alert("xuan");
                $scope.activity().participant.participantors.splice(position, 1);
            }
        });

    };
    $scope.chooseParticipantorType = function (participantorType) {
        var type = new ParticipantType();
        if ("rblParticipantRule" == participantorType) {
            $("#txtParticipantRule").val($scope.participant().participantValue);
            $("#txtspecialActivity").val("");
            $("#txtspecialActivity").attr("disabled", "disabled");
            $("#txtParticipantRule").removeAttr("disabled");
            $("#orgorrole").attr("disabled", "disabled");
            $scope.participant().participantType = type.CustomRegular;
        }
        else if ("rblSpecialActivity" == participantorType) {
            $("#txtspecialActivity").val($scope.participant().participantValue);
            $("#txtParticipantRule").val("");
            $("#txtParticipantRule").attr("disabled", "disabled");
            $("#txtspecialActivity").removeAttr("disabled");
            $("#orgorrole").attr("disabled", "disabled");
            $scope.participant().participantType = type.ProcessExecutor;
        }
        else {
            if ("rblProcessStarter" == participantorType) {

                $scope.participant().participantType = type.ProcessExecutor;
            }
            else {
                $scope.participant().participantType = type.Participantor;
            }
            $("#txtParticipantRule").attr("disabled", "disabled");
            $("#txtspecialActivity").attr("disabled", "disabled");
            $("#txtParticipantRule").val("");
            $("#txtspecialActivity").val("");
        }
    };
    $scope.timeLimit = function () {
        var timeLimit = new Object();
        // alert($scope.activity().Participant);
        if ($scope.activity().timeLimit) {
            timeLimit = $scope.activity().timeLimit;
        }
        else {
            if ($scope.activity().activityType == "ManualActivity" || $scope.activity().activityType == 2) {

                $scope.activity().timeLimit = timeLimit;
            }
        }
        return timeLimit;
    };
    $scope.setLimit = function () {
        if ($("#chbIsTimeLimitSet").attr("Checked")) {
            $("#txtLimitTimeHour").removeAttr("disabled");
            $("#txtLimitTimeMinute").removeAttr("disabled");
            $("#txtRelevantData").removeAttr("disabled");
            $("#chbIsSendMessageForOvertime").removeAttr("disabled");
            $("#txtRemindLimtTimeHour").removeAttr("disabled");
            $("#txtRemindLimtTimeMinute").removeAttr("disabled");
            $("#txtRemindRelevantData").removeAttr("disabled");
            $("#chbisSendMessageForRemind").removeAttr("disabled");
            $("#rabRelevantLimitTime").removeAttr("disabled");
            $("#rabTimeLimitStrategy").removeAttr("disabled");
            $("#rabRemindLimtTime").removeAttr("disabled");
            $("#rabRemindRelevantLimitTime").removeAttr("disabled");
        }
        else {
            $("#txtLimitTimeHour").attr("disabled", "disabled");
            $("#txtLimitTimeMinute").attr("disabled", "disabled");
            $("#txtRelevantData").attr("disabled", "disabled");
            $("#chbIsSendMessageForOvertime").attr("disabled", "disabled");
            $("#txtRemindLimtTimeHour").attr("disabled", "disabled");
            $("#txtRemindLimtTimeMinute").attr("disabled", "disabled");
            $("#txtRemindRelevantData").attr("disabled", "disabled");
            $("#chbisSendMessageForRemind").attr("disabled", "disabled");
            $("#rabRelevantLimitTime").attr("disabled", "disabled");
            $("#rabTimeLimitStrategy").attr("disabled", "disabled");
            $("#rabRemindLimtTime").attr("disabled", "disabled");
            $("#rabRemindRelevantLimitTime").attr("disabled", "disabled");

        }
    };
    $scope.chooseLimit = function (id) {
        var timeLimitStrategy = new TimeLimitStrategy();
        if (id == "rabTimeLimitStrategy") {
            $("#txtRelevantData").attr("disabled", "disabled");
            $("#txtLimitTimeHour").removeAttr("disabled");
            $("#txtLimitTimeMinute").removeAttr("disabled");
            if (!$scope.timeLimit().timeLimitInfo) {
                $scope.timeLimit().timeLimitInfo = new Object();
            }
            $scope.timeLimit().timeLimitInfo.timeLimitStrategy = timeLimitStrategy.setLimit;
        }
        else {
            $("#txtLimitTimeHour").attr("disabled", "disabled");
            $("#txtLimitTimeMinute").attr("disabled", "disabled");
            //alert("1");
            $("#txtRelevantData").removeAttr("disabled");
            if (!$scope.timeLimit().timeLimitInfo) {
                $scope.timeLimit().timeLimitInfo = new Object();
            }
            $scope.timeLimit().timeLimitInfo.timeLimitStrategy = timeLimitStrategy.RelevantLimitTime;
        }
    };
    $scope.chooseRemind = function (id) {
        var remindStrategy = new RemindStrategy();
        if (id == "rabRemindLimtTime") {
            $("#txtRemindRelevantData").attr("disabled", "disabled");
            $("#txtRemindLimtTimeMinute").removeAttr("disabled");
            $("#txtRemindLimtTimeHour").removeAttr("disabled");
            if (!$scope.timeLimit().remindInfo) {
                $scope.timeLimit().remindInfo = new Object();
            }
            $scope.timeLimit().remindInfo.remindStrategy = remindStrategy.RemindLimtTime;
        }
        else {
            $("#txtRemindLimtTimeHour").attr("disabled", "disabled");
            $("#txtRemindLimtTimeMinute").attr("disabled", "disabled");
            $("#txtRemindRelevantData").removeAttr("disabled");
            if (!$scope.timeLimit().remindInfo) {
                $scope.timeLimit().remindInfo = new Object();
            }
            $scope.timeLimit().remindInfo.remindStrategy = remindStrategy.RemindRelevantLimitTime;
        }
    };
    $scope.triggerEvents = function () {
        var triggerEvents = $scope.activity().triggerEvents;
        return triggerEvents;
    };
    $scope.addTriggerEvent = function () {
        //var tbody = $("tbody", "#tblTriggerEvent");
        if (!$scope.triggerEvents()) {
            $scope.activity().triggerEvents = new Array();
        }
        //alert($scope.triggerEvents());
        // $scope.triggerEvents().push({ ID: new Date().getTime()});
        $scope.triggerEvents().push({ id: new Date().getTime(), eventAction: "", invokePattern: "Synchronous", triggerEventType: "ActivityBeforeCreate" });
    };
    $scope.delTriggerEvent = function () {
        if (!confirm("是否确定删除记录？")) {
            return false;
        }
        var table = $("#tblTriggerEvent");
        var rad = table.find("input:checked ").first();
        // alert($(rad).val());
        angular.forEach($scope.triggerEvents(), function (triggerEvent) {
            if ($(rad).val() == triggerEvent.id) {
                var position = $scope.triggerEvents().indexOf(triggerEvent);
                // alert("xuan");
                $scope.triggerEvents().splice(position, 1);
            }
        });
    };
    $scope.rollBack = function () {
        var rollBack = new Object();
        // alert($scope.activity().Participant);
        if ($scope.activity().rollBack) {
            rollBack = $scope.activity().rollBack;
        }
        else {
            if ($scope.activity().activityType == "ManualActivity" || $scope.activity().activityType == 2) {

                $scope.activity().rollBack = rollBack;
            }
        }
        return rollBack;
    };
    $scope.activateRule = function () {
        var activateRule = new Object();
        if ($scope.activity().activateRule) {
            activateRule = $scope.activity().activateRule;
        }
        else {
            if ($scope.activity().activityType == "ManualActivity" || $scope.activity().activityType == 2) {
                $scope.activity().activateRule = activateRule;
            }
        }
        return activateRule;
    };
    $scope.activateRuleType = function (id) {
        var activateRuleType = new ActivateRuleType();
        if (id == "rblDirectRunning") {
            $scope.activateRule().activateRuleType = activateRuleType.DirectRunning;
        }
        else if (id == "rblDisenabled") {
            $scope.activateRule().activateRuleType = activateRuleType.WaitActivate;
        }
        else {
            $scope.activateRule().activateRuleType = activateRuleType.AutoSkip;
        }
    };
    $scope.resetParticipant = function (id) {
        var resetParticipant = new ResetParticipant();
        if (id == "rbFirstParticipantor") {
            $scope.activity().resetParticipant = resetParticipant.FirstParticipantor;
        } else {
            $scope.activity().resetParticipant = resetParticipant.LastParticipantor;
        }
    }
    $scope.resetURL = function () {
        var resetURL = new Object();
        if ($scope.activity().resetURL) {
            resetURL = $scope.activity().resetURL;
        }
        else {
            if ($scope.activity().activityType == "ManualActivity" || $scope.activity().activityType == 2) {
                $scope.activity().resetURL = resetURL;
            }
        }
        return resetURL;
    };

    $scope.freeFlowRule = function () {
        var freeFlowRule = new Object();
        if ($scope.activity().freeFlowRule) {
            freeFlowRule = $scope.activity().freeFlowRule;
        }
        else {
            if ($scope.activity().activityType == "ManualActivity" || $scope.activity().activityType == 2) {
                $scope.activity().freeFlowRule = freeFlowRule;
            }
        }
        return freeFlowRule;
    };
    $scope.addFreeRange = function () {
        //var tbody = $("tbody", "#tblTriggerEvent");
        if (!$scope.freeFlowRule().freeRangeActivities) {
            $scope.activity().freeFlowRule.freeRangeActivities = new Array();
        }
        //  alert($scope.freeFlowRule().freeRangeActivities);
        // $scope.triggerEvents().push({ ID: new Date().getTime()});
        $scope.freeFlowRule().freeRangeActivities.push({ id: new Date().getTime() });
    };
    $scope.delFreeRange = function () {
        if (!confirm("是否确定删除记录？")) {
            return false;
        }
        var table = $("#tblFreeRange");
        var rad = table.find("input:checked ").first();
        angular.forEach($scope.freeFlowRule().freeRangeActivities, function (freeRangeActivity) {
            if ($(rad).val() == freeRangeActivity.id) {
                var position = $scope.freeFlowRule().freeRangeActivities.indexOf(freeRangeActivity);
                // alert("xuan");
                $scope.freeFlowRule().freeRangeActivities.splice(position, 1);
            }
        });
    };
    $scope.chooseFree = function () {
        if ($("#chbIsFreeActivity").attr("checked")) {
            //$(".FreeActivity").find("input:radio").removeAttr("disabled");
            //$(".FreeActivity").find("input:checkbox").removeAttr("disabled");
            $(".FreeActivity").find("input").removeAttr("disabled");
        }
        else {
            //$(".FreeActivity").find("input:radio").attr("disabled", "disabled");
            //$(".FreeActivity").find("input:checkbox").attr("disabled", "disabled");
            $(".FreeActivity").find("input").attr("disabled", "disabled");
        }
    };
    $scope.choosescopeFree = function (id) {
        var freeRangeStrategy = new FreeRangeStrategy();
        if (id == "rblFreeWithinProcess") {
            $scope.freeFlowRule().freeRangeStrategy = freeRangeStrategy.FreeWithinProcess;
        }
        else if (id == "rblFreeWithinActivities") {
            $scope.freeFlowRule().freeRangeStrategy = freeRangeStrategy.FreeWithinActivities;
        }
        else {
            $scope.freeFlowRule().freeRangeStrategy = freeRangeStrategy.FreeWithinNextActivites;
        }
    };
    $scope.form = function () {
        var form = new Object();
        if ($scope.activity().form) {
            form = $scope.activity().form;
        }
        else {
            if ($scope.activity().activityType == "ManualActivity" || $scope.activity().activityType == 2) {
                $scope.activity().form = form;
            }
        }
        return form;
    };
    $scope.formDesigner = function () {
       // var form = window.parent.parent.parent.showDialog("actionDialog3", "表单设计器", "/FormDesigner/Home/FormDesigner", 1250, 700);
      // var form = openOperateDialog("表单设计器", "/FormDesigner/Home/FormDesigner?ActivityID=" + $scope.activity().id+"&&ProcessDefID=" + $.query.get("ProcessDefID"), 1250, 700, true, 1);
        var form = openDialog2({ title: "表单设计器", url: "/FormDesigner/Home/FormDesigner?ActivityID=" + $scope.activity().id + "&&ProcessDefID=" + $.query.get("ProcessDefID"), dialogType: 1, showModal: true,height:screen.height-80,width:screen.width-10,windowStyle: { style: "dialogLeft:0;dialogTop:-100;edge: Raised; center: Yes; resizable: Yes; status: no;scrollbars=no;", auguments: window } });
        //alert(formResult);
       //alert(form);
        if (!form || !form.fields || form.fields.length == 0) return;
        $scope.activity().form = form;
    };
    $scope.executeOperate = function (field) {
    	var fieldName="";
        if (field.indexOf("up$#") > 0) {
            fieldName = field.substring(0, field.indexOf("up$#"));
        }
        else {
            fieldName = field.substring(0, field.indexOf("down$#"));
        }
        angular.forEach($scope.form().fields, function (fieldItem) {
            var i = 0;
            if (fieldItem.name == fieldName) {
                var position = $scope.form().fields.indexOf(fieldItem);
                if (position > 0 && position < $scope.form().fields.length) {
                    $scope.form().fields.splice(position, 1);
                    if (field.indexOf("up$#") > 0) {
                        $scope.form().fields.splice(position - 1, 0, fieldItem);
                        i = 1;
                    }
                    else {
                        $scope.form().fields.splice(position + 1, 0, fieldItem);
                        i = 1;
                    }
                }
                else if (position == 0 && field.indexOf("down$#") > 0) {
                    $scope.form().fields.splice(position, 1);
                    $scope.form().fields.splice(position + 1, 0, fieldItem);
                    i = 1;
                }

            }
            if (i == 1) {
                fieldName = null;
            }
        });


    };
    $scope.chooseParameter = function () {
        var form = openOperateDialog("选择表单来源", "/Workflow/ActivityManager.aspx?Entry=ChooseActivity&ProcessDefID=" + $.query.get("ProcessDefID"), 550, 300, true, 1);
        if (!form || !form.fields || form.fields.length == 0) return;
        //for (var i = 0; i < form.fields.length; i++) {
        //    $scope.form().fields.push({X:form.fields[i],Y:form.Field[i].Y,Width:form.Field[i].Width,Height:form.Field[i].Height, Name: form.fields[i].name, Required: form.fields[i].Required, SortOrder: form.fields[i].SortOrder, Text: form.fields[i].Text, DefaultValue: form.fields[i].DefaultValue, DataType: form.fields[i].DataType, ControlType: form.fields[i].ControlType, AccessPattern: form.fields[i].AccessPattern });
        //}
        $scope.activity().form = form;
        $scope.form().dataSource = form.dataSource;
    };
    $scope.addParameter = function () {
        if (!$scope.form().fields) {
            $scope.activity().form.fields = new Array();
        }
        $scope.form().fields.push({ name: new Date().getTime(), dataType: "Integer", controlType: "TextBox", accessPattern: "Write" });
    };
    $scope.delParameter = function () {
        var table = $("#tblParameter");
        var rad = table.find("input[type='radio']:checked").first();
        if (table.find("input:checked")[0] == undefined) {
            alert("请先选择要删除的记录");
            return false;
        }
        if (!confirm("是否确定删除记录？")) {
            return false;
        }
        angular.forEach($scope.form().fields, function (field) {
            if ($(rad).val() == field.name) {
                var position = $scope.form().fields.indexOf(field);
                $scope.form().fields.splice(position, 1);
            }
        });
    };
    $scope.configureField = function (field) {
        var controlType = $.trim(field.controlType);
        if (controlType == "ChooseBox") {
            var fieldset = openOperateDialog("设置" + controlType + "属性", "/Workflow/FormControlConfigure.aspx?type=ChooseBox&url=" + field.url, 550, 300, true, 1);
            field.url = fieldset.url;
        }
        else if (controlType == "TextBox") {
            var rows = field.rows || 1;
            var cols = field.cols || 1;
            var fieldset = openOperateDialog("设置" + controlType + "属性", "/Workflow/FormControlConfigure.aspx?type=TextBox&rows=" + rows + "&cols=" + cols, 550, 300, true, 1);
            field.cols = fieldset.cols;
            field.rows = fieldset.rows;
        }
        else if (controlType == "SingleCombox" || controlType == "MultiCombox") {
            var datasource = openOperateDialog("设置" + controlType + "属性", "/Plugins/Administration/Pages/ChooseDict.aspx", 550, 300, true, 1);
            if (datasource)
                field.dataSource = datasource;
        }
    };
    $scope.multiWorkItem = function () {
        var multiWorkItem = new Object();
        if ($scope.activity().multiWorkItem) {
            multiWorkItem = $scope.activity().multiWorkItem;
        }
        else {
            if ($scope.activity().activityType == "ManualActivity" || $scope.activity().activityType == 2) {
                $scope.activity().multiWorkItem = multiWorkItem;

            }
        }
        return multiWorkItem;
    };
    $scope.enableMulwi = function () {
        if ($("#chbIsMulWIValid").attr("checked")) {
            $(".MulWIValidConfigure").find("input").removeAttr("disabled");
        }
        else {
            $(".MulWIValidConfigure").find("input").attr("disabled", "disabled");
        }
    };
    $scope.workItemNum = function (id) {
        var workItemNumStrategy = new WorkItemNumStrategy();
        if (id == "rblParticipantNumber") {
            $scope.multiWorkItem().workItemNumStrategy = workItemNumStrategy.ParticipantNumber;
        }
        else {
            $scope.multiWorkItem().workItemNumStrategy = workItemNumStrategy.OperatorNumber;
        }
    };
    $scope.finishRuleType = function (id) {
        var finishRule = new FinishRule();
        if (id == "rblSpecifyNum") {
            $scope.multiWorkItem().finishRule = finishRule.SpecifyNum;
            $("#txtFinishRquiredNum").removeAttr("disabled");
            $("#txtFinishRequiredPercent").attr("disabled", "disabled");
        }
        else if (id == "rblFinishAll") {
            $scope.multiWorkItem().finishRule = finishRule.FinishAll;
            $("#txtFinishRquiredNum").attr("disabled", "disabled");
            $("#txtFinishRequiredPercent").attr("disabled", "disabled");
        } else {
            $scope.multiWorkItem().finishRule = finishRule.SpecifyPercent;
            $("#txtFinishRquiredNum").attr("disabled", "disabled");
            $("#txtFinishRequiredPercent").removeAttr("disabled");
        }
    };
    $scope.sequentialExecute = function (id) {
        if (id == "rabYIsSequentialExecute") {
            $scope.multiWorkItem().isSequentialExecute = true;
        }
        else {
            $scope.multiWorkItem().isSequentialExecute = false;
        }
    };
    $scope.autoCancel = function (id) {
        if (id == "rabYIsAutoCancel") {
            $scope.multiWorkItem().isAutoCancel = true;
        }
        else {
            $scope.multiWorkItem().isAutoCancel = false;
        }
    };
}
