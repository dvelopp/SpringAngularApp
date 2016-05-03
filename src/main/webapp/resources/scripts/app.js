var app = angular.module('myApp',
    [
        'ui.bootstrap',
        'services',
        'controllers',
        'pascalprecht.translate',
        'ngCookies',
        'ui.router',
        'ngRoute',
        'ngStorage'
    ]
);

app.config(function ($translateProvider) {
    $translateProvider.useUrlLoader('/ws/messageBundle/properties');
    $translateProvider.useCookieStorage();
    $translateProvider.preferredLanguage('en');
    $translateProvider.fallbackLanguage('en');
});

app.directive('fieldValidationErrorPlaceHolder', function () {
    return {
        restrict: 'AE',
        templateUrl: '/resources/directives/fieldValidationErrorPlaceHolder.html',
        replace: true,
        scope: {
            'error': '=error'
        }
    };
});

var services = angular.module('services', ['ngResource']);
var controllers = angular.module('controllers', ['ngResource']);
