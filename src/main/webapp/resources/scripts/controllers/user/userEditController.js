controllers.controller('UserEditController', function ($scope, $http, $modalInstance, user, onSuccess) {

    $scope.model = {};

    $http.get("/ws/users/groups/links").success(function(data) {
        $scope.model = data;
    });

    $scope.editedUser = user;

    $scope.saveUser = function () {
        $http({
            method: 'POST',
            url: '/ws/users',
            data: $scope.editedUser,
        }).error(function (data) {
            $scope.errors = data;
        }).success(function () {
            $scope.errors = {};
            $modalInstance.dismiss('cancel');
            onSuccess();
        })
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

});