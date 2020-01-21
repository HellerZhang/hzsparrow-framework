var menuManager = {
    treeUrl: "/hzs/menu/findMenuTree",
    init: function () {
        layui.use(['tree', 'util'], function () {
            menuManager.refreshTree();
        });
    },

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
                    edit: ['add', 'update', 'del'],
                    operate: function (obj) {
                        var type = obj.type; //得到操作类型：add、edit、del
                        var data = obj.data; //得到当前节点的数据
                        var elem = obj.elem; //得到当前节点元素

                        console.log(obj);
                        if (type == "update") {
                            console.log($(elem).parent().parent().attr("data-id"));
                        }
                    }
                });
            }
        })
    },

    treeConversion: function (data) {
        let resultData = [];
        if (!data) {
            return resultData;
        }
        for (let index in data) {

            let node = data[index];
            resultData.push({
                title: node.name,
                id: node.id,
                parentId: node.parentId,
                attribute: node.attribute,
                children: menuManager.treeConversion(node.child)
            });
        }
        return resultData;
    },
}
menuManager.init();