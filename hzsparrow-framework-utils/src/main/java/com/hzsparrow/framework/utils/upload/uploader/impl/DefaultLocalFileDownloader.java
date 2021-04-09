package com.hzsparrow.framework.utils.upload.uploader.impl;

import com.hzsparrow.framework.utils.FileUtils;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.FileDownloader;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.FileNameConverter;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.ResponseContentTypeSetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 默认本地文件下载器
 */
public class DefaultLocalFileDownloader implements FileDownloader {

    Logger logger = LoggerFactory.getLogger(getClass());

    private String rootPath = "d:\\hzsparrow\\upload";

    private FileNameConverter fileNameConverter;

    private ResponseContentTypeSetter responseContentTypeSetter;

    @Override
    public void download(HttpServletRequest req, HttpServletResponse resp, String fileOldName, String filePath) {
        downloadByRootPath(req, resp, fileOldName, rootPath + File.separator + filePath);
    }

    @Override
    public void downloadToLocal(String destFileName, String destFolder, String filePath) {
        try {
            FileUtils.Copy(rootPath + File.separator + filePath, destFolder + File.separator + destFileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("下载文件到本地失败！", e);
        }
    }

    @Override
    public void downloadByRootPath(HttpServletRequest req, HttpServletResponse resp, String fileOldName, String filePath) {
        InputStream in = null;
        try {
            in = new FileInputStream(filePath);
            resp.setCharacterEncoding("UTF-8");
            responseContentTypeSetter.setContentType(resp, fileOldName);

            resp.setHeader("Content-Disposition", "attachment;filename=\"".concat(fileNameConverter.getConvertedFileName(req, fileOldName)).concat("\""));
            resp.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            resp.setHeader("Connection", "close");
            int len = 0;
            byte[] buffer = new byte[1024 * 1024];
            OutputStream out = resp.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("下载文件失败！", e);
        } finally {
            if (!(in == null)) {
                try {
                    in.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public FileNameConverter getFileNameConverter() {
        return fileNameConverter;
    }

    public void setFileNameConverter(FileNameConverter fileNameConverter) {
        this.fileNameConverter = fileNameConverter;
    }

    public ResponseContentTypeSetter getResponseContentTypeSetter() {
        return responseContentTypeSetter;
    }

    public void setResponseContentTypeSetter(ResponseContentTypeSetter responseContentTypeSetter) {
        this.responseContentTypeSetter = responseContentTypeSetter;
    }
}
