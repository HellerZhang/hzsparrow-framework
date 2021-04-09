package com.hzsparrow.framework.utils.autoconfigure;

import com.hzsparrow.framework.utils.property.HzSparrowFileUploadProperty;
import com.hzsparrow.framework.utils.upload.HzSparrowFileUploadUtils;
import com.hzsparrow.framework.utils.upload.HzSparrowFileUploadUtilsFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HzSparrowFileUploadProperty.class)
public class HzSparrowFileUploadConfiguration {

    HzSparrowFileUploadUtilsFactory factory = new HzSparrowFileUploadUtilsFactory();

    @Bean
    @ConditionalOnProperty(name = "hzsparrow.fileupload.serverType", havingValue = "local")
    public HzSparrowFileUploadUtils localHzSparrowFileUploadUtils(HzSparrowFileUploadProperty property) throws ClassNotFoundException {
        return factory.createDefaultLocalUtils(property.getComponent(), property.getValidator(), property.getLocal());
    }

    @Bean
    @ConditionalOnProperty(name = "hzsparrow.fileupload.serverType", havingValue = "ftp")
    public HzSparrowFileUploadUtils ftpHzSparrowFileUploadUtils(HzSparrowFileUploadProperty property) throws ClassNotFoundException {
        return factory.createDefaultFtpUtils(property.getComponent(), property.getValidator(), property.getFtp());
    }

}
