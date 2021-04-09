package com.hzsparrow.framework.utils.property;

import com.hzsparrow.framework.utils.upload.model.DefaultFileUploadComponentProperty;
import com.hzsparrow.framework.utils.upload.model.DefaultFileValidatorProperty;
import com.hzsparrow.framework.utils.upload.model.DefaultFtpFileUploadProperty;
import com.hzsparrow.framework.utils.upload.model.DefaultLocalFileUploadProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hzsparrow.fileupload")
public class HzSparrowFileUploadProperty {

    /**
     * 暂时支持local和ftp
     */
    private String serverType;

    /**
     * 文件上传组件
     */
    private DefaultFileUploadComponentProperty component = new DefaultFileUploadComponentProperty();

    /**
     * 文件校验器
     */
    private DefaultFileValidatorProperty validator = new DefaultFileValidatorProperty();

    /**
     * 本地文件上传参数
     */
    private DefaultLocalFileUploadProperty local = new DefaultLocalFileUploadProperty();

    /**
     * ftp文件上传参数
     */
    private DefaultFtpFileUploadProperty ftp = new DefaultFtpFileUploadProperty();

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public DefaultFileUploadComponentProperty getComponent() {
        return component;
    }

    public void setComponent(DefaultFileUploadComponentProperty component) {
        this.component = component;
    }

    public DefaultFileValidatorProperty getValidator() {
        return validator;
    }

    public void setValidator(DefaultFileValidatorProperty validator) {
        this.validator = validator;
    }

    public DefaultLocalFileUploadProperty getLocal() {
        return local;
    }

    public void setLocal(DefaultLocalFileUploadProperty local) {
        this.local = local;
    }

    public DefaultFtpFileUploadProperty getFtp() {
        return ftp;
    }

    public void setFtp(DefaultFtpFileUploadProperty ftp) {
        this.ftp = ftp;
    }
}
