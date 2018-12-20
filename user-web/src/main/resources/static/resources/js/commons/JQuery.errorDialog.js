(function () {
    "use strict";
    var deps = ['jquery', 'dialog'];
    define(deps, function ($, dialog) {


        $.extend({
            dialog: function (data) {
                var contentType = data.getResponseHeader('Content-Type');
                var responseText = data.responseText;
                var errorText = "";
                if (contentType.indexOf('text/html') !== -1) {
                    errorText = '系统内部错误，联系管理员>><a id="showDetailError" style="cursor: pointer;">查看详情</a>';
                } else {
                    var obj = $.parseJSON(responseText);
                    errorText = obj.status.code + '>><a id="showDetailError" style="cursor: pointer;">查看详情</a>';
                    responseText = obj.status.detail;
                }
                var $dialog = dialog.alter(errorText, '关闭');
                $dialog.find('#showDetailError').click(function () {
                    dialog.modal({body:'<div style="height: 300px;width:100%;overflow: auto;">'+responseText+'</div>'});
                });
            }
        });// $.fn.extend

    });// define(deps,function($)
})();