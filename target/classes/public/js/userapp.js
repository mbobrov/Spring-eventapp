var userApp = angular.module('UserApp', ['ngRoute']);

userApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/users/add', {
                templateUrl: 'createUser.html',
                controller: 'UserController'
            }).
            when('/users/edit/:id', {
                templateUrl: 'editUser.html',
                controller: 'UserController'
            }).
            when('/users/list', {
                templateUrl: 'listUsers.html',
                controller: 'UserController'
            });/*.
            otherwise({
                redirectTo: '/users'
            });*/
    }]
);

userApp.controller('UserController', ['$scope', '$http', '$routeParams', '$location', '$parse',
    function UserController($scope, $http, $routeParams, $location, $parse) {

        if($routeParams.id){
            $http.get('/user/'+$routeParams.id)
                .success(function(data) {
                    $scope.user = data;
                }
            )
                .error(function (data, status, headers, config) {
                    alert("error");
                    return status;
                }
            );
        } else {
            $scope.user = {};
        }

        $scope.getUsers = function() {
            $http({method: 'GET', url: '/users'}).success(function (data) {
                console.log("DATA:" + data);
                $scope.users = data;
            });
        };

        $scope.submitUser = function(event) {
            $scope.createUser();
            event.preventDefault();
        }

        $scope.getUsers();

        $scope.form = {};

        $scope.createUser = function() {
            /*if($scope.userData.name.$error.invalidName) {
                $scope.userData.$error.errors = "Please correct the errors highlighted above";
                return false;
            }*/
            console.log("Create user with data:"+JSON.stringify($scope.form)+"|");
            $http.post('/user', JSON.stringify($scope.form))
                .success(function(){
                    console.log("USER created successfully");
                    $scope.getUsers();
                    $location.path('/users/list');
                }
            ).error(function (data, status, headers, config) {
                    console.log("CreateUser data: "+JSON.stringify(data));
                    console.log("CreateUser status: "+status);
                    if(data.valueOf() == "" && status == 0) return 0;
                    //$location.path('/users/add');
                    if(data instanceof Array && data.length > 0) {
                        for(i=0; i < data.length; i++) {
                            var entry = data[i];
                            console.log("Error for field "+entry.field+", message: "+entry.defaultMessage);
                            if(!$scope.userData[entry.field].$error.serverMsg)
                                $scope.userData[entry.field].$error.serverMsg = [];
                            $scope.userData[entry.field].$error.serverMsg.push(entry.defaultMessage);
                            $scope.userData[entry.field].$error.server = true;
                            //$scope.form[entry.field].$error.server = entry.defaultMessage;
                        }
                    }
                    return status;
                }
            );
        };

        $scope.userCheck = function() {
            console.log("Name:"+$scope.form.name);
            if($scope.form.name) {
                $http.get('/username/' + $scope.form.name).success(function(data) {
                    console.log("Username check:"+(data === "true"));
                    //set the validity of the field
                    $scope.userData.name.$error.invalidName = (data === "true");
                });
            }
        };

        $scope.pswdCheck = function() {
            console.log("Pswd1:"+$scope.form.password1+", Pswd2:"+$scope.form.password2);
            $scope.userData.password2.$error.mismatch = ($scope.form.password1 !== $scope.form.password2);
        };

        $scope.editUser = function(id) {
            console.log(" - Edit User "+id+"CALL!");
            $location.path('/users/edit/'+id);
        };

        $scope.doSave = function(user) {
            var response = $http.put("/user/"+$scope.user.id, JSON.stringify($scope.user));
            return response;
        };

        $scope.saveUser = function() {
            console.log(" - Save User "+JSON.stringify($scope.user)+" CALL!");
            $scope.doSave($scope.user).success(function(data) {
                console.log("SaveUser SUCCESS: "+JSON.stringify(data));
                $scope.getUsers();
                $location.path('/users/list');
            })
                .error(function (data, status, headers, config) {
                    console.log("SaveUser error: "+status);
                    return status;
                }
            );
        };

        $scope.deleteUser = function(id) {
            console.log("Location:"+$location.path());
            console.log(" - Delete User "+id+"CALL!");
            $http.delete('/deleteUser/'+id).success(function() {
                $scope.getUsers();
                //$location.path('/users/list');
            });
        };
    }
]);

userApp.directive('username', ['$http', function($http) {
    var toId;
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elem, attr, ctrl) {
            //when the scope changes, check the email.
            scope.$watch(attr.ngModel, function(value) {
                // if there was a previous attempt, stop it.
                if(toId) clearTimeout(toId);
                if(value.valueOf().length == 0) return;

                // start a new attempt with a delay to keep it from
                // getting too "chatty".
                toId = setTimeout(function(){
                    // call to some API that returns { isValid: true } or { isValid: false }
                    $http.get('/username/' + value).success(function(data) {
                        console.log("Username check:"+JSON.stringify(data));
                        //set the validity of the field
                        ctrl.$setValidity('name', data);
                    });
                }, 2000);
            })
        }
    }
}]);

userApp.directive('onEveryChar', ['$http', function($http) {
    var toId, valid;
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elem, attr, ctrl) {
            ctrl.$parsers.unshift(function(value) {
                if(value && value !== ""){
                    valid = (value == 'user1' ||
                        value == 'user2') ? "true" : "false";
                    ctrl.$setValidity('invalidName', valid === "true");
                    console.log("check:"+valid);
                }
                return valid ? value : undefined;
            });
        }
    }
}]);