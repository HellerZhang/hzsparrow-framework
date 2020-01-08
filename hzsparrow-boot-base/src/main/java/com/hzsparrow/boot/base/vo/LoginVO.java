package com.hzsparrow.boot.base.vo;

import com.hzsparrow.framework.model.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginVO extends BaseModel {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 账号
     */
    private String account;

    /**
     * 权限ID
     */
    private String powerGuid;

    /**
     * 权限名称
     */
    private String powerName;

    /**
     * 权限类别 1、管理；2、全局统计；3、普通统计;
     */
    private Integer powerType;

    /**
     * 电话
     */
    private String phone;

}
