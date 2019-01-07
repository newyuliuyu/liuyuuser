(function () {
    "use strict";
    var models = ['jquery',
        'ajax'
    ];
    define(models, function ($, ajax) {
        var urlMap = {
            resourceMenu: window.app.rootPath + 'res/res.html',
            roleMenu: window.app.rootPath + 'role/role.html',
            userMenu: window.app.rootPath + 'user/user.html'
        };

        function init() {
            var url = '/user/res/query/home-menu';
            ajax.getJson(url).then(function (data) {
                var reses = data.reses;
                reses.sort(function (o1, o2) {
                    return o1.orderNum - o2.orderNum;
                });
                console.log(reses)
                $.each(reses, function (idx, item) {
                    var url = urlMap[item.elementId];
                    if (url) {
                        Req.redirect(url);
                        return false;
                    }
                });
            });
        }

        return {
            render: function () {
                init();
            }
        }
    });
})();