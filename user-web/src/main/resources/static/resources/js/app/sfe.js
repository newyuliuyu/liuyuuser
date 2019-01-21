(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'text!tmpl/sfe-tmpl.html',
        'js/app/selecte.teacher',
        'bootstrap',
        'css!style/bootstrap/bootstrap.min',
        'bootstrapSelect',
        'css!style/font-awesome',
        'css!style/app',
        'loading',
        'ztree',
        'js/commons/JQuery.download',
        'js/commons/JQuery.progress',
        'icheck',
        'js/app/selecte.org'
    ];
    define(models, function ($, ajax, user, dot, dialog, menu, UI, tmpl,
                             selecteTeacherDialog) {

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

        function getCurOrgCode() {
            return $('#curOrgCode').val();
        }

        function getCurOrgDeep() {
            return parseInt($('#curOrgDeep').val());
        }

        function showsfe() {
            var url = 'sfe/list/' + getCurOrgCode() + '/' + role.id;
            ajax.getJson(url).then(function (data) {
                var dataset = {role: role};
                dataset.sfes = data.sfes;
                var template = getTemplate('#sfeUIT');
                var arrText = dot.template(template);
                var html = arrText(dataset);
                $('.layout-center').html(html);
                initEvent();
            }).always(function () {
                $.processError(arguments);
            });

        }

        function initEvent() {
            $('.sfe-list').on('click', '.delete-teacher', function () {
                var $teacher = $(this).parents('.teacher');
                var teacher = {};
                teacher.name = $teacher.data('name');
                teacher.code = $teacher.data('code');
                teacher.account = $teacher.data('account');

                var org = {};
                org.code = getCurOrgCode();

                var sfe = {};
                sfe.roleId = role.id;
                sfe.org = org;
                sfe.teacher = teacher;

                dialog.confirm('解除绑定', '你确定要解除该老师的权限吗?', function () {
                    var url = 'sfe/delete-sfe';
                    ajax.postJson(url, sfe).then(function (data) {
                        dialog.prompt('删除成功');
                        $teacher.remove();
                    }).always(function () {
                        $.processError(arguments);
                    });
                });

            });
            $('.add-role-sfe').on('click', function () {
                selecteTeacherDialog.showTeacherDialog({
                    checkbox: true,
                    disable: function (teacher) {
                        return $('.sm-' + teacher.code).size() > 0;
                    },
                    click: function (teachers) {
                        var org = {};
                        org.code = getCurOrgCode();
                        var sfes = [];
                        $.each(teachers, function (idx, item) {
                            var sfe = {};
                            sfe.roleId = role.id;
                            sfe.org = org;
                            sfe.teacher = item;
                            sfes.push(sfe);
                        });
                        var url = 'sfe/add-sfe';
                        ajax.postJson(url, sfes).then(function (data) {
                            dialog.prompt('保存成功');
                            $.each(sfes, function (idx, item) {
                                var html = '<span class="sm-' + item.teacher.code + ' teacher teacher-name update-teacher"' +
                                    '              data-code="' + item.teacher.code + '"' +
                                    '              data-account="' + item.teacher.account + '"' +
                                    '              data-name="' + item.teacher.name + '">' + item.teacher.name +
                                    '<span class="delete-teacher">x</span></span>';
                                $('.sfe-list').append(html);
                            })
                        }).always(function () {
                            $.processError(arguments);
                        });
                    }
                });
            });
        }

        var role = {};
        return {
            render: function () {
            },
            init: function () {
                var $role = $('.layout-left label.active');
                role = {};
                role.id = $role.data('roleId');
                role.name = $role.data('roleName');
                showsfe();
            }
        }
    });
})();