/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


var meritworkApp = angular.module('meritworkApp', [
    'ngRoute',
    'ngCookies',
    'ui.bootstrap'
]);

meritworkApp.config(["$httpProvider", "$routeProvider", function($httpProvider, $routeProvider) {
        
        $httpProvider.defaults.transformRequest = function (data) {
            if(data == undefined){
                return data
            }
            return $.param(data)
        }

        $httpProvider.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=UTF-8";
        
        $routeProvider.
                when('/', {
                    templateUrl : 'partials/home.html',
                    controller  : 'HomeCtrl'
                }).
                otherwise({
                    redirectTo: '/'
                })
}]);