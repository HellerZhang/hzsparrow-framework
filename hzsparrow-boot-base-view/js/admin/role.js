var roleManager = {
    searchUrl: "/hzs/role/pageFindAll",
    saveUrl: "/hzs/role/save",
    editUrl: "/hzs/role/edit",
    removeUrl: "/hzs/role/remove",
    roleInfoLayer: null,
    isAdd: false,
    roleTable: null,
    init: function () {
        layui.use(['table', 'util', 'layer'], function () {
            roleManager.refreshTable();
        });
    },

    // 刷新表格
    refreshTable: function () {
        let table = layui.table;
        roleManager.roleTable = table.render({
            elem: '#roleTable',
            height: 430,
            url: hs_utils.getServerUrl(roleManager.searchUrl), //数据接口
            page: true, //开启分页
            toolbar: '#roleTableToolBar',
            defaultToolbar: ['filter', 'exports'],
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.data //解析数据列表
                }
            },
            request: {
                pageName: 'page', //页码的参数名称，默认：page
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            response: {
                statusCode: 200 //规定成功的状态码，默认：0
            },
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
        });

        table.on('toolbar(roleTable)', function (obj) {
            let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if (layEvent === 'add') {
                $('#roleForm')[0].reset();
                roleManager.isAdd = true;
                roleManager.openFormLayer();
            }
        });
        table.on('tool(roleTable)', function (obj) {
            let data = obj.data; //获得当前行数据
            let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if (layEvent === 'remove') { //删除
                layui.layer.confirm('真的删除行么', function (index) {
                    layui.layer.close(index);
                    //向服务端发送删除指令
                    roleManager.remove(data.hsrId);
                });
            } else if (layEvent === 'edit') { //编辑
                layui.form.val('roleForm', data);
                roleManager.isAdd = false;
                roleManager.openFormLayer();
            } else if (layEvent === 'assign') {// 分配
                roleMenuAssignManager.openFormLayer(data.hsrId);
            }
        });
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
    },

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
    },

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
    },

    // 重载表格
    reloadTable: function () {
        roleManager.roleTable.reload();
    }
}
roleManager.init();