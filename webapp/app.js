var app = angular.module('showLovers', []);

app.controller('MainCtrl', function($scope, $http){
  $scope.users = [];

  $http.get('http://localhost:8081/users')
    .success(function(data){
      $scope.users = data;
    })
})
