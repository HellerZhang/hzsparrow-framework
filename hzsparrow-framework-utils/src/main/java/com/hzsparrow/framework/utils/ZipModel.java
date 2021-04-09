package com.hzsparrow.framework.utils;

public class ZipModel {

    private String strFileName; //文件名

    private String strFileSuffix; //文件后缀名

    private Long lFileSize; //文件大小, 单位为字节，1 mb= strFileSize/1024/1024 ;1 kb= strFileSize/1024

    private String strFileUrl; //文件存储地址

    public ZipModel() {

    }

    public String getStrFileName() {
        return strFileName;
    }

    public void setStrFileName(String strFileName) {
        this.strFileName = strFileName;
    }

    public String getStrFileSuffix() {
        return strFileSuffix;
    }

    public void setStrFileSuffix(String strFileSuffix) {
        this.strFileSuffix = strFileSuffix;
    }

    public String getStrFileUrl() {
        return strFileUrl;
    }

    public void setStrFileUrl(String strFileUrl) {
        this.strFileUrl = strFileUrl;
    }

    public Long getlFileSize() {
        return lFileSize;
    }

    public void setlFileSize(Long lFileSize) {
        this.lFileSize = lFileSize;
    }

}
