/**
 * $Id:$
 * Copyright 2014-2018 Hebei Sinounited Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author Heller.Zhang
 * @since 2018年11月29日 下午5:55:19
 */
@ConfigurationProperties(prefix = "hzsparrow.fu.ftp")
public class FtpProperty {

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
}
