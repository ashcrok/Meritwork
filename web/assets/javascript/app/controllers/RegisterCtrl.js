/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


meritworkApp.controller('RegisterCtrl', function($scope, $http, $location) {
    
    $scope.input = {};
    
    $scope.submit = function() {
        if(!$scope.input.password || !$scope.input.confirmPassword ||
            !$scope.input.email || !$scope.input.firstName || !$scope.input.lastName) {
            $scope.error = 'true';
            $scope.errorMessage = 'Complete all fields.';
        } else if ($scope.input.password === $scope.input.repeatPassword) {
            $scope.error = 'true';
            $scope.errorMessage = 'Password and Repeat Password do not coincide.';
            /* TODO: Unique username verification */
        } else {
            $scope.error = 'false';
            var jssha       = new jsSHA($scope.input.password, "TEXT");
            var password    = jssha.getHash("SHA-1", "HEX");
            var email       = $scope.input.email;
            var firstName   = $scope.input.firstName;
            var lastName    = $scope.input.lastName;
            
            var content = {
                password    : password,
                email       : email,
                firstName   : firstName,
                lastName    : lastName
            };
            
            $http({
                method: 'POST',
                url: serverUrl + 'wr/users/add',
                data: JSON.stringify(content),
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
                }}).success(function(data) {
                    console.log(data);
                    $location.path("/#/login");
                }).error(function(data, status) {
                    console.log(data);
                    console.log(status);
                });
        }
    };
    
    
});