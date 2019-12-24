package com.hzsparrow.framework.utils.upload;

import com.hzsparrow.framework.utils.files.FileInfoModel;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.FileDownloader;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.FileUploader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * 文件上传工具
 */
public class HzSparrowFileUploadUtils {

    private FileUploader fileUploader;

    private FileDownloader fileDownloader;

    public HzSparrowFileUploadUtils(FileUploader fileUploader, FileDownloader fileDownloader) {
        this.fileUploader = fileUploader;
        this.fileDownloader = fileDownloader;
    }

    /**
     * 上传单个远程文件
     *
     * @param file
     * @return
     */
    public FileInfoModel upload(MultipartFile file) {
        return fileUploader.upload(file);
    }

    /**
     * 上传多个远程文件
     *
     * @param files
     * @return
     */
    public List<FileInfoModel> upload(MultipartFile[] files) {
        return fileUploader.upload(files);
    }

    /**
     * 上传单个远程文件到指定相对目录下
     *
     * @param file
     * @param destFolder
     * @return
     */
    public FileInfoModel upload(MultipartFile file, String destFolder) {
        return fileUploader.upload(file, destFolder);
    }

    /**
     * 上传多个远程文件到指定相对目录下
     *
     * @param files
     * @param destFolder
     * @return
     */
    public List<FileInfoModel> upload(MultipartFile[] files, String destFolder) {
        return fileUploader.upload(files, destFolder);
    }

    /**
     * 上传单个本地文件
     *
     * @param file
     * @return
     */
    public FileInfoModel upload(File file) {
        return fileUploader.upload(file);
    }

    /**
     * 上传多个本地文件
     *
     * @param files
     * @return
     */
    public List<FileInfoModel> upload(File[] files) {
        return fileUploader.upload(files);
    }

    /**
     * 上传单个本地文件到指定相对目录下
     *
     * @param file
     * @param destFolder
     * @return
     */
    public FileInfoModel upload(File file, String destFolder) {
        return fileUploader.upload(file, destFolder);
    }

    /**
     * 上传多个本地文件到指定相对目录下
     *
     * @param files
     * @param destFolder
     * @return
     */
    public List<FileInfoModel> upload(File[] files, String destFolder) {
        return fileUploader.upload(files, destFolder);
    }

    /**
     * 下载文件
     *
     * @param req
     * @param resp
     * @param fileOldName
     * @param filePath
     */
    public void download(HttpServletRequest req, HttpServletResponse resp, String fileOldName, String filePath) {
        fileDownloader.download(req, resp, fileOldName, filePath);
    }

    /**
     * 下载文件到本地
     *
     * @param destFileName
     * @param destFolder
     * @param filePath
     */
    public void downloadToLocal(String destFileName, String destFolder, String filePath) {
        fileDownloader.downloadToLocal(destFileName, destFolder, filePath);
    }

    /**
     * 下载绝对路径下的文件
     *
     * @param req
     * @param resp
     * @param fileOldName
     * @param filePath
     */
    public void downloadByRootPath(HttpServletRequest req, HttpServletResponse resp, String fileOldName, String filePath) {
        fileDownloader.downloadByRootPath(req, resp, fileOldName, filePath);
    }

    public FileUploader getFileUploader() {
        return fileUploader;
    }

    public void setFileUploader(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }

    public FileDownloader getFileDownloader() {
        return fileDownloader;
    }

    public void setFileDownloader(FileDownloader fileDownloader) {
        this.fileDownloader = fileDownloader;
    }
}
