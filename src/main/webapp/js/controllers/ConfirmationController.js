(function() {
    'use strict';

    angular
        .module('myapp')
        .controller('ConfirmationController', confirmationController);

    confirmationController.$inject = ['$uibModalInstance', 'msg'];
    function confirmationController($uibModalInstance, msg) {
        var vm = this;
        vm.message=msg;
        
        vm.ok=function(){
            $uibModalInstance.close(true)
        }

        vm.close=function(){
            $uibModalInstance.close(false)
        }
    }
})();