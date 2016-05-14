controllers.controller("UserGroupOverviewController", function ($rootScope, $scope, $http, $modal, userGroupService) {

    $scope.model = {};

    $scope.loadGroups = function () {
        $http.get("/ws/users/groups/model").success(function (data) {
            $scope.model = data;
        })
    };

    $scope.edit = function (group) {
        editDialog(userGroupService.getUserGroupForEdit(group));
    };

    $scope.copy = function (group) {
        editDialog(userGroupService.getUserGroupForCopy(group));
    };

    $scope.create = function () {
        editDialog({});
    };

    var editDialog = function (userGroup) {
        $modal.open({
            templateUrl: 'user/user_group_edit.html',
            controller: 'UserGroupEditController',
            resolve: {
                group: function () {
                    return userGroup;
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