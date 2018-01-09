(function () {

    angular
        .module('myapp')
        .controller('ErrorController', errorController);

    function errorController($routeParams) {
        var vm = this;
        vm.code = $routeParams.code;
        switch (vm.code) {
            case "403":
                vm.message = "Acceso no autorizado"
                break;
            case "404":
                vm.message = "Pagina no encontrada"
                break;
            default:
                vm.code = 500;
                vm.message = "Error inesperado"
        }
    }
})();