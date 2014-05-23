var app = angular.module('showLovers', []);

app.controller('MainCtrl', function($scope){
  $scope.users = [{name:'Cedric'},{name:'JB'}];
})
