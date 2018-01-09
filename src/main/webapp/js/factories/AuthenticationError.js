(function () {

    angular
        .module('myapp')
        .factory('AuthenticationError', authError);

    function authError() {
        var authenticationError = false;
        var service = {
            setAuthenticationError: function (state) {
                authenticationError = state;
            },
            getAuthenticationError: function () {
                return authenticationError;
            }
        };
        return service;
    }
})();