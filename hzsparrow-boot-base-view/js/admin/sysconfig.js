var sysconfigManager = {
    findAllUrl: '/hzs/sysconfig/findAll',
    init: function () {
        layui.use(['util', 'layer', 'form'], function () {
            sysconfigManager.refreshSysconfig();
        });
    },

    refreshSysconfig: function () {
        hs_utils.post(sysconfigManager.findAllUrl, {}, function (data) {
            if (data.success) {
                let groups = sysconfigGenerate.getGroups(data.data);
                let groupsHtml = sysconfigGenerate.generateGroupHtml(groups);
                $('#sysconfigGroup').html(groupsHtml);
                // 生成各分组的表单
                let itemsHtml = sysconfigGenerate.generateItemsFormHtml(groups);
                $('#sysconfigGroupItem').html(itemsHtml);
                layui.form.render();
            } else {
                layui.layer.msg(data.msg);
            }
        });
    }

}
sysconfigManager.init();
