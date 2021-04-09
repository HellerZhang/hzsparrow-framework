package com.hzsparrow.framework.utils.upload.model;

/**
 * 默认文件校验器的参数配置
 */
public class DefaultFileValidatorProperty {

    /**
     * 是否启用文件大小校验
     */
    private boolean isEnableSizeValid = true;

    /**
     * 最大文件大小
     */
    private Long maxFileSize = 1073741824L;

    /**
     * 是否启用文件类型校验
     */
    private boolean isEnableTypeValid = true;

    /**
     * 是否启用默认校验文件类型
     */
    private boolean isEnableDefaultType = true;

    /**
     * 自定义文件校验类型,多个以,隔开
     */
    private String allowedSuffix;

    public boolean isEnableSizeValid() {
        return isEnableSizeValid;
    }

    public void setEnableSizeValid(boolean enableSizeValid) {
        isEnableSizeValid = enableSizeValid;
    }

    public Long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(Long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public boolean isEnableTypeValid() {
        return isEnableTypeValid;
    }

    public void setEnableTypeValid(boolean enableTypeValid) {
        isEnableTypeValid = enableTypeValid;
    }

    public boolean isEnableDefaultType() {
        return isEnableDefaultType;
    }

    public void setEnableDefaultType(boolean enableDefaultType) {
        isEnableDefaultType = enableDefaultType;
    }

    public String getAllowedSuffix() {
        return allowedSuffix;
    }

    public void setAllowedSuffix(String allowedSuffix) {
        this.allowedSuffix = allowedSuffix;
    }
}
