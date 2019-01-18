(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'text!tmpl/grademaseter-tmpl.html',
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

        function loadGradeMaster() {
            var url = 'grademaseter/list/' + getCurOrgCode();
            ajax.getJson(url).then(function (data) {
                console.log(data)
                var gradeMaseters = data.gradeMaseters;
                $.each(gradeMaseters, function (idx, item) {
                    var key = '.cell-' + item.grade.id + ' .show-teacher';
                    $(key).append(showTeacherHTML(item.teacher));
                })

            }).always(function () {
                $.processError(arguments);
            });
        }

        function showTeacherHTML(teacher) {
            var html = '<label class="t-' + teacher.code + ' teacher teacher-name update-teacher" ' +
                'data-name="' + teacher.name + '" ' +
                'data-code="' + teacher.code + '" ' +
                'data-account="' + teacher.account + '">' + teacher.name + '' +
                '<span class="delete-teacher">x</span></label>';
            return html;
        }

        function buildGradeMaseter($td) {
            var gradeMaseter = {};
            gradeMaseter.grade = {};
            gradeMaseter.grade.id = $td.data('gradeId');
            gradeMaseter.org = {};
            gradeMaseter.org.code = getCurOrgCode();
            return gradeMaseter;
        }

        function saveGradeMaseters($td, gradeMaseters) {
            var ulr = 'grademaseter/add-grade-maseter/' + role.id;
            ajax.postJson(ulr, gradeMaseters).then(function (data) {
                dialog.prompt('设置成功');
                $.each(gradeMaseters, function (idx, item) {
                    var html = showTeacherHTML(item.teacher);
                    $td.find('.show-teacher').append(html);
                });
            }).always(function () {
                $.processError(arguments);
            });
        }

        function removeTeacherPermis($del) {
            var $teacher = $del.parents('.teacher');
            var $td = $del.parents('td');
            var gradeMaseter = buildGradeMaseter($td);
            var teacher = {};
            teacher.name = $teacher.data('name');
            teacher.code = $teacher.data('code');
            teacher.account = $teacher.data('account');
            gradeMaseter.teacher = teacher;
            var url = 'grademaseter/delete-grade-maseter/' + role.id;
            ajax.postJson(url, gradeMaseter).then(function (data) {
                $teacher.remove();
            }).always(function () {
                $.processError(arguments);
            });

        }

        function initEvent() {
            $('.grademaseter-table').on('click', '.add-teacher', function () {
                var $td = $(this).parents('td');
                selecteTeacherDialog.showTeacherDialog({
                    checkbox: true,
                    disable: function (teacher) {
                        return $td.find('.t-' + teacher.code).size() > 0;
                    },
                    click: function (teachers) {
                        var gradeMaseters = [];
                        $.each(teachers, function (idx, item) {
                            var gradeMaseter = buildGradeMaseter($td);
                            gradeMaseter.teacher = item;
                            gradeMaseters.push(gradeMaseter);
                        });
                        saveGradeMaseters($td, gradeMaseters);
                    }
                });
            });
            $('.grademaseter-table').on('click', '.delete-teacher', function (e) {
                var $this = $(this);
                dialog.confirm('解除绑定', '你确定要解除该老师的权限吗?', function () {
                    removeTeacherPermis($this);
                });
                e.stopPropagation();
            });
        }


        function showUI() {
            var orgCode = getCurOrgCode();
            var gradeURL = 'orgconfig/grades/' + orgCode;
            ajax.getJson(gradeURL).then(function (data) {
                var grades = data.grades;
                var dataset = {grades: grades};
                var template = getTemplate('#gradeMaseterUIT');
                var arrText = dot.template(template);
                var html = arrText(dataset);
                $('.layout-center').html(html);
                loadGradeMaster();
                initEvent();
            }).always(function () {
                $.processError(arguments);
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
                showUI();
            }
        }
    });
})();