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


        function loadOrgData() {
            var url = 'org/user-org';
            ajax.getJson(url).then(function (data) {
                var org = data.org;
                $('.org-name-label').text(org.name);
                $('#curOrgCode').val(org.code);
                $('#curOrgDeep').val(org.deep);

            }).always(function () {
                $.processError(arguments);
            });
        }


        function changeOrg() {
            var url = 'user/res/query/org-menu';
            ajax.getJson(url).then(function (data) {
                var dataset = {};
                dataset.orgs = data.reses;
                var tmplate = getTemplate('#changeOrgDialogT');
                var arrText = dot.template(tmplate);
                var html = arrText(dataset);
                var myDialog = dialog.myModal({size: getSize(), body: html});
            }).always(function () {
                $.processError(arguments);
            });
        }

        function initEvent() {
            $('.change-org-btn').click(function () {
                changeOrg();
            });
        }

        return {
            render: function () {
                user.userInfo('teacher');
                loadOrgData();
                initEvent();
                $('main').show();
            }
        }
    });
})();