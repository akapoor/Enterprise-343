/**
 * Created by Anshul on 10/23/2014.
 */

var app = angular.module("orientationApp", ['ngRoute', 'ngTable']);

app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/home', {
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
                redirectTo: '/home'
            });
    }]);

app.controller("homeController", function($scope, $location){
    $scope.options = ["Events", "Assigned Students", "Information"];

    $scope.redirect = function(path){
        console.log("return val is"+path);
        $location.path( path );
    }

});

app.controller("eventsController", function($scope){

    $scope.events =
        [
            {name: "Registration", date: "08/16/14", startTime: "9:30am", endTime: "12:00pm", location:"SAU", onDutyPAL: "Billy, Anshul", description:"Check in with welcome table.", attendees:"All"},
            {name: "Immigration Checkin", date: "08/16/14", startTime: "1:30pm", endTime: "4:30pm", location:"Bamboo Room", onDutyPAL: "Richard, Greg", description:"Submit required documents to ISS, and get them verified.", attendees:"All"},
            {name: "Game Night", date: "08/16/14", startTime: "5:30pm", endTime: "8:00pm", location:"Bamboo Room", onDutyPAL: "Eva, Jhossue", description:"Play various board games and enjoy some pizza.", attendees:"All"},
            {name: "Rochester 101", date: "08/17/14", startTime: "10:00am", endTime: "11:00am", location:"Ingle Auditorium", onDutyPAL: "Marielle, Kim", description:"Presentation on living in Rochester.", attendees:"All"},
            {name: "GV Dance Party", date: "08/17/14", startTime: "6:00pm", endTime: "9:00pm", location:"Global Village", onDutyPAL: "All", description:"Dance Party with free food.", attendees:"All"}
        ];
});

app.controller("studentsController", function($scope, $http){
    $scope.students =
        [
            {name: "Bob", email: "bob@email.com", major: "Software Engineering", level:"Graduate"},
            {name: "Dyllan", email: "dyllan@email.com", major: "Mechanical Engineering", level:"Graduate"},
            {name: "Henry", email: "henry@email.com", major: "Electrical Engineering",level:"Undergraduate"},
            {name: "Rich", email: "rich@email.com", major: "MIS",  level:"Graduate"},
            {name: "Derek", email: "derek@email.com", major: "Finance",  level:"Undergraduate"},
            {name: "Katie", email: "katie@email.com", major: "Graphics Design", level:"Graduate"},
            {name: "Ross", email: "ross@email.com", major: "Industrial Engineering",  level:"Graduate"},
            {name: "Linda", email: "linda@email.com", major: "Industrial Engineering",  level:"Graduate"},
            {name: "Marry", email: "marry@email.com", major: "Mechanical Engineering",  level:"Undergraduate, 3"},
            {name: "Miguel", email: "miguel@email.com", major: "Film and Animation",  level:"Undergraduate"},
            {name: "Kat", email: "kat@email.com", major: "Software Engineering",  level:"Graduate"},
        ];

    $scope.dbUsers = [];


    $http.get('/user?email=abc@123.com').
        success(function(data, status, headers, config) {
        	console.log("in success");
        	console.log(data);
            $scope.dbUsers = data;
            
        }).
        error(function(data, status, headers, config) {
            console.log("Error occurred in getting users.");
            console.log(data);
        });

});

app.controller("infoController", function($scope){
    $scope.options = ["File1", "File2", "File3"];
});

