(function () {
    'use strict';

    angular
        .module('myapp')
        .factory('userFactory', userFactory);

    userFactory.$inject = ['$http'];

    function userFactory($http) {
        var factory = {
            changePassword: function (newPass) {
                return $http.put("/user/update-pass", {}, {
                    params: {
                        newPass: newPass
                    }
                })
            }
        };

        return factory;


    }
})();