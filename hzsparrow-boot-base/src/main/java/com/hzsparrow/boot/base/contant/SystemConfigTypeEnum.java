package com.hzsparrow.boot.base.contant;

import lombok.Getter;

/**
 * 系统参数类型
 */
@Getter
public enum SystemConfigTypeEnum {
    TEXT(1, "文本"),
    SINGLE(2, "单选"),
    MULTIPLE(3, "多选"),
    TIME(4, "时间"),
    SWITCH(5, "开关"),
    NUMERIC(6, "数值"),
    LOCATION(7, "文件位置"),
    ;
    private Integer value;

    private String name;

    SystemConfigTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
