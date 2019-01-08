(function () {
    "use strict";
    var dps = ['jquery', 'ajax'];
    define(dps, function ($, $ajax) {

        var Process = function (_opts) {
            var opts = {
                url: '',
                onlyKey: '',
                timestamp: 0,
                target: '',
                debug: true,
                onlyShowErrorMsg: false,
                completed: 0,
                finishedCallBack: undefined,
                callBack: undefined,
                cors: false
            };
            $.extend(opts, _opts);
            if (opts.target.has('.progress').size()) {
                return;
            }

            function createUI() {
                var template = '<div class="progress progress2" style="width:100%;">\
	    										<div class="progress-bar progress-bar2">0%</div>	\
										    	</div>\
										    	<div class="mytext"></div>';

                var height = opts.target.height();
                if (height - 50 > 50) {
                    var tmpHeight = height - 50;
                    template += '<div class="myDetailMsg" style="height: ' + tmpHeight + 'px;overflow: scroll"></div>';
                } else {
                    template += '<div class="myDetailMsg" style="display:none;overflow: scroll"></div>';
                }

                opts.target.append(template);
            }

            function getUrl() {
                if (opts.debug) {
                    return opts.url + "?timestamp=" + opts.timestamp + '&onlyKey=' + opts.onlyKey;
                } else {
                    return opts.url;
                }
            }

            function start() {
                var url = getUrl();
                if (opts.cors) {
                    $ajax.corsGetJson(url).then(function (dataset) {
                        var progresses = dataset.progresses;
                        var isOver = dataset.isOver;
                        process(progresses, isOver);
                    }, function () {
                        start();
                    });
                } else {
                    $ajax.getJson(url).then(function (dataset) {
                        var progresses = dataset.progresses;
                        var isOver = dataset.isOver;
                        process(progresses, isOver);
                    }, function () {
                        start();
                    });
                }
            }

            function process(progresses, isOver) {
                var size = progresses.length;
                if (size == 0) {
                    start();
                    return;
                }

                var $target = opts.target;
                var result = undefined;
                $.each(progresses, function (idx, progress) {
                    if ($('#' + progress.id).size() > 0) {
                        return;
                    }


                    var date = new Date(parseInt(progress.time));
                    var msgHTML = new Array();
                    msgHTML.push('<span id="');
                    msgHTML.push(progress.id);
                    if (progress.msgType === 1) {
                        msgHTML.push('" style="display:block;color: red;">');
                        msgHTML.push('错误:');
                    } else if (progress.msgType === 2) {
                        msgHTML.push('" style="display:block;color: b7b732;">');
                        msgHTML.push('警告:');
                    } else if (progress.msgType === 3) {
                        msgHTML.push('" style="display:block;">');
                        msgHTML.push('消息:');
                    }
                    msgHTML.push(date.format("yyyy-MM-dd hh:mm:ss"));
                    msgHTML.push('=====');
                    msgHTML.push(progress.message);
                    msgHTML.push('</span>');

                    //onlyShowErrorMsg为fase显示信息，当onlyShowErrorMsg为true并且有错误信息才能显示
                    if (!opts.onlyShowErrorMsg || (opts.onlyShowErrorMsg && progress.msgType === 1)) {
                        if (progress.message && progress.message.length != 0) {
                            $target.find('.myDetailMsg').append(msgHTML.join(''));
                            var scrollHeight = $target.find('.myDetailMsg').get(0).scrollHeight;
                            $target.find('.myDetailMsg').scrollTop(scrollHeight);
                        }
                    }

                    if (idx === size - 1) {
                        var percent = progress.percent + "%";
                        var text = progress.message;
                        opts.completed = progress.completeNum;
                        opts.timestamp = progress.timestamp;
                        $target.find('.progress-bar').css('width', percent).text(percent);
                        $target.find('.mytext').text(text);
                        if ($.isFunction(opts.callBack)) {
                            opts.callBack(progress);
                        }
                    }
                });
                if (isOver) {
                    if ($.isFunction(opts.finishedCallBack)) {
                        opts.finishedCallBack();
                    }
                } else {
                    start();
                }
            }

            createUI();
            start();
        }

        $.fn.extend({
            'myprogress': function (_opts) {
                var opts = {
                    url: '',
                    onlyKey: '',
                    target: $(this)
                };
                if ($.isPlainObject(_opts)) {
                    $.extend(opts, _opts);
                } else {
                    opts.url = _opts;
                    opts.target = $(this);
                }
                new Process(opts);
            }
        });
    });
})();