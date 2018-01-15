(function() {
    'use strict';

    angular
        .module('myapp')
        .controller('AdministrationController', administrationController);

    administrationController.$inject = ['AdministrationFactory'];
    function administrationController(AdministrationFactory) {
        var vm = this;
        vm.users=[];
        vm.devices=[];
        vm.currentUser=null;

        AdministrationFactory.getUsers().then(function(response){
            vm.users=response.data;
            console.log(vm.users);
            
        }, function(error){
            console.error(error);
        })
    }
})();