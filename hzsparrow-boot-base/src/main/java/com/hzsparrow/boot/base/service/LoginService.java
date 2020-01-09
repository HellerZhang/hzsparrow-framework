package com.hzsparrow.boot.base.service;

import com.hzsparrow.boot.base.contant.LoginUserEnum;
import com.hzsparrow.boot.base.entity.HzsRole;
import com.hzsparrow.boot.base.entity.HzsUser;
import com.hzsparrow.boot.base.vo.LoginVO;
import com.hzsparrow.framework.model.result.ResultDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class LoginService {

    @Autowired
    private HzsUserService hzsUserService;

    @Autowired
    private HzsRoleService hzsRoleService;

    /**
     * 登录
     *
     * @param request
     * @param account
     * @param password
     * @param verifyCode
     * @return
     */
    public ResultDTO<LoginVO> login(HttpServletRequest request, String account, String password, String verifyCode) {
        String sessionVerifyCode = (String) request.getSession().getAttribute(LoginUserEnum.SESSION_VERIFY_CODE.getFlag());
        if (StringUtils.isNotBlank(sessionVerifyCode) && !StringUtils.equals(sessionVerifyCode.toLowerCase(), verifyCode.toLowerCase())) {
            return ResultDTO.getDataFaild(200, "验证码错误！", null);
        }
        HzsUser userEntity = hzsUserService.getByAccount(account).getData();
        if (userEntity == null) {
            return ResultDTO.getDataFaild(200, "账号不存在！", null);
        } else {
            String entryPassword = hzsUserService.getEntryPassword(account, password);
            if (!StringUtils.equals(entryPassword, userEntity.getHsuPassword())) {
                return ResultDTO.getDataFaild(200, "密码错误！", null);
            }
            HzsRole role = hzsRoleService.getById(userEntity.getHsrId()).getData();
            if (role == null) {
                return ResultDTO.getDataFaild(200, "角色信息错误！", null);
            }

            HttpSession session = request.getSession();
            LoginVO loginEntity = new LoginVO();
            loginEntity.setUserId(userEntity.getHsuId());
            loginEntity.setAccount(userEntity.getHsuAccount());
            loginEntity.setUserName(userEntity.getHsuName());
            loginEntity.setPowerGuid(userEntity.getHsrId());
            loginEntity.setPowerName(role.getHsrName());
            loginEntity.setPowerType(role.getHsrType());
            loginEntity.setPhone(userEntity.getMobile());
            session.setAttribute(LoginUserEnum.SESSION_USER.getFlag(), loginEntity);
            return ResultDTO.getDataSuccess(loginEntity);
        }
    }
}
