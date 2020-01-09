package com.hzsparrow.boot.base.controller;

import com.hzsparrow.boot.base.component.BaseController;
import com.hzsparrow.boot.base.contant.LoginUserEnum;
import com.hzsparrow.boot.base.service.LoginService;
import com.hzsparrow.boot.base.vo.LoginVO;
import com.hzsparrow.framework.model.result.ResultDTO;
import com.hzsparrow.framework.utils.VerifyCodeImgUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录模块
 */
@RestController
@RequestMapping("/hzs")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     *
     * @param account    账号
     * @param password   密码
     * @param verifyCode 验证码
     * @return 登录结果, 携带用户数据
     */
    @RequestMapping("/login")
    public ResultDTO<LoginVO> login(HttpServletRequest request, String account, String password, String verifyCode) {
        return loginService.login(request, account, password, verifyCode);
    }

    /**
     * 退出
     *
     * @return
     */
    @RequestMapping("/logout")
    public ResultDTO<Object> logout(HttpSession session) {
        session.setAttribute(LoginUserEnum.SESSION_USER.getFlag(), null);
        return ResultDTO.getOptionSuccess();
    }

    /**
     * 获取登录的用户信息
     *
     * @return
     */
    @RequestMapping("/getLoginUser")
    public ResultDTO<LoginVO> getLoginUser() {
        return ResultDTO.getDataSuccess((LoginVO) getSessionUser());
    }

    /**
     * 获取验证码
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getVerifyCode")
    public void getVerifyCode(HttpServletResponse response) throws IOException {
        String verifyCode = VerifyCodeImgUtils.generateVerifyCode(4);
        session.setAttribute(LoginUserEnum.SESSION_VERIFY_CODE.getFlag(), verifyCode);
        VerifyCodeImgUtils.outputImage(106, 54, response.getOutputStream(), verifyCode);
    }

}
