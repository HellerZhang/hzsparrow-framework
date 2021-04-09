/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils.tree;

/**
 * 树形数据创建者接口
 * 
 * @author Heller.Zhang
 * @since 2019年6月21日 下午2:21:57
 */
@SuppressWarnings("rawtypes")
public interface TreeModelCreator<CT extends Tree<CT,AT>, AT extends TreeModelSource> {

    /**
     * 创建树形节点数据
     * 
     * @param source
     * @return
     * @author Heller.Zhang
     * @since 2019年6月21日 下午2:24:11
     */
    public CT creatTreeModel(AT source);
}
