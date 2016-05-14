controllers.controller('UserGroupEditController', function ($scope, $http, $modalInstance, group, onSuccess) {
    $scope.multiSelectSettings = {displayProp: "name"};
    $scope.allAuthorities = [];
    $scope.selectedAuthorities = group.authorities ? group.authorities : [];

    $http.get("/ws/users/authorities/links").success(function(data) {
        $scope.allAuthorities = data.links;
    });

    $scope.editedUserGroup = group;

    $scope.saveGroup = function () {
        $http({
            method: 'POST',
            url: '/ws/users/groups',
            data: $scope.editedUserGroup,
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