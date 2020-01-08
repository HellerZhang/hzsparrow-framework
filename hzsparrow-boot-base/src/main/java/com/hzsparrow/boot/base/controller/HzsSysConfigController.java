package com.hzsparrow.boot.base.controller;

import com.hzsparrow.boot.base.component.BaseController;
import com.hzsparrow.boot.base.entity.HzsSysConfig;
import com.hzsparrow.boot.base.service.HzsSysConfigService;
import com.hzsparrow.framework.model.result.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hzs/sysconfig")
public class HzsSysConfigController extends BaseController {

    @Autowired
    private HzsSysConfigService hzsSysConfigService;

    /**
     * 修改系统参数
     *
     * @param list
     * @return
     */
    @RequestMapping("/edit")
    public ResultDTO<Object> edit(@RequestBody List<HzsSysConfig> list) {
        return hzsSysConfigService.edit(list, getSessionUser());
    }

    /**
     * 获取所有系统参数
     *
     * @param hsscGroup
     * @return
     */
    @RequestMapping("/findAll")
    public ResultDTO<List<HzsSysConfig>> findAll(String hsscGroup) {
        return hzsSysConfigService.findAll(hsscGroup);
    }

    /**
     * 根据ID获取系统参数
     *
     * @param hsscId
     * @return
     */
    @RequestMapping("/getById")
    public ResultDTO<HzsSysConfig> getById(Integer hsscId) {
        return hzsSysConfigService.getById(hsscId);
    }

    /**
     * 通过参数标志获取参数信息
     *
     * @param flag
     * @return
     */
    @RequestMapping("/getByFlag")
    public ResultDTO<HzsSysConfig> getByFlag(String flag) {
        return hzsSysConfigService.getByFlag(flag);
    }
}
