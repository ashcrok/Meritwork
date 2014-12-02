/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


meritworkApp.controller('NewServiceCtrl', function($http, $scope, $rootScope, $modalInstance, $cookies) {
    
    $scope.service          = {};
    $scope.service.location = $rootScope.loggedUser.user.location;
    $scope.service.contact  = [];
    $scope.service.privacy  = false;
    
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
        
    var autocomplete = new google.maps.places.Autocomplete(
            (document.getElementById('autocomplete')),
            { types: ['geocode'] }
    );
    google.maps.event.addListener(autocomplete, 'place_changed', function() {
        var place = autocomplete.getPlace();
        $scope.service.location = place.formatted_address;
    });
    
    
    $scope.checkboxClick = function(contact) {
        var exists = true;
        for (var index_contact in $scope.service.contact) {
            if ($scope.service.contact.hasOwnProperty(index_contact) && $scope.service.contact[index_contact] === contact) {
                $scope.service.contact.splice(index_contact,1);
                exists = false;
            }
        }
        if (exists) {
            $scope.service.contact.push(contact);
        }
    };
    
    $scope.isChecked = function(contact) {
        for (var index_contact in $scope.service.contact) {
            if ($scope.service.contact.hasOwnProperty(index_contact) && $scope.service.contact[index_contact] === contact) {
                return true;
            }
        }
        return false;
    };
    
    
    
    
    $scope.submit = function() {        
        
        if (!$scope.service.service || !$scope.service.occupation || !$scope.service.industry) {
            $scope.error        = 'true';
            $scope.errorMessage = 'The Service Name, Occupation and Industry fields cannot be blank.';
        } else {
            var content = {
                'sessionId'     : $cookies.session,
                'service'       : $scope.service.service
            };
            if ($scope.service.occupation.occupation) { content.occupation = $scope.service.occupation.occupation; }
                else if ($scope.service.occupation) { content.occupation = $scope.service.occupation; }
            if ($scope.service.industry.industry) { content.industry = $scope.service.industry.industry; }
                else if ($scope.service.industry) { content.industry = $scope.service.industry; }
            if ($scope.service.description) { content.description   = $scope.service.description; }
            if ($scope.service.location)    { content.location      = $scope.service.location; }
            if ($scope.service.contact)    { content.contacts      = $scope.service.contact; }
            if ($scope.service.privacy)     { content.privacy       = $scope.service.privacy; }
            
            /* UPDATE IN REST API */
            $http({
                method: 'POST',
                url: serverUrl + 'wr/services/add',
                data: JSON.stringify(content),
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8'
                }}).success(function(data) {
                    console.log(data);
                    if (data.success === "true") {
                        $modalInstance.close(content);
                    } else {
                        $scope.error = 'true';
                        $scope.errorMessage = 'Server error occured.';
                    }
                }).error(function(data, status) {
                    $modalInstance.close(data);
                    console.log(data);
                    console.log(status);
                });
            
        }
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };
    
});