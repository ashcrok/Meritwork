/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


meritworkApp.controller('LoginCtrl', function($scope, $rootScope, $http, $cookies, $location) {
    
    $scope.input = {};
    
    $scope.submit = function() {
        if(!$scope.input.email || !$scope.input.password) {
            $scope.error = 'true';
            $scope.errorMessage = 'The email or password field cannot be blank.';
        } else {
            $scope.error = 'false';
            var email       = $scope.input.email;
            var jssha       = new jsSHA($scope.input.password, "TEXT");
            var password    = jssha.getHash("SHA-1", "HEX");
            
            var content = {
                email       : email,
                password    : password
            };
            
            $http({
                method: 'POST',
                url: serverUrl + 'wr/users/login',
                data: content,
                    headers: {
                        'Content-Type': 'multipart/form-data'
                }}).success(function(data) {
                    if (data === "false") {
                        $rootScope.loggedUser = {
                            'success'   : false,
                            'error'     : "cookie doesn't exist"
                        };
                        
                        $scope.error = 'true';
                        $scope.errorMessage = 'Login failed. Bad credentials.';
                    } else {
                        $cookies.session = data;
                        $scope.$emit('select_sidebar_cat', "main");
                        var content = { 'sessionId' : data };
                        $http({
                            method: 'POST',
                            url: serverUrl + 'wr/users/loggedIn',
                            data: content,
                                 headers: {
                                     'Content-Type': 'application/json'
                            }}).success(function(data) {
                                $rootScope.loggedUser = data;
                                $location.path("/");
                            }).error(function(data, status) {
                                $rootScope.loggedUser = {
                                    'success'   : false,
                                    'error'     : "cookie doesn't exist"
                                };
                            });
                        $location.path("/#");
                    }
                });
        }
    };
    
    $scope.guestLogin = function() {
        $http.get(
            serverUrl + 'wr/users/guestLogin'
            ).success(function(data) {
                $cookies.session = data.session;
                $rootScope.loggedUser = data.loggedUser;
                $location.path("/");
                $scope.$emit('select_sidebar_cat', "main");
            }).error(function(data, status) {
                console.log(data);
                console.log(status);
            });
    };
    
    
});