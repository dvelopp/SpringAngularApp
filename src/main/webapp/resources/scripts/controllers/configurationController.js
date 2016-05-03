controllers.controller("ConfigurationController", ['$rootScope', '$scope', '$http', '$location', function ($rootScope, $scope, $http) {

    $scope.model = {};

    $http.get("/ws/configuration/attributes").success(function(data) {
        $scope.model = data;
    })

}]);