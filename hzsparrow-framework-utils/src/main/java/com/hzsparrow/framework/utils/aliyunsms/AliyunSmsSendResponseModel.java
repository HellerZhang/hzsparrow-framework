/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils.aliyunsms;

/**
 * 阿里云短信发送接口响应信息数据模型
 * 
 * @author Heller.Zhang
 * @since 2019年5月30日 上午11:11:56
 */
public class AliyunSmsSendResponseModel {

    /**
     * 发送回执ID
     */
    private String bizId;

    /**
     * 请求状态码
     */
    private String code;

    /**
     * 状态码的描述
     */
    private String message;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * @return the bizId
     */
    public String getBizId() {
        return bizId;
    }

    /**
     * @param bizId the bizId to set
     */
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
