package com.hzsparrow.framework.utils.upload.uploader.impl;

import com.hzsparrow.framework.utils.ftp.FTPUtil;
import com.hzsparrow.framework.utils.ftp.ReadFTPFile;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.FileDownloader;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.FileNameConverter;
import com.hzsparrow.framework.utils.upload.uploader.interfaces.ResponseContentTypeSetter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DefaultFtpFileDownloader implements FileDownloader {

    Logger logger = LoggerFactory.getLogger(getClass());

    private ReadFTPFile ftpReader;

    private FileNameConverter fileNameConverter;

    private ResponseContentTypeSetter responseContentTypeSetter;

    @Override
    public void download(HttpServletRequest req, HttpServletResponse resp, String fileOldName, String filePath) {
        InputStream in = null;
        try {
            String ftpPath = filePath.replace(File.separator, FTPUtil.strSeparator);
            in = ftpReader.readFileForFTP(ftpPath);
            if (in == null) {
                throw new RuntimeException("下载文件失败，未找到要下载的文件：" + filePath);
            }
            resp.setCharacterEncoding("UTF-8");
            responseContentTypeSetter.setContentType(resp, fileOldName);

            resp.setHeader("Content-Disposition", "attachment;filename=\""
                    .concat(fileNameConverter.getConvertedFileName(req, fileOldName)).concat("\""));
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

    @Override
    public void downloadToLocal(String destFileName, String destFolder, String filePath) {
        InputStream in = null;
        try {
            if (!destFolder.endsWith(File.separator)) {
                destFolder += File.separator;
            }
            String destPath = destFolder + destFileName;

            in = ftpReader.readFileForFTP(filePath);
            if (in == null) {
                throw new RuntimeException("下载文件失败，未找到要下载的文件：" + filePath);
            }
            OutputStream out = FileUtils.openOutputStream(FileUtils.getFile(destPath));
            int len;
            byte[] buffer = new byte[1024 * 1024];
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


    @Override
    @Deprecated
    public void downloadByRootPath(HttpServletRequest req, HttpServletResponse resp, String fileOldName, String filePath) {
        throw new RuntimeException("ftp下载工具不支持使用绝对路径下载");
    }

    public ReadFTPFile getFtpReader() {
        return ftpReader;
    }

    public void setFtpReader(ReadFTPFile ftpReader) {
        this.ftpReader = ftpReader;
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
