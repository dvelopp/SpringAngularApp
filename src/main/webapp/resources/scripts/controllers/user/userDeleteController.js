controllers.controller('UserDeleteController', function ($scope, $http, $modalInstance, user, onSuccess) {

    $scope.userToDelete = user;

    $scope.delete = function () {
        $http({
            method: 'DELETE',
            url: '/ws/users/'+$scope.userToDelete.id,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).error(function (data) {
            alert(data.message);
        }).success(function () {
            $modalInstance.dismiss('cancel');
            onSuccess();
        })
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

});