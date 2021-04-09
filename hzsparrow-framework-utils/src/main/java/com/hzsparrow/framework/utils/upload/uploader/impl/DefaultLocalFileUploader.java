package com.hzsparrow.framework.utils.upload.uploader.impl;

import com.hzsparrow.framework.utils.files.FileInfoModel;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.FileUploader;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.RandomFileNameCreator;
import com.hzsparrow.framework.utils.upload.valid.interfaces.FileUploadValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 默认本地文件上传器
 */
public class DefaultLocalFileUploader implements FileUploader {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    private String rootPath = "d:\\hzsparrow\\upload";

    private String secondPath = "upload";

    private FileUploadValidator fileUploadValidator;

    private RandomFileNameCreator randomFileNameCreator;

    /**
     * 上传单个远程文件
     *
     * @param file
     * @return
     */
    @Override
    public FileInfoModel upload(MultipartFile file) {
        return upload(file, null);
    }

    /**
     * 上传多个远程文件
     *
     * @param files
     * @return
     */
    @Override
    public List<FileInfoModel> upload(MultipartFile[] files) {
        return upload(files, null);
    }

    /**
     * 上传单个远程文件到指定相对目录下
     *
     * @param file
     * @param destFolder
     * @return
     */
    @Override
    public FileInfoModel upload(MultipartFile file, String destFolder) {
        fileUploadValidator.validator(file);
        FileInfoModel uploadDTO = uploadOnece(file, destFolder);
        return uploadDTO;
    }

    /**
     * 上传多个远程文件到指定相对目录下
     *
     * @param files
     * @param destFolder
     * @return
     */
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

    /**
     * 上传单个本地文件
     *
     * @param file
     * @return
     */
    @Override
    public FileInfoModel upload(File file) {
        return upload(file, null);
    }

    /**
     * 上传多个本地文件
     *
     * @param files
     * @return
     */
    @Override
    public List<FileInfoModel> upload(File[] files) {
        return upload(files, null);
    }

    /**
     * 上传单个本地文件到指定相对目录下
     *
     * @param file
     * @param destFolder
     * @return
     */
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
            upload(in, destFile);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("上传文件失败！", e);
        }
        uploadDTO.setByteSize(file.getTotalSpace());
        uploadDTO.setPath(destFile);
        uploadDTO.setOldName(file.getName());
        uploadDTO.setNewName(fileName);
        uploadDTO.setUploadTime(new Date());
        return uploadDTO;
    }

    /**
     * 上传多个本地文件到指定相对目录下
     *
     * @param files
     * @param destFolder
     * @return
     */
    @Override
    public List<FileInfoModel> upload(File[] files, String destFolder) {
        List<FileInfoModel> list = new ArrayList<>();
        for (File file : files) {
            FileInfoModel uploadDTO = upload(file, destFolder);
            list.add(uploadDTO);
        }
        return list;
    }

    /**
     * 上传单个远程文件到指定目录（无校验）
     *
     * @param file
     * @param destFolder
     * @return
     */
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
            InputStream in = file.getInputStream();
            upload(in, destFile);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("上传文件失败！", e);
        }
        uploadDTO.setByteSize(file.getSize());
        uploadDTO.setPath(destFile);
        uploadDTO.setOldName(file.getOriginalFilename());
        uploadDTO.setNewName(fileName);
        uploadDTO.setUploadTime(new Date());
        return uploadDTO;
    }

    /**
     * 本地上传文件的核心上传方法
     *
     * @param in
     * @param destFilePath
     */
    private void upload(InputStream in, String destFilePath) {
        File uploadFile;
        uploadFile = new File(rootPath + File.separator + destFilePath);
        File uploadFolder = uploadFile.getParentFile();
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        try {
            FileOutputStream fs = new FileOutputStream(uploadFile);
            byte[] buffer = new byte[1024 * 1024];
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
                fs.flush();
            }
            fs.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        File f1 = new File(rootPath + File.separator + folder + File.separator + date);
        if (!f1.exists()) {
            f1.mkdirs();
        }
        return folder;
    }

    public String getRootPath() {
        return rootPath;
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
