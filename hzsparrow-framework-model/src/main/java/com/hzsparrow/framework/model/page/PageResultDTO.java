package com.hzsparrow.framework.model.page;

import com.github.pagehelper.Page;
import com.hzsparrow.framework.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResultDTO<T> extends BaseModel {

    /**
     * 最大条数
     */
    private Long total;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页条数
     */
    private Integer size;

    /**
     * 本次查询数据
     */
    private List<T> data;

    /**
     * 其他返回数据
     */
    private Object other;

    public PageResultDTO(){}

    public PageResultDTO(Page<T> page){
        this.total = page.getTotal();
        this.page = page.getPageNum();
        this.size = page.getPageSize();
        this.data = page.getResult();
    }
}
