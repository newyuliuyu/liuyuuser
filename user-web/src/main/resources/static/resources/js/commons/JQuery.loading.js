(function () {
    "use strict";
    var deps = ['jquery', 'js/commons/JQuery.errorDialog'];
    define(deps, function ($) {

        function getHeightAndWidth($target) {
            var tagName = $target.get(0).tagName;

            var result = {};
            if (tagName.toLowerCase() === 'body') {
                var $window = $(window);
                result = {
                    width: $window.width(),
                    height: $window.height()
                }
            } else {
                result = {
                    width: $target.offsetWidth(),
                    height: $target.offsetHeight()
                }

            }
            return result;
        }

        function calcuateLeftAndTop(widthAndHeight) {
            var width = widthAndHeight.width;
            var height = widthAndHeight.height;
            var top = height / 3;
            var left = width / 2 - 70;
            return {
                left: left,
                top: top
            }
        }

        function create($target) {
            var widthAndHeight = getHeightAndWidth($target);
            var topAndLeft = calcuateLeftAndTop(widthAndHeight);
            var $parentDiv = $('<div>', {
                id: 'loadingDIV',
                css: {
                    'display': 'none',
                    'z-index': '10000000',
                    'position': 'absolute',
                    'top': '0px',
                    'width': widthAndHeight.width + "px",
                    'height': widthAndHeight.height + "px"
                }
            });

            var $backgroudDIV = $('<div>', {
                css: {
                    'filter': 'alpha(Opacity=60)',
                    '-moz-opacity': '0.6',
                    'opacity': '0.6',
                    'background-color': '#cccccc',
                    'z-index': '1001',
                    'position': 'relative',
                    'top': '0px',
                    'width': widthAndHeight.width + "px",
                    'height': widthAndHeight.height + "px"
                }
            });

            var $progressDIV = $('<div>', {
                id: 'progressDIV',
                css: {
                    'position': 'relative',
                    'z-index': '1002',
                    'top': topAndLeft.top + 'px',
                    'left': topAndLeft.left + 'px'
                }
            });
            $progressDIV.append('<img src="' + window.app.rootPath + '/resources/images/Loading.gif">');
            $parentDiv.append($progressDIV);
            $parentDiv.append($backgroudDIV);
            $target.prepend($parentDiv);
        }


        $.fn.extend({
            loading: function () {
                var $this = $(this);
                create($this);
                var showFun = (function ($this) {
                    return function () {
                        var $target = $this;
                        $target.find('#loadingDIV').show();
                    }
                })($this);
                setTimeout(showFun, 300);
            },
            progress: function () {

            },
            close: function (data) {
                $(this).find('#loadingDIV').remove();
                if (data) {
                    $.dialog(data)
                }
            }
        });// $.fn.extend

    });// define(deps,function($)
})();