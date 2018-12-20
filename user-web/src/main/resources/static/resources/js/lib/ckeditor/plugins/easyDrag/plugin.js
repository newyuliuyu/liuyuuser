/**
 * 点击浮动功能
 */
CKEDITOR.plugins.add('easyDrag', {
    init : function(editor) {
        editor.ui.addButton("easyDrag", {
            label : '拖动图片',
            icon : this.path + "float.png",
            click : function(e) {
                editor.execCommand('sample');

                var selection = editor.getSelection(),
                    ranges = selection.getRanges(),
                    range;
                var selects = [];
                var iterator = ranges.createIterator();
                while ((range = iterator.getNextRange())) {
                    if (range.collapsed) {
                        //没有选中的内容不执行
                        return false;
                    }
                    var div = new CKEDITOR.dom.element('div');
                    div.append(range.cloneContents());
                    selects.push(div.getHtml());
                }
                //var imgSelector = $("div#"+editor.name+" img:not(.Wirisformula)");
                var src = $(selects[0]).attr("src");
                var imgSelector = $("div#"+editor.name+" img[src='"+src+"']:not(.Wirisformula)");
                ImageCtrl.draggable(imgSelector);
            }
        });
    }
});