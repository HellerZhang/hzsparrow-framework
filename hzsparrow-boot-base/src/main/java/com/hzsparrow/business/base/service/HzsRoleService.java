package com.hzsparrow.business.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hzsparrow.business.base.entity.HzsRole;
import com.hzsparrow.business.base.mapper.HzsRoleMapper;
import com.hzsparrow.business.base.vo.LoginVO;
import com.hzsparrow.framework.model.page.PageQO;
import com.hzsparrow.framework.model.page.PageResultDTO;
import com.hzsparrow.framework.model.result.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HzsRoleService {

    @Autowired
    private HzsRoleMapper hzsRoleMapper;

    @Autowired
    private HzsRoleMenuService hzsRoleMenuService;

    /**
     * 新增角色
     *
     * @param entity
     * @param user
     * @return
     */
    @Transactional
    public ResultDTO<Object> save(HzsRole entity, LoginVO user) {
        entity.setHsrId(UUID.randomUUID().toString());
        if (entity.getSortNum() == null) {
            Integer maxSort = hzsRoleMapper.getMaxSort();
            entity.setSortNum(maxSort == null ? 1 : maxSort + 1);
        }
        hzsRoleMapper.insert(entity);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 修改角色
     *
     * @param entity
     * @param user
     * @return
     */
    @Transactional
    public ResultDTO<Object> edit(HzsRole entity, LoginVO user) {
        hzsRoleMapper.updateByPrimaryKey(entity);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 删除角色
     *
     * @param hsrIds
     * @return
     */
    @Transactional
    public ResultDTO<Object> remove(String[] hsrIds) {
        for (String hsrId : hsrIds) {
            // 删除角色权限
            hzsRoleMenuService.removeByRoleId(hsrId);
            // 删除角色
            hzsRoleMapper.deleteByPrimaryKey(hsrId);
        }
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 根据ID获取角色信息
     *
     * @param hsrId
     * @return
     */
    public ResultDTO<HzsRole> getById(String hsrId) {
        HzsRole entity = hzsRoleMapper.selectByPrimaryKey(hsrId);
        return ResultDTO.getDataSuccess(entity);
    }

    /**
     * 获取所有角色
     *
     * @param hsrName
     * @return
     */
    public ResultDTO<List<HzsRole>> findAll(String hsrName) {
        List<HzsRole> roleList = hzsRoleMapper.selectAll(hsrName);
        return ResultDTO.getDataSuccess(roleList);
    }

    /**
     * 分页查询角色
     *
     * @param hsrName
     * @param pageQO
     * @return
     */
    public ResultDTO<PageResultDTO<HzsRole>> pageFindAll(String hsrName, PageQO pageQO) {
        PageHelper.startPage(pageQO.getPage(), pageQO.getSize());
        List<HzsRole> roleList = hzsRoleMapper.selectAll(hsrName);
        return ResultDTO.getDataSuccess(new PageResultDTO<>((Page<HzsRole>) roleList));
    }
}
