/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


meritworkApp.controller('HomeCtrl', function($scope, $http, $cookies, $location) {
    
    
    /* ****************
     * *** SERVICES ***
     * ****************
     */
    
    var service1 = {
        'service'   : 'Dog-walking',
        'providers' : 15,
        'clients'   : 214
    };
    
    var service2 = {
        'service'   : 'Computer Science',
        'providers' : 12,
        'clients'   : 198
    };
    
    var service3 = {
        'service'   : 'Babysitting',
        'providers' : 11,
        'clients'   : 172
    };
    
    var services = [service1, service2, service3];
    $scope.services = services;
    
    
    
    /* ****************
     * *** MESSAGES ***
     * ****************
     */
    
    var msg1 = {
        'from'      : 'John',
        'message'   : 'Can we establish a face-to-face meeting ?',
        'date'      : '1h ago'
    };
    
    var msg2 = {
        'from'      : 'John',
        'message'   : "I'm excided of what you have to offer.",
        'date'      : '2h ago'
    };
    
    var msg3 = {
        'from'      : 'John',
        'message'   : "Send me a short CV on my mail.",
        'date'      : '1day ago'
    };
    
    var messages = [msg1, msg2, msg3];
    $scope.messages = messages;
    
    
    
    
    
    
    
    
    
    /** *************
     *  *** TESTS ***
     *  *************
     */
    
    
    
    
    
});