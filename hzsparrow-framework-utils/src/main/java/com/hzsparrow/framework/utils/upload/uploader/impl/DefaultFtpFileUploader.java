package com.hzsparrow.framework.utils.upload.uploader.impl;

import com.hzsparrow.framework.utils.files.FileInfoModel;
import com.hzsparrow.framework.utils.ftp.FTPUtil;
import com.hzsparrow.framework.utils.ftp.WriteFTPFile;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.FileUploader;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.RandomFileNameCreator;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileUploadValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 默认FTP文件上传工具
 */
public class DefaultFtpFileUploader implements FileUploader {

    /**
     * ftp写入工具
     */
    private WriteFTPFile ftpWriter;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    private String secondPath = "upload";

    private FileUploadValidator fileUploadValidator;

    private RandomFileNameCreator randomFileNameCreator;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public FileInfoModel upload(MultipartFile file) {
        return upload(file, null);
    }

    @Override
    public List<FileInfoModel> upload(MultipartFile[] files) {
        return upload(files, null);
    }

    @Override
    public FileInfoModel upload(MultipartFile file, String destFolder) {
        fileUploadValidator.validator(file);
        FileInfoModel uploadDTO = uploadOnece(file, destFolder);
        return uploadDTO;
    }

    @Override
    public List<FileInfoModel> upload(MultipartFile[] files, String destFolder) {
        for (MultipartFile file : files) {
            fileUploadValidator.validator(file);
        }
        List<FileInfoModel> list = new ArrayList<>();
        for (MultipartFile file : files) {
            FileInfoModel uploadDTO = uploadOnece(file, destFolder);
            list.add(uploadDTO);
        }
        return list;
    }

    @Override
    public FileInfoModel upload(File file) {
        return upload(file, null);
    }

    @Override
    public List<FileInfoModel> upload(File[] files) {
        return upload(files, null);
    }

    @Override
    public FileInfoModel upload(File file, String destFolder) {
        FileInfoModel uploadDTO = new FileInfoModel();
        String fileExt = file.getName().substring(file.getName().lastIndexOf('.'));
        String fileName = randomFileNameCreator.creatRandomFileName() + fileExt;
        String destFile;
        if (StringUtils.isBlank(destFolder)) {
            destFile = createUploadFolderPath() + File.separator + fileName;
        } else {
            destFile = destFolder + File.separator + fileName;
        }
        try {
            InputStream in = new FileInputStream(file);
            String ftpPath = FTPUtil.strSeparator + destFile.replace(File.separatorChar, '/');
            ftpWriter.upload(ftpPath, in);
            uploadDTO.setByteSize(file.getTotalSpace());
            uploadDTO.setPath(ftpPath);
            uploadDTO.setOldName(file.getName());
            uploadDTO.setNewName(fileName);
            uploadDTO.setUploadTime(new Date());
            return uploadDTO;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("上传文件失败！", e);
        }
    }

    @Override
    public List<FileInfoModel> upload(File[] files, String destFolder) {
        List<FileInfoModel> list = new ArrayList<>();
        for (File file : files) {
            FileInfoModel uploadDTO = upload(file, destFolder);
            list.add(uploadDTO);
        }
        return list;
    }

    private FileInfoModel uploadOnece(MultipartFile file, String destFolder) {
        FileInfoModel uploadDTO = new FileInfoModel();
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        String fileName = randomFileNameCreator.creatRandomFileName() + fileExt;
        String destFile;
        if (StringUtils.isBlank(destFolder)) {
            destFile = createUploadFolderPath() + File.separator + fileName;
        } else {
            destFile = destFolder + File.separator + fileName;
        }
        try {
            String ftpPath = FTPUtil.strSeparator + destFile.replace(File.separatorChar, '/');
            ftpWriter.upload(ftpPath, file.getInputStream());
            uploadDTO.setByteSize(file.getSize());
            uploadDTO.setPath(ftpPath);
            uploadDTO.setOldName(file.getOriginalFilename());
            uploadDTO.setNewName(fileName);
            uploadDTO.setUploadTime(new Date());
            return uploadDTO;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("上传文件失败！", e);
        }
    }

    /**
     * 创建上传文件的相对路径
     *
     * @return
     */
    public String createUploadFolderPath() {
        String date = dateFormat.format(new Date());
        String folder = secondPath + File.separator + date;
        return folder;
    }

    public WriteFTPFile getFtpWriter() {
        return ftpWriter;
    }

    public void setFtpWriter(WriteFTPFile ftpWriter) {
        this.ftpWriter = ftpWriter;
    }

    public String getSecondPath() {
        return secondPath;
    }

    public void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }

    public FileUploadValidator getFileUploadValidator() {
        return fileUploadValidator;
    }

    public void setFileUploadValidator(FileUploadValidator fileUploadValidator) {
        this.fileUploadValidator = fileUploadValidator;
    }

    public RandomFileNameCreator getRandomFileNameCreator() {
        return randomFileNameCreator;
    }

    public void setRandomFileNameCreator(RandomFileNameCreator randomFileNameCreator) {
        this.randomFileNameCreator = randomFileNameCreator;
    }
}
