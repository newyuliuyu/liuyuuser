(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'dot',
        'loading'
    ];
    define(models, function ($, ajax, dot) {

        var urlMap = {
            'home': window.app.rootPath + 'home.html',
            'org': window.app.rootPath + 'org/org.html',
            'orgConfig': window.app.rootPath + 'org/config/org-config.html',
            'teacher': window.app.rootPath + 'teacher/teacher.html'
        };

        var userHTML = '<div class="user-panel">\
                <span><img style="width: 2.5rem;"\
                  src="#"></span>\
                <span>{{=it.realName}}</span>\
                <i class="icon-angle-down"></i>\
                <div class="menu">\
                <!--<span>这是一个三角形，应用的时候删除该文字</span>-->\
                <dl>\
                <dd>菜单部分</dd>\
                </dl>\
                </div>\
                </div>';

        function userInfo(id) {
            var url1 = '/user/info';
            var url2 = '/user/res/query/user-main-menu';
            $.when(ajax.getJson(url1), ajax.getJson(url2)).then(function (data1, data2) {

                // var templateText = $("#t-allsubjecttable").text();
                // var user = data.user;

                var user = data1[0].user;
                var reses = data2[0].reses;
                drawUser(user);
                drawHeaderMainMenu(reses, id);
                $('header').show();
            }).always(function () {
                $('body').close(arguments[0]);
            });
            headerMenu();
        }

        function drawUser(user) {
            var arrText = dot.template(userHTML);
            var html = arrText(user);
            $('.header-end').html(html);
            $('.header-end>.user-panel>span>img').attr('src', window.app.rootPath + 'resources/images/user.png');
            var menus = [];
            menus.push('<dd><a href="' + window.app.rootPath + '/logout" style="color: #000;text-decoration: none;"><span class="icon-signout">&nbsp;退出系统</span> </a></dd>');
            $('.header-end>.user-panel>.menu>dl').html(menus.join(''));
        }

        function drawHeaderMainMenu(reses, id) {
            var htmls = [];
            $.each(reses, function (idx, item) {
                var active = '';
                if (item.elementId === id) {
                    active = 'active';
                }
                htmls.push('<li class="' + item.elementId + ' ' + active + '" data-id="' + item.elementId + '"><i>' + item.displayText + '</i></li>');
            });
            $('.header-main>ul').html(htmls.join(''));
        }

        function headerMenu() {
            $('.header-main').on('click', 'li', function () {
                $('.header-main li').removeClass('active');
                $(this).addClass('active');
                var id = $(this).data('id');
                Req.redirect(urlMap[id]);
            });
        }


        function resize() {
            var allHeight = $(window).height();
            var headerHight = $('.header').height();
            $('.main').height(allHeight - headerHight);
        }

        $(window).resize(function () {
            resize();
        });

        return {
            render: function () {

            },
            userInfo: function (id) {
                userInfo(id);
                resize();
            },
            getURL: function (id) {
                return urlMap[id];
            }
        }
    });
})();