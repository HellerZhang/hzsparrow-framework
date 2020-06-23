var userManager = {
    findRoleUrl: "/hzs/role/findAll",
    searchUrl: "/hzs/user/pageFindAll",
    saveUrl: "/hzs/user/save",
    editUrl: "/hzs/user/edit",
    removeUrl: "/hzs/user/remove",
    uploadUrl: "/hzs/file/save",
    downloadUrl: "/hzs/file/download?hsfId=",
    userTable: null,
    userInfoLayer: null,
    isAdd: false,
    init: function () {
        layui.use(['table', 'util', 'layer', 'form'], function () {
            userManager.refreshTable();
            userManager.refreshRole();
            layui.form.on('submit(userSearch)', userManager.search);
        });
    },

    // 刷新角色选择框
    refreshRole: function () {
        hs_utils.post(userManager.findRoleUrl, {}, function (data) {
            if (data.success) {
                let roleItems = "<option value=\"\"></option>";
                for (let index in data.data) {
                    roleItems += "<option value=\"" + data.data[index].hsrId + "\">" + data.data[index].hsrName + "</option>";
                }
                $("#user_hsrId").html(roleItems);
                layui.form.render('select', 'userForm');
            } else {
                layui.layer.msg(data.msg);
            }
        });
    },

    // 刷新Table
    refreshTable: function () {
        userManager.userTable = hs_utils.renderPageTable(userManager.tableConfig(), userManager.eventConfig());
    },

    // 打开角色信息窗口
    openFormLayer: function () {
        userManager.userInfoLayer = layui.layer.open({
            type: 1,
            title: '用户信息',
            area: ["450px", "470px"],
            content: $("#userDiv")
        });
        layui.form.on('submit(userSave)', userManager.submit)
    }
    ,

    // 提交数据
    submit: function (data) {
        let url_ = userManager.editUrl;
        if (userManager.isAdd) {
            url_ = userManager.saveUrl;
        }
        hs_utils.post(url_, data.field, function (data) {
            if (data.success) {
                layui.layer.close(userManager.userInfoLayer);
                layui.layer.msg("操作成功！");
                userManager.reloadTable();
            } else {
                layui.layer.msg(data.msg);
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    }
    ,

    // 删除
    remove: function (hsuId) {
        hs_utils.post(userManager.removeUrl, {hsuId: hsuId}, function (data) {
            if (data.success) {
                layui.layer.msg("操作成功！");
                userManager.reloadTable();
            } else {
                layui.layer.msg(data.msg);
            }
        });
    }
    ,

    // 查询
    search: function (data) {
        userManager.reloadTable(data.field);
        return false;
    },

    // 重载表格
    reloadTable: function (data) {
        if (data) {
            userManager.userTable.reload({
                where: data
            });
        } else {
            userManager.userTable.reload();
        }
    },

    // 表格参数
    tableConfig: function () {
        return {
            elem: '#userTable',
            url: userManager.searchUrl, //数据接口
            cols: [[ //表头
                {
                    field: 'hsuName', title: '用户姓名', align: "center",
                    templet: function (d) {
                        if (d.imgId) {
                            return d.hsuName + "<img src='" + hs_utils.getServerUrl(userManager.downloadUrl + d.imgId) + "'/>";
                        } else {
                            return d.hsuName;
                        }
                    }
                },
                {field: 'hsuAccount', title: '账号', align: "center"},
                {field: 'hsrName', title: '角色', align: "center"},
                {field: 'email', title: '邮箱', align: "center"},
                {field: 'mobile', title: '手机号', align: "center"},
                {
                    field: 'hsuId', title: '操作', align: "center",
                    toolbar: "#tableItemToolBar"
                }
            ]]
        };
    },

    // 表格事件参数
    eventConfig: function () {
        return {
            filter: 'userTable',
            toolbar: [
                {
                    name: 'add', handler: function (obj) {
                        $('#userForm')[0].reset();
                        userManager.isAdd = true;
                        userManager.openFormLayer();
                    }
                }
            ],
            tool: [
                {
                    name: 'remove', handler: function (obj) {
                        layui.layer.confirm('真的删除行么', function (index) {
                            layui.layer.close(index);
                            //向服务端发送删除指令
                            userManager.remove(obj.data.hsuId);
                        });
                    }
                },
                {
                    name: 'edit', handler: function (obj) {
                        $('#userForm')[0].reset();
                        layui.form.val('userForm', obj.data);
                        userManager.isAdd = false;
                        userManager.openFormLayer();
                    }
                }
            ]
        };
    }
}
userManager.init();