(function () {
    'use strict';

    angular
        .module('myapp')
        .controller('ChangePasswordController', changePasswordController);

    changePasswordController.$inject = ['$uibModalInstance', 'UserFactory', 'AdministrationFactory' , '$timeout', 'item'];

    function changePasswordController($uibModalInstance, UserFactory, AdministrationFactory, $timeout, item) {
        var vm = this;


        vm.changePassword = function () {
            if (vm.newPass1 === vm.newPass2 && vm.newPass2.lenght != 0 && vm.newPass1.lenght != 0) {
                var changePassword;
                if(item==null){
                   changePassword=function(){
                       return UserFactory.changePassword(vm.newPass1);
                   }
                }else{
                    changePassword=function(){
                        return AdministrationFactory.changePasswordUser(item, vm.newPass1);
                    }
                }

                changePassword().then(function (response) {
                    $uibModalInstance.close(true);
                }, function (error) {
                    console.error(error);
                })
                
            } else {
                vm.badPass = true;
                $timeout(function () {
                    vm.badPass = false
                }, 3000)
            }
        }

        vm.close = function () {
            $uibModalInstance.close();
        }

    }
})();