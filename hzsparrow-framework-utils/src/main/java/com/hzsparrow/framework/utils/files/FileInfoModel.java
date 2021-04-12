/**
 * $Id:$
 * Copyright 2019 HellerZhang. All rights reserved.
 */
package com.hzsparrow.framework.utils.files;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 存储文件信息的实体
 * 
 * @author HellerZhang
 * @since 2017年10月16日 下午7:45:47
 */
public class FileInfoModel implements Serializable {
 
    private static final long serialVersionUID = 6546488151099007359L;


    /**
     * 旧名字
     *
     *  2017-10-06 08:32:35
     */
    private String oldName;

    /**
     * 新名字
     *
     *  2017-10-06 08:32:35
     */
    private String newName;

    /**
     * 存储路径
     *
     *  2017-10-06 08:32:35
     */
    private String path;

    /**
     * 上传时间
     *
     *  2017-10-06 08:32:35
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadTime;

    /**
     * 字节大小
     *
     *  2017-10-06 08:32:35
     */
    private Long byteSize;

    /**
     * @return the oldName
     */
    public String getOldName() {
        return oldName;
    }

    /**
     * @param oldName the oldName to set
     */
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    /**
     * @return the newName
     */
    public String getNewName() {
        return newName;
    }

    /**
     * @param newName the newName to set
     */
    public void setNewName(String newName) {
        this.newName = newName;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the uploadTime
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * @param uploadTime the uploadTime to set
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * @return the byteSize
     */
    public Long getByteSize() {
        return byteSize;
    }

    /**
     * @param byteSize the byteSize to set
     */
    public void setByteSize(Long byteSize) {
        this.byteSize = byteSize;
    }
}
