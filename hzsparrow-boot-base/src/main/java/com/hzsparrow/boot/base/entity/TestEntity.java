package com.hzsparrow.boot.base.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class TestEntity {

    @Getter
    @Setter
    @NotNull(message = "id不可为空")
    private Integer id;

}
