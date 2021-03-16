package com.yue.springsecurityjwtlogindemo2.configs;

import com.yue.springsecurityjwtlogindemo2.filters.JWTManagerLoginAuthenticationFilter;
import com.yue.springsecurityjwtlogindemo2.filters.JWTUserLoginAuthenticationFilter;
import com.yue.springsecurityjwtlogindemo2.filters.JWTTokenPreRequestFilter;
import com.yue.springsecurityjwtlogindemo2.handlers.*;
import com.yue.springsecurityjwtlogindemo2.providers.SystemManagerAuthenticationProvider;
import com.yue.springsecurityjwtlogindemo2.providers.SystemUserAuthenticationProvider;
import com.yue.springsecurityjwtlogindemo2.services.impls.SystemUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/26 15:44
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    @Bean
    public JWTTokenPreRequestFilter jwtTokenPreRequestFilter(){
        return new JWTTokenPreRequestFilter();
    }

    @Autowired
    public SystemAuthenticationSuccessHandler systemAuthenticationSuccessHandler;

    @Autowired
    public SystemAuthenticationFailureHandler systemAuthenticationFailureHandler;

    @Bean
    public JWTUserLoginAuthenticationFilter jwtUserLoginAuthenticationFilter() throws Exception{
        JWTUserLoginAuthenticationFilter jwtUserLoginAuthenticationFilter = new JWTUserLoginAuthenticationFilter();
        jwtUserLoginAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        jwtUserLoginAuthenticationFilter.setAuthenticationSuccessHandler(systemAuthenticationSuccessHandler);
        jwtUserLoginAuthenticationFilter.setAuthenticationFailureHandler(systemAuthenticationFailureHandler);
        return jwtUserLoginAuthenticationFilter;
    }

    @Bean
    public UsernamePasswordAuthenticationFilter userAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter userAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
        userAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        userAuthenticationFilter.setAuthenticationSuccessHandler(systemAuthenticationSuccessHandler);
        userAuthenticationFilter.setAuthenticationFailureHandler(systemAuthenticationFailureHandler);
        return userAuthenticationFilter;
    }

    @Bean
    public JWTManagerLoginAuthenticationFilter jwtManagerLoginAuthenticationFilter() throws Exception {
        JWTManagerLoginAuthenticationFilter jwtManagerLoginAuthenticationFilter = new JWTManagerLoginAuthenticationFilter();
        jwtManagerLoginAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        jwtManagerLoginAuthenticationFilter.setAuthenticationSuccessHandler(systemAuthenticationSuccessHandler);
        jwtManagerLoginAuthenticationFilter.setAuthenticationFailureHandler(systemAuthenticationFailureHandler);
        return jwtManagerLoginAuthenticationFilter;
    }

    @Autowired
    public SystemAccessDenyHandler systemAccessDenyHandler;

    @Autowired
    public SystemAuthenticationEntryPoint systemAuthenticationEntryPoint;

    @Autowired
    @Qualifier("systemUserDetailsService")
    public SystemUserDetailsService systemUserDetailsService;

    @Bean
    public SystemUserAuthenticationProvider systemUserAuthenticationProvider(){
        return new SystemUserAuthenticationProvider();
    }

    @Bean
    public SystemManagerAuthenticationProvider systemManagerAuthenticationProvider(){
        return new SystemManagerAuthenticationProvider();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(systemUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SystemLogoutSuccessHandler systemLogoutSuccessHandler(){
        return new SystemLogoutSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/userLogin","/managerLogin","/developer/**",
                        "/swagger-ui.html","/swagger-resources/**","/webjars/**",
                        "/*/api-docs","/getCaptcha","/ws/**","/changeLanguage").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers()
                .cacheControl();
        http.addFilterBefore(jwtTokenPreRequestFilter(),UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(userAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(jwtUserLoginAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(jwtManagerLoginAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint(systemAuthenticationEntryPoint)
                .accessDeniedHandler(systemAccessDenyHandler);

        http.logout().logoutSuccessHandler(systemLogoutSuccessHandler());

    }

    /**
     * @Author YueLi/xiroiyuki@hotmail.com
     * @Date 2021/2/27 17:00
     * @Description 添加验证登录办法
     * @Param
     * @Return
     **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(systemUserDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(systemUserAuthenticationProvider());
        auth.authenticationProvider(systemManagerAuthenticationProvider());
        auth.authenticationProvider(daoAuthenticationProvider());
    }
}
