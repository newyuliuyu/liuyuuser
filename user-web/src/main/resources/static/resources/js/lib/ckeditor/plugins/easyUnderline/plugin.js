CKEDITOR.plugins.add('easyUnderline', {
	init : function(editor) {
		editor.ui.addButton("easyUnderline", {
			label : '点击自动输入下划线',
			icon : this.path + "icon.png",
			toolbar: 'basicstyles,10000',
            click : function(e) {
                e.insertHtml("<span style='line-height: 10mm;'><u>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </u></span>");
            }
		});
	}
});