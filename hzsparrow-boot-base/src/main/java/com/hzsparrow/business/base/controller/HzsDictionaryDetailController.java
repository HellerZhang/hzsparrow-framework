package com.hzsparrow.business.base.controller;

import com.hzsparrow.business.base.component.BaseController;
import com.hzsparrow.business.base.entity.HzsDictionaryDetail;
import com.hzsparrow.business.base.service.HzsDictionaryDetailService;
import com.hzsparrow.framework.model.page.PageResultDTO;
import com.hzsparrow.framework.model.result.ResultDTO;
import com.hzsparrow.framework.utils.tree.DefaultTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hzs/dictionarydetail")
public class HzsDictionaryDetailController extends BaseController {

    @Autowired
    private HzsDictionaryDetailService hzsDictionaryDetailService;

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @RequestMapping("/save")
    public ResultDTO<Object> save(HzsDictionaryDetail entity) {
        return hzsDictionaryDetailService.save(entity);
    }

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    @RequestMapping("/edit")
    public ResultDTO<Object> edit(HzsDictionaryDetail entity) {
        return hzsDictionaryDetailService.edit(entity);
    }

    /**
     * 删除
     *
     * @param hsddId
     * @return
     */
    @RequestMapping("/remove")
    public ResultDTO<Object> remove(String[] hsddId) {
        return hzsDictionaryDetailService.remove(hsddId);
    }

    /**
     * 根据字典类型ID删除
     *
     * @param hsdtId
     * @return
     */
    @RequestMapping("/removeByHsdtId")
    public ResultDTO<Object> removeByHsdtId(String hsdtId) {
        return hzsDictionaryDetailService.removeByHsdtId(hsdtId);
    }

    /**
     * 分页查询字典详细信息
     *
     * @param hsdtId
     * @param hsddName
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/pageFindByHsdtId")
    public ResultDTO<PageResultDTO<HzsDictionaryDetail>> pageFindByHsdtId(String hsdtId, String hsddName, Integer page, Integer size) {
        return hzsDictionaryDetailService.pageFindByHsdtId(hsdtId, hsddName, page, size);
    }

    /**
     * 查询所有字典详细信息
     *
     * @param hsdtId
     * @param hsddName
     * @return
     */
    @RequestMapping("/findByHsdtId")
    public ResultDTO<List<HzsDictionaryDetail>> findByHsdtId(String hsdtId, String hsddName, String parentId) {
        return hzsDictionaryDetailService.findByHsdtId(hsdtId, hsddName, parentId);
    }

    /**
     * 查询树形字典详情信息
     *
     * @param hsdtId
     * @param hsddName
     * @return
     */
    @RequestMapping("/findTreeByHsdtId")
    public ResultDTO<List<DefaultTree<HzsDictionaryDetail>>> findTreeByHsdtId(String hsdtId, String hsddName) {
        return hzsDictionaryDetailService.findTreeByHsdtId(hsdtId, hsddName);
    }
}
