app.config(function($httpProvider) {
    $httpProvider.interceptors.push(function($q) {
        return {
            responseError: function(rejection) {
                if (rejection.status === 500) {
                    alert(rejection.data.message);
                }
                return $q.reject(rejection);
            }
        };
    });
});