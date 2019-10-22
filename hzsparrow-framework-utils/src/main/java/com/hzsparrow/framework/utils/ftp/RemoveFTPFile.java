package com.hzsparrow.framework.utils.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

public class RemoveFTPFile {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private FTPClient ftpClient;

    /**
     * ftp地址
     */
    private String ftpHost = "";

    /**
     * ftp端口
     */
    private Integer ftpPort = 21;

    /**
     * ftp账号
     */
    private String ftpUserName = "";

    /**
     * ftp密码
     */
    private String ftpPassword = "";

    private void openConnect() throws FTPException {

        try {
            ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPort, ftpUserName, ftpPassword);
        } catch (Exception e) {
            throw new FTPException("打开FTP链接失败，请检查ftp配置", e);
        }

    }

    private void disConnect() throws FTPException {
        try {
            if (ftpClient != null) {
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            throw new FTPException("上传链接关闭失败，请检查ftp配置", e);
        }
    }

    public boolean removeFileForFTP(String strFilePath) {
        boolean flag = false;
        logger.info("开始删除" + strFilePath + "文件!");
        try {
            strFilePath = strFilePath.replace(File.separator, "/");
            strFilePath = new String(strFilePath.getBytes("UTF-8"), "iso-8859-1");

            openConnect();
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8"); // 中文支持  
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            flag = ftpClient.deleteFile(strFilePath);
            return flag;
        } catch (FileNotFoundException e) {
            logger.error("没有找到" + strFilePath + "文件");
            throw new FTPException("没有找到" + strFilePath + "文件", e);
        } catch (SocketException e) {
            logger.error("连接FTP失败,FTP的IP地址可能错误。");
            throw new FTPException("连接FTP失败,FTP的IP地址可能错误，请检查ftp配置", e);
        } catch (IOException e) {
            logger.error("文件读取错误。");
            throw new FTPException("文件读取错误。，请检查ftp配置", e);
        } catch (Exception e) {
            logger.error("连接FTP失败,FTP的端口错误。");
            throw new FTPException("连接FTP失败,FTP的端口错误，请检查ftp配置", e);
        }finally {
            disConnect();
        }
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }
}
