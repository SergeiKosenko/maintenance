angular.module('maintenance').controller('adminController', function ($rootScope, $scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8188/maintenance';

    $rootScope.currentUserName = $localStorage.maintenanceUser.username;
    $scope.userRole = {
        ROLE_ADMIN: "ROLE_ADMIN",
        ROLE_SUPER_ADMIN: "ROLE_SUPER_ADMIN",
        ROLE_USER: "ROLE_USER"
    };

    $scope.getAllRegiones = function () {
        $http.get(contextPath + '/api/v1/regiones')
            .then(function (response) {
                $scope.allRegiones = response.data;
            });
    };

    $scope.getAllUsers = function () {
        $http.get(contextPath + '/api/v1/users')
            .then(function (response) {
                $scope.allUsers = response.data;
                var rnpFilters = JSON.parse(response.data);
                $.each( rnpFilters.rnp, function(key, val) {
                    for (var i = 0; i < val.length; i++) {
                        console.log(val[i].name);
                        $scope.roles = val[i].name;
                    }
                });
            });
    };

    $scope.newRegiones = {title: ''};
    $scope.CreateNewRegiones = function () {
        $http.post(contextPath + '/api/v1/regiones', $scope.newRegiones)
            .then(function successCallback(response) {
                alert("Регион " + $scope.newRegiones.title + " добавлен!");
                $scope.newRegiones.title = null;
                $scope.getAllRegiones();
            });
    };

    $scope.getRegionesById = function (regionesId){
        $http.get(contextPath + '/api/v1/regiones/' + regionesId)
            .then(function (response) {
                $scope.currentRegiones = response.data;
            });
    }

    $scope.changeRegiones = function (title, id) {
        $http({
            url: contextPath + '/api/v1/regiones/' + id,
            method: 'PATCH',
            params: {title: title}
        }).then(function (response) {
            alert("Регион изменен");
            $scope.getRegionesById(id);
            $scope.getAllRegiones();
        });
    };

    $scope.deleteRegionesById = function (id) {
        $http.delete(contextPath + '/api/v1/regiones/' + id)
            .then(function (response) {
                alert("Регион удален");
                $scope.getRegionesById(id);
                $scope.getAllRegiones();
            });
    };

    $scope.getAllDistrict = function () {
        $http.get(contextPath + '/api/v1/districts')
            .then(function (response) {
                $scope.allDistrict = response.data;
            });
    };

    $scope.getUserById = function (userId){
        $http.get(contextPath + '/api/v1/users/' + userId)
            .then(function (response) {
                $scope.currentUser = response.data;
            });
    }

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

    $scope.getAllRoles = function () {
        $http.get(contextPath + '/api/v1/users/roles')
            .then(function (response) {
                $scope.allRoles = response.data;
            });
    };

    $scope.clickRole = function (roleId, selected) {
        var idx = selectedRole.indexOf(roleId);
        if (idx > -1) {
            selectedRole.splice(idx, 1);
        } else {
            selectedRole.push(roleId);
        }
    };

    $scope.changeUsersRole = function (roleName, userId) {
        $http({
            url: contextPath + '/api/v1/users/roles/' + userId,
            method: 'PATCH',
            params: {roleName: roleName}
        }).then(function (response) {
            alert("Роль изменена");
            $scope.getUserById(userId);
            $scope.getAllUsers();
        });
    };

    $rootScope.isUserActive = function () {
        if ($scope.currentUser.active) {
            return true;
        } else {
            return false;
        }
    };

    $scope.changeUsersActive = function (active, userId) {
        $http({
            url: contextPath + '/api/v1/users/active/' + userId,
            method: 'PATCH',
            params: {active: active}
        }).then(function (response) {
            if (active) {alert("Пользователь активирован");}
            if (!active) {alert("Пользователь Заблокирован");}
            $scope.getUserById(userId);
            $scope.getAllUsers();
        });
    };

    $scope.deleteUser = function (userId) {
        $http.delete(contextPath + '/api/v1/users/' + userId)
            .then(function (response) {
                alert("Пользователь удален");
                $scope.getUserById(userId);
                $scope.getAllUsers();
            });
    };
    $scope.getAllRegiones();
    $scope.getAllUsers();
    $scope.getAllRoles();
});