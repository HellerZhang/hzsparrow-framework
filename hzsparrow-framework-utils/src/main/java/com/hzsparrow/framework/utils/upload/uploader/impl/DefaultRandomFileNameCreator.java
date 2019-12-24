package com.hzsparrow.framework.utils.upload.uploader.impl;

import com.hzsparrow.framework.utils.upload.uploader.interfaces.RandomFileNameCreator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 默认随机文件名生成器
 */
public class DefaultRandomFileNameCreator implements RandomFileNameCreator {

    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 生成随机文件名
     *
     * @return
     */
    @Override
    public String creatRandomFileName() {
        int r = new Random().nextInt(1000);
        return dateTimeFormat.format(new Date()) + "_" + r;
    }
}
