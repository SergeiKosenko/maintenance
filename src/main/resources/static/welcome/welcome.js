angular.module('maintenance').controller('welcomeController', function ($scope, $rootScope, $http) {


        $scope.fillTable = function () {
        $http.get('http://localhost:8188/maintenance/api/v1/streets/all')
            .then(function (responce) {
               $scope.streets = responce.data;
                // console.log(response);
            });
    };
    //
    // $scope.fillRegionesTable = function () {
    //     $http.get('http://localhost:8188/maintenance/api/v1/regiones')
    //         .then(function (responce) {
    //             $scope.regiones = responce.data;
    //             // console.log(response);
    //         });
    // };

    $scope.deleteStreet = function (id) {
        $http.delete('http://localhost:8188/maintenance/api/v1/streets/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }

    $scope.createNewStreet = function () {
        // console.log($scope.newProduct);
        $http.post('http://localhost:8188/maintenance/api/v1/streets', $scope.newStreets)
            .then(function (response) {
                $scope.newStreets = null;
                $scope.fillTable();
            });
    }

    $scope.fillTable();
    $scope.fillRegionesTable();

});
