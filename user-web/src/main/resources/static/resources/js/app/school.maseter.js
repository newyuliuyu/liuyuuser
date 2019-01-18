(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'text!tmpl/school-maseter-tmpl.html',
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

        function showSchoolMaster(role) {
            var url = 'schoolmaseter/list/' + getCurOrgCode() + '/' + role.id;
            ajax.getJson(url).then(function (data) {
                var dataset = {role: role};
                dataset.schoolMaseters = data.schoolMaseters;
                var template = getTemplate('#schoolMaseterUIT');
                var arrText = dot.template(template);
                var html = arrText(dataset);
                $('.layout-center').html(html);
                initEvent();


            }).always(function () {
                $.processError(arguments);
            });

        }

        function initEvent() {
            $('.school-maseter-list').on('click', '.delete-teacher', function () {
                var $teacher = $(this).parents('.teacher');
                var teacher = {};
                teacher.name = $teacher.data('name');
                teacher.code = $teacher.data('code');
                teacher.account = $teacher.data('account');

                var org = {};
                org.code = getCurOrgCode();

                var schoolMaseter = {};
                schoolMaseter.roleId = role.id;
                schoolMaseter.org = org;
                schoolMaseter.teacher = teacher;
                var url = 'schoolmaseter/delete-school-maseter';
                ajax.postJson(url, schoolMaseter).then(function (data) {
                    dialog.prompt('删除成功');
                    $teacher.remove();
                }).always(function () {
                    $.processError(arguments);
                });

            });
            $('.add-role-teacher').on('click', function () {
                selecteTeacherDialog.showTeacherDialog({
                    checkbox: true,
                    disable: function (teacher) {
                        return $('.sm-' + teacher.code).size() > 0;
                    },
                    click: function (teachers) {
                        var org = {};
                        org.code = getCurOrgCode();
                        var schoolMaseters = [];
                        $.each(teachers, function (idx, item) {
                            var schoolMaseter = {};
                            schoolMaseter.roleId = role.id;
                            schoolMaseter.org = org;
                            schoolMaseter.teacher = item;
                            schoolMaseters.push(schoolMaseter);
                        });
                        var url = 'schoolmaseter/add-school-maseter';
                        ajax.postJson(url, schoolMaseters).then(function (data) {
                            dialog.prompt('保存成功');
                            $.each(schoolMaseters, function (idx, item) {
                                var html = '<span class="sm-' + item.teacher.code + ' teacher teacher-name update-teacher"' +
                                    '              data-code="' + item.teacher.code + '"' +
                                    '              data-account="' + item.teacher.account + '"' +
                                    '              data-name="' + item.teacher.name + '">' + item.teacher.name +
                                    '<span class="delete-teacher">x</span></span>';
                                $('.school-maseter-list').append(html);
                            })
                        }).always(function () {
                            $.processError(arguments);
                        });
                        console.log(schoolMaseters)
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
                showSchoolMaster(role);
            }
        }
    });
})();