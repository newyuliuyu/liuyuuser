(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'bootstrap',
        'css!style/bootstrap/bootstrap.min',
        'css!style/font-awesome',
        'css!style/login'
    ];
    define(models, function ($, ajax) {

        function loginBtnEvent() {
            $('#loginBtn').click(function () {
                var user = {};
                user.username = $('#username').val();
                user.password = $('#password').val();
                ajax.postJsonForm('/login.html', user).then(function (data) {
                    console.log("成功。。。。。")
                    console.log(data)
                    if(data.status.status){
                        window.location.href = window.app.rootPath;
                    }
                }).always(function () {
                    console.log("always。。。。。")
                })
            });
        }

        return {
            render: function () {
                loginBtnEvent();
            }
        }
    });
})();