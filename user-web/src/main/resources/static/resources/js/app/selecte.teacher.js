(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'dot',
        'dialog',
        'text!tmpl/selecte-teacher-tmpl.html',
        'js/commons/UI'
    ];
    define(models, function ($, ajax, dot, dialog, tmpl, UI) {

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

        function showTeacherDialog(opts) {
            var tmplate = getTemplate('#selectTeacherT');
            var myDialog = dialog.myModal({size: 'md', body: tmplate}, function () {
                var $newTeacher = $('.teacher-radio:checked');

                if ($newTeacher.size() > 0) {
                    if (opts.checkbox) {
                        var teachers = [];
                        $newTeacher.each(function () {
                            var newTeacher = {};
                            newTeacher.name = $(this).data('name');
                            newTeacher.code = $(this).data('code');
                            newTeacher.account = $(this).data('account');
                            teachers.push(newTeacher);
                        });
                        if (opts.click && $.isFunction(opts.click)) {
                            opts.click(teachers);
                        }
                    } else {
                        var newTeacher = {};
                        newTeacher.name = $newTeacher.data('name');
                        newTeacher.code = $newTeacher.data('code');
                        newTeacher.account = $newTeacher.data('account');
                        if (opts.click && $.isFunction(opts.click)) {
                            opts.click(newTeacher);
                        }
                    }

                    myDialog.close();
                } else {
                    dialog.alter("没有选择老师");
                }
            });
            teacherList();
            $('#searchBtn').click(function () {
                teacherList();
            });
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
                data.disable = function (teacher) {
                    if (disableFun && $.isFunction(disableFun)) {
                        return disableFun(teacher);
                    }
                    return false;
                };
                data.checkbox = checkbox;

                var template = getTemplate("#teacherListT");
                var arrText = dot.template(template);
                var html = arrText(data);
                $('.teacher-list').html(html);
                UI.pager().create('teacherPager', function (page) {
                    teacherList(page);
                });
                $(".attribute").iCheck({
                    checkboxClass: 'icheckbox_square-blue',
                    radioClass: 'iradio_square-blue'
                });
            }).always(function () {
                $.processError(arguments);
            });
        }

        var disableFun = undefined;
        var checkbox = false;

        return {
            render: function () {

            },
            showTeacherDialog: function (opts) {
                disableFun = opts.disable;
                if (opts.checkbox) {
                    checkbox = opts.checkbox;
                }
                showTeacherDialog(opts)
            }
        }
    });
})();