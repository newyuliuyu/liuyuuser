(function () {
    "use strict";
    var models = ['jquery',
        'bootstrap',
        'css!style/bootstrap/bootstrap.min',
        'css!style/font-awesome',
        'css!style/app'
    ];
    define(models, function ($) {

        function kk() {
            $('.main-menu-item').click(function (event) {
                if ($(this).find('.main-menu').is(':hidden')) {
                    $(this).find('.main-menu').slideDown();
                } else {
                    $(this).find('.main-menu').slideUp();
                }
                event.stopPropagation();
            });
        }
        
        function abc(){
            $('#abc1').click(function () {
                alert("1111111")
            });
            $('#abc11').click(function () {
                alert("112221212")
            });
        }

        return {
            render: function () {
                $('header,main,footer').show();
                kk();
                //abc();
            }
        }
    });
})();