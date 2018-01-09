(function () {

    angular
        .module('myapp')
        .factory('Session', session);

    function session() {
        this.create = function (data) {
            this.id = data.id;
            this.username = data.username;
            this.login = true;
            this.userRoles = [];
            angular.forEach(data.authorities, function (value, key) {
                this.push(value.name);
            }, this.userRoles);

        };

        this.invalidate = function () {
            this.id = null;
            this.username = null;
            this.userRoles = null;
            this.login = false;
        };

        return this;
    }

})();