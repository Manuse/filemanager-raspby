(function () {
    'use strict';

    angular
        .module('myapp')
        .controller('ChangePasswordController', changePasswordController);

    changePasswordController.$inject = ['$uibModalInstance', 'userFactory', '$timeout'];

    function changePasswordController($uibModalInstance, userFactory, $timeout) {
        var vm = this;


        vm.changePassword = function () {
            if (vm.newPass1 === vm.newPass2 && vm.newPass2.lenght != 0 && vm.newPass1.lenght != 0) {
                userFactory.changePassword(vm.newPass1).then(function (response) {
                    $uibModalInstance.close();
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