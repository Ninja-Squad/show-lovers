var app = angular.module('showLovers', ['ngRoute', 'ngCookies']);

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
    .when('/login', {
      templateUrl: 'views/login.html',
      controller: 'LoginCtrl'
    })
    .when('/top', {
      templateUrl: 'views/top.html',
      controller: 'TopCtrl'
    })
    .when('/shows/:id', {
      templateUrl: 'views/show.html',
      controller: 'ShowCtrl'
    })
    .otherwise('/');
});

app.run(function($http, $cookieStore){
  $http.defaults.headers.common['token'] = $cookieStore.get('login');
});

app.controller('MainCtrl', function($scope, $http, $cookieStore){
  $scope.users = [];

  $scope.isLogged = angular.isDefined($cookieStore.get('login'));

  $http.get('http://localhost:8081/users')
    .success(function(data){
      $scope.users = data;
    })
});

app.controller('RegisterCtrl', function($scope, $http, $location){
  $scope.register = function(){
    $http.post('http://localhost:8081/users', $scope.user)
      .success(function(){
        $location.path('/');
      })
      .error(function(){
        $log.error('bad registration');
      });
  }
});

app.controller('LoginCtrl', function($scope, $http, $location, $log, $cookieStore){
  $scope.login = function(){
    $http.post('http://localhost:8081/login', $scope.credentials)
      .success(function(data){
        $cookieStore.put('login', data);
        $http.defaults.headers.common['token'] = data;
        $location.path('/');
      })
      .error(function(){
        $log.error('bad login');
      });
  }
});

app.controller('TopCtrl', function($scope, $http, $log){

  $http.get('http://localhost:8081/shows')
    .success(function(data){
      $scope.shows = data;
    })
    .error(function(){
      $log.error('error while fetching shows');
    });

});

app.controller('ShowCtrl', function($scope, $http, $log, $routeParams){

  $http.get('http://localhost:8081/shows/' + $routeParams.id)
    .success(function(data){
      $scope.show = data;
    })
    .error(function(){
      $log.error('error while fetching show ' + $routeParams.id);
    });

    $scope.vote = function(){
      $http.put('http://localhost:8081/shows/' + $routeParams.id)
        .success(function(data){
          $scope.show = data;
        })
        .error(function(){
          $log.error('error while fetching show ' + $routeParams.id);
        });
    }

    $scope.downvote = function(){
      $http.delete('http://localhost:8081/shows/' + $routeParams.id)
        .success(function(data){
          $scope.show = data;
        })
        .error(function(){
          $log.error('error while fetching show ' + $routeParams.id);
        });
    }

});
