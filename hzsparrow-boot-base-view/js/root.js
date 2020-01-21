var hs_utils = {

    getViewUrl: function (url) {
        return "http://localhost:63342/hzsparrow-boot-base-view/" + url;
    },

    getServerUrl: function (url) {
        return "http://localhost:8080/" + url;
    },

    post: function (url, postData, cbsuccess, cberror, isJson = false) {
        let contentType_ = "application/x-www-form-urlencoded";
        let url_ = hs_utils.getServerUrl(url);
        let data_ = postData;
        if (isJson) {
            contentType_ = "application/json";
            data_ = JSON.stringify(postData);
        }
        $.ajax(url_, {
            type: "POST",
            data: data_,
            dataType: "json",
            xhrFields: {
                withCredentials: true
            },
            contentType: contentType_,
            success: function (data) {
                if (cbsuccess) {
                    cbsuccess(data);
                } else {
                    if (!data.success) {
                        layui.layer.msg(data.msg);
                    }
                }
            },
            error: function (data) {
                if (cberror) {
                    cberror(data);
                } else {
                    layui.layer.msg(data);
                }

            }
        })
    },

    get: function (url, getData, cbsuccess, cberror) {
        let contentType_ = "application/x-www-form-urlencoded";
        let url_ = hs_utils.getServerUrl(url);
        let data_ = getData;
        $.ajax(url_, {
            type: "GET",
            data: data_,
            dataType: "json",
            xhrFields: {
                withCredentials: true
            },
            contentType: contentType_,
            success: function (data) {
                if (cbsuccess) {
                    cbsuccess(data);
                } else {
                    if (!data.success) {
                        layui.layer.msg(data.msg);
                    }
                }
            },
            error: function (data) {
                if (cberror) {
                    cberror(data);
                } else {
                    layui.layer.msg(data);
                }

            }
        })
    },

    getView: function (url, getData, cbsuccess, cberror) {
        let contentType_ = "application/x-www-form-urlencoded";
        let url_ = hs_utils.getViewUrl(url);
        let data_ = getData;
        $.ajax(url_, {
            type: "GET",
            data: data_,
            xhrFields: {
                withCredentials: true
            },
            contentType: contentType_,
            success: function (data) {
                if (cbsuccess) {
                    cbsuccess(data);
                } else {
                    layui.layer.msg(data);
                }
            },
            error: function (data) {
                if (cberror) {
                    cberror(data);
                } else {
                    layui.layer.msg(data);
                }

            }
        })
    },

}