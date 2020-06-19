var menuManager = {
    treeUrl: "/hzs/menu/findMenuTree",
    saveUrl: "/hzs/menu/save",
    editUrl: "/hzs/menu/edit",
    removeUrl: "/hzs/menu/remove",
    operateType: null,
    operateDate: null,
    menuInfoLayer: null,
    isAdd: false,
    init: function () {
        layui.use(['tree', 'util', 'layer'], function () {
            menuManager.refreshTree();
        });
    },

    // 刷新菜单树
    refreshTree: function () {
        hs_utils.post(menuManager.treeUrl, {}, function (data) {
            if (data.success) {
                let tree = layui.tree,
                    layer = layui.layer,
                    util = layui.util;
                tree.render({
                    elem: '#menuTree',
                    data: menuManager.treeConversion(data.data),
                    id: 'id',
                    showCheckbox: true,
                    customOperate: true,
                    edit: ['add', 'update', 'del'],
                    operate: function (obj) {
                        let type = obj.type; //得到操作类型：add、update、del
                        let data = obj.data; //得到当前节点的数据
                        let elem = obj.elem; //得到当前节点元素
                        if (type == "add") {
                            menuManager.isAdd = true;
                            $('#menuForm')[0].reset();
                            menuManager.openMenuLayer();
                            let addData = {
                                parentId: data.attribute.hsmId
                            }
                            layui.form.val('menuForm', addData);
                        } else if (type == "update") {
                            menuManager.isAdd = false;
                            menuManager.openMenuLayer();
                            if (data.attribute.parentId) {
                                data.attribute.isChild = '1';
                            } else {
                                data.attribute.isChild = '';
                            }
                            layui.form.val('menuForm', data.attribute);
                        } else if (type == "del") {
                            menuManager.remove(menuManager.getMenuIds(data));
                        }
                    }
                });
            }
        })
    },

    // 打开菜单信息窗口
    openMenuLayer: function () {
        menuManager.menuInfoLayer = layui.layer.open({
            type: 1,
            title: '菜单信息',
            area: ["580px", "410px"],
            content: $("#menuDiv")
        });
        layui.form.on('submit(menuSave)', menuManager.submit)
    },

    // 提交数据
    submit: function (data) {
        if (!data.field.state) {
            data.field.state = '0';
        }
        if (!data.field.isChild) {
            delete data.field.parentId;
        }
        let url_ = menuManager.editUrl;
        if (menuManager.isAdd) {
            url_ = menuManager.saveUrl;
        }
        hs_utils.post(url_, data.field, function (data) {
            if (data.success) {
                layui.layer.close(menuManager.menuInfoLayer);
                layui.layer.msg("操作成功！");
                menuManager.refreshTree();
            } else {
                layui.layer.msg(data.msg);
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    },

    // 删除菜单
    remove: function (hsmId) {
        layui.layer.confirm("确定删除菜单？", {
            icon: 3, title: '提示'
        }, function (index) {
            layui.layer.close(index);
            hs_utils.post(menuManager.removeUrl, {hsmId: hsmId}, function (data) {
                if (data.success) {
                    layui.layer.msg("操作成功！");
                    menuManager.refreshTree();
                } else {
                    layui.layer.msg(data.msg);
                }
            });
        });
    },

    // 树形数据转换
    treeConversion: function (data, nodeId) {
        let resultData = [];
        if (!data) {
            return resultData;
        }
        for (let index in data) {

            let node = data[index];
            let spread_ = false;
            if (nodeId && nodeId.indexOf(node.id) != -1) {
                spread_ = true;
            }
            resultData.push({
                title: node.name,
                id: node.id,
                parentId: node.parentId,
                attribute: node.attribute,
                spread: spread_,
                children: menuManager.treeConversion(node.child, nodeId)
            });
        }
        return resultData;
    },

    // 获取节点与子节点的ID集合，使用,分隔
    getMenuIds: function (node) {
        let ids = node.id;
        for (let index in node.children) {
            ids += "," + menuManager.getMenuIds(node.children[index]);
        }
        return ids;
    }
}
menuManager.init();