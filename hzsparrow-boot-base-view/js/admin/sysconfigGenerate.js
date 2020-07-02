var sysconfigGenerate = {
    dateItemIds: null,
    // 渲染表单字段
    rendForm: function () {
        if (sysconfigGenerate.dateItemIds.length > 0) {
            let laydate = layui.laydate;
            for (let index in sysconfigGenerate.dateItemIds) {
                laydate.render({
                    elem: '#' + sysconfigGenerate.dateItemIds[index], //指定元素
                    type: 'datetime'
                });
            }
        }
    },

    // 生成分组的html
    generateGroupHtml: function (groups) {
        let resultHtml = "";
        for (let index in groups) {
            resultHtml += "<li";
            if (index == 0) {
                resultHtml += " class=\"layui-this\""
            }
            resultHtml += ">" + groups[index].name + "</li>"
        }
        return resultHtml;
    },

    // 生成各分组的表单
    generateItemsFormHtml: function (groups) {
        let resultHtml = "";
        sysconfigGenerate.dateItemIds = new Array();
        for (let gIndex in groups) {
            // 按分组生成各form
            resultHtml += "<div class=\"layui-tab-item"
            if (gIndex == 0) {
                resultHtml += " layui-show"
            }
            resultHtml += "\">"
            resultHtml += "<form class='layui-form' lay-filter='sysConfigForm" + gIndex + "' data-group='" + groups[gIndex].name + "'>";
            let groupData = groups[gIndex].data;
            for (let dIndex = 0; dIndex < groupData.length;) {// 循环分组的数据生成各字段，每次生成3个，如果遇到多选框则生成1个
                let currMaxIndex = (dIndex + 3) > groupData.length ? groupData.length : (dIndex + 3);
                let datas = new Array();
                for (; dIndex < currMaxIndex; dIndex++) {
                    if (groupData[dIndex].hsscType == 3) {
                        if (datas.length > 0) {
                            break;
                        } else {
                            datas.push(groupData[dIndex])
                            dIndex++;// 当跳出循环时，循环中的自增不会执行，故在此自增
                            break;
                        }
                    }
                    datas.push(groupData[dIndex])
                }
                // 生成表单中的字段
                resultHtml += sysconfigGenerate.generateItemHtml(datas);
            }
            resultHtml += "<div class=\"layui-form-item\">\n" +
                "                <div class=\"layui-input-block\" style=\"text-align: center;margin-left: 0px;\">\n" +
                "                    <button class=\"layui-btn layui-btn-radius layui-btn-normal\" lay-submit lay-filter=\"sysConfigSave\">确认</button>\n" +
                "                </div>\n" +
                "            </div>";
            resultHtml += "</form>";
            resultHtml += "</div>";
        }
        return resultHtml;
    },

    generateItemHtml: function (datas) {
        let resultHtml = "<div class=\"layui-form-item\">";
        for (let index in datas) {
            let data = datas[index];
            resultHtml += sysconfigGenerate.generateItemHtmlFuns[data.hsscType - 1](data);
        }
        resultHtml += "</div>";
        return resultHtml;
    },

    generateItemHtmlFuns: [
        function (data) {
            let resultHtml =
                "<div class=\"layui-inline\" style=\"width: 30%;\">\n" +
                "   <label class=\"layui-form-label\">" + data.hsscName + "</label>\n" +
                "   <div class=\"layui-input-inline\">\n" +
                "       <input type=\"text\" class=\"layui-input\" name=\"" + data.hsscFlag + "\" required lay-verify=\"required\" placeholder=\"请输入" + data.hsscName + "\"\n" +
                "       autocomplete=\"off\"\n value=\"" + data.hsscValue + "\">\n" +
                "   </div>\n" +
                "</div>";
            return resultHtml;
        },// 1文本
        function (data) {
            let resultHtml =
                "<div class=\"layui-inline\" style=\"width: 30%;\">\n" +
                "   <label class=\"layui-form-label\">" + data.hsscName + "</label>\n" +
                "   <div class=\"layui-input-inline\">\n" +
                "        <select name=\"" + data.hsscFlag + "\" lay-verify=\"required\">\n";
            let values = data.hsscReferValue.split(",");
            for (let index in values) {
                let valueAndName = values[index].split("|");
                let value = valueAndName[0];
                let name = valueAndName.length > 1 ? valueAndName[1] : value;
                let select = data.hsscValue == value ? "selected" : "";
                resultHtml += "<option value=\"" + value + "\" " + select + ">" + name + "</option>\n"
            }
            resultHtml += "        </select>" +
                "   </div>\n" +
                "</div>";
            return resultHtml;
        },// 2单选
        function (data) {
            let resultHtml =
                "<div class=\"layui-inline\" style=\"width: 100%;\">\n" +
                "   <label class=\"layui-form-label\">" + data.hsscName + "</label>\n" +
                "   <div class=\"layui-input-inline\" style=\"width: 80%;\">\n";
            let values = data.hsscReferValue.split(",");
            let checkedValues = data.hsscValue.split(",");
            for (let index in values) {
                let valueAndName = values[index].split("|");
                let value = valueAndName[0];
                let name = valueAndName.length > 1 ? valueAndName[1] : value;
                let checked = "";
                for (let i in checkedValues) {
                    if (value == checkedValues[i]) {
                        checked = "checked";
                        break;
                    }
                }
                resultHtml += "<input type=\"checkbox\" name=\"" + data.hsscFlag + "\" title=\"" + name + "\" " + checked + " value='" + value + "'>"
            }
            resultHtml += "   </div>\n" +
                "</div>";
            return resultHtml;
        },// 3多选
        function (data) {
            let id = "sys_" + data.hsscFlag;
            let resultHtml =
                "<div class=\"layui-inline\" style=\"width: 30%;\">\n" +
                "   <label class=\"layui-form-label\">" + data.hsscName + "</label>\n" +
                "   <div class=\"layui-input-inline\">\n";
            resultHtml += "<input type=\"text\" class='layui-input' id=\"" + id + "\" name=\"" + data.hsscFlag + "\" placeholder='yyyy-MM-dd HH:mm:ss' " +
                "value=\"" + data.hsscValue + "\">";
            resultHtml += "   </div>\n" +
                "</div>";
            sysconfigGenerate.dateItemIds.push(id);
            return resultHtml;
        },// 4时间
        function (data) {
            let resultHtml =
                "<div class=\"layui-inline\" style=\"width: 30%;\">\n" +
                "   <label class=\"layui-form-label\">" + data.hsscName + "</label>\n" +
                "   <div class=\"layui-input-inline\">\n" +
                "       <input type=\"checkbox\" name=\"" + data.hsscFlag + "\" required lay-verify=\"required\" lay-skin=\"switch\" lay-text=\"是|否\" " +
                (data.hsscValue == '1' ? "checked" : "") + " value=\"1\">\n" +
                "   </div>\n" +
                "</div>";
            return resultHtml;
        },// 5开关
        function (data) {
            let resultHtml =
                "<div class=\"layui-inline\" style=\"width: 30%;\">\n" +
                "   <label class=\"layui-form-label\">" + data.hsscName + "</label>\n" +
                "   <div class=\"layui-input-inline\">\n" +
                "       <input type=\"text\" class=\"layui-input\" name=\"" + data.hsscFlag + "\" required lay-verify=\"required|number\" placeholder=\"请输入" + data.hsscName + "\"\n" +
                "       autocomplete=\"off\"\n value=\"" + data.hsscValue + "\">\n" +
                "   </div>\n" +
                "</div>";
            return resultHtml;
        }// 6数值
    ],

    // 获取系统参数中的分组数组
    getGroups: function (data) {
        let groups = new Array();
        for (let index in data) {
            let group = data[index].hsscGroup;
            let isHave = false;
            for (let i in groups) {
                if (groups[i].name === group) {
                    isHave = true;
                    groups[i].data.push(data[index]);
                    break;
                }
            }
            if (!isHave) {
                groups.push({name: group, data: [data[index]]});
            }
        }
        return groups;
    }
}