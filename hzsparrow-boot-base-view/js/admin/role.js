var roleManager = {
    searchUrl: "/hzs/role/pageFindAll",
    saveUrl: "/hzs/role/save",
    editUrl: "/hzs/role/edit",
    removeUrl: "/hzs/role/remove",
    roleInfoLayer: null,
    isAdd: false,
    roleTable: null,
    init: function () {
        layui.use(['table', 'util', 'layer', 'form'], function () {
            roleManager.refreshTable();
            layui.form.on('submit(roleSearch)', roleManager.search);
        });
    },

    // 刷新表格
    refreshTable: function () {
        roleManager.roleTable = hs_utils.renderPageTable(roleManager.tableConfig(), roleManager.eventConfig());
    },

    // 打开角色信息窗口
    openFormLayer: function () {
        roleManager.roleInfoLayer = layui.layer.open({
            type: 1,
            title: '角色信息',
            area: ["450px", "350px"],
            content: $("#roleDiv")
        });
        layui.form.on('submit(roleSave)', roleManager.submit)
    }
    ,

    // 提交数据
    submit: function (data) {
        let url_ = roleManager.editUrl;
        if (roleManager.isAdd) {
            url_ = roleManager.saveUrl;
        }
        hs_utils.post(url_, data.field, function (data) {
            if (data.success) {
                layui.layer.close(roleManager.roleInfoLayer);
                layui.layer.msg("操作成功！");
                roleManager.reloadTable();
            } else {
                layui.layer.msg(data.msg);
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    }
    ,

    // 删除
    remove: function (hsrId) {
        hs_utils.post(roleManager.removeUrl, {hsrIds: hsrId}, function (data) {
            if (data.success) {
                layui.layer.msg("操作成功！");
                roleManager.reloadTable();
            } else {
                layui.layer.msg(data.msg);
            }
        });
    }
    ,

    // 查询
    search: function (data) {
        roleManager.reloadTable(data.field);
        return false;
    },

    // 重载表格
    reloadTable: function (data) {
        if (data) {
            roleManager.roleTable.reload({
                where: data
            });
        } else {
            roleManager.roleTable.reload();
        }
    },

    // 表格参数
    tableConfig: function () {
        return {
            elem: '#roleTable',
            url: roleManager.searchUrl, //数据接口
            toolbar: '#roleTableToolBar',
            cols: [[ //表头
                {field: 'hsrName', title: '角色名称', align: "center"},
                {
                    field: 'hsrType',
                    title: '角色类型',
                    align: "center",
                    templet: function (d) {
                        if (d.hsrType == 1) {
                            return "管理";
                        } else if (d.hsrType == 2) {
                            return "普通";
                        }
                    }
                },
                {field: 'icon', title: '图标', align: "center"},
                {field: 'sortNum', title: '排序号', align: "center"},
                {
                    field: 'hsrId', title: '操作', align: "center",
                    toolbar: "#roleTableItemToolBar"
                }
            ]]
        };
    },

    // 表格事件参数
    eventConfig: function () {
        return {
            filter: 'roleTable',
            toolbar: [
                {
                    name: 'add', handler: function (obj) {
                        $('#roleForm')[0].reset();
                        roleManager.isAdd = true;
                        roleManager.openFormLayer();
                    }
                }
            ],
            tool: [
                {
                    name: 'remove', handler: function (obj) {
                        layui.layer.confirm('真的删除行么', function (index) {
                            layui.layer.close(index);
                            //向服务端发送删除指令
                            roleManager.remove(data.hsrId);
                        });
                    }
                },
                {
                    name: 'edit', handler: function (obj) {
                        layui.form.val('roleForm', obj.data);
                        roleManager.isAdd = false;
                        roleManager.openFormLayer();
                    }
                },
                {
                    name: 'assign', handler: function (obj) {
                        roleMenuAssignManager.openFormLayer(obj.data.hsrId);
                    }
                }
            ]
        };
    }
}
roleManager.init();