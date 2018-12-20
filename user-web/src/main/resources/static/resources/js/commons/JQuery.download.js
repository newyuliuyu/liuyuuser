(function() {
	"use strict";
	var dps = [ 'jquery'];
	define(dps, function($) {
		$.extend({
			/**
			 * _opts参数可以为{url:'', data:[]}对象，也可以直接是url字符串
			 */
			download : function(_opts) {
				var opts = {
					url : "",
					data : []
				};

				if ($.isPlainObject(_opts)) {
					$.extend(opts, _opts);
				} else {
					opts.url = _opts;
				}
				if (!opts.url) {
					return;
				}

				function createFrame() {
					var id = new Date().getTime();
					var frameId = "iframe" + id;
					var $frame = $("<iframe/>", {
						name : frameId,
						src : '#',
						css : {
							display : 'none'
						}
					});
					$('body').append($frame);
					return $frame;
				}

				function createFrom($frame) {
					if ($("#downloadForm").size() > 0) {
						$("#downloadForm").remove();
					}

					var frameId = $frame.attr("name");
					var $form = $("<form/>", {
						method : 'post',
						action : opts.url,
						target : frameId
					});
					if ($.isArray(opts.data)) {
						$.each(opts.data, function() {
							if (!$.isEmptyObject(this)) {
								var $input = $("<input/>", {
									type : 'hidden',
									value : this.value,
									name : this.name
								});
								$form.append($input);
							}
						});
					}

					$('body').append($form);
					return $form;
				}

				var $frame = createFrame();
				var $form = createFrom($frame);
				$form.submit();
//				setTimeout(function() {
//					$form.remove();
//					$frame.remove();
//				}, 5000);
			}
		});
	});
})();