(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'dot',
        'loading'
    ];
    define(models, function ($, ajax) {


        function menuClick() {
            $('.main-left').on('click', '.main-menu-item', function (event) {
                if ($(this).find('.main-menu').is(':hidden')) {
                    $(this).find('.main-menu').slideDown();
                    $(this).find('.icon-chevron-down').addClass('text-rotate180');
                } else {
                    $(this).find('.main-menu').slideUp();
                    $(this).find('.icon-chevron-down').removeClass('text-rotate180');
                }
                event.stopPropagation();
            });
        }

        function menu(curElementId) {
            var url = '/user/res/query/home-menu';
            ajax.getJson(url).then(function (data) {
                var reses = data.reses;
                reses.sort(function (o1, o2) {
                    return o1.orderNum - o2.orderNum;
                });
                var resMap = {};
                $.each(reses, function (idx, item) {
                    resMap[item.id] = item;
                    item.child = [];
                    item.parent = undefined;
                });
                $.each(reses, function (idx, item) {
                    var parentItem = resMap[item.parentId];
                    if (parentItem) {
                        parentItem.child.push(item);
                        item.parent = parentItem;
                    }
                });
                var result = [];
                $.each(resMap, function (key, value) {
                    if (!value.parent) {
                        result.push(value);
                    }
                });

                var html = createHTML(result, true, 0, curElementId);
                $('.main-left').html(html);
                $('.main-left .icon-chevron-down:eq(0)').addClass('text-rotate180');
                $('main').show();
                initEvent();
                menuClick();
            }).always(function () {
                $('body').close(arguments[0]);
            })
        }


        function initEvent() {
            // $('.main-left').on('click', '.resourceMenu,.roleMenu,.userMenu', function () {
            //     $('.main-menu-item').removeClass('active')
            //     $(this).addClass('active')
            // });

            $('.main-left').on('click', '.resourceMenu', function () {
                console.log('click resourceMenu.....');
                Req.redirect(window.app.rootPath + 'res/res.html');
            });
            $('.main-left').on('click', '.roleMenu', function () {
                console.log('click roleMenu.....');
                Req.redirect(window.app.rootPath + 'role/role.html');
            });
            $('.main-left').on('click', '.userMenu', function () {
                console.log('click userMenu.....');
                Req.redirect(window.app.rootPath + 'user/user.html');
            });
        }


        function createHTML(reses, show, deep, curElementId) {
            var html = [];
            if (show) {
                html.push('<ul class="main-menu" style="display: block;">');
            } else {
                html.push('<ul class="main-menu" style="display: none;">');
            }
            var nextShow = true;
            $.each(reses, function (idx, item) {

                var className = '';
                if (curElementId === item.elementId) {
                    className = 'active'
                }

                html.push('<li elementId="' + item.elementId + '"   class="' + className + ' main-menu-item text-indent' + deep + ' ' + item.elementId + '">');
                html.push(item.displayText);
                var childReses = item.child;
                if (childReses.length > 0) {
                    var isShow = deep === 0 && nextShow
                    var tmp = createHTML(childReses, isShow, deep + 1, curElementId);
                    nextShow = false;
                    html.push(tmp);
                }
                html.push('</li>');
            });
            html.push('</ul>');
            return html.join('');
        }

        function clickElement() {
            var elementId = findFirstElement();
            if (elementId) {
                $('.' + elementId).click();
            }
        }

        function findFirstElement() {
            var result = undefined;
            $('.main-left li').each(function () {
                var elementId = $(this).attr('elementId');
                if (elementId && elementId !== '') {
                    result = elementId;
                    return false;
                }
            });
            return result;
        }


        return {
            createMenu: function (id) {
                menu(id);
            }
        }
    });
})();