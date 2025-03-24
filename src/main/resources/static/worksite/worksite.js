angular.module('maintenance').controller('workSitesController', function ($scope, $rootScope, $http, $localStorage) {

    const contextPath = 'http://localhost:8188/maintenance';

    $rootScope.isUserLoggedIn = function () {
        if (!!$localStorage.maintenanceUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.regionId = {id: '1'};
    $scope.getUserRegiones = function () {
        $http.get(contextPath + '/api/v1/regiones')
            .then(function (response) {
                $scope.userRegiones = response.data;
                $scope.regionId.id = $scope.userRegiones.id;

                $scope.getDistrictByRegionId($scope.regionId.id);
                $scope.getWorkSiteByUserRegiones($scope.regionId.id);
            });
    };

    $scope.getWorkSiteByUserRegiones = function (id) {
        $http.get(contextPath + '/api/v1/worksites/region/' + id)
            .then(function (response) {
                $scope.WorkSiteAllByRegionId = response.data;
            });
    };

    $scope.getWorkSiteByUserRegionesNoDone = function (id) {
        $http.get(contextPath + '/api/v1/worksites/regionnodone/' + id)
            .then(function (response) {
                $scope.WorkSiteAllByRegionIdNoDone = response.data;
            });
    };

    $scope.changeAtWork = function (atWork, id) {
        $http({
            url: contextPath + '/api/v1/worksites/atwork/' + id,
            method: 'PATCH',
            params: {atWork: atWork}
        }).then(function (response) {
            if (atWork) {alert("Объект взят в работу");}
            if (!atWork) {alert("Работа на объекте отменена");}
            $scope.getWorkSiteByUserRegiones($scope.regionId.id);
            $scope.getWorkSiteByUserRegionesNoDone($scope.regionId.id);
        });
    };

    $scope.changeDone = function (done, id) {
        $http({
            url: contextPath + '/api/v1/worksites/done/' + id,
            method: 'PATCH',
            params: {done: done}
        }).then(function (response) {
            if (done) {alert("Объект сделан");}
            $scope.getWorkSiteByUserRegiones($scope.regionId.id);
        });
    };

    $scope.changeNoDone = function (noDone, id) {
        $http({
            url: contextPath + '/api/v1/worksites/nodone/' + id,
            method: 'PATCH',
            params: {noDone: noDone}
        }).then(function (response) {
            if (noDone) {alert("Объект сделан");}
            $scope.getWorkSiteByUserRegionesNoDone($scope.regionId.id);
        });
    };


    $scope.getUserRegiones();
    $scope.getWorkSiteByUserRegiones($scope.regionId.id);
    $scope.getWorkSiteByUserRegionesNoDone($scope.regionId.id);

});