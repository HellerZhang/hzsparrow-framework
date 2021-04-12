package com.hzsparrow.framework.utils.autoconfigure;

import com.hzsparrow.framework.utils.aliyunsms.AliyunSmsSendUtils;
import com.hzsparrow.framework.utils.property.AliyunSmsProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AliyunSmsProperty.class})
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

}
