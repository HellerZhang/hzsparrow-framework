package com.hzsparrow.boot.base.service;

import com.hzsparrow.boot.base.entity.HzsSysConfig;
import com.hzsparrow.boot.base.mapper.HzsSysConfigMapper;
import com.hzsparrow.boot.base.vo.LoginVO;
import com.hzsparrow.framework.model.result.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HzsSysConfigService {

    @Autowired
    private HzsSysConfigMapper hzsSysConfigMapper;

    /**
     * 修改系统参数
     *
     * @param list
     * @param user
     * @return
     */
    @Transactional
    public ResultDTO<Object> edit(List<HzsSysConfig> list, LoginVO user) {
        for (HzsSysConfig entity : list) {
            hzsSysConfigMapper.updateByPrimaryKey(entity);
        }
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 获取所有系统参数
     *
     * @param hsscGroup
     * @return
     */
    public ResultDTO<List<HzsSysConfig>> findAll(String hsscGroup) {
        List<HzsSysConfig> resultList = hzsSysConfigMapper.selectAll(hsscGroup);
        return ResultDTO.getDataSuccess(resultList);
    }

    /**
     * 根据ID获取系统参数
     *
     * @param hsscId
     * @return
     */
    public ResultDTO<HzsSysConfig> getById(Integer hsscId) {
        HzsSysConfig entity = hzsSysConfigMapper.selectByPrimaryKey(hsscId);
        return ResultDTO.getDataSuccess(entity);
    }

    /**
     * 通过参数标志获取参数信息
     *
     * @param flag
     * @return
     */
    public ResultDTO<HzsSysConfig> getByFlag(String flag) {
        HzsSysConfig entity = hzsSysConfigMapper.getByFlag(flag);
        return ResultDTO.getDataSuccess(entity);
    }
}
