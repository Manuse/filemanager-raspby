(function() {

    angular
        .module('myapp')
        .controller("NavbarController", navbarController)
        
        function navbarController(AuthSharedFactory, Session) {
            var vm = this;
    
            vm.logout = AuthSharedFactory.logout;
            vm.role = Session.userRoles;
            vm.username = Session.username;
    
        }
})();