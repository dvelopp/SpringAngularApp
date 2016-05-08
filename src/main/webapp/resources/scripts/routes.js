app.config(function ($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl: 'home.html',
        controller: 'HomeController',
        controllerAs: 'controller',
        activeTab: 'home'
    }).when('/configuration', {
        templateUrl: 'configuration.html',
        controller: 'ConfigurationController',
        controllerAs: 'controller',
        activeTab: 'configuration'
    }).when('/login', {
        templateUrl: 'login.html',
        controller: 'LoginPageController',
        controllerAs: 'controller',
        activeTab: 'login'
    }).when('/users', {
        templateUrl: 'user/user_list.html',
        controller: 'UserOverviewController',
        controllerAs: 'controller'
    }).when('/users/edit', {
        templateUrl: 'user/user_edit.html',
        controller: 'UserEditController',
        controllerAs: 'controller'
    }).when('/userGroups', {
        templateUrl: 'user/user_group_list.html',
        controller: 'UserGroupOverviewController',
        controllerAs: 'controller'
    }).when('/userGroups/edit', {
        templateUrl: 'user/user_group_edit.html',
        controller: 'UserGroupEditController',
        controllerAs: 'controller'
    }).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});