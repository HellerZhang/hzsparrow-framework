var sysconfigManager = {
    findAllUrl: '/hzs/sysconfig/findAll',
    editUrl: '/hzs/sysconfig/edit',
    groups: null,
    init: function () {
        layui.use(['util', 'layer', 'form', 'laydate'], function () {
            sysconfigManager.refreshSysconfig();
        });
    },

    refreshSysconfig: function () {
        hs_utils.post(sysconfigManager.findAllUrl, {}, function (data) {
            if (data.success) {
                let groups = sysconfigGenerate.getGroups(data.data);
                sysconfigManager.groups = groups;
                let groupsHtml = sysconfigGenerate.generateGroupHtml(groups);
                $('#sysconfigGroup').html(groupsHtml);
                // 生成各分组的表单
                let itemsHtml = sysconfigGenerate.generateItemsFormHtml(groups);
                $('#sysconfigGroupItem').html(itemsHtml);
                sysconfigGenerate.rendForm();
                layui.form.render();
                layui.form.on('submit(sysConfigSave)', sysconfigManager.submit)
            } else {
                layui.layer.msg(data.msg);
            }
        });
    },

    submit: function (data) {
        let datas = sysconfigManager.getGroupData(data.form.dataset.group);
        let formData = $(data.form).serializeObject();
        for (let index in datas) {
            let value = null;
            if (datas[index].hsscType == 3) {
                if (typeof (formData[datas[index].hsscFlag]) == 'object') {
                    value = "";
                    for (let valueIndex in formData[datas[index].hsscFlag]) {
                        if (valueIndex > 0) {
                            value += ",";
                        }
                        value += formData[datas[index].hsscFlag][valueIndex];
                    }
                } else {
                    value = formData[datas[index].hsscFlag];
                }
            } else if (datas[index].hsscType == 5 && !formData[datas[index].hsscFlag]) {
                value = 0;
            } else {
                value = formData[datas[index].hsscFlag];
            }
            datas[index].hsscValue = value;
        }
        hs_utils.post(sysconfigManager.editUrl, datas, function (data) {
            if (data.success) {
                layui.layer.msg("操作成功！");
                indexManager.refreshSysConfig();
            } else {
                layui.layer.msg(data.msg);
            }
        }, function (data) {
            layui.layer.msg(data.msg);
        }, true);
        return false;
    },

    getGroupData: function (groupName) {
        for (let index in sysconfigManager.groups) {
            if (sysconfigManager.groups[index].name == groupName) {
                return sysconfigManager.groups[index].data;
            }
        }
        return null;
    }

}
sysconfigManager.init();
