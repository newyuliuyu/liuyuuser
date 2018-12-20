/**
 * 自定义虚线实线切换。
 * 插件增加icon，
 * 点击进行当前编辑框内灰色虚线和黑色实线的切换
 * 目前只针对 语文和英语 作文题。
 */
CKEDITOR.plugins.add('easyUnderlineSwitch', {
	init : function(editor) {
		editor.ui.addButton("easyUnderlineSwitch", {
			label : '点击切换虚实线',
			icon : this.path + "icon.png",
			toolbar: 'basicstyles,10001',
			click : function(e) {
				window.e = e;
				 var id = e.tabIndex;
				 console.log(e)
				 //utils.composition.switchBtnClick(id);	
			}
		});
	}
});