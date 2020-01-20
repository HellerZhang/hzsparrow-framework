let loginManager = {
    login: function () {
        let postData = $("#loginForm").serializeObject();
        console.log(postData);
        if (!postData.account || !postData.password) {
            layui.layer.msg("请将账号密码填写完整！");
            return;
        }
        hs_utils.post(
            "/hzs/login",
            postData,
            function (data) {
                if (data.success) {
                    window.location.href = "index.html";
                } else {
                    layui.layer.msg(data.msg);
                }
            }
        );
    }
}