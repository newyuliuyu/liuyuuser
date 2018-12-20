(function () {
    "use strict";
    define(['jquery', 'js/commons/drag'], function ($) {
            window.zindex = 9999; // 最高层级
            window.idialog = 0; // 记录有一个非模态框

            function createDialog(settings, modalize) {
                var $dialogBox = $('<div class="dialog-box"></div>');
                $dialogBox.html('<div class="dialog">\
								<div class="dialog-content">\
									<div class="dialog-body"></div>\
								</div>\
							</div>');
                $dialogBox.css('z-index', ++window.zindex);

                var $dialog = $dialogBox.find('.dialog');
                if ($.isPlainObject(settings.size)) {
                    $dialog.css('width', settings.size.w + "px");
                    $dialog.find('.dialog-body').css({'max-height': settings.size.h + "px", "overflow-x": "auto"})
                } else {
                    $dialog.addClass('dialog-' + settings.size);
                    var h = window.getClientHeight() - 200;
                    $dialog.find('.dialog-body').css({'max-height': h + "px", "overflow": "auto"})
                }


                if ($.isPlainObject(settings.dialogStyle)) {
                    $dialog.css(settings.dialogStyle);
                }
                if ($.isPlainObject(settings.dialogBodyStyle)) {
                    $dialog.find('.dialog-body').css(settings.dialogBodyStyle);
                }


                $dialog.find('.dialog-body').html(settings.body);
                if (settings.header.show === true) {
                    var $header = $('<div class="dialog-header" style="height: 50px;">\
							        <button type="button" class="close" style="font-size: 30px;">&times;</button>\
							        <h4 class="dialog-title">' + settings.header.text
                        + '</h4>\
								</div>');
                    $header.prependTo($dialogBox.find('.dialog-content'));
                }

                if (settings.footer.show === true) {
                    var $footer = $('<div class="dialog-footer" style="height: 50px;padding: 5px;"></div>');
                    for (var i = 0; i < settings.footer.buttons.length; i++) {
                        var $btn = $('<button class="btn"></button>');
                        $btn.attr('type', settings.footer.buttons[i].type).addClass(settings.footer.buttons[i].clazz).html(settings.footer.buttons[i].text);
                        $btn.on('click', settings.footer.buttons[i].callback);
                        $btn.on('close', function () {
                            removemodal();
                        });
                        $btn.appendTo($footer);
                    }
                    ;

                    $footer.appendTo($dialog.find('.dialog-content'));
                }

                if (settings.untransparent === false) {
                    $dialog.addClass('trsp');
                }

                $dialogBox.appendTo($(document.body));

                var $target = settings.target;
                var left, right, top, bottom;
                if ($target[0].nodeName == 'body' || $target[0].nodeName == 'BODY') {
                    left = right = top = bottom = 0;
                } else {
                    left = getLeftOf($target[0]);
                    right = getClientWidth() - (left + $target.width());
                    top = getTopOf($target[0]);
                    bottom = getClientHeight() - $target.height() - top;
                }

                if (modalize) {
                    $dialogBox.addClass('modal-box');
                    $dialogBox.css({
                        left: left,
                        right: right,
                        top: top,
                        bottom: bottom
                    });

                    $dialog.css({
                        top: 40,
                        left: (settings.target[0].offsetWidth - $dialog[0].offsetWidth) / 2
                    });

                    if (settings.backdrop === true) {
                        var $oBackdrop = $('<div class="dialog-backdrop"></div>');
                        $oBackdrop.css({
                            left: left,
                            right: right,
                            top: top,
                            bottom: bottom
                        });
                        $oBackdrop.prependTo($dialogBox);
                    }

                } else {
                    $dialogBox.addClass('nonmodal-box');
                    $dialogBox.css({
                        top: getTopOf(settings.target[0]) + getScrollerTop() + 40,
                        left: getLeftOf(settings.target[0]) + (settings.target[0].offsetWidth - $dialog[0].offsetWidth) / 2
                    });

                }

                if (settings.moveable === true) {
                    //drag($header[0]);
                    $header.mousedown(function (e) {
                        $dialog.css('z-index', ++window.zindex);

                        var disX = e.clientX - $dialog[0].offsetLeft;
                        var disY = e.clientY - $dialog[0].offsetTop;
                        var that = $dialog[0];

                        document.onmousemove = function (e) {
                            var e = e || window.event;
                            var L = e.clientX - disX;
                            var T = e.clientY - disY;
                            L = L < 0 ? 0 : L;
                            T = T < 0 ? 0 : T;
                            if (modalize) {
                                var iMaxL = $dialogBox[0].offsetWidth - $dialog[0].offsetWidth;
                                var iMaxT = $dialogBox[0].offsetHeight - $dialog[0].offsetHeight;
                            } else {
                                var iMaxL = getClientWidth - that.offsetWidth;
                                var iMaxT = getClientHeight() - that.offsetHeight;
                            }

                            L = L > iMaxL ? iMaxL : L;

                            T = T > iMaxT ? iMaxT : T;
                            that.style.left = L + 'px';
                            that.style.top = T + 'px';
                        }

                        document.onmouseup = function () {
                            $header.css({
                                'cursor': 'default'
                            });
                            document.onmousemove = document.onmouseup = null;
                        }
                    });
                }

                $dialog.find('.close').on('click', function () {
                    removemodal();
                });

                function removemodal() {
                    $dialog.animate({
                        opacity: 0,
                        top: '-=12px'
                    }, 200, function () {

                        if (settings.backdrop === false) {
                            $dialogBox.remove();
                        } else {
                            $oBackdrop.animate({
                                opacity: 0
                            }, 200, function () {
                                $dialogBox.remove();
                            });
                        }
                    });
                }

                $dialogBox.show = function () {
                    if (settings.backdrop) {
                        $dialogBox.find('.dialog-backdrop').animate({
                            opacity: 0.5
                        }, 200, function () {
                            $dialog.animate({
                                opacity: 1,
                                top: '+=12px'
                            }, 300, function () {
                                settings.shown && settings.shown();
                            });
                        });
                    } else {

                        $dialog.animate({
                            opacity: 1,
                            top: '+=12px'
                        }, 300, function () {
                            settings.shown && settings.shown();
                        });
                    }
                    return this;
                };

                $dialogBox.close = function () {
                    removemodal();
                };

                return $dialogBox;
            }

            var dialog = {
                fadedialog: function (opts) {
                    var settings = {};
                    var baseSettings = {
                        moveable: false,
                        size: 'sm',
                        backdrop: false
                    };
                    var defaultSettings = {
                        speed: 300,
                        delay: 1000,
                        header: {
                            show: false
                        },
                        footer: {
                            show: false,
                            buttons: []
                        },
                        tipText: '这是一个提示',
                        iconInfo: 'ing',
                        target: $('body')
                    };

                    $.extend(settings, defaultSettings, opts, baseSettings);

                    var $dialogBox = createDialog(settings, false);

                    $dialogBox.find('.dialog-body').html('<p class="tipcont"><span class="tip-icon"></span>&nbsp;&nbsp;&nbsp;<span class="tip-text">' + settings.tipText + '</span></p>');
                    $dialogBox.find('.tipcont').addClass('update-' + settings.iconInfo);

                    $dialogBox.show().delay(settings.delay).animate({
                        opacity: 0,
                        top: '-=7px'
                    }, settings.speed, function () {
                        $(this).remove();
                        if (opts.callback) {
                            opts.callback();
                        }
                    });
                },
                fadedialog2: function (opts) {
                    var settings = {};
                    var baseSettings = {
                        moveable: false,
                        size: 'sm',
                        backdrop: false
                    };
                    var defaultSettings = {
                        speed: 300,
                        delay: 1000,
                        header: {
                            show: false
                        },
                        footer: {
                            show: false,
                            buttons: []
                        },
                        tipText: '这是一个提示',
                        iconInfo: 'ing',
                        target: $('body')
                    };

                    $.extend(settings, defaultSettings, opts, baseSettings);

                    var $dialogBox = createDialog(settings, true);

                    $dialogBox.find('.dialog-body').html('<p class="tipcont"><span class="tip-icon"></span>&nbsp;&nbsp;&nbsp;<span class="tip-text">' + settings.tipText + '</span></p>');
                    $dialogBox.find('.tipcont').addClass('update-' + settings.iconInfo);

                    $dialogBox.show();
                    return $dialogBox;
                },
                tipmodal: function (opts) {
                    var settings = {};
                    var defaultSettings = {
                        target: $(document.body),
                        backdrop: true,
                        header: {
                            show: true,
                            text: "提示"
                        },
                        footer: {
                            show: true,
                            buttons: [{
                                type: 'submit',
                                text: "确定",
                                clazz: 'btn-primary',
                                callback: function () {
                                }
                            }, {
                                type: 'button',
                                text: "取消",
                                clazz: 'btn-default',
                                callback: function () {
                                    $(this).trigger('close');
                                }
                            }]
                        },
                        tipText: '这是一个提示',
                        iconInfo: 'update-ing',
                        moveable: false,
                        untransparent: true,
                        tipsType: 'msgif'
                    };

                    $.extend(settings, defaultSettings, opts, {
                        size: 'sm'
                    });

                    var $modalBox = createDialog(settings, true);
                    var $oModal = $modalBox.find('.dialog');
                    // 显示进度条
                    if (settings.tipsType == 'progressbar') {
                        var processbar = function ($source) {
                            var bar = $('<div class="progress-warp"><table class="table progress" ><tr><td style="width:0px;">&nbsp;</td><td style="width:0px;text-align: left; color: #D9534F; background: transparent;"></td></tr></table></div>');

                            function process(w) {
                                bar.find('td:eq(0)').css({
                                    width: w + "%"
                                });
                                bar.find('td:eq(1)').css({
                                    width: (100 - w) + "%"
                                });// .text(w+'%');
                                setTimeout(function () {
                                    var newW = w + 10;
                                    if (newW * 1 < 100) {
                                        process(newW);
                                    } else {
                                        bar.find('td:eq(0)').css({
                                            width: "100%"
                                        });// .text('100%');
                                        bar.find('td:eq(1)').css({
                                            width: "0",
                                            display: 'none'
                                        });
                                        setTimeout(function () {
                                            $source.hide(function () {
                                                $(this).remove();
                                            });
                                        }, 100);
                                    }
                                }, 100);
                            };
                            process(10);
                            return bar;
                        }($modalBox);
                        $modalBox.find('.dialog-body').html(processbar);
                        $modalBox['complete'] = function () {

                        };
                    } else {
                        $modalBox.find('.dialog-body').html('<p class="tipcont"><span class="tip-icon"></span>&nbsp;&nbsp;&nbsp;<span class="tip-text">' + settings.tipText + '</span></p>');
                        $modalBox['complete'] = function () {

                            $modalBox.fadeOut(function () {
                                $(this).remove();
                            });
                        };
                    }
                    $modalBox.find('.tipcont').addClass('update-' + settings.iconInfo);

                    $modalBox.show();

                    return $modalBox;
                },
                confirm: function (title, msg, callback1, btn1Text, callback2, btn2Text) {
                    return this.modal({
                        size: 'sm',
                        header: {
                            show: true,
                            text: title
                        },

                        canmove: true, // 是否拖动
                        iconInfo: 'warning',
                        body: msg,
                        footer: {
                            show: true,
                            buttons: [{
                                type: 'submit',
                                text: btn1Text || "确定",
                                clazz: 'btn-primary',
                                callback: function () {
                                    $(this).trigger('close');
                                    if (callback1) {
                                        callback1();
                                    }
                                }
                            }, {
                                type: 'button',
                                text: btn2Text || "取消",
                                clazz: 'btn-default',
                                callback: function () {
                                    $(this).trigger('close');
                                    if (callback2) {
                                        callback2();
                                    }
                                }
                            }]
                        }
                    });
                },
                guideModal: function (content, buttonText, callBack) {
                    var $dialog = this.modal({
                        size: 'sm',
                        body: content || '',
                        footer: {
                            show: true,
                            buttons: [{
                                type: 'button',
                                text: buttonText || "取消",
                                clazz: 'btn-default myButtonClose',
                                callback: function () {
                                    $(this).trigger('close');
                                    if (callBack) {
                                        callBack();
                                    }
                                }
                            }]
                        }
                    });
                    return $dialog;
                },
                alter: function (content, buttonText) {
                    return this.modal({
                        size: 'sm',
                        body: content || '',
                        footer: {
                            show: true,
                            buttons: [{
                                type: 'button',
                                text: buttonText || "确定",
                                clazz: 'btn-default myButtonClose',
                                callback: function () {
                                    $(this).trigger('close');
                                }
                            }]
                        }
                    });

                },
                modal: function (opts) {
                    var defaultSettings = {
                        size: 'lg',
                        target: $(document.body),
                        moveable: true,
                        backdrop: true,
                        header: {
                            show: true,
                            text: "提示"
                        },
                        footer: {
                            show: true,
                            buttons: [{
                                type: 'button',
                                text: "取消",
                                clazz: 'btn-default',
                                callback: function () {
                                    $(this).trigger('close');
                                }
                            }]
                        },
                        body: ''

                    };
                    var settings = {};
                    $.extend(true, settings, defaultSettings, opts);

                    var $modalBox = createDialog(settings, true);

                    $modalBox.show();
                    return $modalBox;
                },
                modalFull: function (opts) {
                    var w = window.getClientWidth() - 400;
                    var h = window.getClientHeight() - 200;
                    var defaultSettings = {
                        size: {w: w, h: h},
                        target: $(document.body),
                        moveable: true,
                        backdrop: true,
                        header: {
                            show: true,
                            text: "提示"
                        },
                        footer: {
                            show: true,
                            buttons: [{
                                type: 'button',
                                text: "取消",
                                clazz: 'btn-default',
                                callback: function () {
                                    $(this).trigger('close');
                                }
                            }]
                        },
                        body: ''

                    };
                    var settings = {};
                    $.extend(true, settings, defaultSettings, opts);

                    var $modalBox = createDialog(settings, true);

                    $modalBox.show();
                    return $modalBox;
                },
                nonmodal: function (opts) {
                    var defaultSettings = {
                        size: 'lg',
                        target: $('body'),
                        header: {
                            show: true,
                            text: "提示"
                        },
                        footer: {
                            show: true,
                            buttons: [{
                                type: 'submit',
                                text: "确定",
                                clazz: 'btn-primary',
                                callback: function () {
                                }
                            }, {
                                type: 'button',
                                text: "取消",
                                clazz: 'btn-default',
                                callback: function () {
                                    $(this).trigger('close');
                                }
                            }]
                        },
                        body: ''
                    };
                    var settings = {};
                    $.extend(true, settings, defaultSettings, opts, {
                        backdrop: false
                    });

                    var $oNondialogmodalBox = createDialog(settings, false);
                    $oNonmodalBox.show();
                    return $oNonmodalBox;
                }
            };

            return dialog;
        }
    );
})();