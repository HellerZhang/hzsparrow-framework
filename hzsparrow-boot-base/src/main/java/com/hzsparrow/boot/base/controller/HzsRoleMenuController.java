package com.hzsparrow.boot.base.controller;

import com.hzsparrow.boot.base.component.BaseController;
import com.hzsparrow.boot.base.entity.HzsMenu;
import com.hzsparrow.boot.base.entity.HzsMenuAsAssign;
import com.hzsparrow.boot.base.service.HzsRoleMenuService;
import com.hzsparrow.framework.model.result.ResultDTO;
import com.hzsparrow.framework.utils.tree.DefaultTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色菜单模块
 */
@RestController
@RequestMapping("/hzs/rolemenu")
public class HzsRoleMenuController extends BaseController {

    @Autowired
    private HzsRoleMenuService hzsRoleMenuService;

    /**
     * @param hsrId
     * @param hsmIds
     * @return
     */
    @RequestMapping("/assign")
    public ResultDTO<Object> assign(String hsrId, String[] hsmIds) {
        return hzsRoleMenuService.assign(hsrId, hsmIds);
    }

    /**
     * 获取角色菜单
     *
     * @param hsrId
     * @return
     */
    @RequestMapping("/findMenuByRoleId")
    public ResultDTO<List<DefaultTree<HzsMenu>>> findMenuByRoleId(String hsrId) {
        return hzsRoleMenuService.findMenuByRoleId(hsrId);
    }

    /**
     * 获取角色菜单分配状态
     *
     * @param hsrId
     * @return
     */
    @RequestMapping("/findMenuAsAssignByRoleId")
    public ResultDTO<List<DefaultTree<HzsMenuAsAssign>>> findMenuAsAssignByRoleId(String hsrId) {
        return hzsRoleMenuService.findMenuAsAssignByRoleId(hsrId);
    }
}
