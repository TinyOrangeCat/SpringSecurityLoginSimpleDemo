package com.yue.springsecurityjwtlogindemo2.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.springsecurityjwtlogindemo2.models.SystemMessageConstants;
import com.yue.springsecurityjwtlogindemo2.utils.LanguageUtils;
import com.yue.springsecurityjwtlogindemo2.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/26 23:03
 */
@Component
public class SystemAccessDenyHandler implements AccessDeniedHandler {
    @Autowired
    private LanguageUtils languageUtils;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String exceptionMessage = accessDeniedException.getMessage();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        RespBean respBean = null;
        if(exceptionMessage != null && !"".equals(exceptionMessage)){
            respBean = RespBean.accessDeny(exceptionMessage);
        }else{
            String returnMessage = languageUtils.getMessage(SystemMessageConstants.ACCESS_DENIED);
            respBean = RespBean.accessDeny(returnMessage);
        }
        printWriter.write(new ObjectMapper().writeValueAsString(respBean));
        printWriter.flush();
        printWriter.close();
    }
}
