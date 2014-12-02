/* 
 * File created by ashcrok
 *                 Mihai Pricop
 */


meritworkApp.controller('TestCtrl', function($scope, $http, $cookies, $location) {
    
    $scope.patOpts = {x: 0, y: 0, w: 25, h: 25};
    var _video = null;
    
    
    $scope.webcamError = false;
    $scope.onError = function (err) {
        console.log(">> onError reched");
        $scope.$apply(
            function() {
                $scope.webcamError = err;
            }
        );
    };

    $scope.onSuccess = function (videoElem) {
        // The video element contains the captured camera data
        console.log(">> onSuccess reched");
        _video = videoElem;
        $scope.patOpts.w = _video.width;
        $scope.patOpts.h = _video.height;
    };

    $scope.onStream = function (stream, videoElem) {
        console.log(">> onStream reched");
        // You could do something manually with the stream.
    };
    
    
    $scope.makeSnapshot = function makeSnapshot() {
        console.log("Button clicked.");
        if (_video) {
            console.log("Video exists...");
            
            var patCanvas = document.querySelector('#snapshot');
            if (!patCanvas) return;
            
            console.log("Found snapshot...");

            patCanvas.width = _video.width;
            patCanvas.height = _video.height;
            var ctxPat = patCanvas.getContext('2d');
            console.log("width: " + patCanvas.width);
            console.log("height: " + patCanvas.height);

            var idata = getVideoData($scope.patOpts.x, $scope.patOpts.y, $scope.patOpts.w, $scope.patOpts.h);
            console.log(idata);
            ctxPat.putImageData(idata, 0, 0);
        }
    };
    
    var getVideoData = function getVideoData(x, y, w, h) {
        var hiddenCanvas = document.createElement('canvas');
        hiddenCanvas.width = _video.width;
        hiddenCanvas.height = _video.height;
        var ctx = hiddenCanvas.getContext('2d');
        ctx.drawImage(_video, 0, 0, _video.width, _video.height);
        return ctx.getImageData(x, y, w, h);
    };
    
    
});