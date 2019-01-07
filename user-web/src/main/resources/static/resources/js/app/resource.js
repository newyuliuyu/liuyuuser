(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'js/commons/ztree.table',
        'dialog',
        'js/app/menu',
        'bootstrap',
        'css!style/bootstrap/bootstrap.min',
        'bootstrapSelect',
        'css!style/font-awesome',
        'css!style/app',
        'loading'
    ];
    define(models, function ($, ajax, user, dot, ztreetable, dialog, menu) {


        function listResources() {
            var url = '/res/list';
            ajax.getJson(url).then(function (data) {
                var reses = data.reses;

                var headers = ["名称", "资源类型", "元素ID", "分组", "小组", "显示顺序"];
                var dataKeys = ['resourceType', 'elementId', 'group', 'smallGroup', 'orderNum'];
                ztreetable.table('#dataTree', headers, dataKeys, reses, function (treedata) {
                    var htmlStr = '';
                    htmlStr += '<div class="icon_div"><a class="icon_edit del-btn"   href="javascript:void(0);" data-id="' + treedata.id + '">删除</a></div>';
                    htmlStr += '<div class="icon_div"><a class="icon_edit upd-btn"  href="#" href="#" data-id="' + treedata.id + '">修改</a></div>';
                    htmlStr += '<div class="icon_div"><a class="icon_add add-btn"  href="#" href="#" data-id="' + treedata.id + '">增加下级菜单</a></div>';
                    return htmlStr;
                });
                initOperationEvent();
            }).always(function () {
                $.processError(arguments);
            });
        }

        function initOperationEvent() {
            var h = window.getClientHeight() - 200;
            if (h < 200) {
                h = 200;
            }
            var size = {w: 600, h: h};

            $('#dataTree').on('click', '.del-btn', function () {
                var id = $(this).data('id');
                var treeObj = $.fn.zTree.getZTreeObj('dataTree');
                var treeNode = treeObj.getNodeByParam("id", id, null);
                if (treeNode.children && treeNode.children.length > 0) {
                    dialog.alter("有子节点，不能删除");
                    return;
                }


                var myDialog = dialog.confirm('删除资源', '是否删除该资源，删除了该资源所有绑定类该将无法再使用', function () {
                    var url = '/res/delete/' + id;
                    ajax.postJson(url).then(function (value) {
                        dialog.prompt("删除资源成功");
                        treeObj.removeNode(treeNode);
                        myDialog.close();
                    }).always(function () {
                        $.processError(arguments);
                    });
                });
            });
            $('#dataTree').on('click', '.upd-btn', function () {
                var id = $(this).data('id');
                var treeObj = $.fn.zTree.getZTreeObj('dataTree');
                var treeNode = treeObj.getNodeByParam("id", id, null);
                var templateText = $("#resourceT").text();
                var arrText = dot.template(templateText);
                var html = arrText(treeNode);

                var myDialog = dialog.myModal({
                    size: size,
                    body: html
                }, function () {
                    var res = getRes();
                    if (res != false) {
                        var url = '/res/update';
                        ajax.postJson(url, res).then(function (value) {
                            dialog.prompt('修改成功');
                            myDialog.close();
                            treeNode = updateTreeNode(treeNode, res);
                            treeObj.updateNode(treeNode);
                        }).always(function () {
                            $.processError(arguments);
                        });
                    }
                });
                $('.selectpicker').selectpicker({
                    width: '120px'
                });
            });
            $('#dataTree').on('click', '.add-btn', function () {
                var id = $(this).data('id');
                var dataset = {};
                var templateText = $("#resourceT").text();
                var arrText = dot.template(templateText);
                var html = arrText(dataset);
                var myDialog = dialog.myModal({
                    size: size,
                    body: html
                }, function () {
                    var res = getRes();
                    if (res != false) {
                        res.parentId = id;
                        var url = '/res/add';
                        ajax.postJson(url, res).then(function (value) {
                            dialog.prompt('保存成功');
                            myDialog.close();
                            var treeObj = $.fn.zTree.getZTreeObj('dataTree');
                            var treeNode = treeObj.getNodeByParam("id", id, null);
                            treeObj.addNodes(treeNode, res);
                        }).always(function () {
                            $.processError(arguments);
                        });
                    }
                });
                $('.selectpicker').selectpicker({
                    width: '120px'
                });
            });
        }

        function updateTreeNode(treeNod, res) {
            treeNod.id = res.id;
            treeNod.name = res.name;
            treeNod.displayText = res.displayText;
            treeNod.resourceType = res.resourceType;
            treeNod.elementId = res.elementId;
            treeNod.group = res.group;
            treeNod.smallGroup = res.smallGroup;
            treeNod.orderNum = res.orderNum;
            treeNod.parentId = res.parentId;
            return treeNod;
        }

        function getRes() {
            var valid = true;
            var res = {};
            res.id = $('#id').val();
            res.name = $('#name').val();
            if (!res.name || res.name === '') {
                $('#name+small').text('资源的名称必须填写');
                valid = false;
            }
            res.displayText = $('#displayText').val();
            res.resourceType = $('#resourceType').val();
            res.elementId = $('#elementId').val();
            res.group = $('#group').val();
            if (!res.group || res.group === '') {
                $('#group+small').text('资源必须归属到一个组中');
                valid = false;
            }
            res.smallGroup = $('#smallGroup').val();
            res.orderNum = $('#orderNum').val();
            res.parentId = $('#parentId').val();
            if (!valid) {
                return false;
            }
            return res;
        }


        return {
            render: function () {
                user.userInfo('home');
                $('main').show();
                menu.createMenu('resourceMenu');
                listResources();
            }
        }
    });
})();