/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


meritworkApp.controller('MainCtrl', function($scope, $rootScope, $http, $cookies, $location) {
    
    $scope.sessionId            = $cookies.session;
    $rootScope.loggedUser       = {};
    $scope.menus = {};
    
    
    $scope.$on("select_sidebar_cat", function(event, cat){
        $scope.category = cat;
        $http.get(
            serverUrl + 'wr/menus/find/' + $cookies.session
            ).success(function(data) {
                $scope.menus = data.menus;
            }).error(function(data, status) {
                console.log(data);
                console.log(status);
            });
    });
    
    
    /** *******************
     *  *** LOGGED USER ***
     *  *******************
     */
    if (typeof $scope.sessionId === 'undefined') {
        $rootScope.loggedUser = {
            'success'   : false,
            'error'     : "cookie doesn't exist"
        };
    } else {
        var content = { 'sessionId' : $scope.sessionId };
        $http({
            method: 'POST',
            url: serverUrl + 'wr/users/loggedIn',
            data: content,
                 headers: {
                     'Content-Type': 'application/json'
            }}).success(function(data) {
                $rootScope.loggedUser = data;
            }).error(function(data, status) {
                $rootScope.loggedUser = {
                    'success'   : false,
                    'error'     : "cookie doesn't exist"
                };
            });
        
    }
    
    
    /** *******************
     *  *** FETCH MENUS ***
     *  *******************
     */
    $http.get(
        serverUrl + 'wr/menus/find/' + $cookies.session
        ).success(function(data) {
            $scope.menus = data.menus;
        }).error(function(data, status) {
            console.log(data);
            console.log(status);
        });
        
        
    /** *******************
     *  *** TOGGLE MENU ***
     *  *******************
     */
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
    $scope.sidebarToggle = function($event) {
        console.out("scope sidebar toggle function");
        $event.preventDefault();
        $("#wrapper").toggleClass("toggled");
        $scope.sidebarToggled = !$scope.sidebarToggled;
    };
    
    
    /** **************
     *  *** LOGOUT ***
     *  **************
     */
    $scope.logout = function(path) {
        $rootScope.loggedUser = {
            'success'   : false,
            'error'     : "cookie doesn't exist"
        };
        delete $cookies["session"];
        window.location.reload();
        $location.path(path);
        
    };
    
    
    
    
    
    
});