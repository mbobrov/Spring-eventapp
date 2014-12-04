var eventApp = angular.module('EventApp', ['ngRoute']);

eventApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/events/add', {
                templateUrl: 'editEvent.html',
                controller: 'EventController'
            }).
            when('/events/edit/:id', {
                templateUrl: 'editEvent.html',
                controller: 'EventController'
            }).
            when('/events/list', {
                templateUrl: 'listEvents.html',
                controller: 'EventController'
            });/*.
            otherwise({
                redirectTo: '/events'
            });*/
    }]
);

eventApp.controller('EventController', ['$scope', '$http', '$routeParams', '$location', '$parse',
    function EventController($scope, $http, $routeParams, $location, $parse) {

        if($routeParams.id){
            $http.get('/event/'+$routeParams.id)
                .success(function(data) {
                    $scope.event = data;
                }
            )
                .error(function (data, status, headers, config) {
                    alert("error");
                    return status;
                }
            );
        } else {
            $scope.event = {};
        }

        $scope.getEvents = function() {
            $http({method: 'GET', url: '/events'}).success(function (data) {
                console.log("DATA:" + data);
                $scope.events = data;
            });
        };

        $scope.getParticipants = function(name) {
            $http({method: 'GET', url: '/performer/'+name}).success(function (data) {
                console.log("DATA:" + data);
                $scope.participants = data;
            });
        };

        $scope.submitEvent = function(event) {
            $scope.createEvent();
            event.preventDefault();
        }

        $scope.getEvents();

        $scope.form = {};

        $scope.participants = [];

        $scope.createEvent = function() {
            console.log("Create event with data:"+JSON.stringify($scope.form)+"|");
            $http.post('/event', JSON.stringify($scope.form))
                .success(function(){
                    console.log("USER created successfully");
                    $scope.getEvents();
                    $location.path('/events/list');
                }
            ).error(function (data, status, headers, config) {
                    console.log("CreateEvent data: "+JSON.stringify(data));
                    console.log("CreateEvent status: "+status);
                    if(data.valueOf() == "" && status == 0) return 0;
                    //$location.path('/events/add');
                    if(data instanceof Array && data.length > 0) {
                        for(i=0; i < data.length; i++) {
                            var entry = data[i];
                            console.log("Error for field "+entry.field+", message: "+entry.defaultMessage);
                            if(!$scope.eventData[entry.field].$error.serverMsg)
                                $scope.eventData[entry.field].$error.serverMsg = [];
                            $scope.eventData[entry.field].$error.serverMsg.push(entry.defaultMessage);
                            $scope.eventData[entry.field].$error.server = true;
                            //$scope.form[entry.field].$error.server = entry.defaultMessage;
                        }
                    }
                    return status;
                }
            );
        };

        $scope.eventCheck = function() {
            console.log("Name:"+$scope.form.name);
            if($scope.form.name) {
                $http.get('/eventname/' + $scope.form.name).success(function(data) {
                    console.log("Eventname check:"+(data === "true"));
                    //set the validity of the field
                    $scope.eventData.name.$error.invalidName = (data === "true");
                });
            }
        };

        $scope.pswdCheck = function() {
            console.log("Pswd1:"+$scope.form.password1+", Pswd2:"+$scope.form.password2);
            $scope.eventData.password2.$error.mismatch = ($scope.form.password1 !== $scope.form.password2);
        };

        $scope.editEvent = function(id) {
            console.log(" - Edit Event "+id+"CALL!");
            $location.path('/events/edit/'+id);
        };

        $scope.doSave = function(event) {
            var response = $http.put("/event/"+$scope.event.id, JSON.stringify($scope.event));
            return response;
        };

        $scope.saveEvent = function() {
            console.log(" - Save Event "+JSON.stringify($scope.event)+" CALL!");
            $scope.doSave($scope.event).success(function(data) {
                console.log("SaveEvent SUCCESS: "+JSON.stringify(data));
                $scope.getEvents();
                $location.path('/events/list');
            })
                .error(function (data, status, headers, config) {
                    console.log("SaveEvent error: "+status);
                    return status;
                }
            );
        };

        $scope.deleteEvent = function(id) {
            console.log("Location:"+$location.path());
            console.log(" - Delete Event "+id+"CALL!");
            $http.delete('/deleteEvent/'+id).success(function() {
                $scope.getEvents();
                //$location.path('/events/list');
            });
        };
    }
]);

eventApp.directive('checkName', function() {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elem, attr, ctrl) {
            ctrl.$parsers.unshift(function(value) {
                if(value && (typeof value == 'string' || value instanceof String)
                    && value.length > 2){
                    $scope.getParticipants(value);
                    console.log("Participants requested:"+value);
                    return true;
                }
                return false;
            });
        }
    }
});