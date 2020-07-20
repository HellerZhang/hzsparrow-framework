package com.hzsparrow.business.base.vo;

import com.hzsparrow.business.base.entity.HzsUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HzsUserPageVO extends HzsUser {

    /**
     * 角色名称
     */
    private String hsrName;
}
