(function() {


    angular
        .module('myapp')
        .factory('AuthSharedFactory', authSharedFactory)
        
        
            function authSharedFactory(AuthenticationError, $rootScope, $resource, $http, authService, Session) {
        
                var factory = {
                    login: function (userName, password, rememberMe) {
                        var config = {
                            params: {
                                username: userName,
                                password: password,
                                rememberme: rememberMe
                            },
                            ignoreAuthModule: 'ignoreAuthModule'
                        };
                        $http.post('authenticate', '', config)
                            .then(function (data) {
                                authService.loginConfirmed(data);
                            }, function (error) {
                                console.error(error)
                                AuthenticationError.setAuthenticationError(true);
                                Session.invalidate();
                            });
                    },
                    getAccount: function () {
                        $rootScope.loadingAccount = true;
                        $http.get('security/account')
                            .then(function (response) {
                                authService.loginConfirmed(response.data);
                            }, function (error) {
                                console.error(error)
                            });
                    },
                    isRealAuthorized:function(roles){
                        roles = roles.split(",")
                        var auth=false;
                        angular.forEach(roles, function (rol) { 
                            try {
                                if (Session.userRoles.indexOf(rol) !== -1) {
                                    auth= true;
                                }
                            } catch (error) {
                                console.log(error)
                            }
                        });
                        return auth;
                    },
                    isAuthorized: function (authorizedRoles) {
                        if (!angular.isArray(authorizedRoles)) {
                            if (authorizedRoles == '*') {
                                return true;
                            }
                            authorizedRoles = [authorizedRoles];
                        }
                        var isAuthorized = false;
                        angular.forEach(authorizedRoles, function (authorizedRole) { 
                            try {
                                var authorized = (!!Session.login && Session.userRoles.indexOf(authorizedRole) !== -1);
                                if (authorized || authorizedRole == '*') {
                                    isAuthorized = true;
                                }
                            } catch (error) {
            
                            }
                        });
                        return isAuthorized;
                    },
                    logout: function () {
                        $rootScope.authenticationError = false;
                        $rootScope.authenticated = false;
                        $rootScope.account = null;
                        $http.get('logout');
                        Session.invalidate();
                        authService.loginCancelled();
                    }
                };
                return factory;
            }
})();