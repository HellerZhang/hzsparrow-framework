package com.hzsparrow.business.base.contant;

import lombok.Getter;

@Getter
public enum LoginUserEnum {
    SESSION_USER("session_user","用户在session中存储的key值"),// NL
    SESSION_VERIFY_CODE("session_verify_code","用户登录验证码"),// NL
    ;

    /**
     * 标志
     */
    private String flag;

    /**
     * 描述
     */
    private String comment;

    LoginUserEnum(String flag, String comment){
        this.flag = flag;
        this.comment = comment;
    }


}
