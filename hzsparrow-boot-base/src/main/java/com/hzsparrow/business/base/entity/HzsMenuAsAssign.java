package com.hzsparrow.business.base.entity;

import lombok.Getter;

/**
 * 菜单分配模块-分配状态菜单实体
 */
@Getter
public class HzsMenuAsAssign extends HzsMenu {

    /**
     * 是否已分配
     */
    boolean isAssigned;

    public void setAssigned(Integer isAssigned) {
        if (isAssigned == 1) {
            this.isAssigned = true;
        } else {
            this.isAssigned = false;
        }
    }
}
