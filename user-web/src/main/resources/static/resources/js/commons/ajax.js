(function () {
    "use strict";
    define(['jquery'], function ($) {
        var Method = {
            GET: 'GET',
            POST: 'POST',
            PUT: 'PUT',
            DELETE: 'DELETE',
            OPTIONS: 'OPTIONS'
        };
        var DataType = {
            JSON: 'json',
            HTML: 'html',
            TEXT: 'text'
        };

        // complete:function(){}
        // success:function(){}
        // error:function(){}
        // beforeSend:function(){}
        var ajaxSetting = {
            url: "",
            data: {},
            type: Method.GET,
            contentType: 'application/json',
            dataType: DataType.JSON,
            timeOut: 3000
        };

        function correctUrl(settings) {
            var _url = "";
            if (!settings.url.startsWith('http')) {
                if (settings.url.substring(0, 1) === '/') {
                    var length = settings.url.length;
                    _url = window.app.rootPath + settings.url.substring(1, length);
                } else {
                    _url = window.app.rootPath + settings.url;
                }
            } else {
                _url = settings.url;
            }
            settings.url = encodeURI(_url);
        }

        function processSendData(settings) {
            if (!$.isEmptyObject(settings['data'])) {
                if (settings.method !== Method.GET && settings['contentType'] !== 'application/x-www-form-urlencoded') {
                    settings['data'] = JSON.stringify(settings.data);
                }
            }
        }

        function myAjax(opts) {
            var settings = {};
            $.extend(true, settings, ajaxSetting, opts);
            correctUrl(settings);
            processSendData(settings);
            return $.ajax(settings);
        }

        var o = {
            ajax: function (opts) {
                return myAjax(opts);
            },
            url: function (url) {
                return myAjax({
                    'url': url
                });
            },
            getHTML: function (url, timeout) {
                var param = {
                    'url': url,
                    'dataType': DataType.HTML
                };
                if (timeout) {
                    param.timeout = timeout;
                }
                return myAjax(param);
            },
            getJson: function (url, timeout) {
                var param = {
                    'url': url
                };
                if (timeout) {
                    param.timeout = timeout;
                }
                return myAjax(param);
            },
            postJson: function (url, dataset) {
                return myAjax({
                    'url': url,
                    'data': dataset,
                    type: Method.POST
                });
            },
            postJsonForm: function (url, dataset) {
                return myAjax({
                    'url': url,
                    'data': dataset,
                    'contentType':'application/x-www-form-urlencoded',
                    type: Method.POST
                });
            },
            corsPostJson: function (url, dataset) {
                return myAjax({
                    'url': url,
                    'data': dataset,
                    crossDomain: true,
                    xhrFields: {
                        withCredentials: true
                    },
                    type: Method.POST
                });
            },
            corsGetJson: function (url, timeout) {
                var param = {
                    'url': url,
                    crossDomain: true,
                    xhrFields: {
                        withCredentials: true
                    }
                };
                if (timeout) {
                    param.timeout = timeout;
                }
                return myAjax(param);
            },
        }
        return o;
    });
})();