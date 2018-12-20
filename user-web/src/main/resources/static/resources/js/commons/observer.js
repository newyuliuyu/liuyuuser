(function(){
	"use strict";
	var deps = ['jquery'];
	define(deps, function($){
		var o = $({});
		$.extend({
			subscribe		: function(){
				o.on.apply(o, arguments);
			},
			unsubscribe	: function(){
				o.off.apply(o, arguments);
			},
			publish			: function(){
				var params = [];
				for (var i = 1, size = arguments.length; i < size; i++) {
					params.push(arguments[i]);
				}
				var args = [arguments[0], params];
				o.trigger.apply(o, args);
			}
		});
	});
})();