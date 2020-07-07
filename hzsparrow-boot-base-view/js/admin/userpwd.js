var userpwdManager = {
    changePwdUrl: '/hzs/user/changePassword',
    init: function () {
        layui.use('form', function () {
            layui.form.val('userpwdForm', {hsuId: indexManager.userData.userId});
            layui.form.on('submit(userpwdSave)', userpwdManager.submit);
        });
    },

    // 提交修改密码数据
    submit: function (data) {
        if (data.field.newPassword != data.field.surePassword) {
            layui.layer.msg("两次新密码填写不一致！");
            return false;
        }
        hs_utils.post(userpwdManager.changePwdUrl, data.field,
            function (data) {
                if (data.success) {
                    layui.layer.msg("操作成功！");
                } else {
                    layui.layer.msg(data.msg);
                }
                $('#userpwdForm')[0].reset();
                layui.form.val('userpwdForm', {hsuId: indexManager.userData.userId});
            });
        return false;
    }
}
userpwdManager.init();
