angular.module('maintenance').controller('adminController', function ($rootScope, $scope, $http, $localStorage) {

    const contextPath = window.BACKEND_URL;
    $rootScope.currentUserName = $localStorage.maintenanceUser.username;
    $scope.userRole = {
        ROLE_ADMIN: "ROLE_ADMIN",
        ROLE_SUPER_ADMIN: "ROLE_SUPER_ADMIN",
        ROLE_USER: "ROLE_USER",
        ROLE_MANAGER: "ROLE_MANAGER",
        ROLE_STOREKEEPER: "ROLE_STOREKEEPER"
    };

    $scope.getAllRegiones = function () {
        $http.get(contextPath + '/api/v1/regiones/all')
            .then(function (response) {
                $scope.allRegiones = response.data;
            });
    };

    $scope.regionId = {id: '1'};
    $scope.getUserRegiones = function () {
        $http.get(contextPath + '/api/v1/regiones')
            .then(function (response) {
                $scope.userRegiones = response.data;
                $scope.regionId.id = $scope.userRegiones.id;

                $scope.getDistrictByRegionId($scope.regionId.id);
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

    $scope.getRegionesById = function (id){
        $http.get(contextPath + '/api/v1/regiones/' + id)
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

    // ************* Район *************************************

    $scope.getAllDistrict = function () {
        $http.get(contextPath + '/api/v1/districts/all')
            .then(function (response) {
                $scope.allDistrict = response.data;
            });
    };

    $scope.getDistrictByRegionId = function (regionId) {
        $http.get(contextPath + '/api/v1/districts/regionid/' + regionId)
            .then(function (response) {
                $scope.districtAllByRegionId = response.data;
            });
    };

    $scope.newDistrict = {title: '', regionesTitle: ''};
    $scope.CreateNewDistrict = function () {
        $http.post(contextPath + '/api/v1/districts', $scope.newDistrict)
            .then(function successCallback(response) {
                alert("Район " + $scope.newDistrict.title + " добавлен!");
                $scope.newDistrict.title = null;
                // $scope.getDistrictByRegionId(1);
            });
    };

    $scope.getDistrictById = function (id){
        $http.get(contextPath + '/api/v1/districts/' + id)
            .then(function (response) {
                $scope.currentDistrict = response.data;
            });
    }

    $scope.currentDistrictId = {};

    $scope.changeDistrict = function (title, id) {
        $http({
            url: contextPath + '/api/v1/districts/' + id,
            method: 'PATCH',
            params: {title: title}
        }).then(function (response) {
            alert("Район изменен");
            $scope.getDistrictById(id);
            // $scope.getDistrictByRegionId(1);
        });
    };

    $scope.deleteDistrictById = function (id) {
        $http.delete(contextPath + '/api/v1/districts/' + id)
            .then(function (response) {
                alert("Район удален");
                $scope.getDistrictById(id);
                // $scope.getDistrictByRegionId(1);
            });
    };

    // *********************************************************

    // ************* Улица ************************************

    $scope.getAllStreet = function () {
        $http.get(contextPath + '/api/v1/streets/all')
            .then(function (response) {
                $scope.allStreet = response.data;
            });
    };

    $scope.getStreetByDistrictId = function (districtId) {
        $http.get(contextPath + '/api/v1/streets/districtid/' + districtId)
            .then(function (response) {
                $scope.streetAllByDistrictId = response.data;
            });
    };
    $scope.newStreets = {title: '', districtTitle: ''};
    $scope.CreateNewStreet = function () {
        $http.post(contextPath + '/api/v1/streets', $scope.newStreets)
            .then(function successCallback(response) {
                alert("Улица " + $scope.newStreets.title + " добавлена!");
                $scope.newStreets.title = null;
                // $scope.getDistrictByRegionId(1);
            });
    };

    $scope.getStreetById = function (id){
        $http.get(contextPath + '/api/v1/streets/' + id)
            .then(function (response) {
                $scope.currentStreet = response.data;
            });
    }

    $scope.changeStreet = function (title, id) {
        $http({
            url: contextPath + '/api/v1/streets/' + id,
            method: 'PATCH',
            params: {title: title}
        }).then(function (response) {
            alert("Улица изменена");
            $scope.getStreetById(id);
//            $scope.getDistrictByRegionId(1);
        });
    };

    $scope.deleteStreetById = function (id) {
        $http.delete(contextPath + '/api/v1/streets/' + id)
            .then(function (response) {
                alert("Улица удалена");
                $scope.getStreetById(id);
 //               $scope.getDistrictByRegionId(1);
            });
    };

    // *******************************************************


    // // ************* Улица *************************************
    // $scope.getAllStreet = function () {
    //     $http.get(contextPath + '/api/v1/streets/all')
    //         .then(function (response) {
    //             $scope.allStreet = response.data;
    //         });
    // };
    // $scope.getStreetByDistrictId = function (districtId) {
    //     $http.get(contextPath + '/api/v1/streets/districtid/' + districtId)
    //         .then(function (response) {
    //             $scope.streetAllByDistrictId = response.data;
    //         });
    // };
    // // $scope.newStreets = {title: '', districtTitle: ''};
    // $scope.CreateNewStreet = function () {
    //     $http.post(contextPath + '/api/v1/streets', $scope.newStreets)
    //         .then(function successCallback(response) {
    //             alert("Улица " + $scope.newStreets.title + " добавлена!");
    //             $scope.newStreets.title = null;
    //             // $scope.getDistrictByRegionId(1);
    //         });
    // };
    // $scope.getStreetById = function (id){
    //     $http.get(contextPath + '/api/v1/streets/' + id)
    //         .then(function (response) {
    //             $scope.currentStreet = response.data;
    //         });
    // }
    // $scope.changeStreet = function (title, id) {
    //     $http({
    //         url: contextPath + '/api/v1/streets/' + id,
    //         method: 'PATCH',
    //         params: {title: title}
    //     }).then(function (response) {
    //         alert("Улица изменена");
    //         $scope.getStreetById(id);
    //     });
    // };
    // $scope.deleteStreetById = function (id) {
    //     $http.delete(contextPath + '/api/v1/streets/' + id)
    //         .then(function (response) {
    //             alert("Улица удалена");
    //             $scope.getStreetById(id);
    //             //               $scope.getDistrictByRegionId(1);
    //         });
    // };
    // // *******************************************************


    // ************* Объект **********************************
    $scope.getAllWorkSites = function () {
        $http.get(contextPath + '/api/v1/worksites/all')
            .then(function (response) {
                $scope.WorkSites = response.data;
            });
    };
    $scope.getWorkSitesByStreetId = function (streetId) {
        $http.get(contextPath + '/api/v1/worksites/streetid/' + streetId)
            .then(function (response) {
                $scope.workSitesAllByStreetId = response.data;
            });
    };
    $scope.newWorkSites = {streetId: '', house: '', frame: '', manufactureId: '', installationId: '' };

    $scope.CreateNewWorkSites = function () {
        $http.post(contextPath + '/api/v1/worksites', $scope.newWorkSites)
            .then(function successCallback(response) {
                alert("Объект добавлен!");
                $scope.newWorkSites = {streetId: '', house: '', frame: '', manufactureId: '', installationsId: '' };
            });
    };
    $scope.getWorkSitesById = function (id){
        $http.get(contextPath + '/api/v1/worksites/' + id)
            .then(function (response) {
                $scope.currentWorkSites = response.data;
            });
    }
    $scope.changeWorkSites = function (title, id) {
        $http({
            url: contextPath + '/api/v1/worksites/' + id,
            method: 'PATCH',
            params: {title: title}
        }).then(function (response) {
            alert("Объект изменен");
            $scope.getWorkSitesById(id);
//            $scope.getDistrictByRegionId(1);
        });
    };
    $scope.deleteWorkSitesById = function (id) {
        $http.delete(contextPath + '/api/v1/worksites/' + id)
            .then(function (response) {
                alert("Объект удален");
                $scope.getWorkSitesById(id);
                //               $scope.getDistrictByRegionId(1);
            });
    };
    // *******************************************************

    // ************* Объект **********************************
    $scope.getAllManufactures = function () {
        $http.get(contextPath + '/api/v1/manufactures/all')
            .then(function (response) {
                $scope.manufactures = response.data;
            });
    };
    // *******************************************************

    // ************* Холод ***********************************
    $scope.getAllInstallations = function () {
        $http.get(contextPath + '/api/v1/installations/all')
            .then(function (response) {
                $scope.installations = response.data;
            });
    };
    // *******************************************************

    // ************* Пользователи ****************************
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
    $scope.getUserRegiones();
    $scope.getAllInstallations();
    $scope.getAllUsers();
    $scope.getAllRoles();
});