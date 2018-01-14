(function () {
    var app = angular.module("myapp", ['ngResource', 'ngRoute', 'http-auth-interceptor', 'ngAnimate', 'angular-spinkit', 'ngFileUpload', 'ngSanitize', 'ui.bootstrap']);
    /////////////////////////////////////////////////////////////////////////////////
    app.constant('USER_ROLES', {
        all: '*',
        admin: 'admin',
        user: 'user'
    });
    ////////////////////////////////////////////////////////////////////////////////////////
    app.config(function ($routeProvider, USER_ROLES) {

        $routeProvider.when("/login", {
            templateUrl: 'template/login.html',
            controller: 'LoginController',
            controllerAs: "vm",
            access: {
                loginRequired: false,
                authorizedRoles: [USER_ROLES.all]
            }
        }).when('/loading', {
            templateUrl: 'template/loading.html',
            access: {
                loginRequired: false,
                authorizedRoles: [USER_ROLES.all]
            }
        }).when('/', {
            redirectTo: '/home'
        }).when('/home', {
            templateUrl: 'template/home.html',
            controller: "HomeController",
            controllerAs: "vm",
            access: {
                loginRequired: true,
                authorizedRoles: [USER_ROLES.all]
            }
        }).when('/error/:code', {
            templateUrl: 'template/error.html',
            controller: "ErrorController",
            controllerAs: "vm",
            access: {
                loginRequired: false,
                authorizedRoles: [USER_ROLES.all]
            }
        }).when('/admin', {
            templateUrl: 'template/administration.html',
            controller: "AdministrationController",
            controllerAs: "vm",
            access: {
                loginRequired: true,
                authorizedRoles: [USER_ROLES.admin]
            }
        }).otherwise({
            redirectTo: '/error/404',
            access: {
                loginRequired: false,
                authorizedRoles: [USER_ROLES.all]
            }
        });
    })
    
    app.run(function ($rootScope, $location, $http, AuthSharedFactory, Session, USER_ROLES, $q, $timeout) {
        // Call when the 403 response is returned by the server
        $rootScope.$on('event:auth-forbidden', function (rejection) {
            $rootScope.$evalAsync(function () {
                $location.path('/error/403').replace();
            });
        });

        $rootScope.$on('$routeChangeStart', function (event, next) {
            if (next.originalPath === "/login" && $rootScope.authenticated) {
                event.preventDefault();
            } else if (next.access && next.access.loginRequired && !$rootScope.authenticated) {
                event.preventDefault();
                $rootScope.$broadcast("event:auth-loginRequired", {});
            } else if (next.access && !AuthSharedFactory.isAuthorized(next.access.authorizedRoles)) {
                event.preventDefault();
                $rootScope.$broadcast("event:auth-forbidden", {});
            }
        });

        // Call when the the client is confirmed
        $rootScope.$on('event:auth-loginConfirmed', function (event, data) {
            $rootScope.loadingAccount = false;
            var nextLocation = ($rootScope.requestedUrl ? $rootScope.requestedUrl : "/home");
            var delay = ($location.path() === "/loading" ? 1500 : 0);
            $timeout(function () {
                Session.create(data);
                $rootScope.account = Session;
                $rootScope.authenticated = true;
                $location.path(nextLocation).replace();
            }, delay);

        });

        // Call when the 401 response is returned by the server
        $rootScope.$on('event:auth-loginRequired', function (event, data) {
            if ($rootScope.loadingAccount && data.status !== 401) {
                $rootScope.requestedUrl = $location.path()
                $location.path('/loading');
            } else {
                Session.invalidate();
                $rootScope.authenticated = false;
                $rootScope.loadingAccount = false;
                $location.path('/login');
            }
        });

        $rootScope.$on('event:auth-loginCancelled', function () {
            $location.path('/login').replace();
        });

        // Get already authenticated user account
        AuthSharedFactory.getAccount();
    })

})();