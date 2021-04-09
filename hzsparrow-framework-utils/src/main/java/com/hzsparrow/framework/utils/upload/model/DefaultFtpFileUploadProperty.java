package com.hzsparrow.framework.utils.upload.model;

/**
 * 默认Ftp文件上传工具参数
 */
public class DefaultFtpFileUploadProperty {

    /**
     * ftp地址
     */
    private String ftpHost = "";

    /**
     * ftp端口
     */
    private Integer ftpPort = 21;

    /**
     * ftp账号
     */
    private String ftpUserName = "";

    /**
     * ftp密码
     */
    private String ftpPassword = "";

    /**
     * 二级路径
     */
    private String secondPath = "upload";

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getSecondPath() {
        return secondPath;
    }

    public void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }
}
