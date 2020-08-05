package com.hzsparrow.business.base.controller;

import com.hzsparrow.business.base.component.BaseController;
import com.hzsparrow.business.base.entity.HzsDictionaryType;
import com.hzsparrow.business.base.service.HzsDictionaryTypeService;
import com.hzsparrow.framework.model.page.PageResultDTO;
import com.hzsparrow.framework.model.result.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典类型
 */
@RestController
@RequestMapping("/hzs/dictionarytype")
public class HzsDictionaryTypeController extends BaseController {

    @Autowired
    private HzsDictionaryTypeService hzsDictionaryTypeService;

    /**
     * 保存字典类型
     *
     * @param entity
     * @return
     */
    @RequestMapping("/save")
    public ResultDTO<Object> save(HzsDictionaryType entity){
        return hzsDictionaryTypeService.save(entity);
    }

    /**
     * 修改字典类型
     *
     * @param entity
     * @return
     */
    @RequestMapping("/edit")
    public ResultDTO<Object> edit(HzsDictionaryType entity){
        return hzsDictionaryTypeService.edit(entity);
    }

    /**
     * 删除字典类型
     *
     * @param hsdtId
     * @return
     */
    @RequestMapping("/remove")
    public ResultDTO<Object> remove(String hsdtId){
        return hzsDictionaryTypeService.remove(hsdtId);
    }

    /**
     * 分页查询字典类型
     *
     * @param hsdtName
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/pageFindAll")
    public ResultDTO<PageResultDTO<HzsDictionaryType>> pageFindAll(String hsdtName, Integer page, Integer size){
        return hzsDictionaryTypeService.pageFindAll(hsdtName, page, size);
    }
}
