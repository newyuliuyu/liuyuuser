(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'text!tmpl/teaching-tmpl.html',
        'js/app/selecte.teacher',
        'js/app/school.maseter',
        'js/app/LP.group.leader',
        'js/app/grade.maseter',
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
                             selecteTeacherDialog,
                             schoolMaseter,
                             LPGroupLeader,
                             gradeMaster) {

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

        function getLevel() {
            if (getCurOrgDeep() == 4) {
                return 1;
            } else {
                return 2;
            }
        }

        var roleMap = {};

        function loadSetting() {
            var url = 'role/list/' + getLevel();
            ajax.getJson(url).then(function (data) {

                $.each(data.roles, function (idx, item) {
                    roleMap[item.roleType] = item;
                });
                data.isSchool = getLevel() === 1;
                var template = getTemplate('#roleListT');
                var arrText = dot.template(template);
                var html = arrText(data);
                $('#contentDIV .layout-left').html(html);
                loadSettingContent();
            }).always(function () {
                $.processError(arguments);
            });
        }

        function teachTeacher() {
            var orgCode = getCurOrgCode();
            var subjectURL = 'orgconfig/subjects/' + orgCode;
            var clazzURL = 'orgconfig/clazzes/' + orgCode;

            $.when(ajax.getJson(subjectURL), ajax.getJson(clazzURL)).then(function (data) {
                var subjects = arguments[0][0].subjects;
                var clazzes = arguments[1][0].clazzes;

                var grades = [];
                var gradeClazzMap = {};
                $.each(clazzes, function (idx, item) {
                    var gradeId = item.grade.id;
                    if (!gradeClazzMap[gradeId]) {
                        gradeClazzMap[gradeId] = [];
                        grades.push(item.grade);
                    }
                    gradeClazzMap[gradeId].push(item);
                });

                grades.sort(function (a1, a2) {
                    var learnSegment1 = a1.learnSegment;
                    var learnSegment2 = a2.learnSegment;
                    if (learnSegment1 === learnSegment2) {
                        var name1 = getClazzNameNum(a1.name);
                        var name2 = getClazzNameNum(a2.name);
                        return name1 - name2;
                    } else {
                        return learnSegment1 - learnSegment2;
                    }
                });

                var dataset = {
                    subjects: subjects,
                    grades: grades,
                    gradeClazzMap: gradeClazzMap
                };
                var template = getTemplate('#teachTeacherT');
                var arrText = dot.template(template);
                var html = arrText(dataset);
                $('.layout-center').html(html);
                loadClazzMaster();
                loadTeachingTeacher();
            }).always(function () {
                $.processError(arguments);
            });
        }

        function loadTeachingTeacher() {
            var url = 'teaching/teaching-teacher/' + getCurOrgCode();
            ajax.getJson(url).then(function (data) {

                var teachingTeachers = data.teachingTeachers;
                $.each(teachingTeachers, function (idx, item) {
                    //cell-{{=grade.id}}-{{=subject.name}}-{{=clazz.code}}
                    var key = '.cell-' + item.grade.id + '-' + item.subjectName + '-' + item.clazz.code;
                    var html = showTeacherHTML(item.teacher);
                    $(key).html(html);
                });
            }).always(function () {
                $.processError(arguments);
            });
        }

        function loadClazzMaster() {
            var url = 'teaching/clazz-master/' + getCurOrgCode();
            ajax.getJson(url).then(function (data) {
                var clazzMasters = data.clazzMasters;
                $.each(clazzMasters, function (idx, item) {
                    //cell-bzr-{{=grade.id}}-{{=clazz.code}}
                    var key = '.cell-bzr-' + item.grade.id + '-' + item.clazz.code;
                    var html = showTeacherHTML(item.teacher);
                    $(key).html(html);
                });
            }).always(function () {
                $.processError(arguments);
            });
        }

        function showTeacherHTML(teacher) {
            var html = '<label class="teaching-teacher teacher teacher-name update-teacher" ' +
                'data-name="' + teacher.name + '" ' +
                'data-code="' + teacher.code + '" ' +
                'data-account="' + teacher.account + '">' + teacher.name + '' +
                '<span class="delete-teacher">x</span></label>';
            return html;
        }


        function initSchoolMaseter() {
            schoolMaseter.init();
        }

        function initLPGroupLeader() {
            LPGroupLeader.init();
        }

        function initGradeMaseter() {
            gradeMaster.init();
        }

        var roleTypes = {
            teachTeacher: teachTeacher,
            schoolMaseter: initSchoolMaseter,
            LPGroupLeader: initLPGroupLeader,
            gradeMaseter: initGradeMaseter
        };

        function loadSettingContent() {
            var roleType = $('.layout-left .label-btn.active').data('roleType');
            roleTypes[roleType]();
        }


        function selectTeacher($oldTeacher) {
            var $td = $oldTeacher.parents('td');

            var AddClazzMasterAndTeacherDTO = {};
            AddClazzMasterAndTeacherDTO.clazzmaster = $td.data('clazzmaster');
            AddClazzMasterAndTeacherDTO.orgCode = getCurOrgCode();
            AddClazzMasterAndTeacherDTO.gradeId = $td.data('gradeId');
            AddClazzMasterAndTeacherDTO.clazzCode = $td.data('clazzCode');
            if (!AddClazzMasterAndTeacherDTO.clazzmaster) {
                AddClazzMasterAndTeacherDTO.subjectName = $td.data('subjectName');
                AddClazzMasterAndTeacherDTO.roleId = roleMap['clazzMaseter'].id;
            } else {
                AddClazzMasterAndTeacherDTO.roleId = roleMap['teacher'].id;
            }
            var oldTeacher = null;
            if ($oldTeacher.hasClass('update-teacher')) {
                oldTeacher = {};
                oldTeacher.name = $oldTeacher.data('name');
                oldTeacher.code = $oldTeacher.data('code');
                oldTeacher.account = $oldTeacher.data('account');
                AddClazzMasterAndTeacherDTO.oldTeacher = oldTeacher;
            }
            selecteTeacherDialog.showTeacherDialog({
                disable: function (myTeacher) {
                    return oldTeacher && oldTeacher.code === myTeacher.code;
                },
                click: function (newTeacher) {
                    AddClazzMasterAndTeacherDTO.newTeacher = newTeacher;
                    var url = 'teaching/add-clazz-master-and-teacher';
                    ajax.postJson(url, AddClazzMasterAndTeacherDTO).then(function (data) {
                        dialog.prompt("设置成功");
                        var html = showTeacherHTML(newTeacher);
                        $td.html(html);
                    }).always(function () {
                        $.processError(arguments);
                    });
                }
            });
        }


        function removeTeacherPermis($del) {
            var $teacher = $del.parents('.teacher');
            var $td = $del.parents('td');


            var RemoveTeacherPermisDTO = {};
            RemoveTeacherPermisDTO.clazzmaster = $td.data('clazzmaster');

            RemoveTeacherPermisDTO.orgCode = getCurOrgCode();
            RemoveTeacherPermisDTO.gradeId = $td.data('gradeId');
            RemoveTeacherPermisDTO.clazzCode = $td.data('clazzCode');
            if (!RemoveTeacherPermisDTO.clazzmaster) {
                RemoveTeacherPermisDTO.roleType = 'teacher';
                RemoveTeacherPermisDTO.subjectName = $td.data('subjectName');
                RemoveTeacherPermisDTO.roleId = roleMap['clazzMaseter'].id;

            } else {
                RemoveTeacherPermisDTO.roleId = roleMap['teacher'].id;
                RemoveTeacherPermisDTO.roleType = 'clazzMaseter';
            }
            var teacher = {};
            teacher.name = $teacher.data('name');
            teacher.code = $teacher.data('code');
            teacher.account = $teacher.data('account');
            RemoveTeacherPermisDTO.teacher = teacher;

            var url = 'teaching/rmove-teacher-permis';
            ajax.postJson(url, RemoveTeacherPermisDTO).then(function (data) {
                dialog.prompt('解除权限成功');
                $td.html('<label class="teaching-teacher teacher add-teacher" style="">添加老师+</label>')
            }).always(function () {
                $.processError(arguments);
            });
        }


        function initEvent() {

            $('.layout-center').on('click', '.teaching-teacher-table .teaching-teacher', function () {
                selectTeacher($(this));
            });
            $('.layout-center').on('click', '.teaching-teacher-table .teaching-teacher .delete-teacher', function (e) {
                var $this = $(this);
                dialog.confirm('解除绑定', '你确定要解除该老师的权限吗?', function () {
                    removeTeacherPermis($this);
                });
                e.stopPropagation();
            });


            $('.layout-left').on('click', '.label-btn', function () {
                $('.layout-left .label-btn').removeClass('active');
                $(this).addClass('active');
                loadSettingContent();
            });

            $('.org-div').selectOrg({
                load: function () {
                    loadSetting();
                },
                click: function () {
                    loadSetting();
                }
            });
        }


        return {
            render: function () {
                user.userInfo('teaching');
                initEvent();
                $('main').show();
            }
        }
    });
})();