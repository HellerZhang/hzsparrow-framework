/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils.tree;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构创建工具
 *
 * @author Heller.Zhang
 * @since 2019年6月20日 下午3:55:43
 */
public class TreeModelUtils {

    public <AT extends TreeModelSource> List<DefaultTree<AT>> createCustomTreeWithDefault(List<AT> sourceList, Object parentId) {
        return createCustomTree(sourceList, parentId, new DefaultTreeModelCreator<AT>());
    }

    /**
     * 创建树形结构树
     *
     * @param sourceList
     * @param parentId
     * @param creator
     * @return
     * @author Heller.Zhang
     * @since 2019年6月20日 下午6:57:29
     */
    public <CT extends Tree<CT, AT>, AT extends TreeModelSource> List<CT> createCustomTree(List<AT> sourceList, Object parentId,
                                                                                           TreeModelCreator<CT, AT> creator) {
        List<CT> thisLevelList = new ArrayList<CT>();
        if (CollectionUtils.isEmpty(sourceList)) {
            return thisLevelList;
        }
        // 查找本级
        for (AT source : sourceList) {
            if (parentId == null) {
                if (source.getTreeModelParentId() == null) {
                    thisLevelList.add(creator.creatTreeModel(source));
                }
            } else {
                if (parentId.equals(source.getTreeModelParentId())) {
                    thisLevelList.add(creator.creatTreeModel(source));
                }
            }
        }
        // 查找子级
        for (CT thisLevelTreeNode : thisLevelList) {
            List<CT> childLevelList = createCustomTree(sourceList, thisLevelTreeNode.getId(), creator);
            thisLevelTreeNode.putAllChild(childLevelList);
        }
        return thisLevelList;
    }

}
