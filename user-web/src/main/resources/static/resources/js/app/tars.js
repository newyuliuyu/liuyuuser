(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'text!tmpl/tars-tmpl.html',
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

        function showtarsUI() {
            var url = 'orgconfig/grades/' + getCurOrgCode();
            ajax.getJson(url).then(function (data) {
                var grades = data.grades;
                var learnSegmentMap = {};
                $.each(grades, function (idx, item) {
                    var name = '';
                    if (item.learnSegment === 1) {
                        name = '小学';
                    } else if (item.learnSegment === 2) {
                        name = '初中';
                    } else if (item.learnSegment === 3) {
                        name = '高中';
                    }
                    learnSegmentMap[item.learnSegment] = name;
                });
                var learnSegments = [];
                $.each(learnSegmentMap, function (key, value) {
                    learnSegments.push({name: value, num: key});
                });
                var dataset = {learnSegments: learnSegments};
                var template = getTemplate('#tarsUIT');
                var arrText = dot.template(template);
                var html = arrText(dataset);
                $('.layout-center').html(html);
                loadTars();
                initEvent();
            }).always(function () {
                $.processError(arguments);
            });
        }

        function loadTars() {
            var url = 'tars/list/' + getCurOrgCode() + '/' + role.id;
            ajax.getJson(url).then(function (data) {
                var tarsList = data.tarsList;
                console.log(tarsList)
                $.each(tarsList, function (idx, item) {
                    var key = '.cell-' + item.learnSegment + ' .show-teacher';
                    console.log(key)
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

        function buildtars($td) {
            var tars = {};
            tars.learnSegment = $td.data('learnSegment');
            tars.roleId = role.id;
            tars.org = {};
            tars.org.code = getCurOrgCode();
            return tars;
        }

        function savetarsList($td, tarsList) {
            var ulr = 'tars/add-tars';
            ajax.postJson(ulr, tarsList).then(function (data) {
                dialog.prompt('设置成功');
                $.each(tarsList, function (idx, item) {
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
            var tars = buildtars($td);
            var teacher = {};
            teacher.name = $teacher.data('name');
            teacher.code = $teacher.data('code');
            teacher.account = $teacher.data('account');
            tars.teacher = teacher;
            var url = 'tars/delete-tars';
            ajax.postJson(url, tars).then(function (data) {
                $teacher.remove();
            }).always(function () {
                $.processError(arguments);
            });
        }

        function initEvent() {
            $('.tars-table').on('click', '.add-teacher', function () {
                var $td = $(this).parents('td');
                selecteTeacherDialog.showTeacherDialog({
                    checkbox: true,
                    disable: function (teacher) {
                        return $td.find('.t-' + teacher.code).size() > 0;
                    },
                    click: function (teachers) {
                        var tarsList = [];
                        $.each(teachers, function (idx, item) {
                            var tars = buildtars($td);
                            tars.teacher = item;
                            tarsList.push(tars);
                        });
                        savetarsList($td, tarsList);
                    }
                });
            });
            $('.tars-table').on('click', '.delete-teacher', function (e) {
                var $this = $(this);
                dialog.confirm('解除绑定', '你确定要解除该老师的权限吗?', function () {
                    removeTeacherPermis($this);
                });
                e.stopPropagation();
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
                showtarsUI();
            }
        }
    });
})();