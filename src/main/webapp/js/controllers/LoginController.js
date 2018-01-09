(function() {
    'use strict';

    angular
        .module('myapp')
        .controller("LoginController", loginController)
        
        function loginController(AuthenticationError, AuthSharedFactory, $http) {
            var vm = this;
            vm.rememberMe = true;
            vm.authenticationError = AuthenticationError.getAuthenticationError;
            vm.login = function () {
                AuthenticationError.setAuthenticationError(false);
                AuthSharedFactory.login(vm.username, vm.password, vm.rememberMe);
            }
        }
})();