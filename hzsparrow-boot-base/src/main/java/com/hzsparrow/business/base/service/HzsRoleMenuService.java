package com.hzsparrow.business.base.service;

import com.hzsparrow.business.base.entity.HzsMenu;
import com.hzsparrow.business.base.entity.HzsMenuAsAssign;
import com.hzsparrow.business.base.entity.HzsRoleMenu;
import com.hzsparrow.business.base.mapper.HzsRoleMenuMapper;
import com.hzsparrow.framework.model.result.ResultDTO;
import com.hzsparrow.framework.utils.tree.DefaultTree;
import com.hzsparrow.framework.utils.tree.TreeModelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HzsRoleMenuService {

    @Autowired
    private HzsRoleMenuMapper hzsRoleMenuMapper;

    private TreeModelUtils treeModelUtils = new TreeModelUtils();

    /**
     * 为角色分配菜单
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @Transactional
    public ResultDTO<Object> assign(String roleId, String[] menuIds) {
        List<HzsRoleMenu> list = hzsRoleMenuMapper.findByRoleId(roleId);
        if (list == null) {
            list = new ArrayList<>();
        }
        if (menuIds == null) {
            menuIds = new String[0];
        }
        // 查找已删除的
        for (HzsRoleMenu entity : list) {
            boolean isDeleted = true;
            for (String menuId : menuIds) {
                if (StringUtils.equals(entity.getHsmId(), menuId)) {
                    isDeleted = false;
                    break;
                }
            }
            if (isDeleted) {
                hzsRoleMenuMapper.deleteByPrimaryKey(entity.getHsrmId());
            }
        }

        // 查找新增的
        for (String menuId : menuIds) {
            boolean isNew = true;
            for (HzsRoleMenu entity : list) {
                if (StringUtils.equals(entity.getHsmId(), menuId)) {
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                HzsRoleMenu entity = new HzsRoleMenu();
                entity.setHsrId(roleId);
                entity.setHsmId(menuId);
                entity.setHsrmId(UUID.randomUUID().toString());
                hzsRoleMenuMapper.insert(entity);
            }
        }
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 通过角色ID删除角色权限
     *
     * @param roleId
     * @return
     */
    @Transactional
    public ResultDTO<Object> removeByRoleId(String roleId) {
        hzsRoleMenuMapper.removeByRoleId(roleId);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 通过菜单删除角色菜单
     *
     * @param menuId
     * @return
     */
    @Transactional
    public ResultDTO<Object> removeByMenuId(String menuId) {
        hzsRoleMenuMapper.removeByMenuId(menuId);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 获取角色菜单
     *
     * @param roleId
     * @return
     */
    public ResultDTO<List<DefaultTree<HzsMenu>>> findMenuByRoleId(String roleId) {
        List<HzsMenu> list = hzsRoleMenuMapper.findMenuByRoleId(roleId);
        List<DefaultTree<HzsMenu>> resultList = treeModelUtils.createCustomTreeWithDefault(list, null);
        return ResultDTO.getDataSuccess(resultList);
    }

    /**
     * 获取角色菜单分配状态
     *
     * @param roleId
     * @return
     */
    public ResultDTO<List<DefaultTree<HzsMenuAsAssign>>> findMenuAsAssignByRoleId(String roleId) {
        List<HzsMenuAsAssign> list = hzsRoleMenuMapper.findMenuAsAssignByRoleId(roleId);
        List<DefaultTree<HzsMenuAsAssign>> resultList = treeModelUtils.createCustomTreeWithDefault(list, null);
        return ResultDTO.getDataSuccess(resultList);
    }
}
