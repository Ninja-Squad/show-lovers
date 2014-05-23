var app = angular.module('showLovers', ['ngRoute']);

app.config(function($routeProvider){
  $routeProvider
    .when('/', {
      templateUrl: 'views/main.html',
      controller: 'MainCtrl'
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
