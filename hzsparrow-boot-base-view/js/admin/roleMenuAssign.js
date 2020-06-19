var roleMenuAssignManager = {
    findMenuUrl: "/hzs/rolemenu/findMenuAsAssignByRoleId",
    assignMenuUrl: "/hzs/rolemenu/assign",
    roleMenuAssignLayer: null,
    hsrId: null,
    init: function () {
        layui.use(['tree', 'util', 'layer'], function () {

        });
    },

    // 刷新菜单树
    refreshTree: function (hsrId) {
        hs_utils.post(roleMenuAssignManager.findMenuUrl, {hsrId: hsrId}, function (data) {
            if (data.success) {
                let tree = layui.tree,
                    layer = layui.layer,
                    util = layui.util;
                tree.render({
                    elem: '#roleMenuAssignTree',
                    data: roleMenuAssignManager.treeConversion(data.data),
                    id: 'roleMenuAssignTree',
                    showCheckbox: true
                });
            }
        })
    },

    // 打开角色菜单信息窗口
    openFormLayer: function (hsrId) {
        roleMenuAssignManager.refreshTree(hsrId);
        roleMenuAssignManager.hsrId = hsrId;
        roleMenuAssignManager.roleMenuAssignLayer = layui.layer.open({
            type: 1,
            title: '角色菜单信息',
            area: ["450px", "450px"],
            content: $("#roleMenuAssignDiv")
        });
        layui.form.on('submit(roleMenuAssign)', roleMenuAssignManager.assignRoleMenu)
    },

    // 分配权限
    assignRoleMenu: function () {
        let checkData = layui.tree.getChecked('roleMenuAssignTree');
        let hsmIds = roleMenuAssignManager.getAssignId(checkData);
        hs_utils.post(roleMenuAssignManager.assignMenuUrl, {
            hsrId: roleMenuAssignManager.hsrId,
            hsmIds: hsmIds
        }, function (data) {
            if (data.success) {
                layui.layer.close(roleMenuAssignManager.roleMenuAssignLayer);
                layui.layer.msg("操作成功！");
            } else {
                layui.layer.msg(data.msg);
            }
        });

        return false;
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
            let children = roleMenuAssignManager.treeConversion(node.child, nodeId);
            resultData.push({
                title: node.name,
                id: node.id,
                parentId: node.parentId,
                attribute: node.attribute,
                spread: spread_,
                checked: children.length > 0 ? false : node.attribute.assigned,
                children: children
            });
        }
        return resultData;
    },

    // 获取分配的菜单ID
    getAssignId: function (data) {
        let resultId = "";
        for (let index in data) {
            if (index > 0) {
                resultId += ",";
            }
            resultId += data[index].id;
            if (data[index].children && data[index].children.length > 0) {
                resultId += "," + roleMenuAssignManager.getAssignId(data[index].children);
            }
        }
        return resultId;
    }
}
roleMenuAssignManager.init();