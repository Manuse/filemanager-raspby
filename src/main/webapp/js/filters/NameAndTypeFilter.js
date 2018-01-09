(function () {
    'use strict';

    angular
        .module('myapp')
        .filter('nameAndTypeFilter', nameAndTypeFilter);

    function nameAndTypeFilter() {
        return function (array, type, text) {
            if (array != null)
                switch (type) {
                    case "file":
                        return array.filter(function (item) {
                            return !item.directory && item.name.toLowerCase().includes(text.toLowerCase());
                        })

                    case "directory":
                        return array.filter(function (item) {
                            return item.directory && item.name.toLowerCase().includes(text.toLowerCase());
                        })

                    case "both":
                        return array.filter(function (item) {
                            return item.name.toLowerCase().includes(text.toLowerCase());
                        })

                }

        }


    }
})();