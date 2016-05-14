services.service('userGroupService', function () {

    this.getUserGroupForCopy = function (group) {
        var resultUserGroup = angular.copy(group);
        resultUserGroup.id = "";
        resultUserGroup.name = "";
        return resultUserGroup;
    };

    this.getUserGroupForEdit = function (group) {
        return angular.copy(group);
    };

});