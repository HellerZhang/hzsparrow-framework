let indexManager = {
    treeUrl: "/hzs/rolemenu/findMenuByRoleId",
    sessionUserUrl: "/hzs/getLoginUser",
    logoutUrl: "/hzs/logout",
    sysConfigFindUrl: "/hzs/sysconfig/findAll",
    userImgUrl: '/hzs/file/download?hsfId=',
    sysConfigDatas: null,
    userData: null,
    currViewUrl: null,
    currViewType: null,
    init: function () {
        hs_utils.post(indexManager.sessionUserUrl, {}, function (data) {
            if (data.success) {
                indexManager.userData = data.data;
                $('#indexUserName').html(indexManager.userData.userName);
                if (indexManager.userData.imgId) {
                    $('#indexUserImg').attr('src', hs_utils.getServerUrl(indexManager.userImgUrl + indexManager.userData.imgId));
                }
                indexManager.refreshTree();
                indexManager.refreshSysConfig();
            }
        })
    },

    refreshCurrView: function () {
        if (indexManager.currViewUrl) {
            indexManager.openContentPage(indexManager.currViewUrl, indexManager.currViewType);
        }
    },

    getSysConfig: function (flag) {
        for (let index in indexManager.sysConfigDatas) {
            if (indexManager.sysConfigDatas[index].hsscFlag == flag) {
                return indexManager.sysConfigDatas[index];
            }
        }
        return null;
    },

    refreshSysConfig: function () {
        hs_utils.post(indexManager.sysConfigFindUrl, {}, function (data) {
            if (data.success) {
                indexManager.sysConfigDatas = data.data;
                let sysName = indexManager.getSysConfig('sys_name');
                if (sysName) {
                    $('#sys_name').html(sysName.hsscValue);
                }
            }
        });
    },

    refreshTree: function () {
        hs_utils.post(indexManager.treeUrl, {hsrId: indexManager.userData.powerGuid}, function (data) {
            if (data.success) {
                let treeHtml = indexManager.createTree(data.data);
                $("#navTree").html(treeHtml);
                indexManager.layuiInit();
            }
        });
    },

    layuiInit: function () {
        layui.use(['element', 'form'], function () {
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
        indexManager.currViewUrl = null;
        indexManager.currViewType = null;
        if (url == null || url == "" || url == "#") {
            return;
        }
        if (hsmType == 1) {
            indexManager.currViewUrl = url;
            indexManager.currViewType = hsmType;
            hs_utils.getView(url, {}, function (data) {
                $('.popLayer').remove()
                $("#contentBody").html(data);
                $(document.body).append($("#popLayer").html());
                $('#popLayer').remove()
                layui.form.render();
            });
        } else if (hsmType == 2) {
            window.open(url);
        }
    }
};
indexManager.init();
