package com.hzsparrow.boot.base.controller;

import com.hzsparrow.boot.base.component.BaseController;
import com.hzsparrow.boot.base.entity.HzsUser;
import com.hzsparrow.boot.base.service.HzsUserService;
import com.hzsparrow.boot.base.vo.HzsUserPageVO;
import com.hzsparrow.framework.model.page.PageQO;
import com.hzsparrow.framework.model.page.PageResultDTO;
import com.hzsparrow.framework.model.result.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hzs/user")
public class HzsUserController extends BaseController {

    @Autowired
    private HzsUserService hzsUserService;

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @RequestMapping("/save")
    public ResultDTO<Object> save(HzsUser entity) {
        return hzsUserService.save(entity, getSessionUser());
    }

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @RequestMapping("/edit")
    public ResultDTO<Object> edit(HzsUser entity) {
        return hzsUserService.edit(entity, getSessionUser());
    }

    /**
     * 删除
     *
     * @param hsuId
     * @return
     */
    @RequestMapping("/remove")
    public ResultDTO<Object> remove(String[] hsuId) {
        return hzsUserService.remove(hsuId);
    }

    /**
     * 修改密码
     *
     * @param hsuId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("/changePassword")
    public ResultDTO<Object> changePassword(String hsuId, String oldPassword, String newPassword) {
        return hzsUserService.changePassword(hsuId, oldPassword, newPassword);
    }

    /**
     * 分页查询
     *
     * @param hsuName
     * @param mobile
     * @param pageQO
     * @return
     */
    @RequestMapping("/pageFindAll")
    public ResultDTO<PageResultDTO<HzsUserPageVO>> pageFindAll(String hsuName, String mobile, PageQO pageQO) {
        return hzsUserService.pageFindAll(hsuName, mobile, pageQO);
    }

    /**
     * 通过账号获取信息
     *
     * @param hsuAccount
     * @return
     */
    @RequestMapping("/getByAccount")
    public ResultDTO<HzsUser> getByAccount(String hsuAccount) {
        return hzsUserService.getByAccount(hsuAccount);
    }

    /**
     * 通过ID获取信息
     *
     * @param hsuId
     * @return
     */
    @RequestMapping("/getById")
    public ResultDTO<HzsUser> getById(String hsuId) {
        return hzsUserService.getById(hsuId);
    }
}
