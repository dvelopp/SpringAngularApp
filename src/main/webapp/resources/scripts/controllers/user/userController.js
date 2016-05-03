controllers.controller("UserOverviewController", function ($rootScope, $scope, $http, $modal) {

    $scope.model = {};
    $scope.validationErrors = {};
    $scope.editedUser;

    $scope.showDeletionDialog = false;

    $scope.edit = function (user) {
        $scope.editedUserLink = user;
        $scope.validationErrors = {};
        $scope.editedUser = angular.copy(user);
        if ($scope.editedUser.password) {
            $scope.editedUser.password = "******";
        }
        $scope.editDialog();
    };

    $scope.create = function () {
        $scope.validationErrors = {};
        $scope.editedUser = {};
        $scope.editDialog();
    };

    $scope.loadUsers = function () {
        $http.get("/ws/users").success(function (data) {
            $scope.model = data;
        })
    };

    $scope.editDialog = function () {
        $modal.open({
            templateUrl: 'user/user_edit.html',
            controller: 'UserEditController',
            resolve: {
                user: function () {
                    return $scope.editedUser;
                },
                onSuccess: function () {
                    return $scope.loadUsers;
                }
            }
        });
    };

    $scope.deleteUser = function (user) {
        $modal.open({
            templateUrl: 'user/user_delete.html',
            controller: 'UserDeleteController',
            resolve: {
                user:  function () {
                    return user;
                },
                onSuccess: function () {
                    return $scope.loadUsers;
                }
            }
        });
    };

    $scope.loadUsers();

});

