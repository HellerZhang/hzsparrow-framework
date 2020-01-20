let indexManager = {
    treeUrl: "/hzs/rolemenu/findMenuByRoleId",
    sessionUserUrl: "/hzs/getLoginUser",
    logoutUrl: "/hzs/logout",
    userData: null,

    init: function () {
        hs_utils.post(indexManager.sessionUserUrl, {}, function (data) {
            if (data.success) {
                indexManager.userData = data.data;
                indexManager.refreshTree();
            }
        })
    },

    refreshTree: function () {
        hs_utils.post(indexManager.treeUrl, {hsrId: indexManager.userData.powerGuid}, function (data) {
            if (data.success) {
                let treeHtml = indexManager.createTree(data.data);
                $("#navTree").html(treeHtml);
            }
        });
    },

    createTree: function (data) {
        let treeHtml = "";
        for (let index in data) {
            let child = data[index].child;
            treeHtml += "<li class=\"layui-nav-item";
            if (index == 0 && child.length > 0) {
                treeHtml += " layui-nav-itemed";
            }
            treeHtml += "\">";
            treeHtml += "<a href=\"javascript:indexManager.openContentPage('" + data[index].attribute.url + "'," + data[index].attribute.hsmType + ");\">" + data[index].name + "</a>";
            if (child.length > 0) {
                treeHtml += "<dl class=\"layui-nav-child\">";
                for (let i in child) {
                    treeHtml += "<dd><a href=\"javascript:indexManager.openContentPage('" + child[i].attribute.url + "'," + child[i].attribute.hsmType + ");\">" + child[i].name + "</a></dd>";
                }
                treeHtml += "</dl>";
            }
            treeHtml += "</li>";
        }
        return treeHtml;
    },

    logout: function () {
        hs_utils.post(indexManager.logoutUrl, {}, function (data) {
            if (data.success) {
                window.location.href = "login.html";
            }
        });
    },

    openContentPage: function (url, hsmType) {
        if (url == null || url == "" || url == "#") {
            return;
        }
        if (hsmType == 1) {
            hs_utils.getView(url, {}, function (data) {
                $("#contentBody").html(data);
            });
        } else if (hsmType == 2) {
            window.open(url);
        }
    }
};
indexManager.init();
