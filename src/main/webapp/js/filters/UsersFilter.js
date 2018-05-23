(function() {
    'use strict';

    angular
        .module('myapp')
        .filter('usersFilter', usersFilter);

    usersFilter.$inject = ['Session']
    function usersFilter(Session) {
        return function(array){
            return array.filter(function(item){
                if(Session.id!=item.id){
                    if(Session.userRoles.indexOf("filemanager-super-admin")!=-1){
                        return item;
                    }else if(item.authorities.map(function(x){return x.name}).indexOf("filemanager-admin")==-1){
                        return item;
                    }
                }
            })
        }
    }
})();