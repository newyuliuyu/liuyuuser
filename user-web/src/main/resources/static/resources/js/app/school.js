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
            $('.subject-panel .data').remove();
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
            $('.grade-panel .data').remove();
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
            $('.clazz-panel .panel-content').html('');
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


        function addSubject() {
            var tmplate = getTemplate("#addSubjectT");
            var myDialog = dialog.myModal({size: 'md', body: tmplate}, function () {
                var subjectName = $('#subjectName').val();
                if (subjectName === '') {
                    $('#subjectName+small').text('科目名字不能为空');
                    return;
                }
                var subject = {name: subjectName};
                var schoolCode = getSchoolCode();
                var url = 'school/subject/add/' + schoolCode;
                ajax.postJson(url, subject).then(function (data) {
                    var newSubject = data.subject;
                    subjects.push(newSubject);
                    showSubjects(subjects);
                    myDialog.close();
                    dialog.prompt("增加科目成功");
                }).always(function () {
                    $.processError(arguments);
                });
            });
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

        function addGrade() {
            var gradeNames = ["一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "初一", "初二", "初三", "高一", "高二", "高三"];
            $.each(grades, function (idx, item) {
                var idx = gradeNames.indexOf(item.name);
                if (idx != -1) {
                    gradeNames.splice(idx, 1);
                }
            });
            var dataset = {gradeNames: gradeNames};
            var tmplate = getTemplate("#addGradeT");
            var arrText = dot.template(tmplate);
            var html = arrText(dataset);
            var myDialog = dialog.myModal({size: 'md', body: html, overflow: "unset"}, function () {
                var addGradeNames = $('#gradeName').val();
                if (!addGradeNames) {
                    $('#gradeNameMsg').text('新增的年级必须选择');
                    return;
                }
                var url = 'school/grade/add/' + getSchoolCode();
                ajax.postJson(url, addGradeNames).then(function (data) {
                    var newGrades = data.grades;
                    grades = grades.concat(newGrades);
                    console.log(grades)
                    showGrades(grades);
                    showClazz(grades, clazzes);
                    myDialog.close();
                    dialog.prompt("增加年级成功");
                }).always(function () {
                    $.processError(arguments);
                });
            });
            $('.selectpicker').selectpicker({
                width: '120px',
                noneSelectedText: '选择年级'
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

        function addClazz(gradeId) {
            var dataset = {subjects: subjects};
            console.log(dataset)
            var tmplate = getTemplate("#addClazzT");
            var arrText = dot.template(tmplate);
            var html = arrText(dataset);
            var myDialog = dialog.myModal({size: 'md', body: html}, function () {
                var AddClazzDTO = {};
                var type = $('.add-clazz-div a.active').data('type');
                var $content = $('.' + type);
                AddClazzDTO.batchCreate = $('.add-clazz-div a.active').data('batchCreate');
                if (AddClazzDTO.batchCreate) {
                    AddClazzDTO.num = $content.find('.clazzNum').val();
                    var myNum = parseInt(AddClazzDTO.num);
                    if (isNaN(myNum) || myNum < 1) {
                        $content.find('.clazzNum+small').text("必须为数字并且大于0");
                        return;
                    }
                } else {
                    AddClazzDTO.name = $content.find('.clazzName').val().trim();
                    if (AddClazzDTO.name === '') {
                        $content.find('.clazzName+small').text("班级名字不能为空");
                        return;
                    }
                }
                AddClazzDTO.wl = $content.find('.wl:checked').val()
                AddClazzDTO.teachClazz = $content.find('.teachClazz').is(':checked');
                if (AddClazzDTO.teachClazz) {
                    AddClazzDTO.subjectName = $content.find('.subjectName option:selected').val();
                    if (AddClazzDTO.subjectName === '') {
                        $content.find('.subjectName+small').text("必须选择科目");
                        return;
                    }
                }
                AddClazzDTO.schoolCode = getSchoolCode();
                AddClazzDTO.gradeId = gradeId;

                console.log(AddClazzDTO)

                var url = 'school/clazz/add';
                ajax.postJson(url, AddClazzDTO).then(function (data) {
                    var newClazzes = data.clazzes;
                    clazzes = clazzes.concat(newClazzes);
                    showClazz(grades, clazzes);
                    myDialog.close();
                    dialog.prompt("增加编辑成功");
                }).always(function () {
                    $.processError(arguments);
                });

            });
            $('.selectpicker').selectpicker({
                width: '120px'
            });
            $(".attribute").iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue'
            });
            $('.single-add .teachClazz').on('ifChanged', function () {
                $('.single-add .subjectName+small').text("");
                if ($(this).is(':checked')) {
                    $('.single-add .subjectName').attr('disabled', false);
                    $('.single-add .subjectName').selectpicker('refresh');
                } else {
                    $('.single-add .subjectName').attr('disabled', true);
                    $('.single-add .subjectName').selectpicker('refresh');
                }
            });
            $('.batch-add .teachClazz').on('ifChanged', function () {
                $('.batch-add .subjectName+small').text("");
                if ($(this).is(':checked')) {
                    $('.batch-add .subjectName').attr('disabled', false);
                    $('.batch-add .subjectName').selectpicker('refresh');
                } else {
                    $('.batch-add .subjectName').attr('disabled', true);
                    $('.batch-add .subjectName').selectpicker('refresh');
                }
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
                addSubject();
            });
            $('.subject-panel').on('click', '.del-subject', function () {
                delSubject($(this))
            });
            $('.grade-panel').on('click', '.add-grade-btn', function () {
                addGrade();
            });
            $('.grade-panel').on('click', '.del-subject', function () {
                delGrade($(this));
            });
            $('.clazz-panel').on('click', '.add-clazz-btn', function () {
                var gradeId = $(this).data('gradeId');
                addClazz(gradeId);
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