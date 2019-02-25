(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'dot',
        'dialog',
        'text!tmpl/selecte-org-tmpl.html',
        'js/commons/UI'
    ];
    define(models, function ($, ajax, dot, dialog, tmpl, UI) {

        function getSize() {
            var h = window.getClientHeight() - 200;
            if (h < 200) {
                h = 200;
            }
            return {w: 600, h: h};
        }

        function getTemplate(id) {
            return $('<div>' + tmpl + '</div>').find(id).text();
        }


        function changeOrg($this, isSchool, callback) {
            $this.find('.change-org-btn').click(function () {
                var url = 'user/res/query/org-menu';
                ajax.getJson(url).then(function (data) {
                    var dataset = {};
                    dataset.orgs = data.reses;
                    if (isSchool === true) {
                        var schools = [];
                        $.each(data.reses, function (idx, item) {
                            if (item.elementId === 'school') {
                                schools.push(item);
                                return false;
                            }
                        });
                        dataset.orgs = schools;
                    }
                    var tmplate = getTemplate('#selecteSchoolDialogT');
                    var arrText = dot.template(tmplate);
                    var html = arrText(dataset);
                    var myDialog = dialog.myModal({size: getSize(), body: html}, function () {
                        var selecteOrg = $('.org-radio:checked');
                        if (selecteOrg.size() > 0) {
                            $('.org-name-label').text(selecteOrg.data('name'));
                            $('#curOrgCode').val(selecteOrg.val());
                            $('#curOrgDeep').val(selecteOrg.data('deep'));
                            if ($.isFunction(callback)) {
                                callback();
                            }

                            // loadOrgConfig();
                        }
                        myDialog.close();
                    });
                    listUserOrgs();
                    $('#searchBtn').click(function () {
                        listUserOrgs();
                    });
                    $('.org-nav-list').on('click', 'a', function () {
                        var type = $(this).data('type');
                        var deep = getOrgNavDeep(type);
                        listUserOrgs(undefined, deep, type);
                    });
                }).always(function () {
                    $.processError(arguments);
                });
            });
        }

        function getOrgNavDeep(type) {
            if (!type) {
                type = $('.org-nav-list a.active').data('type');
            }
            var deep = 0;
            if (type === 'province') {
                deep = 1;
            } else if (type === 'city') {
                deep = 2;
            } else if (type === 'county') {
                deep = 3;
            } else if (type === 'school') {
                deep = 4;
            }
            return deep;
        }

        function getOrgNavType() {
            var type = $('.org-nav-list a.active').data('type');
            return type;
        }

        function listUserOrgs(page, deep, type) {
            if (!deep) {
                var deep = getOrgNavDeep();
                if (deep === 0) {
                    dialog.alter("没有类型不能进行切换");
                    return;
                }
            }
            if (!type) {
                type = getOrgNavType();
            }


            var url = 'org/user-orgs/' + deep + '?1=1';
            if ($.isPlainObject(page)) {
                url += '&pageNum=' + page.pageNum + "&pageSize=" + page.pageSize;
            }
            var searchText = $('#searchText').val();
            if (searchText && searchText !== '') {
                url += '&search=' + searchText;
            }
            ajax.getJson(url).then(function (data) {
                var tmplate = getTemplate("#orgListT");
                var arrText = dot.template(tmplate);
                var html = arrText(data);
                $('#nav-' + type).html(html);
                UI.pager().create('orgPager', function (page) {
                    listUserOrgs(page);
                });
                $(".attribute").iCheck({
                    checkboxClass: 'icheckbox_square-blue',
                    radioClass: 'iradio_square-blue'
                });
            }).always(function () {
                $.processError(arguments);
            });

        }

        function loadOrgData(isSchool, callback) {
            if (isSchool !== true) {
                isSchool = false;
            }
            var url = 'org/user-org/' + isSchool;
            ajax.getJson(url).then(function (data) {
                var org = data.org;
                $('.org-name-label').text(org.name);
                $('#curOrgCode').val(org.code);
                $('#curOrgDeep').val(org.deep);
                if ($.isFunction(callback)) {
                    callback();
                }
            }).always(function () {
                $.processError(arguments);
            });
        }

        $.fn.extend({
            selectOrg: function (opts) {
                var $this = $(this);
                loadOrgData(opts.isSchool, opts.load);
                changeOrg($this, opts.isSchool, opts.click);
            }
        });// $.fn.extend


        return {
            render: function () {

            }
        }
    });
})();