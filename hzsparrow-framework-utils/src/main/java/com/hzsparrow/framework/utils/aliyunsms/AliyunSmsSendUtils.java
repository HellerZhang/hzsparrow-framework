/**
 * $Id:$
 * Copyright 2019-2019 ShiJiaZhuang ShanZhouWangLuo Technology Company Ltd. All rights reserved.
 */
package com.hzsparrow.framework.utils.aliyunsms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.hzsparrow.framework.utils.JsonUtils;
import com.hzsparrow.framework.utils.SimpleBeanUtils;
import com.hzsparrow.framework.utils.StringFormatUtils;

import java.util.Map;

/**
 * 
 * @author Heller.Zhang
 * @since 2019年5月30日 上午11:10:39
 */
public class AliyunSmsSendUtils {

    private String accessKeyId;

    private String accessSecret;

    /**
     * 发送短信
     * 
     * @param mobile 手机号
     * @param signName 签名
     * @param templateCode 模板编号
     * @param templateParam 模板数据
     * @return
     * @author Heller.Zhang
     * @since 2019年5月30日 下午4:00:28
     */
    public AliyunSmsSendResponseModel sendSms(String mobile, String signName, String templateCode,
            Object templateParam) {
        String templateParamJson = JsonUtils.serialize(templateParam);
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParamJson);
        try {
            CommonResponse response = client.getCommonResponse(request);
            Map<String, Object> resultMap = JsonUtils.readValue(response.getData());
            AliyunSmsSendResponseModel responseModel = new AliyunSmsSendResponseModel();
            for (String key : resultMap.keySet()) {
                SimpleBeanUtils.setProperty(response, StringFormatUtils.camelName(key), resultMap.get(key));
            }
            return responseModel;
        } catch (ServerException e) {
            throw new RuntimeException("Aliyun服务连接失败！", e);
        } catch (ClientException e) {
            throw new RuntimeException("Aliyun服务客户端信息异常！", e);
        }
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }
}
