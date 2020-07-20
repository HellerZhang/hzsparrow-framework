package com.hzsparrow.business.base.controller;

import com.hzsparrow.business.base.component.BaseController;
import com.hzsparrow.business.base.entity.HzsMenu;
import com.hzsparrow.business.base.service.HzsMenuService;
import com.hzsparrow.framework.model.result.ResultDTO;
import com.hzsparrow.framework.utils.tree.DefaultTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hzs/menu")
public class HzsMenuController extends BaseController {

    @Autowired
    private HzsMenuService hzsMenuService;

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @RequestMapping("/save")
    public ResultDTO<Object> save(HzsMenu entity) {
        return hzsMenuService.save(entity, getSessionUser());
    }

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @RequestMapping("/edit")
    public ResultDTO<Object> edit(HzsMenu entity) {
        return hzsMenuService.edit(entity, getSessionUser());
    }

    /**
     * 删除
     *
     * @param hsmId
     * @return
     */
    @RequestMapping("/remove")
    public ResultDTO<Object> remove(String[] hsmId) {
        return hzsMenuService.remove(hsmId);
    }

    /**
     * 获取全部菜单的树形结构数据
     *
     * @return
     */
    @RequestMapping("/findMenuTree")
    public ResultDTO<List<DefaultTree<HzsMenu>>> findMenuTree() {
        return hzsMenuService.findMenuTree();
    }
}
