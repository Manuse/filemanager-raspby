(function () {
    'use strict';

    angular
        .module('myapp')
        .controller('AdministrationController', administrationController);

    administrationController.$inject = ['AdministrationFactory', 'Session', '$uibModal'];

    function administrationController(AdministrationFactory, Session, $uibModal) {
        var vm = this;
        vm.users = [];
        vm.devices = [];
        vm.selectUser = null;
        vm.roles = [];
        vm.partialPaths = [];

        if (Session.userRoles.indexOf('super-admin') !== -1) {
            vm.roles = [{
                id: 1,
                name: "User"
            }, {
                id: 2,
                name: "Admin"
            }];
            vm.newRole = 1;
        }

        AdministrationFactory.getUsers().then(function (response) {
            vm.users = response.data;
        }, function (error) {
            console.error(error);
        })

        vm.changeEnabled = function (user) {
            AdministrationFactory.changeEnabled(user.id).then(function (response) {
                user.enabled = response.data.enabled;
            }, function (error) {
                console.error(error);
            })
        }

        //uso de check path y de modal en caso contrario y comprobar si ya existe
        vm.createAccessPath = function () {
            if (!vm.newPath.startsWith("/")) {
                vm.newPath = "/" + vm.newPath;
            }
            AdministrationFactory.checkPath(vm.selectUser.id, vm.device, vm.newPath).then(function (response) {
                if (response.data) {
                    var modalInstance = $uibModal.open({
                        animation: true,
                        templateUrl: 'modals/mConfirmation.html',
                        controller: 'ConfirmationController',
                        controllerAs: 'vmm',
                        size: 'sm',
                        resolve: {
                            msg: function () {
                                return "Ya existe una ruta padre o hija ¿sustituirla?";
                            }
                        }
                    });

                    modalInstance.result.then(function (result) {
                        if (result) {
                            addAccessPath();
                        }

                    }, function () {

                    });
                } else {
                    addAccessPath();
                }
            }, function (error) {
                console.error(error);
            })

        }

        vm.changePassword = function(user){
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'modals/mChangePassword.html',
                controller: 'ChangePasswordController',
                controllerAs: 'vmm',
                resolve: {
                    item: function () {
                        return user.id;
                    }
                }
            });

            modalInstance.result.then(function (result) {
                if(result){
                    console.log("password cambiada");            
                }
            }, function () {

            });
        }

        function addAccessPath() {
            var accessPath = {
                device: vm.device,
                path: vm.newPath,
                permissions: vm.permission,
                user: {
                    id: vm.selectUser.id
                }
            };
            AdministrationFactory.addAccessPath(accessPath).then(function (response) {
                getAccessPath();
            }, function (error) {
                console.error(error);
            })
        }

        vm.changePermission = function (accessPath) {
            AdministrationFactory.changePermission(accessPath.id, accessPath.permissions == 1 ? 0 : 1).then(function (response) {
                accessPath.permissions = response.data.permissions;
            }, function (error) {
                console.error(error);
            })
        }

        vm.deleteAccessPath = function (index, accessPath) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'modals/mConfirmation.html',
                controller: 'ConfirmationController',
                controllerAs: 'vmm',
                size: 'sm',
                resolve: {
                    msg: function () {
                        return "¿Borrar ruta de acceso?";
                    }
                }
            });

            modalInstance.result.then(function (result) {
                if (result) {
                    AdministrationFactory.deleteAccessPath(accessPath.id).then(function (response) {
                        if (response) {
                            vm.accessPaths.splice(index, 1);
                        } else {
                            console.log("no se ha borrado");
                        }
                    }, function (error) {
                        console.error(error);
                    })
                }
            }, function () {

            });
        }

        vm.createUser = function () {
            if (vm.newPassword === vm.newPassword2) {
                var auth = [{
                    id: 1,
                    name: "user"
                }];
                if (vm.newRole == 2) {
                    auth.push({
                        id: 2,
                        name: "admin"
                    });
                }
                var user = {
                    username: vm.newUsername,
                    password: vm.newPassword,
                    enabled: true,
                    authorities: auth
                }
                AdministrationFactory.addUser(user).then(function (response) {
                    vm.users.push(response.data);
                }, function (error) {
                    console.error(error);

                })
            } else {
                console.log("mal pass");
            }
        }

        vm.deleteUser = function (index, user) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'modals/mConfirmation.html',
                controller: 'ConfirmationController',
                controllerAs: 'vmm',
                size: 'sm',
                resolve: {
                    msg: function () {
                        return "¿Borrar Usuario?";
                    }
                }
            });

            modalInstance.result.then(function (result) {
                if (result) {
                    AdministrationFactory.deleteUser(user.id).then(function (response) {
                        if (response.data) {
                            vm.users.splice(index, 1);
                        } else {
                            console.log("falla borrar");
                        }
                    }, function (error) {
                        console.error(error);

                    })
                }
            }, function () {

            });
        }

        vm.setSelectUser = function (user) {
            vm.selectUser = user;
            vm.permission = '1';
            vm.isAdmin = vm.selectUser.authorities.map(function (x) {
                return x.name
            }).indexOf('admin') !== -1
            if (vm.isAdmin) {
                vm.newPath = "/";
            }

            getAccessPath();

            AdministrationFactory.getDevices().then(function (response) {
                vm.devices = response.data;
                console.log(vm.devices);
                if (vm.devices.length !== 0) {
                    vm.device = vm.devices[0];
                }
            }, function (error) {
                console.error(error);

            })
        }

        function getAccessPath() {
            AdministrationFactory.getAccessPath(vm.selectUser.id).then(function (response) {
                vm.accessPaths = response.data;
            }, function (error) {
                console.error(error);
            })
        }

        vm.searchPath=function(){          
            AdministrationFactory.searchPath(vm.device, vm.newPath == null?"":vm.newPath).then(function(response){
                vm.partialPaths=response.data;
                console.log(vm.partialPaths);
                
            },function(error){
                console.error(error);
            })
        }

    }
})();