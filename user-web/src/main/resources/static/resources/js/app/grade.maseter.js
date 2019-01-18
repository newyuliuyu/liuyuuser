(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'text!tmpl/grademaseter-tmpl.html',
        'js/app/selecte.teacher',
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
    define(models, function ($, ajax, user, dot, dialog, menu, UI, tmpl,
                             selecteTeacherDialog) {

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


        var role = {};
        return {
            render: function () {
            },
            init: function () {
                var $role = $('.layout-left label.active');
                role = {};
                role.id = $role.data('roleId');
                role.name = $role.data('roleName');

            }
        }
    });
})();