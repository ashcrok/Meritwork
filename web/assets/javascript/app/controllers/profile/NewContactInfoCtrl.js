/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


meritworkApp.controller('NewContactInfoCtrl', function($http, $scope, $rootScope, $modalInstance, $cookies) {
    
    $scope.contact = {};
    
    
    $scope.submit = function() {
        if (!$scope.contact.type || !$scope.contact.content) {
            $scope.error = 'true';
            $scope.errorMessage = 'The Type or Content field cannot be blank.';
        }
        
        var new_contact = {
                "content"   : $scope.contact.content,
                "type"      : $scope.contact.type
            };
        
        $rootScope.loggedUser.user.contact.push(new_contact);
        
        var content = {
            'sessionId' : $cookies.session,
            'user'      : $rootScope.loggedUser.user
        };
        
        /* UPDATE IN REST API */
        $http({
            method: 'POST',
            url: serverUrl + 'wr/users/update',
            data: JSON.stringify(content),
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
            }}).success(function(data) {
                if(data.success === "false") {
                    console.log(data);
                }
                $modalInstance.close(data);
            }).error(function(data, status) {
                $modalInstance.close(data);
                console.log(data);
                console.log(status);
            });
        
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };
    
});