package com.hzsparrow.framework.utils.autoconfigure;

import com.hzsparrow.framework.utils.aliyunsms.AliyunSmsSendUtils;
import com.hzsparrow.framework.utils.files.FileUploadUtils;
import com.hzsparrow.framework.utils.ftp.ReadFTPFile;
import com.hzsparrow.framework.utils.ftp.WriteFTPFile;
import com.hzsparrow.framework.utils.property.AliyunSmsProperty;
import com.hzsparrow.framework.utils.property.FileUploadProperty;
import com.hzsparrow.framework.utils.property.FtpProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AliyunSmsProperty.class, FileUploadProperty.class, FtpProperty.class})
public class HzSparrowFrameworkUtilsConfiguration {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    @ConditionalOnProperty(prefix = "hzsparrow.aliyunsms", name = {"accessKeyId","accessSecret"})
    public AliyunSmsSendUtils aliyunSmsSendUtils(AliyunSmsProperty aliyunSmsProperty){
        AliyunSmsSendUtils utils = new AliyunSmsSendUtils();
        utils.setAccessKeyId(aliyunSmsProperty.getAccessKeyId());
        utils.setAccessSecret(aliyunSmsProperty.getAccessSecret());
        logger.info("创建aliyunsms工具成功！");
        return utils;
    }

    @Bean
    @ConditionalOnProperty(name = "hzsparrow.fu.fileServerType", havingValue = "1")
    public FileUploadUtils fileUploadUtils(FileUploadProperty property){
        FileUploadUtils utils = new FileUploadUtils();
        utils.setFileServerType(property.getFileServerType());
        utils.setMaxFileSize(property.getMaxFileSize());
        utils.setRootPath(property.getRootPath());
        utils.setSecondPath(property.getSecondPath());
        logger.info("创建本地上传文件工具成功！");
        return utils;
    }

    @Bean
    @ConditionalOnProperty(name = "hzsparrow.fu.fileServerType", havingValue = "2")
    public FileUploadUtils ftpFileUploadUtils(FileUploadProperty property, FtpProperty ftpProperty){
        FileUploadUtils utils = new FileUploadUtils();
        utils.setFileServerType(property.getFileServerType());
        utils.setMaxFileSize(property.getMaxFileSize());
        utils.setRootPath(property.getRootPath());
        utils.setSecondPath(property.getSecondPath());

        ReadFTPFile readFTPFile = new ReadFTPFile();
        readFTPFile.setFtpHost(ftpProperty.getFtpHost());
        readFTPFile.setFtpPort(ftpProperty.getFtpPort());
        readFTPFile.setFtpUserName(ftpProperty.getFtpUserName());
        readFTPFile.setFtpPassword(ftpProperty.getFtpPassword());
        utils.setFtpReader(readFTPFile);

        WriteFTPFile writeFTPFile = new WriteFTPFile();
        writeFTPFile.setFtpHost(ftpProperty.getFtpHost());
        writeFTPFile.setFtpPort(ftpProperty.getFtpPort());
        writeFTPFile.setFtpUserName(ftpProperty.getFtpUserName());
        writeFTPFile.setFtpPassword(ftpProperty.getFtpPassword());
        utils.setFtpWriter(writeFTPFile);
        logger.info("创建ftp上传文件工具成功！");
        return utils;
    }
}
