/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


var meritworkApp = angular.module('meritworkApp', [
    'ngRoute',
    'ngCookies',
    'ui.bootstrap',
    'ui.bootstrap.typeahead',
    'webcam'
]);


meritworkApp.config(["$httpProvider", "$routeProvider", function($httpProvider, $routeProvider) {
        
    $httpProvider.defaults.transformRequest = function (data) {
        if(data === undefined){
            return data;
        }
        return $.param(data);
    };

    $httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=UTF-8";

    var param = function(obj) {
        var query = '', name, value, fullSubName, subName, subValue, innerObj, i;

        for(name in obj) {
          value = obj[name];

          if(value instanceof Array) {
            for(i=0; i<value.length; ++i) {
              subValue = value[i];
              fullSubName = name + '[' + i + ']';
              innerObj = {};
              innerObj[fullSubName] = subValue;
              query += param(innerObj) + '&';
            }
          }
          else if(value instanceof Object) {
            for(subName in value) {
              subValue = value[subName];
              fullSubName = name + '[' + subName + ']';
              innerObj = {};
              innerObj[fullSubName] = subValue;
              query += param(innerObj) + '&';
            }
          }
          else if(value !== undefined && value !== null)
            query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
        }

        return query.length ? query.substr(0, query.length - 1) : query;
    };

    // Override $http service's default transformRequest
    $httpProvider.defaults.transformRequest = [function(data) {
      return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
    }];



    $routeProvider.
            when('/', {
                templateUrl : 'partials/home.html',
                controller  : 'HomeCtrl'
            }).
            when('/login', {
                templateUrl : 'partials/login.html',
                controller  : 'LoginCtrl'
            }).
            when('/register', {
                templateUrl : 'partials/register.html',
                controller  : 'RegisterCtrl'
            }).
            when('/profile', {
                templateUrl : 'partials/profile/profile.html',
                controller  : 'ProfileCtrl'
            }).
            when('/test', {
                templateUrl : 'partials/test.html',
                controller  : 'TestCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
                
}]);



meritworkApp.run( function($rootScope, $location) {

    $rootScope.$on( "$routeChangeStart", function(event, next, current, previous) {
        if ( $rootScope.loggedUser.success === false ) {
            if ( next.templateUrl === "partials/login.html" ) {
//                console.log("login partial");
            } else if ( next.templateUrl === "partials/register.html" ) {
//                console.log("register partial");
            } else {
//                console.log("Default");
                $location.path( "/login" );
            }
        }
    });
    
      
    $rootScope.$on( "$routeChangeSuccess", function(event,next,current,previous) {
        
    });
      
 });