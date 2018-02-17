(function () {
    'use strict';

    angular
        .module('myapp')
        .factory('UserFactory', userFactory);

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