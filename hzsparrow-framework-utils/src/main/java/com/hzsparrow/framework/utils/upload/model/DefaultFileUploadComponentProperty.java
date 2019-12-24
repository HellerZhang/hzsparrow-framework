package com.hzsparrow.framework.utils.upload.model;

public class DefaultFileUploadComponentProperty {

    /**
     * 随机文件名生成器
     */
    private String randomFileNameCreator = "com.hzsparrow.framework.utils.upload.uploader.impl.DefaultRandomFileNameCreator";

    /**
     * 响应内容类型设置器
     */
    private String responseContentTypeSetter = "com.hzsparrow.framework.utils.upload.uploader.impl.DefaultResponseContentTypeSetter";

    /**
     * 下载文件名适配工具
     */
    private String fileNameConverter = "com.hzsparrow.framework.utils.upload.uploader.impl.DefaultFileNameConverter";

    /**
     * 文件大小校验器
     */
    private String fileSizeValidator = "com.hzsparrow.framework.utils.upload.valid.impl.DefaultFileSizeValidator";

    /**
     * 文件类型校验器
     */
    private String fileTypeValidator = "com.hzsparrow.framework.utils.upload.valid.impl.DefaultFileTypeValidator";

    /**
     * 文件上传类型校验器
     */
    private String fileUploadValidator = "com.hzsparrow.framework.utils.upload.valid.impl.DefaultFileUploadValidator";

    /**
     * 文件其他校验器,多个以,隔开
     */
    private String fileOtherValidator;

    public String getRandomFileNameCreator() {
        return randomFileNameCreator;
    }

    public void setRandomFileNameCreator(String randomFileNameCreator) {
        this.randomFileNameCreator = randomFileNameCreator;
    }

    public String getResponseContentTypeSetter() {
        return responseContentTypeSetter;
    }

    public void setResponseContentTypeSetter(String responseContentTypeSetter) {
        this.responseContentTypeSetter = responseContentTypeSetter;
    }

    public String getFileNameConverter() {
        return fileNameConverter;
    }

    public void setFileNameConverter(String fileNameConverter) {
        this.fileNameConverter = fileNameConverter;
    }

    public String getFileSizeValidator() {
        return fileSizeValidator;
    }

    public void setFileSizeValidator(String fileSizeValidator) {
        this.fileSizeValidator = fileSizeValidator;
    }

    public String getFileTypeValidator() {
        return fileTypeValidator;
    }

    public void setFileTypeValidator(String fileTypeValidator) {
        this.fileTypeValidator = fileTypeValidator;
    }

    public String getFileUploadValidator() {
        return fileUploadValidator;
    }

    public void setFileUploadValidator(String fileUploadValidator) {
        this.fileUploadValidator = fileUploadValidator;
    }

    public String getFileOtherValidator() {
        return fileOtherValidator;
    }

    public void setFileOtherValidator(String fileOtherValidator) {
        this.fileOtherValidator = fileOtherValidator;
    }
}
