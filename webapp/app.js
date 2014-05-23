var app = angular.module('showLovers', ['ngRoute']);

app.config(function($routeProvider){
  $routeProvider
    .when('/', {
      templateUrl: 'views/main.html',
      controller: 'MainCtrl'
    })
    .when('/register', {
      templateUrl: 'views/register.html',
      controller: 'RegisterCtrl'
    })
    .otherwise('/')
});

app.controller('MainCtrl', function($scope, $http){
  $scope.users = [];

  $http.get('http://localhost:8081/users')
    .success(function(data){
      $scope.users = data;
    })
});

app.controller('RegisterCtrl', function($scope, $http, $location){
  $scope.register = function(){
    $http.post('http://localhost:8081/users', $scope.user)
      .success(function(){
        $location.path('/')
      })
  }
});
