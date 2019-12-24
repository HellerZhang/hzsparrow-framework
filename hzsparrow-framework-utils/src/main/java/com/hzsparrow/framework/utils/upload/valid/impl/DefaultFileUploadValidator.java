package com.hzsparrow.framework.utils.upload.valid.impl;

import com.hzsparrow.framework.utils.upload.valid.interfaces.FileOtherValidator;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileSizeValidator;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileTypeValidator;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileUploadValidator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 默认文件上传校验器
 */
public class DefaultFileUploadValidator implements FileUploadValidator {

    /**
     * 是否启用文件大小校验
     */
    private boolean isEnableSizeValid = true;

    /**
     * 是否启用文件类型校验器
     */
    private boolean isEnableTypeValid = true;

    /**
     * 文件大小校验器
     */
    private FileSizeValidator fileSizeValidator;

    /**
     * 文件类型校验器
     */
    private FileTypeValidator fileTypeValidator;

    /**
     * 其他文件校验器
     */
    private List<FileOtherValidator> otherValidators;

    /**
     * 校验
     *
     * @param file
     * @return 校验成功返回true, 校验失败通过异常信息区分校验失败类型
     */
    @Override
    public boolean validator(MultipartFile file) {
        boolean isValid = true;
        if (isEnableSizeValid) {
            // 文件大小校验
            isValid = fileSizeValidator.validFileSize(file);
            if (!isValid) {
                throw new RuntimeException("文件大小超过最大值限制！");
            }
        }
        if (isEnableTypeValid) {
            // 文件类型校验
            isValid = fileTypeValidator.validFileType(file);
            if (!isValid) {
                throw new RuntimeException("该格式的文件被禁止上传！");
            }
        }
        if (CollectionUtils.isNotEmpty(otherValidators)) {
            for (FileOtherValidator validator : otherValidators) {
                isValid = fileTypeValidator.validFileType(file);
                if (!isValid) {
                    throw new RuntimeException("文件校验失败，禁止上传！");
                }
            }
        }
        return true;
    }

    public boolean isEnableSizeValid() {
        return isEnableSizeValid;
    }

    public void setEnableSizeValid(boolean enableSizeValid) {
        isEnableSizeValid = enableSizeValid;
    }

    public boolean isEnableTypeValid() {
        return isEnableTypeValid;
    }

    public void setEnableTypeValid(boolean enableTypeValid) {
        isEnableTypeValid = enableTypeValid;
    }

    public FileSizeValidator getFileSizeValidator() {
        return fileSizeValidator;
    }

    public void setFileSizeValidator(FileSizeValidator fileSizeValidator) {
        this.fileSizeValidator = fileSizeValidator;
    }

    public FileTypeValidator getFileTypeValidator() {
        return fileTypeValidator;
    }

    public void setFileTypeValidator(FileTypeValidator fileTypeValidator) {
        this.fileTypeValidator = fileTypeValidator;
    }

    public List<FileOtherValidator> getOtherValidators() {
        return otherValidators;
    }

    public void setOtherValidators(List<FileOtherValidator> otherValidators) {
        this.otherValidators = otherValidators;
    }
}
