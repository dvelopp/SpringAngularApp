services.service('userService', function () {

    this.getUserForCopy = function (user) {
        var resultUser = angular.copy(user);
        resultUser.id = "";
        resultUser.userName = "";
        resultUser.password = "";
        return resultUser;
    },

    this.getUserForEdit = function (user) {
        var resultUser = angular.copy(user);
        if (resultUser.password) {
            resultUser.password = "******";
        }
        return resultUser;
    }

});