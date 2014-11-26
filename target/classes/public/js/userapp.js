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
            }).
            otherwise({
                redirectTo: '/users'
            });
    }]
);

userApp.controller('UserController', ['$scope', '$http', '$routeParams', '$location',
    function UserController($scope, $http, $routeParams, $location) {

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

        $scope.getUsers();

        $scope.form = {};

        $scope.createUser = function() {
            console.log("Create user with data:"+JSON.stringify($scope.form)+"|");
            $http.post('/user', JSON.stringify($scope.form))
                .success(function(){
                    console.log("USER created successfully");
                    $scope.getUsers();
                    $location.path('/users/list');
                }
            ).error(function (data, status, headers, config) {
                    console.log("CreateUser data: "+data);
                    console.log("CreateUser status: "+status);
                    return status;
                }
            );
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