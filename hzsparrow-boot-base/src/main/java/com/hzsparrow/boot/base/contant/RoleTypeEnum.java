package com.hzsparrow.boot.base.contant;

import lombok.Getter;

/**
 * 角色类型枚举
 */
@Getter
public enum RoleTypeEnum {
    MANAGER(1, "管理"),// NL
    COUNT(2, "普通");

    private Integer value;

    private String name;

    private RoleTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
