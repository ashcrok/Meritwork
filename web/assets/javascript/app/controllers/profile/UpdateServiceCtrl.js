/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


meritworkApp.controller('UpdateServiceCtrl', function($http, $scope, $rootScope, $modalInstance, $cookies, service) {
    
    $scope.service = service;
    if (service.contacts === "[]") { $scope.service.contacts = []; }
    
    
    
    $scope.checkboxClick = function(contact) {
        console.log(contact);
        var exists = true;
        for (var index_contact in $scope.service.contacts) {
            if ($scope.service.contacts.hasOwnProperty(index_contact) && $scope.service.contacts[index_contact] === contact) {
                $scope.service.contacts.splice(index_contact,1);
                exists = false;
            }
        }
        if (exists) {
            $scope.service.contacts.push(contact);
        }
    };
    
    $scope.isChecked = function(contact) {
        for (var index_contact in $scope.service.contacts) {
            if ($scope.service.contacts.hasOwnProperty(index_contact) && $scope.service.contacts[index_contact] === contact) {
                return true;
            }
        }
        return false;
    };
    
    
    
    $scope.submit = function() {
        console.log($scope.service);
    };
    
    $scope.deleteService = function() {
        var content = {
            'sessionId' : $cookies.session,
            'service'   : $scope.service.service
        };
        
        /* UPDATE IN REST API */
        $http({
            method: 'POST',
            url: serverUrl + 'wr/services/delete',
            data: JSON.stringify(content),
                headers: {
                    'Content-Type': 'application/json; charset=utf-8'
            }}).success(function(data) {
                console.log(data);
                if(data.success === "false") {
                    console.log(data);
                } else {
                    var backvar = {
                        'type'      : "delete",
                        'content'   : $scope.service.service
                    };
                    $modalInstance.close(backvar);
                }
                
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