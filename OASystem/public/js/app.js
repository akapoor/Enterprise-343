/**
 * Created by Anshul on 10/23/2014.
 */

var app = angular.module("orientationApp",[])

app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'templates/home.html',
                controller: 'homeController'
            }).
            when('/events', {
                templateUrl: 'templates/allEvents.html',
                controller: 'eventsController'
            }).
            when('/students', {
                templateUrl: 'templates/assignedStudents.html',
                controller: 'studentsController'
            }).
            when('/info', {
                templateUrl: 'templates/generalInfo.html',
                controller: 'infoController'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);

app.controller("homeController", function($scope){
    $scope.options = ["Events", "Assigned Students", "Information"];
});

app.controller("eventsController", function($scope){
    $scope.options = ["Events", "Assigned Students", "Information"];
});

app.controller("studentsController", function($scope){
    $scope.options = ["Events", "Assigned Students", "Information"];
});

app.controller("infoController", function($scope){
    $scope.options = ["Events", "Assigned Students", "Information"];
});
