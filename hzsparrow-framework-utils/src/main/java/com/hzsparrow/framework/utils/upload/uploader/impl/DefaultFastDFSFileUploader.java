package com.hzsparrow.framework.utils.upload.uploader.impl;


import com.hzsparrow.framework.fastdfs.util.FastDFSFile;
import com.hzsparrow.framework.fastdfs.util.FileManager;
import com.hzsparrow.framework.utils.files.FileInfoModel;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.FileUploader;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.RandomFileNameCreator;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileUploadValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultFastDFSFileUploader implements FileUploader {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    private String secondPath = "upload";

    private FileUploadValidator fileUploadValidator;

    private RandomFileNameCreator randomFileNameCreator;

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
        try {
            String fileExt = file.getName().substring(file.getName().indexOf('.'));
            String fileName = randomFileNameCreator.creatRandomFileName() + fileExt;
            byte[] sources = FileUtils.readFileToByteArray(file);

            String filePath = fastDFSUpload(sources, fileName, fileExt);
            uploadDTO.setByteSize(file.getTotalSpace());
            uploadDTO.setPath(filePath);
            uploadDTO.setOldName(file.getName());
            uploadDTO.setNewName(fileName + fileExt);
            uploadDTO.setUploadTime(new Date());
            return uploadDTO;
        } catch (Exception e) {
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
        String fileExt = file.getName().substring(file.getName().indexOf('.'));
        String fileName = randomFileNameCreator.creatRandomFileName() + fileExt;
        String destFile;
        if (StringUtils.isBlank(destFolder)) {
            destFile = createUploadFolderPath() + File.separator + fileName;
        } else {
            destFile = destFolder + File.separator + fileName;
        }
        try {
            byte[] sources = file.getBytes();

            String filePath = fastDFSUpload(sources, fileName, fileExt);
            uploadDTO.setByteSize(file.getSize());
            uploadDTO.setPath(filePath);
            uploadDTO.setOldName(file.getOriginalFilename());
            uploadDTO.setNewName(fileName + fileExt);
            uploadDTO.setUploadTime(new Date());
            return uploadDTO;
        } catch (Exception e) {
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

    private String fastDFSUpload(byte[] source, String fileName, String fileExt) throws Exception {
        FastDFSFile dfsFile = new FastDFSFile(source, fileExt);

        NameValuePair[] metaList = new NameValuePair[4];
        metaList[0] = new NameValuePair("fileName", fileName);
        metaList[1] = new NameValuePair("fileLength", String.valueOf(source.length));
        metaList[2] = new NameValuePair("fileExt", fileExt.substring(1));
        metaList[3] = new NameValuePair("fileAuthor", "hzsparrow");
        String filePath = FileManager.upload(dfsFile);
        return filePath;
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
