(function () {
    'use strict';

    angular
        .module('myapp')
        .controller('AdministrationController', administrationController);

    administrationController.$inject = ['AdministrationFactory', 'Session'];

    function administrationController(AdministrationFactory, Session) {
        var vm = this;
        vm.users = [];
        vm.devices = [];
        vm.selectUser = null;
        vm.permission = 1;
        vm.roles=[];

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

        vm.createUser = function () {
            if (vm.newPassword === vm.newPassword2) {
                var auth = [{
                    id: 1
                }];
                if (vm.newRole == 2) {
                    auth.push({
                        id: 2
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
            }else{
                console.log("mal pass");
                
            }
        }

        vm.setSelectUser = function (user) {
            vm.selectUser = user;
            vm.isAdmin = vm.selectUser.authorities.map(function (x) {
                return x.name
            }).indexOf('admin') !== -1
            if (vm.isAdmin) {
                vm.newPath = "/";
            }

            AdministrationFactory.getAccessPath(user.id).then(function (response) {
                vm.accessPaths = response.data;
            }, function (error) {
                console.error(error);
            })

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
    }
})();