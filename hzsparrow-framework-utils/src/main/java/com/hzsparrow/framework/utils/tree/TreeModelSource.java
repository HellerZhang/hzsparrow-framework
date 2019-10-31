/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 树形数据源
 * 
 * @author Heller.Zhang
 * @since 2019年6月20日 下午6:42:47
 */
public interface TreeModelSource {

    /**
     * 获取本级ID
     * 
     * @return
     * @author Heller.Zhang
     * @since 2019年6月20日 下午6:45:04
     */
    @JsonIgnore
    public Object getTreeModelId();

    /**
     * 获取本级名称
     * 
     * @return
     * @author Heller.Zhang
     * @since 2019年6月20日 下午6:45:15
     */
    @JsonIgnore
    public String getTreeModelName();

    /**
     * 获取上级ID
     * 
     * @return
     * @author Heller.Zhang
     * @since 2019年6月20日 下午6:45:22
     */
    @JsonIgnore
    public Object getTreeModelParentId();
}
