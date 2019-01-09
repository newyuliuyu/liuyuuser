(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'bootstrap',
        'css!style/bootstrap/bootstrap.min',
        'bootstrapSelect',
        'css!style/font-awesome',
        'css!style/app',
        'loading',
        'ztree'
    ];
    define(models, function ($, ajax, user, dot, dialog, menu, UI) {


        function list(page) {
            var url = '/role/list?1=1';
            if ($.isPlainObject(page)) {
                url += '&pageNum=' + page.pageNum + "&pageSize=" + page.pageSize;
            }

            ajax.getJson(url).then(function (data) {
                var templateText = $("#roleTableT").text();
                var arrText = dot.template(templateText);
                var html = arrText(data);
                $('#tableDIV').html(html);
                UI.pager().create('pager', function (page) {
                    list(page);
                });
            }).always(function () {
                $.processError(arguments);
            })
        }

        function eventInit() {
            $('#tableDIV').on('click', '.del-btn', function () {
                var $this = $(this);
                var myDialog = dialog.confirm("删除角色", "确定要删除角色，删除角色以后用户关联改角色都不可用", function () {
                    delRole($this);
                    myDialog.close();
                });

            });
            $('#tableDIV').on('click', '.update-btn', function () {
                updateRole($(this));
            });
            $('#tableDIV').on('click', '.setting-btn', function () {
                settingRes($(this));
            });

            $('#addRole').click(function () {
                addRole();
            });
        }


        function settingRes($this) {
            var id = $this.data('id');
            var url = '/role/res/' + id;
            ajax.getJson(url).then(function (data) {
                var roleReses = data.reses;
                var treedataset = createTreeData(roleReses);
                var myDialog = dialog.myModal({size: 'md', body: '<div id="ktree" class="ztree"></div>'}, function () {
                    var nodes = treeObj.getChangeCheckedNodes();
                    if (nodes.length > 0) {
                        saveRoleRes(id, nodes);
                    }
                    myDialog.close();
                });
                window.treeObj = $.fn.zTree.init($("#ktree"), initTreeSetting(), treedataset);
            }).always(function () {
                $.processError(arguments);
            })
        }

        function saveRoleRes(roleId, nodes) {
            var delResIds = [];
            var saveResIds = [];
            $.each(nodes, function (idx, item) {
                if (item.checked) {
                    saveResIds.push({id: item.id})
                } else {
                    delResIds.push({id: item.id});
                }
            });

            var roleRes = {};
            roleRes.saveResource = saveResIds;
            roleRes.delResource = delResIds;
            var url = '/role/saveres/' + roleId;
            ajax.postJson(url, roleRes).then(function (data) {
                dialog.prompt("设置成功");
            }).always(function () {
                $.processError(arguments);
            })
        }

        function initTreeSetting() {
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: 'id',
                        pIdKey: 'parentId',
                        rootPId: '0'
                    }
                },
                check: {
                    enable: true,
                    chkStyle: 'checkbox',
                    radioType: 'all'
                }
            };
            return setting;
        }


        function createTreeData(roleReses) {
            var roleResesMap = {};
            $.each(roleReses, function (idx, item) {
                roleResesMap[item.id] = item;
            });

            var result = [];
            $.each(allReses, function (idx, item) {
                var treeData = {};
                treeData.id = item.id;
                treeData.name = item.name;
                treeData.parentId = item.parentId;
                treeData.checked = false;
                if (roleResesMap[treeData.id]) {
                    treeData.checked = true;
                }
                result.push(treeData);
            });

            return result;
        }


        var h = window.getClientHeight() - 200;
        if (h < 200) {
            h = 200;
        }
        var size = {w: 600, h: h};

        function addRole() {
            var dataset = {};
            dataset.roleTypes = roleTypes;
            dataset.role = {};
            var templateText = $("#newAndUpdateRoleT").text();
            var arrText = dot.template(templateText);
            var html = arrText(dataset);
            var myDialog = dialog.myModal({size: size, body: html, overflow: "unset"}, function () {
                var roleName = $('#name').val().trim();
                var roleType = $('#roleType').val().trim();
                var systemBuiltin = $('#systemBuiltin').val().trim();
                var role = {}
                role.name = roleName;
                role.systemBuiltin = systemBuiltin;
                role.roleType = roleType;
                if (!role.name || role.name === '') {
                    $('#name+small').text("角色名字不能为空");
                    return;
                }
                var url = '/role/add';
                ajax.postJson(url, role).then(function (data) {
                    dialog.prompt("增加成功");
                    list();
                    myDialog.close();
                }).always(function () {
                    $.processError(arguments);
                })

            });
            $('.selectpicker').selectpicker({
                width: '120px'
            });
        }

        function updateRole($this) {

            var id = $this.data('id');
            var roleType = $this.data('roleType');
            var name = $this.data('name');
            var systembuiltin = $this.data('systembuiltin');
            var role = {}
            role.id = id;
            role.roleType = roleType;
            role.name = name;
            role.systemBuiltin = systembuiltin;

            var dataset = {};
            dataset.roleTypes = roleTypes;
            dataset.role = role;

            var templateText = $("#newAndUpdateRoleT").text();
            var arrText = dot.template(templateText);
            var html = arrText(dataset);
            var myDialog = dialog.myModal({size: size, body: html, overflow: "unset"}, function () {
                var id = $('#id').val().trim();
                var roleName = $('#name').val().trim();
                var roleType = $('#roleType').val().trim();
                var systemBuiltin = $('#systemBuiltin').val().trim();
                var role = {}
                role.id = id;
                role.name = roleName;
                role.systemBuiltin = systemBuiltin;
                role.roleType = roleType;
                if (!role.name || role.name === '') {
                    $('#name+small').text("角色名字不能为空");
                    return;
                }
                var url = '/role/update';
                ajax.postJson(url, role).then(function (data) {
                    dialog.prompt("修改成功");
                    clickCurPage();
                    myDialog.close();
                }).always(function () {
                    $.processError(arguments);
                    // $('body').showError(arguments[0]);
                })

            });
            $('.selectpicker').selectpicker({
                width: '120px'
            });

        }


        function delRole($this) {
            var id = $this.data('id');
            var url = '/role/delete';
            ajax.postJson(url, {id: id}).then(function (data) {
                dialog.prompt('删除成功');
                var trSize = $('.role-table>tbody>tr').size();
                if (trSize < 2) {
                    $('#pager').clickPreviousPage()
                } else {
                    clickCurPage();
                }

            }).always(function () {
                $.processError(arguments);
            })
        }

        function clickCurPage() {
            $('#pager').clickCurPage();
        }



        var roleTypes = undefined;
        var allReses = undefined;

        function initData() {
            var url = '/role/roletype/-1';
            ajax.getJson(url).then(function (data) {
                roleTypes = data.roleTypes;
                console.log(roleTypes)
            }).always(function () {
                $.processError(arguments);
            });
            var resUrl = '/res/list';
            ajax.getJson(resUrl).then(function (data) {
                allReses = data.reses;
            }).always(function () {
                $.processError(arguments);
            })

        }

        return {
            render: function () {
                initData();
                user.userInfo('home');
                $('main').show();
                menu.createMenu('roleMenu');
                list()
                eventInit();
            }
        }
    });
})();