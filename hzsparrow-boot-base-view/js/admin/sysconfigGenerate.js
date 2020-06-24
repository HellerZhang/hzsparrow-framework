var sysconfigGenerate = {
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
        for (let gIndex in groups) {
            resultHtml += "<div class=\"layui-tab-item"
            if (gIndex == 0) {
                resultHtml += " layui-show"
            }
            resultHtml += "\">"
            resultHtml += "<form class='layui-form'>"
            for (let dIndex = 0; dIndex < groups[gIndex].data.length;) {
                let index = dIndex;
                dIndex = (dIndex + 3) > groups[gIndex].data.length ? groups[gIndex].data.length : (dIndex + 3);
                let datas = new Array();
                for (; index < dIndex; index++) {
                    datas.push(groups[gIndex].data[index])
                }
                resultHtml += sysconfigGenerate.generateItemHtml(datas);
            }
            resultHtml += "</form>";
            resultHtml += "</div>";
        }
        return resultHtml;
    },

    generateItemHtml: function (datas) {
        let resultHtml = "<div class=\"layui-form-item\">";
        for (let index in datas) {
            let data = datas[index];
            if (data.hsscType == 5) {
                resultHtml += sysconfigGenerate.generateItemHtml5(data);
            }
        }
        resultHtml += "</div>";
        return resultHtml;
    },

    generateItemHtml5: function (data) {
        let resultHtml =
            "<div class=\"layui-inline\" style=\"width: 30%;\">\n" +
            "   <label class=\"layui-form-label\">" + data.hsscName + "</label>\n" +
            "   <div class=\"layui-input-inline\">\n" +
            "       <input type=\"checkbox\" name=\"" + data.hsscFlag + "\" required lay-verify=\"required\" lay-skin=\"switch\" lay-text=\"是|否\" " +
            (data.hsscValue == '1' ? "checked" : "") + " value=\"1\">\n" +
            "   </div>\n" +
            "</div>";
        return resultHtml;
    },

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