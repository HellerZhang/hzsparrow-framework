package com.hzsparrow.boot.base.controller;

import com.hzsparrow.boot.base.component.BaseController;
import com.hzsparrow.boot.base.entity.HzsFile;
import com.hzsparrow.boot.base.service.HzsFileService;
import com.hzsparrow.boot.base.vo.LoginVO;
import com.hzsparrow.framework.model.page.PageQO;
import com.hzsparrow.framework.model.page.PageResultDTO;
import com.hzsparrow.framework.model.result.ResultDTO;
import com.hzsparrow.framework.utils.files.FileInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hzs/file")
public class HzsFileController extends BaseController {

    @Autowired
    private HzsFileService hzsFileService;

    /**
     * 批量上传并保存文件信息
     *
     * @param relationId
     * @param request
     * @return
     */
    @RequestMapping("/saveAll")
    public ResultDTO<Map<String, HzsFile>> save(String relationId, String relationFlag, HttpServletRequest request) {
        return hzsFileService.save(relationId, relationFlag, request, getSessionUser());
    }

    /**
     * 上传单个文件，并保存文件信息
     *
     * @param relationId
     * @param relationFlag
     * @param file
     * @return
     */
    @RequestMapping("/save")
    public ResultDTO<HzsFile> save(String relationId, String relationFlag, @RequestParam("uploadFile") MultipartFile file) {
        return hzsFileService.save(relationId, relationFlag, file, getSessionUser());
    }

    /**
     * 删除附件
     *
     * @param hsfId
     * @return
     */
    @RequestMapping("/remove")
    public ResultDTO<Object> remove(String[] hsfId) {
        return hzsFileService.remove(hsfId);
    }

    /**
     * 查询所有文件
     *
     * @param relationId
     * @return
     */
    @RequestMapping("/findByRelationId")
    public ResultDTO<List<HzsFile>> findByRelationId(String relationId) {
        return hzsFileService.findByRelationId(relationId);
    }

    /**
     * 分页查询所有文件
     *
     * @param relationId
     * @param pageQO
     * @return
     */
    @RequestMapping("/pageFindByRelationId")
    public ResultDTO<PageResultDTO<HzsFile>> pageFindByRelationId(String relationId, PageQO pageQO) {
        return hzsFileService.pageFindByRelationId(relationId, pageQO);
    }

    /**
     * 上传多个文件，并返回上传的文件信息集合
     *
     * @param request
     * @return
     */
    @RequestMapping("/upload")
    public ResultDTO<Map<String, FileInfoModel>> upload(HttpServletRequest request) {
        return hzsFileService.upload(request);
    }

    /**
     * 下载文件
     *
     * @param hsfId
     */
    @RequestMapping("/download")
    public void download(String hsfId, HttpServletRequest request, HttpServletResponse response) {
        hzsFileService.download(hsfId, request, response);
    }
}
