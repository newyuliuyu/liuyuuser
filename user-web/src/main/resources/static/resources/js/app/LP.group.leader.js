(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'text!tmpl/LPgroupleader-tmpl.html',
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

        function buildLPGroupLeader($td) {
            var lpGroupLeader = {};
            lpGroupLeader.subjectName = $td.data('subjectName');
            lpGroupLeader.grade = {};
            lpGroupLeader.grade.id = $td.data('gradeId');
            lpGroupLeader.org = {};
            lpGroupLeader.org.code = getCurOrgCode();
            return lpGroupLeader;
        }

        function saveLPGroupLeaders($td, lpGroupLeaders) {
            var ulr = 'lpgroupleader/add-lp-group-leader/' + role.id;
            ajax.postJson(ulr, lpGroupLeaders).then(function (data) {
                dialog.prompt('设置成功');
                $.each(lpGroupLeaders, function (idx, item) {
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
            var lpGroupLeader = buildLPGroupLeader($td);
            var teacher = {};
            teacher.name = $teacher.data('name');
            teacher.code = $teacher.data('code');
            teacher.account = $teacher.data('account');
            lpGroupLeader.teacher = teacher;
            var url = 'lpgroupleader/delete-lp-group-leader/' + role.id;
            ajax.postJson(url, lpGroupLeader).then(function (data) {
                $teacher.remove();
            }).always(function () {
                $.processError(arguments);
            });

        }

        function initEvent() {
            $('.lpgroupleader-table').on('click', '.add-teacher', function () {
                var $td = $(this).parents('td');
                selecteTeacherDialog.showTeacherDialog({
                    checkbox: true,
                    disable: function (teacher) {
                        return $td.find('.t-' + teacher.code).size() > 0;
                    },
                    click: function (teachers) {
                        var lpGroupLeaders = [];
                        $.each(teachers, function (idx, item) {
                            var lpGroupLeader = buildLPGroupLeader($td);
                            lpGroupLeader.teacher = item;
                            lpGroupLeaders.push(lpGroupLeader);
                        });
                        saveLPGroupLeaders($td, lpGroupLeaders);
                    }
                });
            });
            $('.lpgroupleader-table').on('click', '.delete-teacher', function (e) {
                var $this = $(this);
                dialog.confirm('解除绑定', '你确定要解除该老师的权限吗?', function () {
                    removeTeacherPermis($this);
                });
                e.stopPropagation();
            });
        }

        function showUI() {
            var orgCode = getCurOrgCode();
            var subjectURL = 'orgconfig/subjects/' + orgCode;
            var gradeURL = 'orgconfig/grades/' + orgCode;
            $.when(ajax.getJson(subjectURL), ajax.getJson(gradeURL)).then(function () {
                var subjects = arguments[0][0].subjects;
                var grades = arguments[1][0].grades;
                var dataset = {subjects: subjects, grades: grades};
                var template = getTemplate('#lpgroupleaderUIT');
                var arrText = dot.template(template);
                var html = arrText(dataset);
                $('.layout-center').html(html);
                loadLPGroupLeader();
                initEvent();
            }).always(function () {
                $.processError(arguments);
            });
        }

        function loadLPGroupLeader() {
            var url = 'lpgroupleader/list/' + getCurOrgCode();
            ajax.getJson(url).then(function (data) {
                var lpGroupLeaders = data.lpGroupLeaders;
                $.each(lpGroupLeaders, function (idx, item) {
                    var key = '.cell-' + item.grade.id + '-' + item.subjectName + ' .show-teacher';
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