controllers.controller('LanguageController', ['$scope','$translate','$location', function($scope, $translate) {
    $scope.changeLanguage = function (locale) {
        $translate.use(locale);
    };
}]);