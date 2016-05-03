controllers.controller('UserEditController', function ($scope, $http, $modalInstance, user, onSuccess) {

    $scope.model = {};

    $http.get("/ws/users/groups").success(function(data) {
        $scope.model = data;
    });

    $scope.editedUser = user;

    $scope.saveUser = function () {
        $http({
            method: 'POST',
            url: '/ws/users',
            data: $.param($scope.editedUser),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).error(function (data) {
            $scope.validationErrors = data;
        }).success(function () {
            $scope.validationErrors = {};
            $modalInstance.dismiss('cancel');
            onSuccess();
        })
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

});