/**
 * Created by Anshul on 10/23/2014.
 */

//initialize the angular app
var app = angular.module("orientationApp", ['ngRoute', 'ngTable']);

//----------------------Create services----------------------
// create a service to check if a user is logged in at a given time
app.factory('SessionService', function(){
    return {
        status : false
    };
});

app.factory('SessionCache', function($cacheFactory){
    return $cacheFactory('session');
});

//configure app routes
app.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'index.html',
                controller: 'indexController'
            }).
            when('/login', {
                templateUrl: 'templates/userLogin.html',
                controller: 'loginController'
            }).
            when('/home', {
                templateUrl: 'templates/home.html',
                controller: 'homeController'
            }).
            when('/create', {
                templateUrl: 'templates/create.html',
                controller: 'createController'
            }).
            when('/manage', {
                templateUrl: 'templates/manage.html',
                controller: 'manageController'
            }).
            when('/events', {
                templateUrl: 'templates/allEvents.html',
                controller: 'eventsController'
            }).
            when('/students', {
                templateUrl: 'templates/assignedStudents.html',
                controller: 'studentsController'
            }).
            when('/questions', {
                templateUrl: 'templates/questions.html',
                controller: 'questionController'
            }).
            when('/info', {
                templateUrl: 'templates/generalInfo.html',
                controller: 'infoController'
            }).
            otherwise({
                redirectTo: '/login'

            });
    }]);

app.controller("indexController", function($scope, $location){

    $scope.loginStatus = localStorage.getItem('session');//SessionCache.get('session');
    /*	if($scope.Lstatus == "false"){
     $location.path('/login');
     }
     if($scope.Lstatus == "true"){
     $location.path('/home');
     }
     */
});

app.controller("loginController", function($scope, $http, $location){
	
	if(sessionStorage.getItem('session') == 'true')
	{
		$location.path( '/home' );
    }
	else{
		
	    $scope.response= "";
	    $scope.error="";
	    $scope.submit= function(){
	        var request={
	            email : $scope.userID,
	            password : $scope.passWORD
	        };
	        console.log(request);
	        $http.post('/login', request).
	            success(function(data, status, headers, config) {
	                $scope.response = data;
	
	                if (typeof(Storage) != "undefined") {
	                    localStorage.setItem('session', "false");
	                    console.log("loginController: session == true");
	                } else {
	                    console.log("Browser doesn't support local storage");
	                }
	
	                if($scope.response == "admin" || $scope.response == "pal" || $scope.response == "student"){ //login credentials true
	
	                    sessionStorage.setItem('session', "true");
	                    console.log("loginController: session == true");
	                    sessionStorage.setItem('currEmail', $scope.userID); //start keeping track of current logged user
	                    sessionStorage.setItem('userType', $scope.response);
	                    $location.path('/home');
	                }
	                else{
	                    $scope.error = "Error: Invalid username or password. Try again.";
	                }
	            }).
	            error(function(data, status, headers, config) {
	                console.log("Error occurred in loggin in.");
	                console.log(data);
	            });
	    };

    //user/connect pass in pal email and the person logged in email to link new students to pal
    }

});

app.controller("homeController", function($scope, $location){
	sessionStorage.setItem('userType', "admin");
	sessionStorage.setItem('currEmail', "rnn7726@g.rt.edu");
	 
    $scope.loginStatus = "true";
    $scope.userType = sessionStorage.getItem('userType');
    $scope.currEmail = sessionStorage.getItem('currEmail');
    //console.log("homeController: session == " + localStorage.getItem('session'));
    $scope.userType = sessionStorage.getItem('userType'); //get the type of user

    $scope.optionsPAL = ["Events", "Students", "Information", "Forum"];
    $scope.optionsStudent = ["Events", "Information", "Forum"];
    $scope.optionsAdmin = ["Events", "Students", "Information", "Forum"];

    $scope.redirect = function(path){
        $location.path( path );
    }

});

app.controller("eventsController", function($scope, $http){

    $scope.currEmail = sessionStorage.getItem('currEmail');
    $scope.userType = sessionStorage.getItem('userType');

    if($scope.userType == "pal"){
        $("#loadMePAL").click();
        console.log("events-loadMePAL clicked");
    }
    if($scope.userType == "student"){
        $("#loadMeStudent").click();
        console.log("events-loadMeStudent clicked");
    }
    if($scope.userType == "admin"){
        $("#loadMeAdmin").click();
        console.log("events-loadMeAdmin clicked");
    }

    /*    $scope.events =
     [
     {name: "Registration", date: "08/16/14", startTime: "9:30am", endTime: "12:00pm", location:"SAU", onDutyPAL: "Billy, Anshul", description:"Check in with welcome table.", attendees:"All"},
     {name: "Immigration Checkin", date: "08/16/14", startTime: "1:30pm", endTime: "4:30pm", location:"Bamboo Room", onDutyPAL: "Richard, Greg", description:"Submit required documents to ISS, and get them verified.", attendees:"All"},
     {name: "Game Night", date: "08/16/14", startTime: "5:30pm", endTime: "8:00pm", location:"Bamboo Room", onDutyPAL: "Eva, Jhossue", description:"Play various board games and enjoy some pizza.", attendees:"All"},
     {name: "Rochester 101", date: "08/17/14", startTime: "10:00am", endTime: "11:00am", location:"Ingle Auditorium", onDutyPAL: "Marielle, Kim", description:"Presentation on living in Rochester.", attendees:"All"},
     {name: "GV Dance Party", date: "08/17/14", startTime: "6:00pm", endTime: "9:00pm", location:"Global Village", onDutyPAL: "All", description:"Dance Party with free food.", attendees:"All"}
     ];
     */
    $scope.events = [];
    //view my events for pal and admins
    $scope.myEvents = function(user){
        var userType = user;
        var request={
            name : $scope.currEmail
        };
        var url = '/events/email?email='+$scope.currEmail;
        $http.get(url).
            success(function(data, status, headers, config) {
                $scope.events = data;
                console.log("request OK 200");

            }).
            error(function(data, status, headers, config) {
                console.log("Error occurred in getting my events.");
                console.log(data);
            });
    }

    // view all events for the three user types
    $scope.allEvents = function(user){
        var userType = user;
        var request={
            name : $scope.currEmail
        };
        $http.get('/events/all').
            success(function(data, status, headers, config) {
                $scope.events = data;
                console.log("request OK 200");

            }).
            error(function(data, status, headers, config) {
                console.log("Error occurred in getting all events.");
                console.log(data);
            });
    }

});

app.controller("studentsController", function($scope, $http){

    $scope.currEmail = sessionStorage.getItem('currEmail');
    $scope.userType = sessionStorage.getItem('userType');
    if($scope.userType == "pal"){
        $("#loadMePAL").click();
        console.log("assignedStudents-loadMePAL clicked");
    }
    if($scope.userType == "admin"){
        $("#loadMeAdmin").click();
        console.log("assignedStudents-loadMeAdmin clicked");
    }

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
            {name: "Kat", email: "kat@email.com", major: "Software Engineering",  level:"Graduate"}
        ];

    $scope.dbUsers = [];


    //view my events for pal and admins
    $scope.myStudents = function(user){
        var userType = user;
        var request={
            name : $scope.currEmail
        };
        $http.post('/users/assigned', request).
            success(function(data, status, headers, config) {
                $scope.dbUsers = data;
                console.log("request OK 200");

            }).
            error(function(data, status, headers, config) {
                console.log("Error occurred in getting my assigned students.");
                console.log(data);
            });
    }

    // view all events for the three user types
    $scope.myPALs = function(user){
        var userType = user;
        var request={
            name : $scope.currEmail
        };
        //$scope.dbPAL = [{name:"Billy"},{name:"Matt"},{name:"Olivia"},{name:"Jose"}];
        var getPalUrl = "user/pal"/+ sessionStorage.getItem('currEmail'); //get email of current logged user
      //get all the current pals at the load of this controller
        $http.get(getPalUrl).
            success(function(data, status, headers, config) {
                $scope.dbPAL = data;
                console.log("data is "+data);
            }).
            error(function(data, status, headers, config) {
                console.log("Error occurred in all PALs.");
                console.log(data);
            });
    }

});

app.controller("createController", function($scope, $http){
	
	$scope.userType = sessionStorage.getItem('userType');
    $scope.currEmail = sessionStorage.getItem('currEmail'); 
    
    $scope.event = "student";
    $("#loadStudent").click(); //loads the create student form
    $scope.dbPAL = [{name:"Empty"}, {name:"Empty2"}];
    
    //get all pals, set it equal to dbPALs
    var getPalUrl = "user/pal?loggedIn="+ sessionStorage.getItem('currEmail'); //get email of current logged user
    $http.get(getPalUrl).
        success(function(data, status, headers, config) {
            $scope.dbPAL = data;
            console.log("All PALs: "+data);
        }).
        error(function(data, status, headers, config) {
            console.log("Error occurred in getting all PALs.");
            console.log(data);
        });

    $scope.createStudent = function(){
        $scope.event = "student";
    }
    $scope.createPAL = function(){
        $scope.event = "pal";
    }
    $scope.createEvent = function(){
        $scope.event = "events";
    };

    //submit form to create a new student.
    $scope.submitStudent = function(){
        var request={
            personId : 0,
            userPassword : "123passWORD",
            userType : "student",
            fName : $scope.fname,
            lName : $scope.lname,
            email : $scope.emailID,
            yearLevel : $scope.level,
            major : $scope.majorID
        }; //no need to include logged in person's email
/*
        //get the name of pal selected.
        var palNum = $( "#selectPal option:selected" ).text();
        var url = '/user/palNum='+ palNum;
        */
        $http.post('/user', request).
            success(function(data, status, headers, config) {
            	alert("New student created");
                console.log("New student created");
            }).
            error(function(data, status, headers, config) {
            	alert("Error creating student");
                console.log("Error occurred in creating new student.");
                console.log(data);
            });
        $("#createForm1")[0].reset(); //reset form fields
    }

    //submit form to create a new PAL
    $scope.submitPAL = function(){
        var request={
            personId : 0, //auto generated in db
            userPassword : "passWORD123",
            userType : "pal",
            fName : $scope.fname,
            lName : $scope.lname,
            email : $scope.emailID,
            yearLevel : $scope.level,
            major : $scope.majorID
        }; //include admin email to verify who is making the request?

        $http.post('/user', request).
            success(function(data, status, headers, config) {
            	alert("New pal created");
                console.log("New PAL created");
            }).
            error(function(data, status, headers, config) {
            	alert("Error creating pal");
                console.log("Error occurred in creating new pal.");
                console.log(data);
            });
        $("#createForm2")[0].reset(); //reset form fields
    }

    //submit form to create a new Event
    $scope.submitEvent = function(){
        var request={
            eventId : 0, //auto generated by mysql
            eventName : $scope.name,
            location : "admin",
            date : $scope.fname,
            start : $scope.lname,
            end : $scope.emailID,
            description : $scope.level,
            attendees : $scope.attendees, //fix getting the list of attendees
            epId : 1 // what is epID
        }; //also include admin email to verify who is making the request

        $http.post('/events', request).
            success(function(data, status, headers, config) {
            	alert("New event created");
                console.log("New event created");
            }).
            error(function(data, status, headers, config) {
            	alert("Error creating event");
                console.log("Error occurred in creating new event.");
                console.log(data);
            });
        $("#createForm3")[0].reset(); //reset form fields
    }

});

app.controller("manageController", function($scope, $http){
	$scope.userType = sessionStorage.getItem('userType');
    $scope.currEmail = sessionStorage.getItem('currEmail');
    $scope.event = "editP";

    $("#loadPAL").click(); //loads the create student form

    $scope.dbPAL = [{name:"Anshul"},{name:"Greg"}];

    /*

     //get all the current pals at the load of this controller
     $http.get("user/pal").
     success(function(data, status, headers, config) {
     $scope.dbPAL = data;
     }).
     error(function(data, status, headers, config) {
     console.log("Error occurred in all PALs.");
     console.log(data);
     });

     */
    $scope.editPAL = function(){
        $scope.event = "editP";
    }
    $scope.editEvent = function(){
        $scope.event = "editE";
    }
});

app.controller("infoController", function($scope){
	$scope.userType = sessionStorage.getItem('userType');
    $scope.currEmail = sessionStorage.getItem('currEmail');
    $scope.options = ["File1", "File2", "File3"];
});

app.controller("questionController", function($scope){
	$scope.userType = sessionStorage.getItem('userType');
    $scope.currEmail = sessionStorage.getItem('currEmail');
});
