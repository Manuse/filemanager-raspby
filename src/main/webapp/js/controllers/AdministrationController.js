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
        AdministrationFactory.getUsers().then(function(response){
            vm.users=response.data;
        }, function(error){
            console.error(error);
        })
    }
})();