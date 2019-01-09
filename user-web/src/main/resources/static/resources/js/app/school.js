(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        // 'text!tmpl/-tmpl.html',
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
    define(models, function ($, ajax, user, dot, dialog, menu, UI) {

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


        // var url = 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546949097395&di=74b5858ffe93ecb7bd420b58c0895b65&imgtype=0&src=http%3A%2F%2Fwww.pptbz.com%2Fpptpic%2FUploadFiles_6909%2F201211%2F2012111719294197.jpg';
        // url = 'http://127.0.0.1:8006/user/resources/images/user.png'
        // url = 'http://192.168.1.230/zimg/2c158e322ab6310ce9d57b85d120d576'
        // window.cc=new Image();
        // cc.src=url;
        // cc.onload=function () {
        //     console.log(arguments)
        //     console.log(cc.length)
        //     console.log(cc)
        // }
        //


        function testNetspeed() {
            // var url='http://192.168.1.230/zimg/2c158e322ab6310ce9d57b85d120d576';
            // var url='http://192.168.1.230/abc';
            var url = 'http://192.168.1.252/index.html';
            url = 'http://192.168.1.252:81/easydata/sys/reload';
            url = 'school/test';
            var timestamp1 = (new Date()).valueOf();
            // ajax.corsGetJson(url).then(function () {
            //     console.log(arguments[2]);
            //     console.log(arguments[2].getAllResponseHeaders());
            //     console.log(arguments[2].getResponseHeader('content-length'));
            //     var timestamp2 = (new Date()).valueOf();
            //     console.log(timestamp2 - timestamp1);
            // });
            ajax.getJson(url).then(function () {
                console.log(arguments[2]);
                console.log(arguments[2].getAllResponseHeaders());
                console.log(arguments[2].getResponseHeader('content-length'));
                var timestamp2 = (new Date()).valueOf();
                console.log(timestamp2 - timestamp1);
            });
        }

        testNetspeed();


        // var xmlhttp;
        // xmlhttp=new XMLHttpRequest();
        // xmlhttp.open("GET",url,true);
        // xmlhttp.responseType = "blob";
        // xmlhttp.onload = function(){
        //     console.log(this);
        //     if (this.status == 200) {
        //         var blob = this.response;
        //         var img = document.createElement("img");
        //         img.onload = function(e) {
        //             window.URL.revokeObjectURL(img.src);
        //         };
        //         img.src = window.URL.createObjectURL(blob);
        //         document.body.appendChild(img);
        //     }
        // }
        // xmlhttp.send();

        return {
            render: function () {
                user.userInfo('school');

                $('main').show();


            }
        }
    });
})();