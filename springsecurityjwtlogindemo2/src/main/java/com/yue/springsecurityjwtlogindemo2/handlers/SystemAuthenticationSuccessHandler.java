package com.yue.springsecurityjwtlogindemo2.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.springsecurityjwtlogindemo2.models.SystemLoginAccount;
import com.yue.springsecurityjwtlogindemo2.services.ISystemMailService;
import com.yue.springsecurityjwtlogindemo2.utils.JWTTokenUtils;
import com.yue.springsecurityjwtlogindemo2.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/*
 * @Description 账号认证成功
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/26 22:48
 */
@Component
public class SystemAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JWTTokenUtils jwtTokenUtils;
    @Value("${developer.username}")
    private  String developerName;
    @Value("${developer.role}")
    private  String developerRole;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private ISystemMailService systemMailServiceImpl;
    @Value("${developer.email}")
    private String email;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = null;
        boolean isDeveloper = false;
        Integer accountId = null;
        String username = null;
        if(authentication != null && authentication.getPrincipal() !=null) {
            SystemLoginAccount userDetails = (SystemLoginAccount) authentication.getPrincipal();
            accountId = userDetails.getAccountId();
            username = userDetails.getUsername();
            System.out.println("YUE onAuthenticationSuccess "+userDetails.getUsername()+","+authentication.getCredentials()+","+userDetails.getAccountId());
            //登录后发送邮件
            //systemMailServiceImpl.sendMailMailQueue(userDetails.getUsername(),email);
            //systemMailServiceImpl.sendMailMail(userDetails.getUsername(),email);
            token = jwtTokenUtils.generateToken(userDetails);
            if(developerName.equals(userDetails.getUsername())){
                isDeveloper = true;
            }
        }
        if(isDeveloper){
            response.sendRedirect("/developer/swagger-ui/index.html");
            return;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        Map<String,Object> tokenInfo = new HashMap<>();
        tokenInfo.put("token",token);
        tokenInfo.put("tokenHead",tokenHead);
        tokenInfo.put("accountId",accountId);
        tokenInfo.put("username",username);
        RespBean respBean = RespBean.success("账号认证成功!",tokenInfo);
        printWriter.write(new ObjectMapper().writeValueAsString(respBean));
        printWriter.flush();
        printWriter.close();
    }
}
