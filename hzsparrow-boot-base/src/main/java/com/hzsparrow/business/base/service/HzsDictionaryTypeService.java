package com.hzsparrow.business.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hzsparrow.business.base.entity.HzsDictionaryType;
import com.hzsparrow.business.base.mapper.HzsDictionaryTypeMapper;
import com.hzsparrow.framework.model.page.PageResultDTO;
import com.hzsparrow.framework.model.result.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * 字典类型
 */
@Service
public class HzsDictionaryTypeService {

    @Autowired
    private HzsDictionaryTypeMapper hzsDictionaryTypeMapper;

    @Autowired
    private HzsDictionaryDetailService hzsDictionaryDetailService;

    /**
     * 新增字典类型
     *
     * @param entity
     * @return
     */
    @Transactional
    public ResultDTO<Object> save(HzsDictionaryType entity) {
        entity.setHsdtId(UUID.randomUUID().toString());
        hzsDictionaryTypeMapper.insert(entity);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 修改字典类型
     *
     * @param entity
     * @return
     */
    @Transactional
    public ResultDTO<Object> edit(HzsDictionaryType entity) {
        hzsDictionaryTypeMapper.updateByPrimaryKey(entity);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 删除字典类型
     *
     * @param hsdtId
     * @return
     */
    @Transactional
    public ResultDTO<Object> remove(String hsdtId) {
        hzsDictionaryDetailService.removeByHsdtId(hsdtId);
        hzsDictionaryTypeMapper.deleteByPrimaryKey(hsdtId);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 分页查询字典类型
     *
     * @param hsdtName
     * @param page
     * @param size
     * @return
     */
    public ResultDTO<PageResultDTO<HzsDictionaryType>> pageFindAll(String hsdtName, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Page<HzsDictionaryType> pageResult = (Page<HzsDictionaryType>) hzsDictionaryTypeMapper.pageFindAll(hsdtName);
        PageResultDTO<HzsDictionaryType> result = new PageResultDTO<>(pageResult);
        return ResultDTO.getDataSuccess(result);
    }

}
