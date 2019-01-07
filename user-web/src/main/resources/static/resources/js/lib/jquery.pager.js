/*
 * jQuery pager plugin
 * Version 1.0 (12/22/2008)
 * @requires jQuery v1.2.6 or later
 *
 * Example at: http://jonpauldavies.github.com/JQuery/Pager/PagerDemo.html
 *
 * Copyright (c) 2008-2009 Jon Paul Davies
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Read the related blog post and contact the author at http://www.j-dee.com/2008/12/22/jquery-pager-plugin/
 *
 * This version is far from perfect and doesn't manage it's own state, therefore contributions are more than welcome!
 *
 * Usage: .pager({ pagenumber: 1, pagecount: 15, buttonClickCallback: PagerClickTest });
 *
 * Where pagenumber is the visible page number
 *       pagecount is the total number of pages to display
 *       buttonClickCallback is the method to fire when a pager button is clicked.
 *
 * buttonClickCallback signiture is PagerClickTest = function(pageclickednumber) 
 * Where pageclickednumber is the number of the page clicked in the control.
 *
 * The included Pager.CSS file is a dependancy but can obviously tweaked to your wishes
 * Tested in IE6 IE7 Firefox & Safari. Any browser strangeness, please report.
 */
(function ($) {
    $.fn.getCurPage = function () {
        var curPage = this.find(".pgCurrent").text();
        return curPage;
    };
    $.fn.clickCurPage = function () {
        var myId = $(this).attr("id");
        $(this).trigger(myId + "/curPage/click");
    };
    $.fn.clickNextPage = function () {
        var myId = $(this).attr("id");
        $(this).trigger(myId + "/nextPage/click");
    };
    $.fn.clickPreviousPage = function () {
        var myId = $(this).attr("id");
        $(this).trigger(myId + "/previousPage/click");
    };
    $.fn.pager = function (_opts) {

        var setting = {
            pageNum: 1,
            pageCount: 1,
            click: null
        };
        var self = $(this);


        var obj = null;
        $.extend(setting, _opts);

        var myId = $(this).attr("id");
        self.off(myId + "/curPage/click");
        self.on(myId + "/curPage/click", function () {
            if ($.isFunction(setting.click)) {
                setting.click(setting.pageNum, setting.pageCount);
            }
        });
        self.off(myId + "/nextPage/click");
        self.on(myId + "/nextPage/click", function () {
            if ($.isFunction(setting.click)) {
                var pageNum = setting.pageNum;
                if (setting.pageNum + 1 <= setting.pageCount) {
                    pageNum = setting.pageNum + 1;
                }
                setting.click(pageNum, setting.pageCount);
            }
        });
        self.off(myId + "/previousPage/click");
        self.on(myId + "/previousPage/click", function () {
            if ($.isFunction(setting.click)) {
                var pageNum = setting.pageNum;
                if (setting.pageNum - 1 > 0) {
                    pageNum = setting.pageNum - 1;
                }
                setting.click(pageNum, setting.pageCount);
            }
        });

        return this.each(function () {
            obj = this;
            renderpager();
        });

        function buttonClickCallback(pageNum) {
            setting.pageNum = parseInt(pageNum);
            renderpager();

            if ($.isFunction(setting.click)) {
                setting.click(pageNum, setting.pageCount);
            }
        }

        function renderpager() {

            var $pager = $('<ul class="pages"></ul>');
            $pager.append(renderButton("first", "首页")).append(
                renderButton("prev", "上一页"));

            var startPoint = 1;
            var endPoint = 9;

            if (setting.pageNum > 4) {
                startPoint = setting.pageNum - 4;
                endPoint = setting.pageNum + 4;
            }

            if (endPoint > setting.pageCount) {
                startPoint = setting.pageCount - 8;
                endPoint = setting.pageCount;
            }

            if (startPoint < 1) {
                startPoint = 1;
            }

            for (var page = startPoint; page <= endPoint; page++) {
                var currentButton = $('<li class="page-number">' + (page)
                    + '</li>');

                page == setting.pageNum ? currentButton.addClass('pgCurrent')
                    : currentButton.click(function () {
                        buttonClickCallback(this.firstChild.data);
                    });
                currentButton.appendTo($pager);
            }

            $pager.append(renderButton("next", "下一页")).append(
                renderButton("last", "末页"));

            // return $pager;
            $(obj).empty().append($pager);
            $('.pages li').mouseover(function () {
                $(".pages").css({cursor: "pointer"});
                //document.body.style.cursor = "pointer";
            }).mouseout(function () {
                $(".pages").css({cursor: "auto"});
                //document.body.style.cursor = "auto";
            });
        }

        function renderButton(labelId, labelText) {
            var $Button = $('<li class="pgNext">' + labelText + '</li>');
            var destPage = 1;
            // work out destination page for required button type
            switch (labelId) {
                case "first":
                    destPage = 1;
                    break;
                case "prev":
                    destPage = setting.pageNum - 1;
                    break;
                case "next":
                    destPage = setting.pageNum + 1;
                    break;
                case "last":
                    destPage = setting.pageCount;
                    break;
            }

            // disable and 'grey' out buttons if not needed.
            if (labelId == "first" || labelId == "prev") {
                setting.pageNum <= 1 ? $Button.addClass('pgEmpty') : $Button
                    .click(function () {
                        buttonClickCallback(destPage);
                    });
            } else {
                setting.pageNum >= setting.pageCount ? $Button
                    .addClass('pgEmpty') : $Button.click(function () {
                    buttonClickCallback(destPage);
                });
            }

            return $Button;
        }
    };
})(jQuery);
