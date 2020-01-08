package com.hzsparrow.boot.base.controller;

import com.hzsparrow.boot.base.component.BaseController;
import com.hzsparrow.boot.base.entity.HzsRole;
import com.hzsparrow.boot.base.service.HzsRoleService;
import com.hzsparrow.framework.model.page.PageQO;
import com.hzsparrow.framework.model.page.PageResultDTO;
import com.hzsparrow.framework.model.result.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/hzs/role")
public class HzsRoleController extends BaseController {

    @Autowired
    private HzsRoleService hzsRoleService;

    /**
     * 新增角色
     *
     * @param entity
     * @return
     */
    @RequestMapping("/save")
    public ResultDTO<Object> save(HzsRole entity) {
        return hzsRoleService.save(entity, getSessionUser());
    }

    /**
     * 修改角色
     *
     * @param entity
     * @return
     */
    @RequestMapping("/edit")
    public ResultDTO<Object> edit(HzsRole entity) {
        return hzsRoleService.edit(entity, getSessionUser());
    }

    /**
     * 删除角色
     *
     * @param hsrIds
     * @return
     */
    @RequestMapping("/remove")
    public ResultDTO<Object> remove(String[] hsrIds) {
        return hzsRoleService.remove(hsrIds);
    }

    /**
     * 根据ID获取角色信息
     *
     * @param hsrId
     * @return
     */
    @RequestMapping("/getById")
    public ResultDTO<HzsRole> getById(String hsrId) {
        return hzsRoleService.getById(hsrId);
    }

    /**
     * 获取所有角色
     *
     * @param hsrName
     * @return
     */
    @RequestMapping("/findAll")
    public ResultDTO<List<HzsRole>> findAll(String hsrName) {
        return hzsRoleService.findAll(hsrName);
    }

    /**
     * 分页查询角色
     *
     * @param hsrName
     * @param pageQO
     * @return
     */
    @RequestMapping("/pageFindAll")
    public ResultDTO<PageResultDTO<HzsRole>> pageFindAll(String hsrName, PageQO pageQO) {
        return hzsRoleService.pageFindAll(hsrName, pageQO);
    }
}

