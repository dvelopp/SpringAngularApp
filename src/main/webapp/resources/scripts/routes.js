app.config(function ($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl: 'home.html',
        controller: 'HomeController',
        controllerAs: 'controller'
    }).when('/configuration', {
        templateUrl: 'configuration.html',
        controller: 'ConfigurationController',
        controllerAs: 'controller'
    }).when('/login', {
        templateUrl: 'login.html',
        controller: 'LoginPageController',
        controllerAs: 'controller'
    }).when('/users', {
        templateUrl: 'user/user_list.html',
        controller: 'UserOverviewController',
        controllerAs: 'controller'
    }).when('/users/edit', {
        templateUrl: 'user/user_edit.html',
        controller: 'UserEditController',
        controllerAs: 'controller'
    }).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});