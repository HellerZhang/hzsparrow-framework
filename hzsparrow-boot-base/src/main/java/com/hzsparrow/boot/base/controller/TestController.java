package com.hzsparrow.boot.base.controller;

import com.hzsparrow.boot.base.entity.Test;
import com.hzsparrow.boot.base.entity.TestEntity;
import com.hzsparrow.boot.base.service.TestService;
import com.hzsparrow.framework.utils.files.FileInfoModel;
import com.hzsparrow.framework.utils.files.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Autowired
    private TestService testService;

    @RequestMapping("/test1")
    public Test test1(@Validated TestEntity entity){
        System.out.println("测试成功"+entity.getId());
        return testService.getById(entity.getId());
    }

    @RequestMapping("/test2")
    public FileInfoModel test2(@RequestParam("file1") MultipartFile file){
        try {
            return fileUploadUtils.upload(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
