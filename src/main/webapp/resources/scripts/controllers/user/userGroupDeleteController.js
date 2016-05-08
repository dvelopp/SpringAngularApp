controllers.controller('UserGroupDeleteController', function ($scope, $http, $modalInstance, group, onSuccess) {

    $scope.group = group;

    $scope.delete = function () {
        $http({
            method: 'DELETE',
            url: '/ws/users/groups/'+group.id,
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