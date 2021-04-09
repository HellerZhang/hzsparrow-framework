package com.hzsparrow.framework.utils.upload.model;

/**
 * 默认本地文件上传参数
 */
public class DefaultLocalFileUploadProperty {

    /**
     * 根路径
     */
    private String rootPath = "d:\\hzsparrow\\upload";

    /**
     * 二级路径
     */
    private String secondPath = "upload";

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getSecondPath() {
        return secondPath;
    }

    public void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }
}
