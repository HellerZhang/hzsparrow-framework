package com.hzsparrow.framework.utils.upload.uploader.interfaces;

/**
 * 随机文件名生成器
 */
@FunctionalInterface
public interface RandomFileNameCreator {

    /**
     * 生成随机文件名
     *
     * @return
     */
    String creatRandomFileName();
}
