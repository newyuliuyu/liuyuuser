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
                if (user.password === '' || user.username === '') {
                    $('.error-msg').html("用户名和密码不能为空");
                    return;
                }

                ajax.postJsonForm('/login.html', user).then(function (data) {
                    console.log("成功。。。。。")
                    if (data.status.status) {
                        if(data.redirectURL){
                            Req.redirect(data.redirectURL);
                        }else{
                            Req.redirect(window.app.rootPath)
                        }
                       // window.location.href = window.app.rootPath;
                    } else {
                        $('.error-msg').html(data.status.msg);
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