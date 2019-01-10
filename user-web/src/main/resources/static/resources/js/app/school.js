(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'text!tmpl/school-tmpl.html',
        'bootstrap',
        'css!style/bootstrap/bootstrap.min',
        'bootstrapSelect',
        'css!style/font-awesome',
        'css!style/app',
        'loading',
        'ztree',
        'icheck'
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

        var subjects = null;
        var grades = null;
        var clazzes = null;

        function loadSchoolConfig() {
            var schoolCode = $('#curSchoolCode').val();
            var subjectURL = 'school/subjects/' + schoolCode;
            var gradeURL = 'school/grades/' + schoolCode;
            var clazzURL = 'school/clazzes/' + schoolCode;
            $.when(ajax.getJson(subjectURL), ajax.getJson(gradeURL), ajax.getJson(clazzURL)).then(function (data) {
                subjects = arguments[0][0].subjects;
                grades = arguments[1][0].grades;
                clazzes = arguments[2][0].clazzes;
                $('.subject-panel .data').remove();
                $('.grade-panel .data').remove();
                $('.clazz-panel .panel-content').html('');
                if (subjects.length === 0 && grades.length === 0) {
                    $('.my-tool-bar').append('<button type="button" class="a-key-config-school btn btn-outline-primary float-right">一键初始化学校配置</button>');
                } else {
                    $('.a-key-config-school').remove();
                    showSubjects(subjects);
                    showGrades(grades);
                    showClazz(grades, clazzes);
                }
            }).always(function () {
                $.processError(arguments);
            });
        }

        function showSubjects(subjects) {
            var dataset = {subjects: subjects};
            var tmplate = '{{~it.subjects:item:idx}}<div class="subject-name-block data">\
                             <span class="del-subject" data-id="{{=item.id}}">x</span>\
                             <label>{{=item.name}}</label>\
                          </div>{{~}}';
            var arrText = dot.template(tmplate);
            var html = arrText(dataset);
            $('.add-subject-btn').before(html);
        }

        function showGrades(grades) {
            var learnSegmentMap = {1: [], 2: [], 3: []};
            $.each(grades, function (idx, item) {
                learnSegmentMap[item.learnSegment].push(item);
            });

            for (var i = 1; i <= 3; i++) {
                var dataset = {grades: learnSegmentMap[i]};
                var tmplate = '{{~it.grades:item:idx}}<div class="subject-name-block data">\
                                 <span class="del-subject" data-id="{{=item.id}}">x</span>\
                                 <label>{{=item.name}}</label>\
                              </div>{{~}}';
                var arrText = dot.template(tmplate);
                var html = arrText(dataset);
                $('.add-grade-' + i + '-btn').before(html);
            }
        }

        function showClazz(grades, clazzes) {
            var gradeClazzMap = {};
            $.each(clazzes, function (idx, item) {
                var gradeId = item.grade.id;
                if (!gradeClazzMap[gradeId]) {
                    gradeClazzMap[gradeId] = [];
                }
                gradeClazzMap[gradeId].push(item);
            });

            var dataset = {grades: grades, gradeClazzMap: gradeClazzMap};

            var tmplate = getTemplate("#clazzListT");
            var arrText = dot.template(tmplate);
            var html = arrText(dataset);
            $('.panel-content').html(html);
        }


        function loadUserSchool() {
            var url = 'school/user/school';
            ajax.getJson(url).then(function (data) {
                var school = data.school;
                $('.school-name-label').text(school.name);
                $('#curSchoolCode').val(school.code);
                loadSchoolConfig();
            }).always(function () {
                $.processError(arguments);
            });
        }

        function changeSchool() {
            $('.change-school-btn').click(function () {
                var tmplate = getTemplate("#selecteSchoolDialogT");
                var myDialog = dialog.myModal({size: getSize(), body: tmplate}, function () {
                    var selecteSchool = $('.school-radio:checked');
                    if (selecteSchool.size() > 0) {
                        $('.school-name-label').text(selecteSchool.data('name'));
                        $('#curSchoolCode').val(selecteSchool.val());
                        loadSchoolConfig();
                    }
                    myDialog.close();
                });
                schoolList();
                $('#searchBtn').click(function () {
                    var schoolName = $('#searchText').val().trim();
                    schoolList();
                });
            });
        }

        function schoolList(page) {
            var url = 'school/schools?1=1';
            if ($.isPlainObject(page)) {
                url += '&pageNum=' + page.pageNum + "&pageSize=" + page.pageSize;
            }
            var searchText = $('#searchText').val();
            if (searchText && searchText !== '') {
                url += '&schoolName=' + searchText;
            }

            ajax.getJson(url).then(function (data) {
                var tmplate = getTemplate("#schoolListT");
                var arrText = dot.template(tmplate);
                var html = arrText(data);
                $('.school-ul-dialog').html(html);
                UI.pager().create('pager', function (page) {
                    schoolList(page);
                });
                $(".attribute").iCheck({
                    checkboxClass: 'icheckbox_square-blue',
                    radioClass: 'iradio_square-blue'
                });
            }).always(function () {
                $.processError(arguments);
            });
        }

        function aKeyConfigSchool() {
            var html = '<input type="checkbox" class="attribute" data-type="hasPrimarySchool">是否有小学\
                    <input type="checkbox" class="attribute" data-type="hasJuniorHighSchool">是否有初中\
                    <input type="checkbox" class="attribute" data-type="hasHighSchool">是否有高中';
            var myDialog = dialog.myModal({size: 'sm', body: html}, function () {
                var AKeyConfigSchoolDTO = {
                    schoolCode: $('#curSchoolCode').val(),
                    hasPrimarySchool: false,
                    hasJuniorHighSchool: false,
                    hasHighSchool: false
                };
                if ($(".attribute:checked").size() == 0) {
                    dialog.alter("请选择一个条件");
                    return;
                }
                $(".attribute:checked").each(function () {
                    AKeyConfigSchoolDTO[$(this).data('type')] = true;
                });
                var url = 'school/a-key-config-school';
                ajax.postJson(url, AKeyConfigSchoolDTO).then(function (data) {
                    myDialog.close();
                    dialog.prompt("初始化成功");
                    loadSchoolConfig();
                }).always(function () {
                    $.processError(arguments);
                });

            });
            $(".attribute").iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue'
            });
        }

        function getSchoolCode() {
            return $('#curSchoolCode').val();
        }

        function delSubject($this) {
            var subjectId = $this.data('id');
            dialog.confirm("删除科目", "你确定要删除改科目吗?删除该科目可能会影响其他业务模块", function () {
                var schoolCode = getSchoolCode();
                var url = 'school/subject/del/' + schoolCode + '/' + subjectId;
                ajax.postJson(url).then(function (data) {
                    $.each(subjects, function (idx, item) {
                        if (item.id === subjectId) {
                            subjects.splice(idx, 1);
                            return false;
                        }
                    });
                    $this.parents('.data').remove();
                    dialog.prompt("删除科目成功");
                }).always(function () {
                    $.processError(arguments);
                });
            });
        }

        function delGrade($this) {
            var gradeId = $this.data('id');
            dialog.confirm("删除年级", "你确定要删除该年级吗?删除该年级同时删除该年级的班级，可能会影响其他业务模块", function () {
                var schoolCode = getSchoolCode();
                var url = 'school/grade/del/' + schoolCode + '/' + gradeId;
                ajax.postJson(url).then(function (data) {
                    $.each(grades, function (idx, item) {
                        if (item.id === gradeId) {
                            grades.splice(idx, 1);
                            return false;
                        }
                    });
                    showClazz(grades, clazzes);
                    $this.parents('.data').remove();
                    dialog.prompt("删除年级成功");
                }).always(function () {
                    $.processError(arguments);
                });
            });
        }

        function delClazz($this) {
            var clazzCode = $this.data('id');
            dialog.confirm("删除班级", "你确定要删除该年级吗?删除该班级可能会影响其他业务模块", function () {
                var schoolCode = getSchoolCode();
                var url = 'school/clazz/del/' + schoolCode + '/' + clazzCode;
                ajax.postJson(url).then(function (data) {
                    $.each(clazzes, function (idx, item) {
                        if (item.code === clazzCode) {
                            clazzes.splice(idx, 1);
                            return false;
                        }
                    });
                    $this.parents('.data').remove();
                    dialog.prompt("删除班级成功");
                }).always(function () {
                    $.processError(arguments);
                });
            });
        }

        function initEvent() {
            $('.my-tool-bar').on('click', '.a-key-config-school', function () {
                aKeyConfigSchool();
            });
            $('.subject-panel').on('click', '.add-subject-btn', function () {
                console.log("add-subject-btn")
            });
            $('.subject-panel').on('click', '.del-subject', function () {
                delSubject($(this))
            });
            $('.grade-panel').on('click', '.add-grade-btn', function () {
                console.log("add-grade-btn")
            });
            $('.grade-panel').on('click', '.del-subject', function () {
                delGrade($(this));
            });
            $('.clazz-panel').on('click', '.add-clazz-btn', function () {
                console.log("add-clazz-btn")
            });
            $('.clazz-panel').on('click', '.del-subject', function () {
                delClazz($(this))
            });
        }

        return {
            render: function () {
                user.userInfo('school');
                loadUserSchool();
                changeSchool();
                initEvent();
                $('main').show();
            }
        }
    });
})();