/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


meritworkApp.controller('MainCtrl', function($scope, $http, $cookies) {
        
    $scope.loggedIn = {
        logged  : 'false',
        user    : 'user'
    }
    
    $http.get('TestServlet')
        .success(function(data) {
            console.log(data);
        });
    
});