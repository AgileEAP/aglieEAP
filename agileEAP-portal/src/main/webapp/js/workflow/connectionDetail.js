function ConnectionCtrl($scope) {
    $scope.processDefine = window.parent.$("#actionDialog").find("#bg_div_iframe")[0].contentWindow.processDefine;
    $scope.transition = function () {
        var currentTransition = new Object();
        var processDefID = $.query.get("processDefID");
        var transitionID = $.query.get("transitionID");
        angular.forEach($scope.processDefine.transitions, function (transition) {
            if (processDefID == $scope.processDefine.id && transition.id == transitionID) {
            	currentTransition = transition;
            }
        });
        window.parent.$("#actionDialog").find("#bg_div_iframe")[0].contentWindow.setConnectionLabel(currentTransition.name);
        return currentTransition;
    };
}
