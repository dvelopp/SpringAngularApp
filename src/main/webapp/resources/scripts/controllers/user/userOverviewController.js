controllers.controller("UserOverviewController", function ($rootScope, $scope, $http, $modal, userService) {

    $scope.model = {};

    $scope.edit = function (user) {
        $scope.editDialog(userService.getUserForEdit(user));;
    };

    $scope.copy = function (user) {
        $scope.editDialog(userService.getUserForCopy(user));
    };

    $scope.create = function () {
        $scope.editDialog({});
    };

    $scope.loadModel = function () {
        $http.get("/ws/users/model").success(function (data) {
            $scope.model = data;
        })
    };

    $scope.editDialog = function (user) {
        $modal.open({
            templateUrl: 'user/user_edit.html',
            controller: 'UserEditController',
            resolve: {
                user: function () {
                    return user;
                },
                onSuccess: function () {
                    return $scope.loadModel;
                }
            }
        });
    };

    $scope.deleteDialog = function (user) {
        $modal.open({
            templateUrl: 'user/user_delete.html',
            controller: 'UserDeleteController',
            resolve: {
                user: function () {
                    return user;
                },
                onSuccess: function () {
                    return $scope.loadModel;
                }
            }
        });
    };

    $scope.loadModel();

});

