controllers.controller("UserGroupOverviewController", function ($rootScope, $scope, $http, $modal) {

    $scope.model = {};

    $scope.loadGroups = function () {
        $http.get("/ws/users/groups/model").success(function (data) {
            $scope.model = data;
        })
    };

    $scope.edit = function (group) {
        $scope.editedUserGroupLink = group;
        $scope.validationErrors = {};
        $scope.editedUserGroup = angular.copy(group);
        editDialog();
    };

    $scope.create = function () {
        $scope.validationErrors = {};
        $scope.editedUserGroup = {};
        editDialog();
    };


    var editDialog = function () {
        $modal.open({
            templateUrl: 'user/user_group_edit.html',
            controller: 'UserGroupEditController',
            resolve: {
                group: function () {
                    return $scope.editedUserGroup;
                },
                onSuccess: function () {
                    return $scope.loadGroups;
                }
            }
        });
    };

    $scope.delete = function (group) {
        $modal.open({
            templateUrl: 'user/user_group_delete.html',
            controller: 'UserGroupDeleteController',
            resolve: {
                group:  function () {
                    return group;
                },
                onSuccess: function () {
                    return $scope.loadGroups;
                }
            }
        });
    };

    $scope.loadGroups();

});