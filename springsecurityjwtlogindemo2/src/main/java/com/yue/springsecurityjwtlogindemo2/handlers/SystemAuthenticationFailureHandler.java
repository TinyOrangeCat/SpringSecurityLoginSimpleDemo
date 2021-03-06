package com.yue.springsecurityjwtlogindemo2.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.springsecurityjwtlogindemo2.models.SystemMessageConstants;
import com.yue.springsecurityjwtlogindemo2.utils.LanguageUtils;
import com.yue.springsecurityjwtlogindemo2.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * @Description 认证失败
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/26 22:43
 */
@Component
public class SystemAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private LanguageUtils languageUtils;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String exceptionMessage = exception.getMessage();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        RespBean respBean = null;
        if(exceptionMessage != null && !"".equals(exceptionMessage)){
            respBean = RespBean.unauthorized(exceptionMessage);
        }else{
            String returnMessage = languageUtils.getMessage(SystemMessageConstants.AUTHENTICATION_FAILURE);
            respBean = RespBean.unauthorized(returnMessage);
        }
        printWriter.write(new ObjectMapper().writeValueAsString(respBean));
        printWriter.flush();
        printWriter.close();
    }
}
