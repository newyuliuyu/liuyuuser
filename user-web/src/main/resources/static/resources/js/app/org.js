(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'text!tmpl/org-tmpl.html',
        'bootstrap',
        'css!style/bootstrap/bootstrap.min',
        'bootstrapSelect',
        'css!style/font-awesome',
        'css!style/app',
        'loading',
        'ztree',
        'js/commons/JQuery.download',
        'js/commons/JQuery.progress'
    ];
    define(models, function ($, ajax, user, dot, dialog, menu, UI, tmpl) {

        function getSize() {
            var h = window.getClientHeight() - 200;
            if (h < 200) {
                h = 200;
            }
            return {w: 600, h: h};
        }


        function treeSetting() {
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: 'code',
                        pIdKey: 'parentCode',
                        rootPId: '1'
                    }
                },
                callback: {
                    onClick: queryChild
                }
            };
            return setting;
        }

        function queryChild(event, treeId, treeNode, clickFlag) {
            var code = getSelectedNodeCode();
            list(undefined, code);
        }

        function getSelectedNodeCode() {
            var treeObj = $.fn.zTree.getZTreeObj("ktree");
            var nodes = treeObj.getSelectedNodes();
            if (nodes.length > 0) {
                return nodes[0].code;
            } else {
                return '0';
            }
        }

        function buildLeftTreeOrg() {
            var url = 'org/listwithnotschool';
            ajax.getJson(url).then(function (data) {
                var org = data.orgs;
                var treeObj = $.fn.zTree.init($("#ktree"), treeSetting(), org);
            }).always(function () {
                $.processError(arguments);
            });
        }

        function list(page, parentCode) {
            var url = '/org/child/' + parentCode + '?1=1';
            if ($.isPlainObject(page)) {
                url += '&pageNum=' + page.pageNum + "&pageSize=" + page.pageSize;
            } else {
                url += '&pageNum=1&pageSize=20';
            }
            ajax.getJson(url).then(function (data) {
                var templateText = getTemplate('#tableT');
                var arrText = dot.template(templateText);
                var html = arrText(data);
                $('#tableDIV').html(html);
                UI.pager().create('pager', function (page) {
                    var code = getSelectedNodeCode();
                    list(page, code);
                });
            }).always(function () {
                $.processError(arguments);
            });
        }

        function clickCurPage(isDel) {
            if (isDel) {
                var trSize = $('.org-table>tbody>tr').size();
                if (trSize < 2) {
                    $('#pager').clickPreviousPage();
                }
            }
            $('#pager').clickCurPage();
        }


        function initEvent() {
            $('#tableDIV').on('click', '.del-btn', function () {
                var code = $(this).data('code');
                delOrg(code);
            });
            $('#tableDIV').on('click', '.update-btn', function () {
                var code = $(this).data('code');
                updOrg(code);
            });
            $('#tableDIV').on('click', '.add-btn', function () {
                var code = $(this).data('code');
                addOrg(code);
            });

            $('#import').click(function () {
                importOrg();
            });

            $('#downloadTmpl').click(function () {
                var url = window.app.rootPath + "org/download/tmpl";
                $.download(url);
            });
        }


        function importOrg() {
            var html = getTemplate('#importOrgT');
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
            var url = 'org/import';
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

        function delOrg(code) {
            var myDialog = dialog.confirm("删除机构", "如果删除了结构，该机构关联的数据将不能用了", function () {
                myDialog.close();
                var url = 'org/delete';
                var org = {code: code};
                ajax.postJson(url, org).then(function (data) {
                    dialog.prompt("删除成功");
                    clickCurPage(true);
                    var treeObj = $.fn.zTree.getZTreeObj("ktree");
                    var node = treeObj.getNodeByParam('code', code);
                    treeObj.removeNode(node);
                }).always(function () {
                    $.processError(arguments);
                });
            });
        }

        function addOrg(parentCode) {
            var url = 'org/get/' + parentCode;
            ajax.getJson(url).then(function (data) {
                var templateText = getTemplate('#newOrgT');
                var arrText = dot.template(templateText);
                var html = arrText(data);

                var myDialog = dialog.myModal({size: getSize(), body: html}, function () {
                    var org = {};
                    org.code = $('#code').val().trim();
                    if (!org.code || org.code === '') {
                        $('#code+small').text("机构代码必须填写");
                        return
                    }
                    org.name = $('#name').val().trim();
                    if (!org.name || org.name === '') {
                        $('#name+small').text("机构名称必须填写");
                        return
                    }
                    org.deep = $('#deep').val().trim();
                    org.parentCode = $('#parentCode').val().trim();

                    var url = 'org/add';
                    ajax.postJson(url, org).then(function (data) {
                        myDialog.close();
                        dialog.prompt("保存成功");
                        var treeObj = $.fn.zTree.getZTreeObj("ktree");
                        var parentNode = treeObj.getNodeByParam('code', org.parentCode);
                        treeObj.addNodes(parentNode, org);
                        clickCurPage();
                    }).always(function () {
                        $.processError(arguments);
                    });
                });
                $('.selectpicker').selectpicker({
                    width: '120px'
                });

                $('#code').blur(function () {
                    var code = $(this).val();
                    if (code && code !== '') {
                        var url = 'org/get/' + code;
                        ajax.getJson(url).then(function (data) {
                            if (data.org) {
                                $('#code+small').text("机构代码已存在");
                            } else {
                                $('#code+small').text("");
                            }
                        }).always(function () {
                            $.processError(arguments);
                        });
                    }
                });

            }).always(function () {
                $.processError(arguments);
            });
        }

        function updOrg(code) {
            var url = 'org/get/' + code;
            ajax.getJson(url).then(function (data) {
                var templateText = getTemplate('#updT');
                var arrText = dot.template(templateText);
                var html = arrText(data);
                var org = data.org;
                var myDialog = dialog.myModal({size: getSize(), body: html}, function () {
                    org.name = $('#name').val().trim();
                    if (!org.name || org.name === '') {
                        $('#name+small').text("机构名称必须填写");
                        return
                    }

                    var url = 'org/update';
                    ajax.postJson(url, org).then(function (data) {
                        myDialog.close();
                        dialog.prompt("保存成功");
                        var treeObj = $.fn.zTree.getZTreeObj("ktree");
                        var node = treeObj.getNodeByParam('code', org.code);
                        node.name = org.name;
                        treeObj.updateNode(node, org);
                        clickCurPage();
                    }).always(function () {
                        $.processError(arguments);
                    });
                });
            }).always(function () {
                $.processError(arguments);
            });

        }


        function getTemplate(id) {
            return $('<div>' + tmpl + '</div>').find(id).text();
        }


        return {
            render: function () {
                user.userInfo('org');
                buildLeftTreeOrg();
                list(undefined, "0");
                $('main').show();
                initEvent();

            }
        }
    });
})();