var demoBasic = angular.module('demoBasic', ['angular-growl', 'ngAnimate']);

demoBasic.config(['growlProvider', function (growlProvider) {
  growlProvider.globalTimeToLive(5000);
  growlProvider.onlyUniqueMessages(false);
}]);

demoBasic.controller('basicDemoCtrl', ['$scope', 'growl',
function($scope, growl) {
  $scope.basicUsage = function (type) {
    var config = {};
    switch (type) {
      case "success":
        growl.success("<b>I'm</b> a success message and not unique", config);
        break;
      case "info":
        growl.info("I'm an info message", config);
        break;
      case "warning":
        growl.warning("<strong style='color:red'>I'm the warning message</strong><h1 style='color:purple'>Purple Title</h1>", {title: "Warning!"});
        break;
      default: 
        growl.error("Ups, error message here!", config);
    }
  };
}]);