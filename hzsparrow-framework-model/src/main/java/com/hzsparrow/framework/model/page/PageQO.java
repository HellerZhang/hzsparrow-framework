package com.hzsparrow.framework.model.page;

import com.hzsparrow.framework.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageQO extends BaseModel {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页条数
     */
    private Integer size;
}
