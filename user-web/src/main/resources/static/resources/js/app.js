function getUrlFileName() {
    var pathname = window.location.pathname;
    if (pathname == '' || pathname == '/')
        return 'index';
    var pathArr = pathname.split("/");
    var fileName = (pathArr[pathArr.length - 1] == '') ? 'index' : pathArr[pathArr.length - 1].split(".")[0];
    return fileName;
}

function getBrowser() {
    var browser = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    // 浏览器
    (s = ua.match(/msie ([\d.]+)/)) ? browser.ie = s[1] : (s = ua.match(/firefox\/([\d.]+)/)) ? browser.firefox = s[1] : (s = ua.match(/chrome\/([\d.]+)/)) ? browser.chrome = s[1] : (s = ua
        .match(/opera.([\d.]+)/)) ? browser.opera = s[1] : (s = ua.match(/version\/([\d.]+).*safari/)) ? browser.safari = s[1] : 0;
    // 移动设备
    (s = ua.match(/ipad/i)) ? browser.ipad = true : (s = ua.match(/iphone os/i)) ? browser.iphone = true : (s = ua.match(/midp/i)) ? browser.midp = strue : (s = ua.match(/android/i)) ?
        browser.android = true :
        (s = ua.match(/windows ce/i)) ? browser.windowsCE = true : (s = ua.match(/windows mobile/i)) ? browser.windowsMobile = true : 0;
    browser.isMobile = function () {
        return browser.ipad || browser.iphone || browser.midp || browser.android || browser.windowCE || browser.windowsMobile;
    };
    return browser;
}

if (!window.app) {
    var includeRootPath = document.body.getAttribute('rootPath');
    if (includeRootPath != null) {
        window.app = {
            rootPath: includeRootPath
        };
    }
    // 如果没有设置 window.app
    var includeEntry = document.body.getAttribute('entry');
    if (includeEntry != null) {
        // 如果body内有设置entry属性
        window.app.entry = includeEntry;
    } else {
        // 如果两个都没有设置则自动加载与网址后缀名相同的js
        window.app.entry = getUrlFileName();
    }
    if (!window.app.jsMehtod) {
        window.app.jsMehtod = 'render';
    }
}

function getBrowser() {
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua.match(/chrome\/([\d.]+)/)) ?
        Sys.chrome = s[1] :
        (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
    return Sys;
}

var jqueryURL = 'js/lib/jquery-2.1.1';
var b = getBrowser();
if (b.ie) {
    var v = parseInt(b.ie);
    if (v < 9) {
        jqueryURL = 'js/lib/jquery1.11.1';
    }
}

var config = {
    urlArgs: 'v=1.0',
    contextPath: window.app.rootPath,
    baseUrl: window.app.rootPath + 'resources/',
    config: {},
    optimize: 'none',
    paths: {
        'jquery': jqueryURL,
        'Popper': 'js/lib/popper.1.14.3',
        'bootstrap': 'js/lib/bootstrap/bootstrap',
        'webuploader': 'js/lib/webuploader',
        'css': 'js/lib/css0.1.10',
        'text': 'js/lib/text2.0.15',
        'utils': 'js/utils/utils',
        'vue': 'js/lib/vue',
        'pin': 'js/lib/jquery.pin',
        'dot': 'js/lib/doT2.0.0',
        'ezconfig': 'js/config/ezconfig',
        'ajax': 'js/commons/ajax',
        'dialog': 'js/commons/dialog',
        'ckeditor': 'js/lib/ckeditor/ckeditor',
        'loading': 'js/commons/JQuery.loading',
        'icheck': 'js/lib/icheck.min',
        'pager': 'js/lib/jquery.pager',
        'ztree': 'js/lib/jquery.ztree.all',
        'datetimepicker': 'js/lib/bootstrap-datetimepicker',
        'copyutils': 'js/lib/clipboard.min',
        'TableFreeze': 'js/lib/TableFreeze',
        'migrate': 'js/lib/jquery-migrate-1.2.1',
        'pace': 'js/lib/pace'

    },
    map: {
        '*': {
            'css': 'css',
            'text': 'text'
        }
    },
    shim: {
        'bootstrap': {
            deps: ['jquery', 'Popper', 'css!style/bootstrap/bootstrap.min']
        },
        'pin': {
            deps: ['jquery', 'css!style/pin']
        },
        'ckeditor': {
            deps: ['jquery']
        },
        'icheck': {
            deps: ['jquery', 'css!style/icheck-blue']
        },
        'pager': {
            deps: ['jquery', 'css!style/page']
        },
        'ztree': {
            deps: ['jquery', 'css!style/zTreeStyle']
        },
        'datetimepicker': {
            deps: ['bootstrap', 'css!style/bootstrap-datetimepicker']
        },
        'TableFreeze': {
            deps: ['jquery', 'migrate']
        },
        'pace': {
            deps: ['css!style/pace/pace-theme-minimal']
        }
    }
};

requirejs.config(config);
// 'bootstrap', 'dot', 'bootstrapselect', 'datetimepicker', 'css!styles/bootstrap/bootstrap.min', 'css!styles/public', 'css!styles/style',
// 'css!styles/index',
// 'css!styles/report'
var globalModel = ['jquery', 'utils', 'pace'];
require(globalModel, function ($) {

    Pace.start();

    var p = ['js/app/' + window.app.entry];
    if (!window.JSON) { // 如果浏览器不支持JSON，使用JSON2
        p[1] = 'util/json3';
    }

    var $include = $('include');
    var size = $include.size();

    var b = p.length;
    for (var i = 0; i < size; i++) {
        p[b + i] = 'text!' + $($include[i]).attr('src');
    }

    require(p, function () {
        try {
            for (var i = 0; i < size; i++) {
                $($include[i]).append(arguments[b + i]);
            }
        } catch (e) {

        }

        var module = arguments[0];
        if (module) {
            if (window.app.method && window.app.method != '') {
                module[window.app.method]();
            } else {
                module.render();
            }
        }

    });
});