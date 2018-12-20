/**
 * 拖动组件，用于在指定的容器中对目标div进行拖动，
 * 同时限制拖动元素在容器范围内进行拖动，不能超出边界
 * Created by dell on 2017/3/30.
 */
(function() {
    "use strict";
    
    define(['jquery'], function($) {
        var $target, $container;
        var parentWidth, parentHeight;
        var targetWidth, targetHeight;
        var maxLeft, maxTop; //可以拖动的最大范围
        var mousedown = null;
        
        function initParams() {
            parentWidth = $container.outerWidth(true);
            parentHeight = $container.outerHeight(true);
            
            targetWidth = $target.outerWidth(true);
            targetHeight = $target.outerHeight(true);
            
            maxLeft = parentWidth - targetWidth;
            maxTop = parentHeight - targetHeight;
        }
        
        function initDragEvent() {
            var startXY, curXY, distanceXY;
            mousedown = function(e) { //将mousedown 脱离出来是为了实现后面拖动事件解绑删除
                if(e.which != 3) { //不影响右键菜单
                    preventBuddle(e);
                }

                startXY = getXY(e);
                $(document).on('mousemove', function(e) {
                    if(e.which != 3) { //不影响右键菜单
                        preventBuddle(e);
                    }
                    curXY = getXY(e);
                    distanceXY = getDistanceXY(startXY, curXY);
                    dragDiv($target[0], distanceXY);
                    startXY = $.extend(true, {}, curXY);
                    return false;
                });

                $(document).on('mouseup', function(e) {
                    if(e.which != 3) {
                        preventBuddle(e);
                    }

                    $(document).off('mousemove');
                    $(document).off('mouseup');

                    return false;
                });

                return false;
            };
            $target.on('mousedown', mousedown);
        }

        /**
         * 拖动div
         * @param div
         * @param distanceXY
         */
        function dragDiv(div, distanceXY) {
            validatePos(div, distanceXY);
            var left = div.offsetLeft + distanceXY.dx;
            var top = div.offsetTop + distanceXY.dy;
            $(div).css({
                left: left + 'px',
                top: top + 'px'
            });
        }

        /**
         * 验证当前拖动的位置坐标是否合理
         * @param div
         * @param distanceXY
         */
        function validatePos(div, distanceXY) {
            var left = div.offsetLeft;
            var top = div.offsetTop;
            
            var curLeft = left + distanceXY.dx;
            var curTop = top + distanceXY.dy;

            if(curLeft < 0) {
                distanceXY.dx = -left;
            }

            if(curTop < 0) {
                distanceXY.dy = -top;
            }
            
            if(curLeft > maxLeft) {
                distanceXY.dx = parentWidth - left - targetWidth;
            }

            if(curTop > maxTop) {
                distanceXY.dy = parentHeight - top - targetHeight;
            }
        }

        /**
         * 获取拖动的xy偏移量
         * @param startXY
         * @param curXY
         */
        function getDistanceXY(startXY, curXY) {
            return {
                dx : (curXY.x - startXY.x),
                dy : (curXY.y - startXY.y)
            };
        }
        
        function getXY(e) { //获取鼠标的pageX,pageY
            return {
                x : e.pageX,
                y : e.pageY
            };
        }

        /**
         * 阻止事件冒泡
         */
        function preventBuddle(e) {
            if(e.stopPropagation) {
                e.stopPropagation();
            } else {
                e.cancelBubble = true;
            }
        }

        /**
         * 元素拖动时改变鼠标样式为拖动样式
         */
        function changeMouseToDragStyle($target) {
            $target.css({
                cursor : 'move'
            });
        }
        
        /**
         * 元素停止拖动后改变鼠标样式为默认样式
         */
        function changeMouseToDefaultStyle($target) {
            $target.css({
                cursor : 'default'
            });
        }
        
        return {
            /**
             * 
             * @param target 拖拽的目标组件
             * @param container 拖拽的元素所在的容器，为了做边界限制
             */
            drag: function(target, container) {
                if(!target || !container) {
                    throw new Error('drag target and drag container must be null!!!');
                }
                $target = $(target);
                $container = $(container);
                initParams();
                initDragEvent();
            },
            /**
             * 删除拖动事件
             * @param target
             */
            destroy: function(target) {
                if(null != mousedown) {
                    $(target).off('mousedown', mousedown);
                }
                
                return this;
            }
        }
    });
    
})();