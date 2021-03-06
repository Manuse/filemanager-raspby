(function () {
    'use strict';

    angular
        .module('myapp')
        .factory('AdministrationFactory', administrationFactory);

    administrationFactory.$inject = ['$http'];

    function administrationFactory($http) {
        var factory = {
            getUsers: function () {
                return $http.get("/admin/users");
            },
            addUser: function (user) {
                return $http.post("/admin/user", user)
            },
            deleteUser: function (id) {
                return $http.delete("/admin/user", {
                    params: {
                        id: id
                    }
                })
            },
            changeEnabled: function (id) {
                return $http.put("/admin/change-enabled", {}, {
                    params: {
                        id: id
                    }
                })
            },
            getDevices: function () {
                return $http.get("/admin/file/current-devices")
            },
            getAccessPath: function (userId) {
                return $http.get("/admin/user-access-path", {
                    params: {
                        userId: userId
                    }
                })
            },
            addAccessPath: function (accessPath) {
                return $http.post("/admin/access-path", accessPath)
            },
            deleteAccessPath: function (accessPathId) {
                return $http.delete("/admin/access-path", {
                    params: {
                        accessPathId: accessPathId
                    }
                })
            },
            changePermission: function (accessPathId, permission) {
                return $http.put("/admin/change-permission", {}, {
                    params: {
                        accessPathId: accessPathId,
                        permission: permission
                    }
                })
            },
            checkPath: function (userId, device, path) {
                return $http.get("/admin/check-path", {
                    params: {
                        userId: userId,
                        device: device,
                        path: path
                    }
                })
            },
            changePasswordUser: function (userId, newPass) {
                return $http.put("/admin/user/update-pass", {}, {
                    params: {
                        id: userId,
                        newPass: newPass
                    }
                })
            },
            searchPath: function (device, partialPath) {
                return $http.get("/admin/search-path", {
                    params: {
                        device: device,
                        partialPath: partialPath
                    }
                })
            }
        };

        return factory;

    }
})();