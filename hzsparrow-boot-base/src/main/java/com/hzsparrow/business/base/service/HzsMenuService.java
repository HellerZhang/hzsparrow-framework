package com.hzsparrow.business.base.service;

import com.hzsparrow.business.base.entity.HzsMenu;
import com.hzsparrow.business.base.mapper.HzsMenuMapper;
import com.hzsparrow.business.base.vo.LoginVO;
import com.hzsparrow.framework.model.result.ResultDTO;
import com.hzsparrow.framework.utils.tree.DefaultTree;
import com.hzsparrow.framework.utils.tree.TreeModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class HzsMenuService {

    @Autowired
    private HzsMenuMapper hzsMenuMapper;

    @Autowired
    private HzsRoleMenuService hzsRoleMenuService;

    private TreeModelUtils treeModelUtils = new TreeModelUtils();

    /**
     * 新增
     *
     * @param entity
     * @param user
     * @return
     */
    @Transactional
    public ResultDTO<Object> save(HzsMenu entity, LoginVO user) {
        entity.setHsmId(UUID.randomUUID().toString());
        hzsMenuMapper.insert(entity);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 修改
     *
     * @param entity
     * @param user
     * @return
     */
    @Transactional
    public ResultDTO<Object> edit(HzsMenu entity, LoginVO user) {
        hzsMenuMapper.updateByPrimaryKey(entity);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 删除
     *
     * @param hsmId
     * @return
     */
    @Transactional
    public ResultDTO<Object> remove(String[] hsmId) {
        for (String id : hsmId) {
            // 删除角色权限
            hzsRoleMenuService.removeByMenuId(id);
            // 删除菜单
            hzsMenuMapper.deleteByPrimaryKey(id);
        }
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 获取全部菜单的树形结构数据
     *
     * @return
     */
    public ResultDTO<List<DefaultTree<HzsMenu>>> findMenuTree() {
        List<HzsMenu> list = hzsMenuMapper.selectAll();
        List<DefaultTree<HzsMenu>> resultList = treeModelUtils.createCustomTreeWithDefault(list, null);
        return ResultDTO.getDataSuccess(resultList);
    }

}
