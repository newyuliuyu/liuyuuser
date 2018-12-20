(function() {
	var moudles = ['jquery', 'pager'];
	define(moudles, function($) {
		    var ui = {
			    pager	: function() {
				    var p = function() {
					    var pagerEl = undefined;
					    var callback = function() {
					    };
					    var pagenation = {
						    pageSize		: 10,
						    pageCount		: 1,
						    pageNum			: 1,
						    concreator	: this
					    };

					    this.init = function() {
						    pagenation.pageNum = 1;
						    pagenation.pageCount = 1;
						    return pagenation;
					    };

					    this.create = function() {// arg1 pagerId,arg2 callback
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
						    
						    if (pagenation.pageCount < 2) {
							    return;
						    }
						    pagerEl.pager( {
							        pageNum		: pagenation.pageNum,
							        pageCount	: pagenation.pageCount,
							        click			: function(pageNum, pageCount) {
								        pagenation.pageNum = pageNum;
								        callback.call(this, {
									            pageSize	: pagenation.pageSize,
									            pageNum		: pagenation.pageNum
								            });
							        }
						        });
						    return pagenation;
					    };

					    this.redraw = function(pagerHtml) {
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
