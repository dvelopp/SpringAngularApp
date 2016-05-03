controllers.controller("NavigationController", ['$rootScope', '$scope', '$http', '$location', '$sessionStorage', function ($rootScope, $scope, $http, $location, $sessionStorage) {

    $scope.$storage = $sessionStorage;
    $scope.model = {};

    var reloadNavigation = function(){
        $http.get("/ws/navigation/attributes").success(function(data) {
            $scope.model = data;
        })
    };

    $rootScope.$on("reloadNavigation", function(){
        reloadNavigation();
    });

    reloadNavigation();

    $scope.logout = function () {
        $http.post('logout', {}).success(function () {
            $sessionStorage.authenticated = false;
            $sessionStorage.principal = undefined;
            $location.path("/");
            $rootScope.$emit("reloadNavigation", {});
        }).error(function (data) {
            $sessionStorage.principal = undefined;
            $sessionStorage.authenticated = false;
        });
    }

}]);