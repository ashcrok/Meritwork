/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


meritworkApp.controller('UpdateContactInfoCtrl', function($http, $rootScope, $scope, $modalInstance, $cookies, contact) {
    
    $scope.contact = {};
    $scope.contact.type = contact.type;
    $scope.contact.content = contact.content;
    
    $http({
        method: 'GET',
        url: serverUrl + 'wr/occupations/find'
        }).success(function(data) {
            $scope.occupations = data.occupations;
        }).error(function(data, status) {
            console.log(data);
            console.log(status);
        });
        
     $http({
        method: 'GET',
        url: serverUrl + 'wr/industry/find'
        }).success(function(data) {
            $scope.industries = data.industries;
        }).error(function(data, status) {
            console.log(data);
            console.log(status);
        });
    
    $scope.isPrimaryEmail = function() {
        if (contact.type === "primary email") {
            console.log(true);
            return true;
        } else {
            return false;
        }
    };
    
    
    $scope.update = function() {
        if (!$scope.contact.type || !$scope.contact.content) {
            $scope.error = 'true';
            $scope.errorMessage = 'The Type or Content field cannot be blank.';
        }
        
        for (key in $rootScope.loggedUser.user.contact) {
            if ($rootScope.loggedUser.user.contact[key].type === contact.type &&
                $rootScope.loggedUser.user.contact[key].content === contact.content) {
                    $rootScope.loggedUser.user.contact[key].type = $scope.contact.type;
                    $rootScope.loggedUser.user.contact[key].content = $scope.contact.content;
            }
        }
        
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
                } else {
                    //$rootScope.loggedUser.user.contact = contacts;
                }
                $modalInstance.close(data);
            }).error(function(data, status) {
                $modalInstance.close(data);
                console.log(data);
                console.log(status);
            });
    };
    
    
    
    
    $scope.deleteContact = function() {
                
        for (key in $rootScope.loggedUser.user.contact) {
            if ($rootScope.loggedUser.user.contact[key].type === contact.type &&
                $rootScope.loggedUser.user.contact[key].content === contact.content) {
                    $rootScope.loggedUser.user.contact.splice(key,1);
            }
        }
        
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
                } else {
                    //$rootScope.loggedUser.user.contact = contacts;
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