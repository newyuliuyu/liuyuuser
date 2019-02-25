(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'text!tmpl/student-tmpl.html',
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
        'js/app/selecte.org',
        'ztree'
    ];
    define(models, function ($, ajax, user, dot, dialog, menu, UI, tmpl) {

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

        function getCurOrgName() {
            return $('.org-name-label').text();
        }

        function getCurOrgDeep() {
            return parseInt($('#curOrgDeep').val());
        }

        function initTreeSetting() {
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: 'code',
                        pIdKey: 'parentCode',
                        rootPId: '0'
                    }
                },
                callback: {
                    beforeClick: true,
                    onClick: function (event, treeId, treeNode, clickFlag) {
                        loadClazzStudent(treeNode)
                    }
                }
            };
            return setting;
        }

        function loadClazzStudent(clazz) {
            if (!clazz.isClazz) {
                $('.layout-center').html('');
                return;
            }
            var tmplate = getTemplate('#studentUIT');
            var arrText = dot.template(tmplate);
            var dataset = {clazz: clazz.clazz};
            var html = arrText(dataset);
            $('.layout-center').html(html);
            initStudentEvent();
            loadClazzTeacher(clazz.clazz);
            loadStudent(clazz.clazz);
        }

        function loadStudent(clazz) {
            var url = 'student/list/' + getCurOrgCode() + '/' + clazz.code;
            ajax.getJson(url).then(function (data) {
                $('.student-num').text(data.students.length + '人');
                var tmplate = getTemplate('#student-table-divT');
                var arrText = dot.template(tmplate);
                var html = arrText(data);
                $('.student-table-div').html(html);
            }).always(function () {
                $.processError(arguments);
            });
        }

        function add(myDialog, student) {
            var url = 'student/add'
            ajax.postJson(url, student).then(function (data) {
                dialog.prompt('增加成功');
                myDialog.close();
                var clazz = getSelecteClazz();
                loadStudent(clazz);
            }).always(function () {
                $.processError(arguments);
            });
        }

        function del(id) {
            var url = 'student/delete/' + id;
            ajax.postJson(url).then(function (data) {
                dialog.prompt('删除成功');
                var clazz = getSelecteClazz();
                loadStudent(clazz);
            }).always(function () {
                $.processError(arguments);
            });
        }

        function udpate(id) {
            var url = 'student/get/' + id;
            ajax.getJson(url).then(function (data) {
                var student = data.student;
                var template = getTemplate('#new-and-update-studentT');
                var arryText = dot.template(template);
                var dataset = {student: student};
                var html = arryText(dataset);
                var myDialog = dialog.myModal({size: getSize(), body: html}, function () {
                    var newStudent = {};
                    newStudent.school = student.school;
                    newStudent.clazz = student.clazz;
                    newStudent.grade = student.grade;
                    newStudent.studentId = $('.studentId').val();
                    newStudent.zkzh = $('.zkzh').val();
                    newStudent.name = $('.name').val();
                    newStudent.account = $('.account').val();
                    newStudent.id = $('.id').val();
                    var url = 'student/update';
                    ajax.postJson(url, newStudent).then(function (data) {
                        myDialog.close();
                        dialog.prompt('修改成功');
                        var clazz = getSelecteClazz();
                        loadStudent(clazz);
                    }).always(function () {
                        $.processError(arguments);
                    });
                });
            }).always(function () {
                $.processError(arguments);
            });
        }

        function getSelecteClazz() {
            var treeObj = $.fn.zTree.getZTreeObj('ktree');
            var nodes = treeObj.getSelectedNodes();
            var clazz = nodes[0].clazz;
            return clazz;
        }

        function importStudent() {
            var html = getTemplate('#importStudentT');
            var myDialog = dialog.myModal({size: getSize(), body: html}, function () {

            });
            UI.uploadFileBtn({
                id: '#selectedFileBtn',
                progressBar: '#uploadFileProgressDiv',
                extensions: 'xls,xlsx',
                callback: function () {
                    var uploadFile = {};
                    $('#uploadFileProgressDiv>div').each(function (idx, item) {
                        var oldfile = $(item).attr('old');
                        var newfile = $(item).attr('new');
                        uploadFile.newName = newfile;
                        uploadFile.oldName = oldfile;
                    });
                    importTo(uploadFile);
                }
            });
        }

        function importTo(uploadFile) {
            var url = 'student/import';
            ajax.postJson(url, uploadFile).then(function (data) {
                generateProgress(data.key);
            }).always(function () {
                $.processError(arguments);
            });
        }

        function generateProgress(key) {
            $('.upload-file-div').hide();
            $('.progress-div').show();
            $('.progress-div').myprogress({
                url: '/progress',
                onlyKey: key,
                finishedCallBack: function () {
                }
            });
        }

        function initStudentEvent() {
            $('.student-tool-bar').on('click', '.add', function () {
                var school = {
                    code: getCurOrgCode(),
                    name: getCurOrgName()
                };
                var clazz = getSelecteClazz();
                var student = {
                    school: school,
                    clazz: clazz,
                    grade: clazz.grade
                };
                var template = getTemplate('#new-and-update-studentT');
                var arryText = dot.template(template);
                var dataset = {student: student};
                var html = arryText(dataset);
                var myDialog = dialog.myModal({size: getSize(), body: html}, function () {
                    var newStudent = {};
                    newStudent.school = school;
                    newStudent.clazz = clazz;
                    newStudent.grade = clazz.grade;
                    newStudent.studentId = $('.studentId').val();
                    newStudent.zkzh = $('.zkzh').val();
                    newStudent.name = $('.name').val();
                    newStudent.account = $('.account').val();
                    add(myDialog, newStudent);
                });

            });
            $('.student-tool-bar').on('click', '.import', function () {
                importStudent();
            });
            $('.student-tool-bar').on('click', '.download-tmp', function () {
                var url = window.app.rootPath + "student/download/tmpl/" + getCurOrgCode();
                $.download(url);
            });
            $('.student-table-div').on('click', '.del-btn', function () {
                var id = $(this).data('id');
                dialog.confirm('删除学生', '你确定要删除该学生', function () {
                    del(id);
                });
            });
            $('.student-table-div').on('click', '.update-btn', function () {
                var id = $(this).data('id');
                udpate(id);
            });

            $('.student-tool-bar').on('click', '.chang-clazz', function () {
                if ($(this).hasClass('setting-true')) {
                    $(this).text('设置转班');
                    $(this).removeClass('setting-true');

                    var changeStudents = [];
                    $('.student-table .select-div').each(function () {
                        var $data = $(this).find('select option:selected');
                        if ($data.val() === '0') {
                            return;
                        }
                        var id = $(this).parents('td').data('id');
                        changeStudents.push({studentId: id, clazzCode: $data.val()});
                    });
                    console.log(changeStudents)
                    $('.student-table .select-div').html('');
                } else {
                    $(this).text('保存转班班学生设置');
                    $(this).addClass('setting-true');
                    var treeObj = $.fn.zTree.getZTreeObj('ktree');
                    var nodes = treeObj.getSelectedNodes();
                    var gradeNode = nodes[0].getParentNode();
                    var clazzs = gradeNode.children;
                    var html = [];
                    html.push('<select class="selectpicker">');
                    html.push('<option value="0">选择转入的班级</option>');
                    $.each(clazzs, function (idx, item) {
                        if (!item.clazz.teachClazz && item.clazz.code !== nodes[0].clazz.code) {
                            html.push('<option value="' + item.clazz.code + '">' + item.clazz.name + '</option>');
                        }
                    });
                    html.push('</select>');
                    $('.student-table .select-div').html(html.join(''));
                    $('.selectpicker').selectpicker({
                        width: '120px'
                    });
                }

            });
            $('.student-tool-bar').on('click', '.setting-teach-clazz', function () {
                if ($(this).hasClass('setting-true')) {
                    $(this).text('设置教学班');
                    $(this).removeClass('setting-true');

                    var changeStudents = [];
                    $('.student-table .select-div').each(function () {
                        var id = $(this).parents('td').data('id');
                        var $data = $(this).find('select option:selected');
                        $data.each(function () {
                            changeStudents.push({studentId: id, clazzCode: $(this).val()});
                        });
                    });
                    console.log(changeStudents)
                    $('.student-table .select-div').html('');
                } else {
                    $(this).text('保存教学班学生设置');
                    $(this).addClass('setting-true');
                    var treeObj = $.fn.zTree.getZTreeObj('ktree');
                    var nodes = treeObj.getSelectedNodes();
                    var gradeNode = nodes[0].getParentNode();
                    var clazzs = gradeNode.children;
                    var html = [];
                    html.push('<select class="selectpicker" multiple>');
                    $.each(clazzs, function (idx, item) {
                        if (item.clazz.teachClazz) {
                            html.push('<option value="' + item.clazz.code + '">' + item.clazz.name + '(' + item.clazz.subjectName + ')</option>');
                        }
                    });
                    html.push('</select>');
                    $('.student-table .select-div').html(html.join(''));
                    $('.selectpicker').selectpicker({
                        noneSelectedText: '选择教学班',
                        width: '200px'
                    });
                }

            });
        }


        function loadClazzTeacher(clazz) {
            var orgCode = getCurOrgCode();
            var subjectURL = 'orgconfig/subjects/' + orgCode;
            var clazzMasterURL = 'teaching/get/clazz/clazzmaseter/' + orgCode + '/' + clazz.code;
            var teachingteacherURL = 'teaching/get/clazz/teachingteacher/' + orgCode + '/' + clazz.code;
            $.when(ajax.getJson(subjectURL), ajax.getJson(clazzMasterURL), ajax.getJson(teachingteacherURL))
                .then(function () {
                    var subjects = arguments[0][0].subjects;
                    var clazzMaster = arguments[1][0].clazzMaster;
                    var teachingTeachers = arguments[2][0].teachingTeachers;
                    var teachingTeacherMap = {};
                    $.each(teachingTeachers, function (idx, item) {
                        teachingTeacherMap[item.subjectName] = item;
                    });
                    var dataset = {};
                    dataset.subjects = subjects;
                    dataset.clazzMaster = clazzMaster;
                    dataset.teachingTeacherMap = teachingTeacherMap;
                    var tmplate = getTemplate('#clazz-teacher-listT');
                    var arryText = dot.template(tmplate);
                    var html = arryText(dataset);
                    $('.clazz-teacher-list-div').html(html);
                }).always(function () {
                $.processError(arguments);
            });
        }

        function loadClazz() {
            var url = 'orgconfig/clazzes/' + getCurOrgCode();
            ajax.getJson(url).then(function (data) {
                var learnSegments = [];
                var grades = [];
                var clazzs = [];
                var gradeMap = {};
                var learnSegmentMap = {};
                $.each(data.clazzes, function (idx, item) {
                    var grade = item.grade;
                    if (!learnSegmentMap[grade.learnSegment]) {
                        learnSegmentMap[grade.learnSegment] = true;
                        var tmp = {};
                        tmp.code = grade.learnSegment;
                        tmp.parentCode = 0;
                        tmp.isClazz = false;
                        if (grade.learnSegment === 1) {
                            tmp.name = '小学';
                        } else if (grade.learnSegment === 2) {
                            tmp.name = '初中';
                        } else if (grade.learnSegment === 3) {
                            tmp.name = '高中';
                        }
                        learnSegments.push(tmp);
                    }

                    if (!gradeMap[grade.id]) {
                        gradeMap[grade.id] = true;
                        var tmp = {};
                        tmp.isClazz = false;
                        tmp.code = grade.id;
                        tmp.parentCode = grade.learnSegment;
                        tmp.name = grade.name;
                        grades.push(tmp);
                    }


                    var tmp = {};
                    tmp.isClazz = true;
                    tmp.code = item.code;
                    tmp.parentCode = grade.id;
                    tmp.name = item.name;
                    tmp.clazz = item;
                    if (item.teachClazz) {
                        tmp.name = tmp.name + '=教学班(' + item.subjectName + ')';
                    }
                    clazzs.push(tmp)
                });
                grades.sort(function (a, b) {
                    if (a.parentCode != b.parentCode) {
                        return a.parentCode - b.parentCode;
                    } else {
                        var v1 = getClazzNameNum(a.name);
                        var v2 = getClazzNameNum(b.name);
                        return v1 - v2;
                    }
                });

                learnSegments[0].open = true;
                grades[0].open = true;

                var treedata = [];
                treedata = treedata.concat(learnSegments)
                treedata = treedata.concat(grades)
                treedata = treedata.concat(clazzs)
                window.treeObj = $.fn.zTree.init($("#ktree"), initTreeSetting(), treedata);
            }).always(function () {
                $.processError(arguments);
            });
        }


        function initEvent() {
            $('.org-div').selectOrg({
                isSchool: true,
                load: function () {
                    loadClazz();
                },
                click: function () {
                    loadClazz();
                }
            });
        }

        return {
            render: function () {
                user.userInfo('student');
                $('main').show();
                initEvent();
            }
        }
    });
})();