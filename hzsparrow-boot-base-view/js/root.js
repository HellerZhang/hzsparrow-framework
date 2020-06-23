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

    /**
     * 渲染分页表格
     *
     * @param config {elem: 表格的id, url: 数据接口, toolbar: 左侧操作按钮（可为空，默认使用新增按钮）,cols: 表头}
     * @param eventConfig {filter: 表格的filter, toolbar: 左侧按钮的事件[{name: 事件名, handler: 事件函数(自动传入当前行数据信息)}], tool: 表格内操作按钮事件[{name: 事件名, handler:事件函数}] }
     * @returns {*}
     */
    renderPageTable: function (config, eventConfig) {
        let table = layui.table;
        let tableConfig = hs_utils.getDefaultTableConfig();
        for (var param in config) {
            if (param == 'url') {
                config[param] = hs_utils.getServerUrl(config[param]);
            }
            tableConfig[param] = config[param];
        }
        let pageTable = table.render(tableConfig);
        if (eventConfig && eventConfig.filter) {
            if (eventConfig.toolbar) {
                table.on('toolbar(' + eventConfig.filter + ')', function (obj) {
                    let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    for (let index in eventConfig.toolbar) {
                        let event = eventConfig.toolbar[index];
                        if (layEvent === event.name) {
                            event.handler(obj);
                        }
                    }
                });
            }
            if (eventConfig.tool) {
                table.on('tool(' + eventConfig.filter + ')', function (obj) {
                    let data = obj.data; //获得当前行数据
                    let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                    for (let index in eventConfig.tool) {
                        let event = eventConfig.tool[index];
                        if (layEvent === event.name) {
                            event.handler(obj);
                        }
                    }
                });
            }
        }
        return pageTable;
    },

    getDefaultTableConfig: function () {
        let tableConfig = {
            height: 430,
            page: true, //开启分页
            toolbar: '#tableToolBar',
            defaultToolbar: ['filter', 'exports'],
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.data //解析数据列表
                }
            },
            request: {
                pageName: 'page', //页码的参数名称，默认：page
                limitName: 'size' //每页数据量的参数名，默认：limit
            },
            response: {
                statusCode: 200 //规定成功的状态码，默认：0
            }
        };
        return tableConfig;
    }
}