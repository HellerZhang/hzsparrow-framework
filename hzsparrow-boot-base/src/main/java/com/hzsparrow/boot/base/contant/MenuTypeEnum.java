package com.hzsparrow.boot.base.contant;

import lombok.Getter;

/**
 * 角色类型枚举
 */
@Getter
public enum MenuTypeEnum {
    INTERNAL(1, "内链菜单"),// NL
    EXTERNAL(2, "外链菜单"),
    FEATURES(3, "功能菜单"),
    ;

    private Integer value;

    private String name;

    private MenuTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
