package com.yue.springsecurityjwtlogindemo2.providers;

import com.yue.springsecurityjwtlogindemo2.models.SystemLoginAccount;
import com.yue.springsecurityjwtlogindemo2.services.impls.SystemUserDetailsService;
import com.yue.springsecurityjwtlogindemo2.utils.SystemUserDetailsAuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/27 15:25
 */
public class SystemManagerAuthenticationProvider implements AuthenticationProvider {
    private static final String TABLE_NAME = "tab_manager";

    @Autowired
    @Qualifier("systemUserDetailsService")
    public SystemUserDetailsService systemUserDetailsService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private SystemUserDetailsAuthenticationUtils systemUserDetailsAuthenticationUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //此处是用户输入的Username和Password
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken)authentication;
        String username = String.valueOf(authenticationToken.getPrincipal());
        SystemLoginAccount userDetails;
        try {
            //userDetails = systemUserDetailsAuthenticationUtils.retrieveUser(username,authenticationToken,TABLE_NAME);
            userDetails = retrieveUser(username,authenticationToken,TABLE_NAME);
        }catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Can't find the manager account!");
        }
        try {
            systemUserDetailsAuthenticationUtils.userAccountCheck(userDetails);
            //systemUserDetailsAuthenticationUtils.checkPasswordToken(userDetails,authenticationToken);
            //userAccountCheck(userDetails);
            checkPasswordToken(userDetails,authenticationToken);
        }catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Manager password or account is wrong!");
        }
        //不要暴露密码
        //userDetails = systemUserDetailsAuthenticationUtils.safeUserDetails(userDetails);
        userDetails = safeUserDetails(userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
//    认证代码

    protected SystemLoginAccount retrieveUser(String username, UsernamePasswordAuthenticationToken authentication,String tableName)
            throws AuthenticationException {
        //prepareTimingAttackProtection();
        try {
            SystemLoginAccount loadedUser = systemUserDetailsService.loadUserByUsername(username,tableName);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        }
        catch (UsernameNotFoundException ex) {
            throw ex;
        }
        catch (InternalAuthenticationServiceException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    private void checkPasswordToken(SystemLoginAccount userDetails,UsernamePasswordAuthenticationToken authenticationToken){
        if (authenticationToken.getCredentials() == null) {
            throw new BadCredentialsException("Password is null");
        }
        String rowPassword = authenticationToken.getCredentials().toString();
        if(!passwordEncoder.matches(rowPassword,userDetails.getPassword())){
            throw new BadCredentialsException("Bad credentials");
        }
    }

    private SystemLoginAccount safeUserDetails(SystemLoginAccount userDetails){
        String rowPassword = userDetails.getPassword();
        userDetails.setLoginPassword(passwordEncoder.encode(rowPassword));
        return userDetails;
    }
//    认证代码 END


    @Override
    public boolean supports(Class<?> authentication) {
        //return false;
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
