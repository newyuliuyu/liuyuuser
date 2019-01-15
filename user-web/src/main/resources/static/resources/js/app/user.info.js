(function () {
    "use strict";
    var models = ['jquery',
        'ajax',
        'js/app/user',
        'dot',
        'dialog',
        'js/app/menu',
        'js/commons/UI',
        'bootstrap',
        'css!style/bootstrap/bootstrap.min',
        'bootstrapSelect',
        'css!style/font-awesome',
        'css!style/app',
        'icheck',
        'loading',
        'ztree'

    ];
    define(models, function ($, ajax, user, dot, dialog, menu, UI) {

        function list(page) {
            var url = '/user/list?1=1';
            if ($.isPlainObject(page)) {
                url += '&pageNum=' + page.pageNum + "&pageSize=" + page.pageSize;
            }
            var searchText = $('#searchText').val();
            if (searchText && searchText !== '') {
                url += '&search=' + searchText;
            }

            ajax.getJson(url).then(function (data) {
                var templateText = $("#userTableT").text();
                var arrText = dot.template(templateText);
                var html = arrText(data);
                $('#tableDIV').html(html);
                UI.pager().create('pager', function (page) {
                    list(page);
                });
            }).always(function () {
                $.processError(arguments);
            })
        }

        function initEvent() {
            $('#searchUser').click(function () {
                list();
            });
            $('#addUser').click(function () {
                console.log("增加用户");
                var data = {user: {}};
                var templateText = $("#newAndUpdateUserT").text();
                var arrText = dot.template(templateText);
                var html = arrText(data);

                var myDialog = dialog.myModal({size: size, body: html}, function () {
                    var user = getUser();
                    if (user != false) {
                        var url = '/user/add';
                        ajax.postJson(url, user).then(function (data) {
                            myDialog.close();
                            dialog.prompt("保存成功");
                            clickCurPage();
                        }).always(function () {
                            $.processError(arguments);
                        })
                    }
                });
                boundValidate();
            });

            $('#tableDIV').on('click', '.del-btn', function () {
                console.log("删除用户");
                var userId = $(this).data('id');
                var myDialog = dialog.confirm("删除用户", "确定要删除用户，删除用户后不能再恢复", function () {
                    delUser(userId);
                    myDialog.close();
                });
            });
            $('#tableDIV').on('click', '.update-btn', function () {
                console.log("修改用户信息");
                var userId = $(this).data('id');
                updateUser(userId);
            });
            $('#tableDIV').on('click', '.resetpwd-btn', function () {
                console.log("重置密码");
                var userId = $(this).data('id');
                var url = '/user/resetpwd/' + userId;
                ajax.postJson(url).then(function (data) {
                    dialog.prompt("重置密码成功");
                }).always(function () {
                    $.processError(arguments);
                });
            });
            $('#tableDIV').on('click', '.setting-btn', function () {
                console.log("设置权限");
                var userId = $(this).data('id');
                settingRes(userId);
            });
            $('#tableDIV').on('click', '.setting-role-btn', function () {
                console.log("设置角色");
                var userId = $(this).data('id');
                settingRole(userId);
            });
        }

        function settingRole(userId) {
            var url = '/user/role/' + userId;
            ajax.getJson(url).then(function (data) {

                var roles = data.roles;
                var roleMap = {};
                $.each(roles, function (idx, item) {
                    roleMap[item.id] = true;
                });
                console.log(roleMap)
                var dataset = {};
                dataset.roleMap = roleMap;
                dataset.roles = allRoles;
                var templateText = $("#userRolesT").text();
                var arrText = dot.template(templateText);
                var html = arrText(dataset);
                var myDialog = dialog.myModal({size: 'sm', body: html}, function () {
                    var roles = [];
                    $('.role:checked').each(function () {
                        var role = {};
                        role.id = $(this).val();
                        roles.push(role);
                    });
                    // if (roles.length > 0) {
                    //
                    // } else {
                    //     myDialog.close();
                    // }

                    var url = 'user/save/role/' + userId;
                    ajax.postJson(url, roles).then(function (value) {
                        dialog.prompt("设置角色成功");
                        myDialog.close();
                    }).always(function () {
                        $.processError(arguments);
                    });
                });
                $(".attribute").iCheck({
                    checkboxClass: 'icheckbox_square-blue',
                    radioClass: 'iradio_square-blue'
                });

            }).always(function () {
                $.processError(arguments);
            });
        }


        function settingRes(userId) {
            var url = '/user/res/' + userId;
            ajax.getJson(url).then(function (data) {
                var reses = data.reses;
                var treedataset = createTreeData(reses);
                var myDialog = dialog.myModal({size: 'md', body: '<div id="ktree" class="ztree"></div>'}, function () {
                    var nodes = treeObj.getChangeCheckedNodes();
                    if (nodes.length > 0) {
                        saveRes(userId, nodes);
                    }
                    myDialog.close();
                });
                window.treeObj = $.fn.zTree.init($("#ktree"), initTreeSetting(), treedataset);
            }).always(function () {
                $.processError(arguments);
            });

        }

        function saveRes(userId, nodes) {
            var delResIds = [];
            var saveResIds = [];
            $.each(nodes, function (idx, item) {
                if (item.checked) {
                    saveResIds.push({id: item.id})
                } else {
                    delResIds.push({id: item.id});
                }
            });

            var saveXRes = {};
            saveXRes.saveResource = saveResIds;
            saveXRes.delResource = delResIds;
            var url = '/user/saveres/' + userId;
            ajax.postJson(url, saveXRes).then(function (data) {
                dialog.prompt("设置成功");
            }).always(function () {
                $.processError(arguments);
            });
        }

        function createTreeData(reses) {
            var resesMap = {};
            $.each(reses, function (idx, item) {
                resesMap[item.id] = item;
            });

            var result = [];
            $.each(allReses, function (idx, item) {
                var treeData = {};
                treeData.id = item.id;
                treeData.name = item.name;
                treeData.parentId = item.parentId;
                treeData.checked = false;
                if (resesMap[treeData.id]) {
                    treeData.checked = true;
                }
                result.push(treeData);
            });

            return result;
        }

        function initTreeSetting() {
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: 'id',
                        pIdKey: 'parentId',
                        rootPId: '0'
                    }
                },
                check: {
                    enable: true,
                    chkStyle: 'checkbox',
                    radioType: 'all'
                }
            };
            return setting;
        }

        function updateUser(userId) {
            var url = '/user/get/' + userId;
            ajax.getJson(url).then(function (data) {
                var templateText = $("#newAndUpdateUserT").text();
                var arrText = dot.template(templateText);
                var html = arrText(data);

                var myDialog = dialog.myModal({size: size, body: html}, function () {
                    var user = getUser();
                    console.log(user)
                    if (user != false) {
                        var url = '/user/update';
                        ajax.postJson(url, user).then(function (data) {
                            myDialog.close();
                            dialog.prompt("修改成功");
                            clickCurPage();
                        }).always(function () {
                            $.processError(arguments);
                        })
                    }
                });
                boundValidate();

            }).always(function () {
                $.processError(arguments);
            })
        }

        function boundValidate() {
            $('.dialog-box button:contains(确定)').attr('disabled', true);

            $("#userName").blur(function () {
                var $this = $(this);
                var userName = $this.val().trim();
                var oldValue = $this.data('oldValue') + '';
                if (userName && userName !== '' && userName !== oldValue) {
                    beginValidate($this);
                    var url = '/user/validate/1?value=' + userName;
                    ajax.postJson(url).then(function (data) {
                        if (data.exist) {
                            validate($this, false)
                            $("#userName+small").text('用户名已存在');
                        } else {
                            validate($this, true);
                        }
                    }).always(function () {
                        $.processError(arguments);
                    })
                }
            });
            $("#phone").blur(function () {
                var $this = $(this);
                var phone = $this.val().trim();
                var oldValue = $this.data('oldValue') + '';
                if (phone && phone !== '' && phone !== oldValue) {
                    beginValidate($this);
                    if (!validatePhone(phone)) {
                        validate($this, false);
                        $("#phone+small").text('不是合法的手机号');
                    } else {
                        var url = '/user/validate/2?value=' + phone;
                        ajax.postJson(url).then(function (data) {
                            if (data.exist) {
                                validate($this, false)
                                $("#phone+small").text('手机已存在');
                            } else {
                                validate($this, true);
                                endValidate($this);
                            }
                        }).always(function () {
                            $.processError(arguments);
                        })
                    }
                } else {
                    beginValidate($this);
                    validate($this, true);
                    endValidate($this);
                }
            });
            $("#email").blur(function () {
                var $this = $(this);
                var email = $this.val().trim();
                var oldValue = $this.data('oldValue') + '';
                if (email && email !== '' && email !== oldValue) {
                    beginValidate($this);
                    if (!validateEmail(email)) {
                        validate($this, false)
                        $("#email+small").text('不是合法的邮箱地址');
                    } else {
                        var url = '/user/validate/3?value=' + email;
                        ajax.postJson(url).then(function (data) {
                            if (data.exist) {
                                validate($this, false)
                                $("#email+small").text('邮箱已存在');
                            } else {
                                validate($this, true);
                                endValidate($this);
                            }
                        }).always(function () {
                            $.processError(arguments);
                        })
                    }
                } else {
                    beginValidate($this);
                    validate($this, true);
                    endValidate($this);
                }

            });
            $("#realName").blur(function () {
                var $this = $(this);
                endValidate($this);
            });
        }

        function beginValidate($e) {
            $e.data('valid', true);
            $e.next('small').text('');
            $('.dialog-box button:contains(确定)').attr('disabled', true);
        }

        function validate($e, bool) {
            $e.data('validResult', bool);
            $e.data('valid', false);
        }

        function endValidate($e) {
            if (!$("#userName").data('valid')
                && $("#userName").data('validResult')
                && !$("#phone").data('valid')
                && $("#phone").data('validResult')
                && !$("#email").data('valid')
                && $("#email").data('validResult')) {
                $('.dialog-box button:contains(确定)').attr('disabled', false);
            }
        }

        function validatePhone(phone) {
            var re = /^1\d{10}$/;
            return re.test(phone);
        }

        function validateEmail(email) {
            var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
            return re.test(email);
        }

        function getUser() {
            var user = {};
            var hasError = false;

            user.id = $("#id").val().trim();
            user.account = $("#userName").val().trim();
            if (!user.account || user.account === '') {
                $("#userName+small").text("不能为空");
                hasError = true;
            }
            user.name = $("#realName").val().trim();
            user.phone = $("#phone").val().trim();
            user.email = $("#email").val().trim();

            if (hasError) {
                return false;
            }
            return user;
        }

        function delUser(userId) {
            var url = '/user/delete/';
            var user = {id: userId};
            ajax.postJson(url, user).then(function (data) {
                dialog.prompt("删除用户成功");
                clickCurPage(true);
            }).always(function () {
                $.processError(arguments);
            });
        }

        function clickCurPage(isDel) {
            if (isDel) {
                var trSize = $('.user-table>tbody>tr').size();
                if (trSize < 2) {
                    $('#pager').clickPreviousPage();
                }
            }
            $('#pager').clickCurPage();
        }


        var allReses = [];
        var allRoles = [];

        function initData() {
            var resUrl = '/res/list';
            ajax.getJson(resUrl).then(function (data) {
                allReses = data.reses;
            }).always(function () {
                $.processError(arguments);
            });
            var roleUrl = '/role/list?pageSize=30';
            ajax.getJson(roleUrl).then(function (data) {
                allRoles = data.roles;
            }).always(function () {
                $.processError(arguments);
            });
        }

        var h = window.getClientHeight() - 200;
        if (h < 200) {
            h = 200;
        }
        var size = {w: 600, h: h};


        return {
            render: function () {
                initData();
                user.userInfo('home');
                $('main').show();
                menu.createMenu('userMenu');
                list();

                initEvent();
            }
        }
    });
})();