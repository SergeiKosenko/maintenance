angular.module('maintenance').controller('adminController', function ($rootScope, $scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8188/maintenance/';
    $scope.userRole = {
        ROLE_ADMIN: "ROLE_ADMIN",
        ROLE_SUPER_ADMIN: "ROLE_SUPER_ADMIN",
        ROLE_USER: "ROLE_USER"
    };
    $scope.getAllUsers = function () {
        $http.get(contextPath + 'api/v1/users')
            .then(function (response) {
                $scope.allUsers = response.data;
                // $scope.roles = $scope.allUsers.role;
                var rnpFilters = JSON.parse(response.data);
                $.each( rnpFilters.rnp, function(key, val) {
                    for (var i = 0; i < val.length; i++) {
                        console.log(val[i].name);
                        $scope.roles = val[i].name;
                    }
                });
            });
    };
    $scope.getUserById = function (userId){
        $http.get(contextPath + 'api/v1/users/' + userId)
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
        $http.get(contextPath + 'api/v1/users/roles')
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
    }

    $scope.changeUsersRole = function (roleName, userId) {
        $http({
            url: contextPath + 'api/v1/users/roles/' + userId,
            method: 'PATCH',
            params: {roleName: roleName}
        }).then(function (response) {
            alert("Роль изменена");
            $scope.getUserById(userId);
            $scope.getAllUsers();
        });
    };

    $scope.deleteUser = function (userId) {
        $http.delete('http://localhost:8188/maintenance/api/v1/users/' + userId)
            .then(function (response) {
                alert("Пользователь удален");
                $scope.getUserById(userId);
                $scope.getAllUsers();
            });
    };
    $scope.getAllUsers();
    $scope.getAllRoles();
});