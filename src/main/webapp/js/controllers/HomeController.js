(function () {
    'use strict';

    angular
        .module('myapp')
        .controller("HomeController", homeController)

    homeController.$inject = ['$uibModal', 'FileFactory', '$timeout', 'Session', 'AuthSharedFactory', 'Upload', 'USER_ROLES', '$window']

    function homeController($uibModal, FileFactory, $timeout, Session, AuthSharedFactory, Upload, USER_ROLES, $window) {
        var vm = this;
        var hiddenPath = "";
        vm.alerts = [];
        vm.files = null;
        vm.file = null;
        vm.parent = null;
        vm.permission = 1;
        vm.porcent = 0;
        vm.newDir = "";
        vm.disabledUpload = false;
        vm.order = "name";
        vm.filterText = "";
        vm.filter = "both";
        

        if (Session.id === null || Session.id === undefined) {
            AuthSharedFactory.getAccount();
        }


        var timeout = function () {
            $timeout(function () {
                if (Session.id !== null && Session.id !== undefined) {
                    getRoots();
                } else {
                    timeout();
                }
            }, 1000);
        }

        timeout();

        vm.getParentFiles = function () {
            if (vm.parent.rootFile) {
                getRoots();
            } else {
                vm.getFiles(vm.parent)
            }
        }

        vm.getFiles = function (file) {
            FileFactory.getFiles(file.device, file.path.length == 0 ? "/" : file.path).then(function (response) {
                    vm.files = response.data.files;
                    vm.permission = response.data.permission;
                    var fil = vm.files[0];
                    //archivo padre
                    vm.parent = {
                        path: file.parentPath != null ? file.parentPath : "",
                        parentPath: parentPathEmpty(file.parentPath != null ? file.parentPath : ""),
                        device: file.device,
                    }
                    //true si se encuentra dentro de la raiz de un dispositivo
                    vm.parent.rootFile = file.rootFile == null ? true : vm.files[0] == null ? false : vm.files[0].rootFile;

                    //ruta de los archivos mostrado arriba
                    var currentPath = file.path;
                    if (vm.parent.rootFile) {
                        currentPath = "";
                        hiddenPath = "";
                    } else if (file.rootFile) {
                        hiddenPath = file.parentPath;
                    }
                    hidePath(currentPath);

                },
                function (error) {
                    console.error(error);
                });

        }

        vm.deleteFile = function (index, file) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'modals/mConfirmation.html',
                controller: 'ConfirmationController',
                controllerAs: 'vmm',
                size: 'sm',
                resolve: {
                    msg: function () {
                        return "¿Borrar " + (file.directory ? "directorio " + file.name + " y todos sus elementos " : "archivo " + file.name) + "?"
                    }
                }
            });

            modalInstance.result.then(function (result) {
                if (result) {
                    FileFactory.deleteFile(file.device, file.path).then(function (response) {
                        if (response.data) {
                            vm.files.splice(index, 1);
                            addAlert((file.directory ? "Directorio" : "Archivo") + " borrado correctamente", "success");
                        } else {
                            addAlert("No se ha podido borrar", "danger");
                        }
                    }, function (error) {
                        console.error(error);
                        addAlert("Ha ocurrido un error", "danger");
                    })
                }

            }, function () {

            });
        }

        vm.href = function (file) {
            return FileFactory.getDownload(file.device, file.path);
        }

        vm.getLastModified = function (milliseconds) {
            return new Date(milliseconds);
        }

        vm.closeAlert = function (alert) {
            vm.alerts.splice(alert, 1);
        }

        function addAlert(text, type) {
            vm.alerts.push({
                text: text,
                type: type
            })
        }

        vm.mkDir = function () {
            if (vm.newDir.length === 0) {
                addAlert("El campo nombre esta vacio", "warning")
            } else if (vm.files.map(function (dir) {
                    if (dir.directory) {
                        return dir.name;
                    }
                }).indexOf(vm.newDir) != -1) {
                addAlert("Ya existe esa carpeta", "warning")
            } else {
                FileFactory.mkDir(vm.parent.device, (hiddenPath + vm.currentPath).length == 0 ? "/" : hiddenPath + vm.currentPath, vm.newDir).then(function (response) {
                    vm.files.push(response.data);
                    addAlert("Carpeta creada", "success");
                    vm.newDir = "";
                }, function (error) {
                    addAlert("Ha ocurrido un error", "danger")
                    console.error(error);
                })

            }
        }

        vm.uploadFiles = function (file, errFiles) {
            vm.file = file;
            vm.errFiles = errFiles;
            file.upload = Upload.upload({
                url: '/file/upload',
                resumeChunkSize: '1MB',
                resumeSizeUrl: '/file/upload/size?path=' + vm.parent.device + vm.currentPath + '/' + file.name,
                data: {
                    file: file,
                    path: (hiddenPath + vm.currentPath).length == 0 ? "/" : hiddenPath + vm.currentPath,
                    fileSize: file.size,
                    userId: Session.id,
                    device: vm.parent.device
                }
            });
            vm.disabledUpload = true;
            file.upload.then(function (response) {
                $timeout(function () {
                    //file.result = response.data;
                    var map = vm.files.map(function (f) {
                        return f.name;
                    });
                    map.indexOf(response.data.name) == -1 ? vm.files.push(response.data) : vm.files.splice(map.indexOf(response.data.name), 1, response.data)
                    addAlert("Archivo subido con exito", "success");
                    vm.disabledUpload = false;
                    $timeout(function () {
                        vm.porcent = 0;
                    }, 3000);
                });

            }, function (response) {
                vm.disabledUpload = false;
                if (response.status > 0) {
                    addAlert(response.status + ': ' + response.data, "danger");
                    vm.typeProgressBar = 'info';
                    $timeout(function () {
                        vm.porcent = 0;
                    }, 3000);
                }
            }, function (evt) {
                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                if (vm.porcent < 50) {
                    vm.typeProgressBar = 'warning';
                } else if (vm.porcent < 100) {
                    vm.typeProgressBar = 'info';
                } else {
                    vm.typeProgressBar = 'success';
                }

            });

        }

        function getRoots() {
            vm.parent = null;
            vm.permission = 1;
            FileFactory.getRoots().then(function (response) {
                vm.files = response.data;
            }, function (error) {
                console.error(error);
            });
        }



        function parentPathEmpty(path) {
            var fl = path.split("/");
            fl.pop();
            return fl.join("/");
        }

        function hidePath(path) {
            var array = hiddenPath.split("/");
            var arrayPath = path.split("/");
            arrayPath.splice(0, array.length)
            vm.currentPath = arrayPath.join("/");
            if (vm.currentPath.length != 0) {
                vm.currentPath = "/" + vm.currentPath;
            }
        }


    }
})();