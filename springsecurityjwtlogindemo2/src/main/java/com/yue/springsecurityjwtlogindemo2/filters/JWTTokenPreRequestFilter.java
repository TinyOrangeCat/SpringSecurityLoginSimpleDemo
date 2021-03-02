package com.yue.springsecurityjwtlogindemo2.filters;

import com.yue.springsecurityjwtlogindemo2.models.SystemLoginAccount;
import com.yue.springsecurityjwtlogindemo2.services.impls.SystemUserDetailsService;
import com.yue.springsecurityjwtlogindemo2.utils.JWTTokenUtils;
import com.yue.springsecurityjwtlogindemo2.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
 * @Description 请求前会先走这里
 * YUE JWTTokenFilter
    YUE JWTTokenFilter
    YUE JWTTokenFilter
    YUE UsernamePasswordAuthenticationFilter attemptAuthentication
    YUE UsernamePasswordAuthenticationFilter obtainUsername
    YUE UsernamePasswordAuthenticationFilter obtainPassword
    YUE UsernamePasswordAuthenticationFilter setDetails
    YUE SystemUserDetailsService
    YUE onAuthenticationSuccess admin
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/26 19:25
 */
public class JWTTokenPreRequestFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JWTTokenUtils jwtTokenUtils;
    @Autowired
    @Qualifier("systemUserDetailsService")
    private SystemUserDetailsService systemUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("YUE JWTTokenPreRequestFilter");
        String authenticationToken = httpServletRequest.getHeader(tokenHeader);
        if(authenticationToken != null && authenticationToken.startsWith(tokenHead)){
            String token = authenticationToken.substring(tokenHead.length());
            String accountName = jwtTokenUtils.getUserNameFromToken(token);
                System.out.println("username:"+accountName);
            if(accountName != null && null == SecurityContextHolder.getContext().getAuthentication()){
                Integer accountId = jwtTokenUtils.getAccountIdFromToken(token);
                List<String> authorities = jwtTokenUtils.getAuthoritiesFromToken(token);
                System.out.println("id:"+accountId);
                System.out.println("authorities:"+authorities);
                SystemLoginAccount userDetails = systemUserDetailsService.loadUserByUsername("developer");
                for(String authorityString : authorities){
                    if(authorityString.equals("ROLE_manager")){
                        userDetails = systemUserDetailsService.loadUserByUsername(accountName,"tab_manager");
                        break;
                    }else if(authorityString.equals("ROLE_user")){
                        userDetails = systemUserDetailsService.loadUserByUsername(accountName,"tab_user");
                        break;
                    }
                }

                if(jwtTokenUtils.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    //return;
                }
            }

        }
        System.out.println("header:"+authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        //super.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
    }
}
