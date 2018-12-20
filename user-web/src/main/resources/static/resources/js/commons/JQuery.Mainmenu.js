(function() {
    "use strict";
    var dps = ['jquery'];
    define(dps, function($) {
        // 该功能没有做完
        $.fn.extend( {
            'mainmenu'	: function() {
                var $this = $(this);
               var width = this.width();
               var menus = this.find('.main-menu');
               var sumWidth=0;
               menus.each(function(idx,item){
                   sumWidth+=$(item).outerWidth(true)+5;
                   //console.log(item)
               });
                this.width(sumWidth)
                var $outerDiv = $('<div>',{id:'abc',
                    css:{
                        width:width,
                        overflow:'hidden',
                        border:'1px solid #64b9b9'
                    }
                });
                $this.wrap($outerDiv)

            }
        });
    });
})();