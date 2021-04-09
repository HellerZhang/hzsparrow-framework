package com.hzsparrow.framework.utils.upload;

import com.hzsparrow.framework.utils.ftp.ReadFTPFile;
import com.hzsparrow.framework.utils.ftp.WriteFTPFile;
import com.hzsparrow.framework.utils.upload.model.DefaultFileUploadComponentProperty;
import com.hzsparrow.framework.utils.upload.model.DefaultFileValidatorProperty;
import com.hzsparrow.framework.utils.upload.model.DefaultFtpFileUploadProperty;
import com.hzsparrow.framework.utils.upload.model.DefaultLocalFileUploadProperty;
import com.hzsparrow.framework.utils.upload.uploader.impl.DefaultFtpFileDownloader;
import com.hzsparrow.framework.utils.upload.uploader.impl.DefaultFtpFileUploader;
import com.hzsparrow.framework.utils.upload.uploader.impl.DefaultLocalFileDownloader;
import com.hzsparrow.framework.utils.upload.uploader.impl.DefaultLocalFileUploader;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.*;
import com.hzsparrow.framework.utils.upload.valid.impl.DefaultFileSizeValidator;
import com.hzsparrow.framework.utils.upload.valid.impl.DefaultFileTypeValidator;
import com.hzsparrow.framework.utils.upload.valid.impl.DefaultFileUploadValidator;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileOtherValidator;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileSizeValidator;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileTypeValidator;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileUploadValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传工具工厂类
 */
public class HzSparrowFileUploadUtilsFactory {

    private final String defaultFileUploadValidator = "com.hzsparrow.framework.utils.upload.valid.impl.DefaultFileUploadValidator";

    /**
     * 文件大小校验器
     */
    private String defaultFileSizeValidator = "com.hzsparrow.framework.utils.upload.valid.impl.DefaultFileSizeValidator";

    /**
     * 文件类型校验器
     */
    private String defaultFileTypeValidator = "com.hzsparrow.framework.utils.upload.valid.impl.DefaultFileTypeValidator";

    /**
     * 构建默认本地文件上传工具
     *
     * @param validatorProperty
     * @param uploadProperty
     * @return
     */
    public HzSparrowFileUploadUtils createDefaultLocalUtils(DefaultFileUploadComponentProperty componentProperty, DefaultFileValidatorProperty validatorProperty, DefaultLocalFileUploadProperty uploadProperty) throws ClassNotFoundException {

        // 文件下载工具
        DefaultLocalFileDownloader fileDownloader = new DefaultLocalFileDownloader();
        fileDownloader.setRootPath(uploadProperty.getRootPath());
        fileDownloader.setFileNameConverter(getFileNameConverter(componentProperty));
        fileDownloader.setResponseContentTypeSetter(getResponseContentTypeSetter(componentProperty));

        // 文件上传工具
        DefaultLocalFileUploader fileUploader = new DefaultLocalFileUploader();
        fileUploader.setRootPath(uploadProperty.getRootPath());
        fileUploader.setSecondPath(uploadProperty.getSecondPath());
        fileUploader.setFileUploadValidator(getFileUploadValidator(componentProperty, validatorProperty));
        fileUploader.setRandomFileNameCreator(getRandomFileNameCreator(componentProperty));

        // 文件上传下载统一接口工具
        return createFileUploadUtils(fileUploader, fileDownloader);
    }

    /**
     * 构建默认ftp文件上传下载工具
     *
     * @param componentProperty
     * @param validatorProperty
     * @param uploadProperty
     * @return
     * @throws ClassNotFoundException
     */
    public HzSparrowFileUploadUtils createDefaultFtpUtils(DefaultFileUploadComponentProperty componentProperty, DefaultFileValidatorProperty validatorProperty, DefaultFtpFileUploadProperty uploadProperty) throws ClassNotFoundException {

        // 文件下载工具
        DefaultFtpFileDownloader fileDownloader = new DefaultFtpFileDownloader();
        fileDownloader.setFileNameConverter(getFileNameConverter(componentProperty));
        fileDownloader.setResponseContentTypeSetter(getResponseContentTypeSetter(componentProperty));
        ReadFTPFile ftpReader = new ReadFTPFile();
        ftpReader.setFtpHost(uploadProperty.getFtpHost());
        ftpReader.setFtpPort(uploadProperty.getFtpPort());
        ftpReader.setFtpUserName(uploadProperty.getFtpUserName());
        ftpReader.setFtpPassword(uploadProperty.getFtpPassword());
        fileDownloader.setFtpReader(ftpReader);

        // 文件上传工具
        DefaultFtpFileUploader fileUploader = new DefaultFtpFileUploader();
        fileUploader.setSecondPath(uploadProperty.getSecondPath());
        fileUploader.setFileUploadValidator(getFileUploadValidator(componentProperty, validatorProperty));
        fileUploader.setRandomFileNameCreator(getRandomFileNameCreator(componentProperty));
        WriteFTPFile ftpWriter = new WriteFTPFile();
        ftpWriter.setFtpHost(uploadProperty.getFtpHost());
        ftpWriter.setFtpPort(uploadProperty.getFtpPort());
        ftpWriter.setFtpUserName(uploadProperty.getFtpUserName());
        ftpWriter.setFtpPassword(uploadProperty.getFtpPassword());
        fileUploader.setFtpWriter(ftpWriter);

        // 文件上传下载统一接口工具
        return createFileUploadUtils(fileUploader, fileDownloader);
    }

    /**
     * 创建文件上传下载统一接口工具
     *
     * @param fileUploader
     * @param fileDownloader
     * @return
     */
    public HzSparrowFileUploadUtils createFileUploadUtils(FileUploader fileUploader, FileDownloader fileDownloader) {
        // 文件上传下载统一接口工具
        HzSparrowFileUploadUtils utils = new HzSparrowFileUploadUtils(fileUploader, fileDownloader);
        return utils;
    }

    /**
     * 获取文件校验工具
     *
     * @param componentProperty
     * @param validatorProperty
     * @return
     * @throws ClassNotFoundException
     */
    private FileUploadValidator getFileUploadValidator(DefaultFileUploadComponentProperty componentProperty, DefaultFileValidatorProperty validatorProperty) throws ClassNotFoundException {
        // 文件上传校验器
        FileUploadValidator fileUploadValidator;
        if (StringUtils.equals(componentProperty.getFileUploadValidator(), defaultFileUploadValidator)) {

            // 文件上传校验器
            DefaultFileUploadValidator defaultFileUploadValidator = new DefaultFileUploadValidator();
            defaultFileUploadValidator.setEnableSizeValid(validatorProperty.isEnableSizeValid());
            defaultFileUploadValidator.setEnableTypeValid(validatorProperty.isEnableTypeValid());
            if (defaultFileUploadValidator.isEnableSizeValid()) {
                // 文件大小校验器
                if (StringUtils.equals(componentProperty.getFileSizeValidator(), defaultFileSizeValidator)) {
                    DefaultFileSizeValidator fileSizeValidator = new DefaultFileSizeValidator();
                    fileSizeValidator.setMaxFileSize(validatorProperty.getMaxFileSize());
                    defaultFileUploadValidator.setFileSizeValidator(fileSizeValidator);
                } else {
                    FileSizeValidator fileSizeValidator = (FileSizeValidator) BeanUtils.instantiateClass(Class.forName(componentProperty.getFileSizeValidator()));
                    defaultFileUploadValidator.setFileSizeValidator(fileSizeValidator);
                }
            }
            if (defaultFileUploadValidator.isEnableTypeValid()) {
                // 文件类型校验器
                if (StringUtils.equals(componentProperty.getFileTypeValidator(), defaultFileTypeValidator)) {
                    String[] allowedSuffix = null;
                    if (StringUtils.isNotBlank(validatorProperty.getAllowedSuffix())) {
                        allowedSuffix = validatorProperty.getAllowedSuffix().split(",");
                    }
                    DefaultFileTypeValidator fileTypeValidator = new DefaultFileTypeValidator(allowedSuffix, validatorProperty.isEnableDefaultType());
                    defaultFileUploadValidator.setFileTypeValidator(fileTypeValidator);
                } else {
                    FileTypeValidator fileTypeValidator = (FileTypeValidator) BeanUtils.instantiateClass(Class.forName(componentProperty.getFileTypeValidator()));
                    defaultFileUploadValidator.setFileTypeValidator(fileTypeValidator);
                }
            }
            if (StringUtils.isNotBlank(componentProperty.getFileOtherValidator())) {
                // 其他文件校验器
                String[] otherValidatorClassNames = componentProperty.getFileOtherValidator().split(",");
                List<FileOtherValidator> otherValidatorList = new ArrayList<>();
                for (String otherValidatorClassName : otherValidatorClassNames) {
                    otherValidatorList.add((FileOtherValidator) BeanUtils.instantiateClass(Class.forName(otherValidatorClassName)));
                }
                defaultFileUploadValidator.setOtherValidators(otherValidatorList);
            }
            fileUploadValidator = defaultFileUploadValidator;
        } else {
            // 自定义文件上传校验器
            fileUploadValidator = (FileUploadValidator) BeanUtils.instantiateClass(Class.forName(componentProperty.getFileUploadValidator()));
        }
        return fileUploadValidator;
    }

    /**
     * 获取随机文件名生成器
     *
     * @param componentProperty
     * @return
     * @throws ClassNotFoundException
     */
    private RandomFileNameCreator getRandomFileNameCreator(DefaultFileUploadComponentProperty componentProperty) throws ClassNotFoundException {
        // 随机文件名生成器
        RandomFileNameCreator randomFileNameCreator = (RandomFileNameCreator) BeanUtils.instantiateClass(Class.forName(componentProperty.getRandomFileNameCreator()));
        return randomFileNameCreator;
    }

    /**
     * 获取响应内容类型设置器
     *
     * @param componentProperty
     * @return
     * @throws ClassNotFoundException
     */
    private ResponseContentTypeSetter getResponseContentTypeSetter(DefaultFileUploadComponentProperty componentProperty) throws ClassNotFoundException {
        // 响应内容类型设置器
        ResponseContentTypeSetter responseContentTypeSetter = (ResponseContentTypeSetter) BeanUtils.instantiateClass(Class.forName(componentProperty.getResponseContentTypeSetter()));
        return responseContentTypeSetter;
    }

    /**
     * 获取下载文件名适配工具
     *
     * @param componentProperty
     * @return
     * @throws ClassNotFoundException
     */
    private FileNameConverter getFileNameConverter(DefaultFileUploadComponentProperty componentProperty) throws ClassNotFoundException {
        // 下载文件名适配工具
        FileNameConverter fileNameConverter = (FileNameConverter) BeanUtils.instantiateClass(Class.forName(componentProperty.getFileNameConverter()));
        return fileNameConverter;
    }

}
