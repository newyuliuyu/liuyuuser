(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'text!tmpl/teacher-tmpl.html',
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

        function getCurOrgDeep() {
            return parseInt($('#curOrgDeep').val());
        }

        function teacherList(page) {
            var url = 'teacher/list?orgCode=' + getCurOrgCode();
            if ($.isPlainObject(page)) {
                url += '&pageNum=' + page.pageNum + "&pageSize=" + page.pageSize;
            }
            var searchText = $('#searchText').val();
            if (searchText && searchText !== '') {
                url += '&search=' + searchText;
            }
            ajax.getJson(url).then(function (data) {
                var template = getTemplate("#teacherListT");
                var arrText = dot.template(template);
                var html = arrText(data);
                $('#contentDIV').html(html);
                UI.pager().create('pager1', function (page) {
                    teacherList(page);
                });
            }).always(function () {
                $.processError(arguments);
            });
        }

        function clickCurPage(isDel) {
            if (isDel) {
                var trSize = $('.teacher-table>tbody>tr').size();
                if (trSize < 2) {
                    $('#pager1').clickPreviousPage();
                }
            }
            $('#pager1').clickCurPage();
        }

        function delTeacher(code) {
            var url = 'teacher/delete/' + code;
            ajax.postJson(url).then(function (data) {
                dialog.prompt("删除老师成功");
                clickCurPage(true);
            }).always(function () {
                $.processError(arguments);
            });
        }

        function updateTeacher(code) {
            var url = 'teacher/get/' + code;
            ajax.getJson(url).then(function (data) {
                console.log(data)
                var template = getTemplate("#addAndUpdateTeacherT");
                var arryText = dot.template(template);
                var html = arryText(data);
                var myDialog = dialog.myModal({size: getSize(), body: html}, function () {
                    var teacher = getTeacher();
                    if (!teacher) {
                        return;
                    }
                    var url = 'teacher/update';
                    ajax.postJson(url, teacher).then(function (data) {
                        dialog.prompt("修改教师成功");
                        myDialog.close();
                        clickCurPage();
                    }).always(function () {
                        $.processError(arguments);
                    });
                });
            }).always(function () {
                $.processError(arguments);
            });
        }



        function initEvent() {


            $('#contentDIV').on('click', '.del-btn', function () {
                var code = $(this).data('code');

                var myDialog = dialog.confirm("删除老师", "确定要删除老师，删除老师后不能再恢复", function () {
                    delTeacher(code);
                    myDialog.close();
                });
            });
            $('#contentDIV').on('click', '.update-btn', function () {
                var code = $(this).data('code');
                updateTeacher(code);
            });
            $('.add').on('click', function () {
                addTeacher();
            });
            $('.import').on('click', function () {
                importTeacher();
            });
            $('.download-tmp').on('click', function () {
                var url = window.app.rootPath + "teacher/download/tmpl/" + getCurOrgCode();
                $.download(url);
            });
            $('.search-teacher').on('click', function () {
                teacherList();
            });

            $('.org-div').selectOrg({
                load:function () {
                    teacherList();
                },
                click:function () {
                    teacherList();
                }
            })
        }

        function addTeacher() {
            var data = {teacher: {}};
            var template = getTemplate("#addAndUpdateTeacherT");
            var arryText = dot.template(template);
            var html = arryText(data);
            var myDialog = dialog.myModal({size: getSize(), body: html}, function () {
                var teacher = getTeacher();
                if (!teacher) {
                    return;
                }
                var url = 'teacher/add';
                ajax.postJson(url, teacher).then(function (data) {
                    dialog.prompt("增加教师成功");
                    myDialog.close();
                    teacherList();
                }).always(function () {
                    $.processError(arguments);
                });
            });
        }

        function getTeacher() {
            var teacher = {};
            teacher.code = $('.code').val().trim();
            teacher.name = $('.name').val().trim();
            teacher.account = $('.account').val().trim();
            teacher.phone = $('.phone').val().trim();
            teacher.email = $('.email').val().trim();
            teacher.org = {};
            teacher.org.code = getCurOrgCode();

            var isError = false;
            if (teacher.name === '') {
                $('.name+small').text('教师姓名不能为空');
                isError = true;
            }
            if (teacher.account === '') {
                $('.account+small').text('教师帐号不能为空');
                isError = true;
            }

            if (isError) {
                return false;
            }
            return teacher;
        }

        function importTeacher() {
            var html = getTemplate('#importTeacherT');
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
            var url = 'teacher/import';
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

        return {
            render: function () {
                user.userInfo('teacher');
                initEvent();
                $('main').show();
            }
        }
    });
})();