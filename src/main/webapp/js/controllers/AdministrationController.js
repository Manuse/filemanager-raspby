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

        vm.changeEnabled=function(user){
            AdministrationFactory.changeEnabled(user.id).then(function(response){
                user.enabled=response.data.enabled;
            },function(error){
                console.error(error);           
            })
        }

        //uso de check path y de modal en caso contrario y comprobar si ya existe
        vm.createAccessPath=function(){
            if(!vm.newPath.startsWith("/")){
                vm.newPath = "/"+vm.newPath;
            }
            AdministrationFactory.checkPath(vm.selectUser.id, vm.device, vm.newPath).then(function(response){
                console.log(response.data);
                
            }, function(error){
                console.error(error);            
            })
            
        }

        vm.changePermission = function (accessPath){
            AdministrationFactory.changePermission(accessPath.id, accessPath.permissions==1 ? 0:1).then(function(response){
                accessPath.permissions = response.data.permissions;
            }, function(error){
                console.error(error);              
            })
        }

        vm.deleteAccessPath=function(index, accessPath){
            AdministrationFactory.deleteAccessPath(accessPath.id).then(function(response){
                if(response){
                    vm.accessPaths.splice(index,1);
                }else{
                    console.log("no se ha borrado");                  
                }
            }, function(error){
                console.error(error);              
            })
        }

        vm.createUser = function () {
            if (vm.newPassword === vm.newPassword2) {
                var auth = [{
                    id: 1,
                    name:"user"
                }];
                if (vm.newRole == 2) {
                    auth.push({
                        id: 2,
                        name:"admin"
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

        vm.deleteUser=function(index, user){
            
            AdministrationFactory.deleteUser(user.id).then(function(response){
                if(response.data){
                    vm.users.splice(index,1);
                }else{
                    console.log("falla borrar");
                    
                }
            }, function(error){
                console.error(error);
                
            })
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