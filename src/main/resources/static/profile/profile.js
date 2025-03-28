angular.module('maintenance').controller('profileController', function ($rootScope, $scope, $http) {

    $scope.fillRegionesTable = function () {
        $http.get('http://localhost:8188/maintenance/api/v1/profile')
            .then(function (responce) {
                $scope.regiones = responce.data;
            });
    };


    const contextPath = 'http://localhost:8188/maintenance/api/v1/users/';

    $scope.newUser = {regionesTitle: '', lastName: '', firstName: '', phone: '',username: '', password: '', email: '', passwordConfirm: ''};

    $scope.tryToReg = function () {

        $http.post(contextPath + 'register', $scope.newUser)
            .then(function successCallback(response) {

                $scope.newUser.lastName = null;
                $scope.newUser.firstName = null;
                $scope.newUser.username = null;
                $scope.newUser.regionesTitle = null;
                $scope.newUser.email = null;
                $scope.newUser.phone = null;
                $scope.newUser.password = null;
                $scope.newUser.passwordConfirm = null;

                $location.path('#!/');

            }, function errorCallback(response) {
        });
    };

    $scope.tryToLogout = function () {
            $scope.clearUser();
            $scope.newUser = null;
            $location.path('#!/');
    };

    $scope.clearUser = function () {
        delete $localStorage.maintenanceUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        return !!$localStorage.maintenanceUser;
    };

    $scope.fillRegionesTable();
});