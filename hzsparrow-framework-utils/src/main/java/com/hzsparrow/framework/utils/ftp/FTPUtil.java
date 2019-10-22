/**
 * $Id:$
 * Copyright 2019 HellerZhang. All rights reserved.
 */
package com.hzsparrow.framework.utils.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP 工具类，完成ftp连接的建立
 * 
 * @author leon
 * @since 2017年3月30日 上午10:29:23
 */
public class FTPUtil {

    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    public static String strSeparator = "/";

    /**
     * 获取FTPClient对象
     *
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost,Integer ftpPort,String ftpUserName,String ftpPassword) throws Exception {
        FTPClient ftpClient = null;
        ftpClient = new FTPClient();
        ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
        ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            logger.warn("未连接到FTP，用户名或密码错误。");
            ftpClient.disconnect();
        } else {
            logger.info("FTP连接成功。");
        }

        return ftpClient;
    }

}
