/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils.tree;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构
 * 
 * @author Heller.Zhang
 * @since 2019年6月21日 下午1:55:53
 */
@SuppressWarnings("rawtypes")
public interface Tree<CT extends Tree<CT,AT>, AT extends TreeModelSource> {

    /**
     * 获取本级ID
     * 
     * @return
     * @author Heller.Zhang
     * @since 2019年6月20日 下午6:45:04
     */
    public Object getId();

    /**
     * 获取子集
     *
     * @return
     */
    public List<CT> getChild();

    /**
     * 设置子集
     * @param child
     */
    public void setChild(List<CT> child);

    /**
     * 本级增加一个子集
     * 
     * @param onceChild
     * @author Heller.Zhang
     * @since 2019年6月21日 下午2:07:40
     */
      default public void putChild(CT onceChild){
          List<CT> child = getChild();
          if(CollectionUtils.isEmpty(child)){
              child = new ArrayList<CT>();
              setChild(child);
          }
          child.add(onceChild);
      }

    /**
     * 将传入的list全部装入子集
     * 
     * @param listChild
     * @author Heller.Zhang
     * @since 2019年6月21日 下午2:07:35
     */
    default public void putAllChild(List<CT> listChild){
        List<CT> child = getChild();
        if(CollectionUtils.isEmpty(child)){
            child = new ArrayList<CT>();
            setChild(child);
        }
        child.addAll(listChild);
    }
}
