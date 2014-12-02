/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


meritworkApp.controller('ProfileCtrl', function($scope, $rootScope, $modal, $http, $cookies) {
    
    
    /* *************** */ 
    /* ** USER INFO ** */ 
    /* *************** */
    
    function imageExists(url, callback) {
        var img = new Image();
        img.onload = function() { callback(true); };
        img.onerror = function() { callback(false); };
        img.src = url;
    }
    
    $scope.avatar = [];
    $scope.avatar.url = $rootScope.loggedUser.user.avatar;
    $scope.submitAvatar = function() {
        if ( $scope.avatar.url !== $rootScope.loggedUser.user.avatar ) {
            
            imageExists($scope.avatar.url, function(exists) {
                if (exists) {
                    $rootScope.loggedUser.user.avatar = $scope.avatar.url;

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

                            }
                        }).error(function(data, status) {
                            $modalInstance.close(data);
                            console.log(data);
                            console.log(status);
                        });
                } else {
                    /* Image doesn't exist */
                }
            });
        }
    };
    
    
    var autocomplete = new google.maps.places.Autocomplete(
            (document.getElementById('autocomplete')),
            { types: ['geocode'] }
    );
    google.maps.event.addListener(autocomplete, 'place_changed', function() {
        var place = autocomplete.getPlace();
        $rootScope.loggedUser.user.location = place.formatted_address;
    });
    
    $scope.submitLocation = function() {        
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
                    
                }
            }).error(function(data, status) {
                $modalInstance.close(data);
                console.log(data);
                console.log(status);
            });
    };
    
    
    if ($rootScope.loggedUser.user.privacy === 1) {
        $scope.privacy = "PUBLIC";
    } else {
        $scope.privacy = "PRIVATE";
    }
    $scope.changePrivacy = function() {
        if ($scope.privacy === "PUBLIC") {
            $scope.privacy = "PRIVATE";
            $rootScope.loggedUser.user.privacy = 0;
        } else if ($scope.privacy === "PRIVATE") {
            $scope.privacy = "PUBLIC";
            $rootScope.loggedUser.user.privacy = 1;
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
                    
                }
            }).error(function(data, status) {
                $modalInstance.close(data);
                console.log(data);
                console.log(status);
            });
    };
    
    
    
    /* ************ */
    /* CONTACT INFO */ 
    /* ************ */
    
    $scope.editContact = function(contact) {
        var newPostModal = $modal.open({
            templateUrl: 'partials/profile/updateContactInfo.html',
            controller: 'UpdateContactInfoCtrl',
            resolve: {
                contact: function() {
                    return contact;
                }
            }
        });

        newPostModal.result.then(function() {
            
        });
    };
    
    $scope.newContact = function() {
        var newPostModal = $modal.open({
            templateUrl: 'partials/profile/newContactInfo.html',
            controller: 'NewContactInfoCtrl'
        });

        newPostModal.result.then(function() {
            
        });
    };
    
    
    
    /* *************** */
    /* CHANGE PASSWORD */
    /* *************** */
    
    $scope.password = {};
    $scope.password.changePassword = {};
    $scope.changePassword = function() {
        if (!$scope.password.changePassword.currentPassword ||
            !$scope.password.changePassword.newPassword ||
            !$scope.password.changePassword.confirmNewPassword) {
                $scope.password.error = 'true';
                $scope.password.errorMessage = 'All fields must be completed.';
        } else if ($scope.password.changePassword.newPassword !== $scope.password.changePassword.confirmNewPassword) {
            $scope.password.error = 'true';
            $scope.password.errorMessage = 'New Password and Confirm New Password fields must match.';
        } else {
            var jsshaCurrent    = new jsSHA($scope.password.changePassword.currentPassword, "TEXT");
            var currentPassword = jsshaCurrent.getHash("SHA-1", "HEX");
            
            var jsshaNew    = new jsSHA($scope.password.changePassword.newPassword, "TEXT");
            var newPassword = jsshaNew.getHash("SHA-1", "HEX");
            
            var content = { 
                "sessionId"         : $cookies.session,
                "currentPassword"   : currentPassword,
                "newPassword"       : newPassword
            };
                        
            $http({
                method: 'POST',
                url: serverUrl + 'wr/users/changePassword',
                data: JSON.stringify(content),
                     headers: {
                         'Content-Type': 'application/json; charset=utf-8'
                }}).success(function(data) {
                    if (data.success === "false") {
                        $scope.password.error = 'true';
                        $scope.password.errorMessage = data.errorMessage;
                    } else if (data.success === "true") {
                        $scope.password.error = 'false';
                        $scope.password.finish = 'true';
                        $scope.password.finishMessage = "Password changed successfully.";
                    }
                }).error(function(data, status) {
                    console.log(data);
                    console.log(status);
                });
        }
    };
    
    
    
    /* **************** */
    /* *** SERVICES *** */
    /* **************** */
    
    
    var content = { 
        "sessionId" : $cookies.session
    };
    $http({
        method: 'POST',
        url: serverUrl + 'wr/services/find',
        data: JSON.stringify(content),
             headers: {
                 'Content-Type': 'application/json; charset=utf-8'
        }}).success(function(data) {
            $scope.services = data.services;
        }).error(function(data, status) {
            console.log(data);
            console.log(status);
        });
        
        
    $scope.newService = function() {
        var newPostModal = $modal.open({
            templateUrl: 'partials/profile/newService.html',
            controller: 'NewServiceCtrl'
        });

        newPostModal.result.then(function(data) {
            var new_service = {
                'occupation' : data.occupation
            };
            if (data.description) { new_service.description = data.description; }
            $scope.services.push(data);
        });
    };
    
    $scope.updateService = function(service) {
        var newPostModal = $modal.open({
            templateUrl: 'partials/profile/updateService.html',
            controller: 'UpdateServiceCtrl',
            resolve: {
                service: function() {
                    return service;
                }
            }
        });

        newPostModal.result.then(function(data) {
            if (data.type === "delete") {
                $scope.services.splice($scope.services.indexOf(data.content),1);
            } else if (type === "update") {
                
            }
        });
    };
    
    $scope.canProvideService = function() {
        if (!$rootScope.loggedUser.user.location ||
            $rootScope.loggedUser.user.contact.length < 1) {
                return false;
        }
        return true;
    };
    
    
    
    
});