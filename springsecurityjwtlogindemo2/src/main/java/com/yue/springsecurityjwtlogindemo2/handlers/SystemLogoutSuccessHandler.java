package com.yue.springsecurityjwtlogindemo2.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.springsecurityjwtlogindemo2.utils.RespBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/27 19:05
 */
public class SystemLogoutSuccessHandler implements LogoutSuccessHandler {
    @Value("${developer.username}")
    private  String developerName;
    @Value("${developer.role}")
    private  String developerRole;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("YUE onLogoutSuccess");
        if(authentication != null && authentication.getName() != null && authentication.getName().equals(developerName)){
            response.sendRedirect("/login");
        }else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            RespBean respBean = RespBean.success("成功退出!");
            printWriter.write(new ObjectMapper().writeValueAsString(respBean));
            printWriter.flush();
            printWriter.close();
        }
    }
}
