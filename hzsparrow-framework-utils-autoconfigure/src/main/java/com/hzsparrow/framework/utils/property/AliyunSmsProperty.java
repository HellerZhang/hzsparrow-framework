/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云短信服务配置信息
 * 
 * @author Heller.Zhang
 * @since 2019年5月30日 上午10:53:36
 */
@ConfigurationProperties("hzsparrow.aliyunsms")
public class AliyunSmsProperty {

    private String accessKeyId = "";

    private String accessSecret = "";

    /**
     * @return the accessKeyId
     */
    public String getAccessKeyId() {
        return accessKeyId;
    }

    /**
     * @param accessKeyId the accessKeyId to set
     */
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    /**
     * @return the accessSecret
     */
    public String getAccessSecret() {
        return accessSecret;
    }

    /**
     * @param accessSecret the accessSecret to set
     */
    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

}
