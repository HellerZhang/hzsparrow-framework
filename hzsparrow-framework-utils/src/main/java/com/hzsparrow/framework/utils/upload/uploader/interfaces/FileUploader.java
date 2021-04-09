package com.hzsparrow.framework.utils.upload.uploader.interfaces;

import com.hzsparrow.framework.utils.files.FileInfoModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileUploader {

    /**
     * 上传单个远程文件
     *
     * @param file
     * @return
     */
    FileInfoModel upload(MultipartFile file);

    /**
     * 上传多个远程文件
     *
     * @param files
     * @return
     */
    List<FileInfoModel> upload(MultipartFile[] files);

    /**
     * 上传单个远程文件到指定相对目录下
     *
     * @param file
     * @param destFolder
     * @return
     */
    FileInfoModel upload(MultipartFile file, String destFolder);

    /**
     * 上传多个远程文件到指定相对目录下
     *
     * @param files
     * @param destFolder
     * @return
     */
    List<FileInfoModel> upload(MultipartFile[] files, String destFolder);

    /**
     * 上传单个本地文件
     *
     * @param file
     * @return
     */
    FileInfoModel upload(File file);

    /**
     * 上传多个本地文件
     *
     * @param files
     * @return
     */
    List<FileInfoModel> upload(File[] files);

    /**
     * 上传单个本地文件到指定相对目录下
     *
     * @param file
     * @param destFolder
     * @return
     */
    FileInfoModel upload(File file, String destFolder);

    /**
     * 上传多个本地文件到指定相对目录下
     *
     * @param files
     * @param destFolder
     * @return
     */
    List<FileInfoModel> upload(File[] files, String destFolder);
}
