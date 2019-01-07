(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user'
    ];
    define(models, function ($, ajax, user) {

        function init() {
            var url = '/user/res/query/user-main-menu';
            ajax.getJson(url).then(function (data) {
                var reses = data.reses;
                reses.sort(function (o1, o2) {
                    return o1.orderNum - o2.orderNum;
                })
                if (reses.length > 0) {
                    var url = user.getURL(reses[0].elementId);
                    console.log(url)
                    Req.redirect(url);
                } else {

                }

            });
        }

        return {
            render: function () {
                init();

            }
        }
    });
})();