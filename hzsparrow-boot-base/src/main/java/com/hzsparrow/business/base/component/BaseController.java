package com.hzsparrow.business.base.component;


import com.alibaba.fastjson.JSON;
import com.hzsparrow.business.base.contant.LoginUserEnum;
import com.hzsparrow.business.base.vo.LoginVO;
import com.hzsparrow.framework.model.result.ResultDTO;
import com.hzsparrow.framework.utils.CalendarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.io.PrintWriter;
import java.util.Date;

public class BaseController {
    protected HttpSession session;

    Logger logger = LoggerFactory.getLogger(getClass());

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(CalendarUtils.parseDateWithTry(text));
            }
        });
    }

    @ModelAttribute
    protected void init(HttpServletRequest request, HttpServletResponse response) {
        this.session = request.getSession();
    }

    private void printErrorMessage(HttpServletResponse response, ResultDTO<String> msg) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json");
            String msgJson = JSON.toJSONString(msg);
            PrintWriter out = response.getWriter();
            out.print(msgJson);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    @ExceptionHandler({Exception.class})
    protected ModelAndView exception(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ResultDTO<String> res = new ResultDTO<>();
        res.setSuccess(false);
        if (ex instanceof RuntimeException) {
            res.setMsg(ex.getMessage());
        } else {
            res.setMsg("系统错误，请联系管理员！");
            res.setData(ex.getMessage());
        }
        logger.error(ex.getMessage(), ex);
        printErrorMessage(response, res);
        return null;
    }


    /**
     * 获取登录用户的信息
     *
     * @return
     */
    protected LoginVO getSessionUser() {
        return (LoginVO) session.getAttribute(LoginUserEnum.SESSION_USER.getFlag());
    }
}
