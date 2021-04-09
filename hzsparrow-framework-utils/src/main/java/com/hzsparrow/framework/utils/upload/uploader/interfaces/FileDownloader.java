package com.hzsparrow.framework.utils.upload.uploader.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileDownloader {

    /**
     * 下载文件
     *
     * @param resp
     * @param fileOldName
     * @param filePath
     */
    void download(HttpServletRequest req, HttpServletResponse resp, String fileOldName, String filePath);

    /**
     * 将文件下载到本地
     *
     * @param destFileName
     * @param destFolder
     * @param filePath
     */
    void downloadToLocal(String destFileName, String destFolder, String filePath);

    /**
     * 下载绝对路径下的文件
     *
     * @param resp
     * @param fileOldName
     * @param filePath
     */
    void downloadByRootPath(HttpServletRequest req, HttpServletResponse resp, String fileOldName, String filePath);
}
