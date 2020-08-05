package com.hzsparrow.business.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hzsparrow.business.base.entity.HzsDictionaryDetail;
import com.hzsparrow.business.base.mapper.HzsDictionaryDetailMapper;
import com.hzsparrow.framework.model.page.PageResultDTO;
import com.hzsparrow.framework.model.result.ResultDTO;
import com.hzsparrow.framework.utils.tree.DefaultTree;
import com.hzsparrow.framework.utils.tree.TreeModelUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 字典详细
 */
@Service
public class HzsDictionaryDetailService {

    @Autowired
    private HzsDictionaryDetailMapper hzsDictionaryDetailMapper;

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @Transactional
    public ResultDTO<Object> save(HzsDictionaryDetail entity) {
        boolean isHaveSameName = this.isHaveSameName(entity.getHsdtId(), entity.getHsddName(), null);
        if (isHaveSameName) {
            throw new RuntimeException("名称已存在，请检查！");
        }
        entity.setHsddId(UUID.randomUUID().toString());
        if (entity.getSortNum() == null) {
            Integer maxSort = hzsDictionaryDetailMapper.getMaxSortNum(entity.getHsdtId());
            if (maxSort == null) {
                maxSort = 0;
            }
            entity.setSortNum(++maxSort);
        }
        if (StringUtils.isBlank(entity.getParentId())) {
            entity.setParentId(null);
        }
        hzsDictionaryDetailMapper.insert(entity);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @Transactional
    public ResultDTO<Object> edit(HzsDictionaryDetail entity) {
        boolean isHaveSameName = this.isHaveSameName(entity.getHsdtId(), entity.getHsddName(), entity.getHsddId());
        if (isHaveSameName) {
            throw new RuntimeException("名称已存在，请检查！");
        }
        if (StringUtils.isBlank(entity.getParentId())) {
            entity.setParentId(null);
        }
        hzsDictionaryDetailMapper.updateByPrimaryKey(entity);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 删除
     *
     * @param hsddId
     * @return
     */
    @Transactional
    public ResultDTO<Object> remove(String[] hsddId) {
        for (String id : hsddId) {
            hzsDictionaryDetailMapper.deleteByPrimaryKey(id);
        }
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 根据字典类型ID删除
     *
     * @param hsdtId
     * @return
     */
    @Transactional
    public ResultDTO<Object> removeByHsdtId(String hsdtId) {
        hzsDictionaryDetailMapper.deleteByHsdtId(hsdtId);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 分页查询字典详细信息
     *
     * @param hsdtId
     * @param hsddName
     * @param page
     * @param size
     * @return
     */
    public ResultDTO<PageResultDTO<HzsDictionaryDetail>> pageFindByHsdtId(String hsdtId, String hsddName, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        Page<HzsDictionaryDetail> pageResult = (Page<HzsDictionaryDetail>) hzsDictionaryDetailMapper.pageFindByHsdtId(hsdtId, hsddName, null);
        PageResultDTO<HzsDictionaryDetail> result = new PageResultDTO<>(pageResult);
        return ResultDTO.getDataSuccess(result);
    }

    /**
     * 查询所有字典详细信息
     *
     * @param hsdtId
     * @param hsddName
     * @return
     */
    public ResultDTO<List<HzsDictionaryDetail>> findByHsdtId(String hsdtId, String hsddName, String parentId) {
        List<HzsDictionaryDetail> list = hzsDictionaryDetailMapper.pageFindByHsdtId(hsdtId, hsddName, parentId);
        return ResultDTO.getDataSuccess(list);
    }

    /**
     * 查询树形字典详情信息
     *
     * @param hsdtId
     * @param hsddName
     * @return
     */
    public ResultDTO<List<DefaultTree<HzsDictionaryDetail>>> findTreeByHsdtId(String hsdtId, String hsddName) {
        List<HzsDictionaryDetail> list = hzsDictionaryDetailMapper.pageFindByHsdtId(hsdtId, hsddName, null);
        List<DefaultTree<HzsDictionaryDetail>> treeList = new TreeModelUtils().createCustomTreeWithDefault(list, null);
        return ResultDTO.getDataSuccess(treeList);
    }

    private boolean isHaveSameName(String hsdtId, String hsddName, String hsddId) {
        Integer count = hzsDictionaryDetailMapper.isHaveSameName(hsdtId, hsddName, hsddId);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
