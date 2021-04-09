/**
 * $Id:$
 * Copyright 2014-2018 Hebei hzsparrowunited Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author Heller.Zhang
 * @since 2018年11月29日 下午5:29:30
 */
@ConfigurationProperties(prefix = "hzsparrow.fu")
public class FileUploadProperty {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String rootPath = "d:\\hzsparrow\\upload";

    private String secondPath = "upload";

    private Integer fileServerType = 1;

    private Long maxFileSize = 1000000000L;

    /**
     * @return the rootPath
     */
    public String getRootPath() {
        return rootPath;
    }

    /**
     * @return the fileServerType
     */
    public Integer getFileServerType() {
        return fileServerType;
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

    public void setFileServerType(Integer fileServerType) {
        this.fileServerType = fileServerType;
    }

    public Long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(Long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
}
