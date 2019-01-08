(function () {
    var moudles = ['jquery', 'webuploader', 'dialog', 'dot', 'pager'];
    define(moudles, function ($, WebUploader, dialog, dot) {
        function initUploadFile(opts) {
            // opts.id
            // opts.extensions
            // opts.progressBar
            // opts.callback
            // opts.multiple

            var url = window.app.rootPath + "upload";
            var flashURL = window.app.rootPath + 'Uploader.swf';
            var uploader = WebUploader.create({
                // 选完文件后，是否自动上传。
                auto: true,
                // swf文件路径
                swf: flashURL,
                // 文件接收服务端。
                server: url,
                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: {id: opts.id, multiple: opts.multiple || false},
                fileVal: 'liuyufile',
                // 只允许选择图片文件。
                accept: {
                    title: 'Applications',
                    extensions: opts.extensions || '*',
                    mimeTypes: '*/*'
                }
            });


            uploader.on('uploadProgress', function () {
                var file = arguments[0];
                var percent = (arguments[1] * 100).toFixed2(0);
                if (percent === 100) {
                    percent = 99;
                }
                $('#' + file.id + ' .progress-bar').css('width', percent + '%').text(percent + '%');
            });

            uploader.on('error', function () {
                var file = arguments[1];
                var text = file.name + "文件格式不对";
                if ('F_DUPLICATE' === arguments[0]) {
                    text = file.name + "已经存在"
                }
                dialog.alter(text, "关闭");
            });
            uploader.on('fileQueued', function () {
                var mydataset = {
                    'file': arguments[0]
                };
                var templateText = '<div id="{{=it.file.id}}"  style="padding: 5px 5px;">\
                       <span title="{{=it.file.name}}" class="textTitle">{{=it.file.name}}</span>\
                       <div class="progress progress2">\
                           <div class="progress-bar progress-bar2 bar-unOver">0%</div>\
                       </div>\
                       <span class="removeFile  icon-remove"></span>\
                    </div>';
                var arrText = dot.template(templateText);
                var html = arrText(mydataset);
                $(opts.progressBar).append(html);
            });
            $(opts.progressBar).on('click', '.removeFile', function () {
                var $parent = $(this).parent();
                $parent.remove();
                uploader.removeFile($parent.attr('id'), true);
            });
            var hasError=false;
            uploader.on('uploadSuccess', function () {
                var file = arguments[0];
                var dataset = arguments[1];
                if (dataset.status && dataset.status.status && !dataset.status.status) {
                    var msg = dataset.status.detail;
                    hasError = true;
                    dialog.alter(msg, "关闭");
                    return;
                }
                $.each(dataset.files, function (idx, item) {
                    $('#' + file.id).attr('old', item.old);
                    $('#' + file.id).attr('new', item['new']);
                });
            });
            uploader.on('uploadError', function () {
                // console.log('uploadError', arguments)
            });
            uploader.on('uploadComplete', function () {
                var file = arguments[0];
                $('#' + file.id + ' .progress-bar').css('width', '100%').text('100%').addClass('bar-over').removeClass('bar-unOver');
                if (!hasError) {
                    if ($.isFunction(opts.callback)) {
                        opts.callback();
                    }
                }
            });
        }


        var ui = {
            uploadFileBtn: initUploadFile,
            pager: function () {
                var p = function () {
                    var pagerEl = undefined;
                    var callback = function () {
                    };
                    var pagenation = {
                        pageSize: 10,
                        pageCount: 1,
                        pageNum: 1,
                        concreator: this
                    };

                    this.init = function () {
                        pagenation.pageNum = 1;
                        pagenation.pageCount = 1;
                        return pagenation;
                    };

                    this.create = function () {// arg1 pagerId,arg2 callback
                        //console.log(arguments)
                        if (arguments.length > 0) {
                            for (var i = 0; i < arguments.length; i++) {
                                if (typeof arguments[i] === 'string') {
                                    pagerEl = $('#' + arguments[i]);
                                    continue;
                                }

                                if (typeof arguments[i] === 'function') {
                                    callback = arguments[i];
                                    continue;
                                }
                            }
                        }

                        if (!pagerEl.size())
                            return undefined;

                        if (pagerEl.find("#pageSize").size() > 0) {
                            pagenation.pageSize = parseInt(pagerEl.find("#pageSize").val());
                        }

                        if (pagerEl.find("#pageNum").size() > 0) {
                            pagenation.pageNum = parseInt(pagerEl.find("#pageNum").val());
                        }

                        if (pagerEl.find("#pageCount").size() > 0) {
                            pagenation.pageCount = parseInt(pagerEl.find("#pageCount").val());
                        }
                        if (pagerEl.find("#totalRows").size() > 0) {
                            pagenation.totalRows = parseInt(pagerEl.find("#totalRows").val());
                        }


                        pagerEl.pager({
                            pageNum: pagenation.pageNum,
                            pageCount: pagenation.pageCount,
                            click: function (pageNum, pageCount) {
                                pagenation.pageNum = pageNum;
                                callback.call(this, {
                                    pageSize: pagenation.pageSize,
                                    pageNum: pagenation.pageNum
                                });
                            }
                        });
                        if (pagenation.pageCount < 2) {
                            pagerEl.hide();
                        }
                        return pagenation;
                    };

                    this.redraw = function (pagerHtml) {
                        var pagerOuter = pagerEl.parent().parent();
                        pagerOuter.find('>div.pager-container').remove();
                        pagerOuter.append(pagerHtml.find('div.pager-container'));
                        return this.create(pagerOuter.find('div.pager').attr('id'));
                    };
                };

                return new p();
            }

        };

        return ui;
    });
})();
