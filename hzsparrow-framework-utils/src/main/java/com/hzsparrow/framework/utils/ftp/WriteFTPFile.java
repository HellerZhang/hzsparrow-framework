/**
 * $Id:$
 * Copyright 2019 HellerZhang. All rights reserved.
 */
package com.hzsparrow.framework.utils.ftp;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * ftp 工具类 写入文件到ftp服务器
 * 
 * @author leon
 * @since 2017年3月30日 上午10:31:29
 */
public class WriteFTPFile {

    private Logger logger = LoggerFactory.getLogger(WriteFTPFile.class);

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

    /**
     * 本地上传文件到FTP服务器
     *
     * @param strFtpPath
     * @param stream
     */
    public void upload(String strFtpPath, InputStream stream) {
        strFtpPath = strFtpPath.replace(File.separator, FTPUtil.strSeparator);

        if (logger.isInfoEnabled()) {
            logger.info("开始上传文件到FTP.");
        }
        try {
            openConnect();
            String remoteFileName = new String(strFtpPath.getBytes("UTF-8"), "iso-8859-1");
            String strDirectory = null;

            logger.info("--strFtpPath=" + strFtpPath);
            logger.info("--strDirectory=" + strFtpPath.indexOf(FTPUtil.strSeparator));

            if (strFtpPath.indexOf(FTPUtil.strSeparator) > -1) {
                if (strFtpPath.lastIndexOf(FTPUtil.strSeparator) > 0) {
                    strDirectory = new String(
                            strFtpPath.substring(0, strFtpPath.lastIndexOf(FTPUtil.strSeparator)).getBytes("UTF-8"),
                            "iso-8859-1");
                    logger.info("--strDirectory subString ");
                }
            }

            logger.info("--strDirectory=" + strDirectory);

            // 设置PassiveMode传输  
            ftpClient.enterLocalPassiveMode();
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            // 设置以二进制流的方式传输  
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 对远程目录的处理  

            if (StringUtils.isNotBlank(strDirectory) && strDirectory.contains(FTPUtil.strSeparator)) {
                createDirectory(strDirectory);
            }

            ftpClient.storeFile(remoteFileName, stream);
            stream.close();
            if (logger.isInfoEnabled()) {
                logger.info("上传文件" + remoteFileName + "到FTP成功!");
            }

        } catch ( Exception e) {
            throw new FTPException("上传文件失败，请检查ftp配置", e);
        }finally {
            disConnect();
        }
    }

    private void createDirectory(String strDirectory) throws IOException {
        if (strDirectory.contains(FTPUtil.strSeparator)) {
            String[] paths = strDirectory.split(FTPUtil.strSeparator);
            String tmp = "";
            for (String p : paths) {
                if (StringUtils.isNotBlank(p)) {
                    tmp += FTPUtil.strSeparator + p;
                    ftpClient.makeDirectory(tmp);
                }
            }

            if (logger.isInfoEnabled()) {
                logger.info("--创建目录=" + strDirectory);
            }
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
