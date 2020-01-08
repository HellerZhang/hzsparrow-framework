package com.hzsparrow.boot.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hzsparrow.boot.base.entity.HzsFile;
import com.hzsparrow.boot.base.mapper.HzsFileMapper;
import com.hzsparrow.boot.base.vo.LoginVO;
import com.hzsparrow.framework.model.page.PageQO;
import com.hzsparrow.framework.model.page.PageResultDTO;
import com.hzsparrow.framework.model.result.ResultDTO;
import com.hzsparrow.framework.utils.files.FileInfoModel;
import com.hzsparrow.framework.utils.upload.HzSparrowFileUploadUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
public class HzsFileService {

    @Autowired
    private HzsFileMapper hzsFileMapper;

    @Autowired
    private HzSparrowFileUploadUtils hzSparrowFileUploadUtils;

    /**
     * 新增
     *
     * @param relationId
     * @param request
     * @param user
     * @return data为文件信息集合
     */
    @Transactional
    public ResultDTO<Map<String, HzsFile>> save(String relationId, HttpServletRequest request, LoginVO user) {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = req.getFileMap();
        Map<String, HzsFile> map = new HashMap<>();
        for (String name : fileMap.keySet()) {
            MultipartFile file = fileMap.get(name);
            if (file == null) {
                continue;
            }
            ResultDTO<HzsFile> resultDTO = save(relationId, file, user);
            if (resultDTO.isSuccess()) {
                map.put(name, resultDTO.getData());
            }
        }
        return ResultDTO.getDataSuccess(map);
    }

    /**
     * 新增
     *
     * @param relationId
     * @param file
     * @param user
     * @return data为文件信息
     */
    @Transactional
    public ResultDTO<HzsFile> save(String relationId, MultipartFile file, LoginVO user) {
        FileInfoModel fileInfoModel = null;
        try {
            fileInfoModel = hzSparrowFileUploadUtils.upload(file);
        } catch (Exception e) {
            return ResultDTO.getDataFaild(500, "文件上传失败!", null);
        }
        return save(relationId, fileInfoModel, user);
    }

    /**
     * 新增
     *
     * @param relationId
     * @param fileInfoModel
     * @param user
     * @return data为文件信息
     */
    @Transactional
    public ResultDTO<HzsFile> save(String relationId, FileInfoModel fileInfoModel, LoginVO user) {
        HzsFile entity = new HzsFile();
        entity.setFileRegId(relationId);
        entity.setUploadId(user.getUserId());
        entity.setUploadName(user.getUserName());
        return save(entity, fileInfoModel);
    }

    /**
     * 新增
     *
     * @param entity
     * @param fileInfoModel
     * @return data为文件信息
     */
    @Transactional
    public ResultDTO<HzsFile> save(HzsFile entity, FileInfoModel fileInfoModel) {
        entity.setFileOldName(fileInfoModel.getOldName());
        entity.setFileNewName(fileInfoModel.getNewName());
        entity.setFileSuffix(fileInfoModel.getNewName().substring(fileInfoModel.getNewName().lastIndexOf(".")));
        entity.setFileSize(new BigDecimal(fileInfoModel.getByteSize() / 1024 / 1024).setScale(2, BigDecimal.ROUND_UP));
        entity.setFilePath(fileInfoModel.getPath());
        if (fileInfoModel.getUploadTime() == null) {
            entity.setUploadTime(new Date());
        } else {
            entity.setUploadTime(fileInfoModel.getUploadTime());
        }
        return save(entity);
    }

    /**
     * 新增
     *
     * @param entity
     * @return data为文件信息
     */
    @Transactional
    public ResultDTO<HzsFile> save(HzsFile entity) {
        if (StringUtils.isBlank(entity.getFileRegId())) {
            ResultDTO.getOptionFaild(400, "关联ID不可为空！");
        }
        entity.setHsfId(UUID.randomUUID().toString());
        hzsFileMapper.insert(entity);
        return ResultDTO.getDataSuccess(entity);
    }

    /**
     * 批量删除文件
     *
     * @param hsfId
     * @return
     */
    @Transactional
    public ResultDTO<Object> remove(String[] hsfId) {
        for (String id : hsfId) {
            hzsFileMapper.deleteByPrimaryKey(id);
        }
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 根据关联ID删除文件
     *
     * @param relationId
     * @return
     */
    @Transactional
    public ResultDTO<Object> removeByRelationId(String relationId) {
        hzsFileMapper.removeByRelationId(relationId);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 根据关联ID查询所有文件
     *
     * @param relationId
     * @return
     */
    public ResultDTO<List<HzsFile>> findByRelationId(String relationId) {
        List<HzsFile> list = hzsFileMapper.findByRelationId(relationId);
        return ResultDTO.getDataSuccess(list);
    }

    /**
     * 根据关联ID分页查询所有文件
     *
     * @param relationId
     * @param pageQO
     * @return
     */
    public ResultDTO<PageResultDTO<HzsFile>> pageFindByRelationId(String relationId, PageQO pageQO) {
        PageHelper.startPage(pageQO.getPage(), pageQO.getSize());
        Page<HzsFile> files = (Page<HzsFile>) hzsFileMapper.findByRelationId(relationId);
        return ResultDTO.getDataSuccess(new PageResultDTO<>(files));
    }

    /**
     * 一次性上传多个文件
     *
     * @param request
     * @return
     */
    public ResultDTO<Map<String, FileInfoModel>> upload(HttpServletRequest request) {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = req.getFileMap();
        Map<String, FileInfoModel> map = new HashMap<>();
        for (String name : fileMap.keySet()) {
            MultipartFile file = fileMap.get(name);
            if (file == null) {
                continue;
            }
            ResultDTO<FileInfoModel> resultDTO = upload(file);
            if (resultDTO.isSuccess()) {
                map.put(name, resultDTO.getData());
            }
        }
        return ResultDTO.getDataSuccess(map);
    }

    public ResultDTO<Map<String, FileInfoModel>> upload(MultipartFile[] files) {
        Map<String, FileInfoModel> map = new HashMap<>();
        List<FileInfoModel> list;
        try {
            list = hzSparrowFileUploadUtils.upload(files);
        } catch (RuntimeException e) {
            return ResultDTO.getDataFaild(500, e.getMessage(), null);
        } catch (Exception e) {
            return ResultDTO.getDataFaild(500, "文件上传失败！", null);
        }
        int i = 0;
        for (FileInfoModel file : list) {
            i++;
            if (file == null) {
                continue;
            }
            map.put("files" + i, file);
        }
        return ResultDTO.getDataSuccess(map);
    }

    /**
     * 单独上传一个文件
     *
     * @param file
     * @return
     */
    public ResultDTO<FileInfoModel> upload(MultipartFile file) {
        try {
            FileInfoModel fileInfoModel = hzSparrowFileUploadUtils.upload(file);
            return ResultDTO.getDataSuccess(fileInfoModel);
        } catch (RuntimeException e) {
            return ResultDTO.getDataFaild(500, e.getMessage(), null);
        } catch (Exception e) {
            return ResultDTO.getDataFaild(500, "文件上传失败！", null);
        }
    }

    /**
     * 下载
     *
     * @param hsfId
     * @param response
     */
    public void download(String hsfId, HttpServletRequest request, HttpServletResponse response) {
        HzsFile file = hzsFileMapper.selectByPrimaryKey(hsfId);
        if (file == null) {
            throw new RuntimeException("未找到文件！");
        }
        hzSparrowFileUploadUtils.download(request, response, file.getFileOldName(), file.getFilePath());
    }
}
