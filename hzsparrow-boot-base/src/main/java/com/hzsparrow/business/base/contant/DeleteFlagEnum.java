package com.hzsparrow.business.base.contant;

import lombok.Getter;

@Getter
public enum DeleteFlagEnum {
    NORMAL(1, "正常"),// NL
    DESTROY(-1, "已删"),
    ;

    private Integer value;

    private String name;

    DeleteFlagEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
