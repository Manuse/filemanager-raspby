(function () {
    'use strict';

    angular
        .module('myapp')
        .factory('FileFactory', fileFactory);

    function fileFactory($http, Session) {
        var factory = {
            getRoots: function () {
                return $http.get("/file/roots", {
                    params: {
                        userId: Session.id
                    }
                });
            },
            getFiles: function (device, path) {
                return $http.get("/file/files", {
                    params: {
                        userId: Session.id,
                        device: device,
                        path: path
                    }
                })
            },
            getDownload: function (device, path) {
                return "/file/download?userId=" + Session.id + "&device=" + device + "&path=" + path;
            },
            mkDir: function (device, path, newDir) {
                return $http({
                    method: "POST",
                    url: "/file/mkdir",
                    params: {
                        userId: Session.id,
                        device: device,
                        path: path,
                        newDir: newDir
                    }
                })
            },
            deleteFile: function (device, path) {
                return $http.delete("/file/delete", {
                    params: {
                        userId: Session.id,
                        device: device,
                        path: path
                    }
                })
            }
        };

        return factory;

    }
})();