(function () {
    "use strict";
    var deps = ['jquery',
        'css!style/zTreeStyle',
        'css!style/ztreetable',
        'ztree'];
    define(deps, function ($) {
        var setting = {
            view: {
                showLine: false,
                showIcon: false,
                addDiyDom: addDiyDom
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey:'id',
                    pIdKey:'parentId'
                }
            }
        };

        var keys = [];
        var optionHander = undefined;

        /**
         * 自定义DOM节点
         */
        function addDiyDom(treeId, treeNode) {
            var spaceWidth = 15;
            var liObj = $("#" + treeNode.tId);
            var aObj = $("#" + treeNode.tId + "_a");//后面需要构建的行
            var switchObj = $("#" + treeNode.tId + "_switch");//加号或减号
            var icoObj = $("#" + treeNode.tId + "_ico");//文件夹图标
            var spanObj = $("#" + treeNode.tId + "_span");//显示文字
            aObj.attr('title', '');


            switchObj.remove();
            spanObj.remove();
            icoObj.remove();


            var size = keys.length + 1;
            if ($.isFunction(optionHander)) {
                size = size + 1;
            }
            var w = 100.0 / size;

            aObj.append('<div class="cell swich" style="width: ' + w + '%;"></div>');
            var div = $(liObj).find('div').eq(0);
            var spaceStr = "<span style='height:1px;display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
            div.append(spaceStr);
            div.append(switchObj);
            div.append(spanObj);
            // switchObj.before(spaceStr);

            // var editStr = '';
            // editStr += '<div class="cell">' + (treeNode.CONTACT_USER == null ? '&nbsp;' : treeNode.CONTACT_USER) + '</div>';
            // var corpCat = '<div title="' + treeNode.CORP_CAT + '">' + treeNode.CORP_CAT + '</div>';
            // editStr += '<div class="cell">' + (treeNode.CORP_CAT == '-' ? '&nbsp;' : corpCat) + '</div>';
            // editStr += '<div class="cell">' + (treeNode.CONTACT_PHONE == null ? '&nbsp;' : treeNode.CONTACT_PHONE) + '</div>';
            // editStr += '<div class="cell">' + formatHandle(treeNode) + '</div>';
            // editStr += '<div class="cell">' + formatHandle(treeNode) + '</div>';


            var editStr = [];
            $.each(keys, function (idx, item) {
                var value = treeNode[item];
                if (!value) {
                    value = "&nbsp;";
                }
                editStr.push('<div class="cell" style="width: ' + w + '%;">' + value + '</div>');
            });
            if ($.isFunction(optionHander)) {
                editStr.push('<div class="cell" style="width: ' + w + '%;">' + optionHander(treeNode) + '</div>')
            }
            aObj.append(editStr.join(''));
        }

        function initTree(selector,treeData) {
            //初始化树
            $.fn.zTree.init($(selector), setting, treeData);
        }

        function createTableHeader(headers) {
            var headerHTML = [];
            var size = headers.length;
            if ($.isFunction(optionHander)) {
                size = size + 1;
            }
            var w = 100.0 / size;
            $.each(headers, function (idx, item) {
                headerHTML.push('<div class="cell" style="width: ' + w + '%">' + item + '</div>');
            });
            if ($.isFunction(optionHander)) {
                headerHTML.push('<div class="cell" style="width: ' + w + '%">操作</div>');
            }
            var li_head = ' <li class="head"><a>' + headerHTML.join('') + '</a></li>';
            var rows = $("#dataTree").find('li');
            if (rows.length > 0) {
                rows.eq(0).before(li_head)
            } else {
                $("#dataTree").append(li_head);
                $("#dataTree").append('<li ><div style="text-align: center;line-height: 30px;" >无符合条件数据</div></li>')
            }
        }


        return {
            table: function (selector,headers, dataKey, treedata, optionHander1) {
                keys = dataKey;
                optionHander = optionHander1;
                initTree(selector,treedata);
                createTableHeader(headers);
            }
        }

    });
})();