(function() {

    angular
        .module('myapp')
        .controller("NavbarController", navbarController)
        
        navbarController.$inject = ['AuthSharedFactory', 'Session', '$uibModal']
        function navbarController(AuthSharedFactory, Session, $uibModal) {
            var vm = this;
    
            vm.logout = AuthSharedFactory.logout;
            vm.role = Session.getUserRoles;
            vm.username = Session.username;

            vm.changePass=function(){
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: 'modals/mChangePassword.html',
                    controller: 'ChangePasswordController',
                    controllerAs: 'vmm',
                    //size: 'sm',
                });
    
                modalInstance.result.then(function () {
    
                }, function () {
    
                });
            }

        }
})();