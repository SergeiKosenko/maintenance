(function () {
    angular
        .module('maintenance', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/streets', {
                templateUrl: 'street/streets.html',
                controller: 'streetController'
            })
            .when('/sensor', {
                templateUrl: 'sensor/sensor.html',
                controller: 'sensorController'
            })
            .when('/user', {
                templateUrl: 'user/userLK.html',
                controller: 'userController'
            })
            .when('/delivery', {
                templateUrl: 'delivery/delivery.html',
                controller: 'deliveryController'
            })
            .when('/contacts', {
                templateUrl: 'contacts/contacts.html',
                controller: 'contactsController'
            })
            .when('/auth', {
                templateUrl: 'auth/auth.html',
                controller: 'authController'
            })
            .when('/profile', {
                templateUrl: 'profile/profile.html',
                controller: 'profileController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'regController'
            })
            .when('/admin', {
                templateUrl: 'admin/admin.html',
                controller: 'adminController'
            })
            .when('/adminusers', {
                templateUrl: 'admin/adminusers.html',
                controller: 'adminusersController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }


    function run($rootScope, $http, $localStorage, $routeParams) {
        if ($localStorage.maintenanceUser) {
            try {
                let jwt = $localStorage.maintenanceUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.maintenanceUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {
            }

            if ($localStorage.maintenanceUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.maintenanceUser.token;
            }
        }
    }
})();

angular.module('maintenance').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8188/maintenance/';
    $rootScope.listRoles = new Set();
    if ($localStorage.maintenanceUser){
        $rootScope.currentUserName = $localStorage.maintenanceUser.username;
    }

        $scope.tryToAuth = function () {
        $location.path('/auth');
    };

    $rootScope.tryToLogout = function () {
        $rootScope.listRoles.clear();
        $scope.clearUser();
        $scope.user = null;
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.maintenanceUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if (!!$localStorage.maintenanceUser) {
            return true;
        } else {
            return false;
        }
    };
    $rootScope.isUserHasAdminRole = function () {
        if (!!$localStorage.maintenanceUser){
            $localStorage.maintenanceUser.listRoles.forEach($rootScope.listRoles.add, $rootScope.listRoles);
            return $rootScope.listRoles.has('ROLE_ADMIN');
        }
        return false;
    };

    $rootScope.isUserHasUserRole = function () {
        if (!!$localStorage.maintenanceUser){
            $localStorage.maintenanceUser.listRoles.forEach($rootScope.listRoles.add, $rootScope.listRoles);
            return $rootScope.listRoles.has('ROLE_USER');
        }
        return false;
    };

    $rootScope.isUserHasSuperAdminRole = function () {
        if (!!$localStorage.maintenanceUser){
            $localStorage.maintenanceUser.listRoles.forEach($rootScope.listRoles.add, $rootScope.listRoles);
            return $rootScope.listRoles.has('ROLE_SUPER_ADMIN');
        }
        return false;
    };
});