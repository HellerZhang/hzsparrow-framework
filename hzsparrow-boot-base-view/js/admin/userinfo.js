var userinfoManager = {
    editUserUrl: '/hzs/user/edit',
    getUserInfoUrl: '/hzs/user/getById',
    userImgUrl: '/hzs/file/download?hsfId=',
    uploadUserImgUrl: '/hzs/file/save',
    init: function () {
        layui.use(['form', 'upload'], function () {
                userinfoManager.initForm();
                userinfoManager.initUpload();
                layui.form.on('submit(userinfoSave)', userinfoManager.submit);
            }
        );
    },

    // 初始化表单数据
    initForm: function () {
        hs_utils.post(userinfoManager.getUserInfoUrl, {hsuId: indexManager.userData.userId},
            function (data) {
                layui.form.val('userinfoForm', data.data);
                if (data.data.imgId) {
                    $('#userinfoImg').attr('src', hs_utils.getServerUrl(userinfoManager.userImgUrl + data.data.imgId));
                }
            });
    },

    // 初始化上传控件
    initUpload: function () {
        layui.upload.render({
            elem: '#userinfoImg',
            url: hs_utils.getServerUrl(userinfoManager.uploadUserImgUrl), //必填项
            field: 'uploadFile',
            drag: false,
            done: function (res, index, upload) {
                $('#userinfoImg').attr('src', hs_utils.getServerUrl(userinfoManager.userImgUrl + res.data.hsfId));
                $('#userinfoImgId').val(res.data.hsfId);
            }
        });
    },

    // 提交用户数据
    submit: function (data) {
        hs_utils.post(userinfoManager.editUserUrl, data.field,
            function (data) {
                if (data.success) {
                    layui.layer.msg("操作成功！");
                    userinfoManager.initForm();
                } else {
                    layui.layer.msg(data.msg);
                }
            });
        return false;
    }
}
userinfoManager.init();