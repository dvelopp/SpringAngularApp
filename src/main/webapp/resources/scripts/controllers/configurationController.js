controllers.controller("ConfigurationController", ['$rootScope', '$scope', '$http', '$location', function ($rootScope, $scope, $http) {

    $scope.model = {};

    $http.get("/ws/configuration/model").success(function(data) {
        $scope.model = data;
    })

}]);