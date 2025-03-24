angular.module('maintenance').controller('authController', function ($rootScope, $scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8188/maintenance/';

    $scope.tryToAuth = function () {
        $http.post(contextPath + 'auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.maintenanceUser = {username: $scope.user.username, token: response.data.token, listRoles: response.data.list};
                    $rootScope.currentUserName = $scope.user.username;
                    $scope.user.username = null;
                    $scope.user.password = null;

                    $location.path('#!/');
                }
            }, function errorCallback(response) {
            });
    };

    // $rootScope.tryToLogout = function () {
    //     $rootScope.listRoles.clear();
    //     $scope.clearUser();
    //     $scope.user = null;
    //     $location.path('/');
    // };
    //
    // $scope.clearUser = function () {
    //     delete $localStorage.maintenanceUser;
    //     $http.defaults.headers.common.Authorization = '';
    // };

    $rootScope.isUserLoggedIn = function () {
        return !!$localStorage.maintenanceUser;
    };

});