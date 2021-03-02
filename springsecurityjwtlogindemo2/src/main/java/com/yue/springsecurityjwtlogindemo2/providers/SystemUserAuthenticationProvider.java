package com.yue.springsecurityjwtlogindemo2.providers;

import com.yue.springsecurityjwtlogindemo2.models.SystemLoginAccount;
import com.yue.springsecurityjwtlogindemo2.services.impls.SystemUserDetailsService;
import com.yue.springsecurityjwtlogindemo2.utils.SystemUserDetailsAuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/27 11:54
 */
public class SystemUserAuthenticationProvider implements AuthenticationProvider {
    private static final String TABLE_NAME = "tab_user";
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Autowired
    @Qualifier("systemUserDetailsService")
    public SystemUserDetailsService systemUserDetailsService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private SystemUserDetailsAuthenticationUtils systemUserDetailsAuthenticationUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                this.messages.getMessage("UsernamePasswordAuthenticationToken.onlySupports", "Only UsernamePasswordAuthenticationToken is supported"));
        //此处是用户输入的Username和Password
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken)authentication;
        String username = String.valueOf(authenticationToken.getPrincipal());
        SystemLoginAccount userDetails;
        try {
            userDetails = retrieveUser(username,authenticationToken,TABLE_NAME);
        }catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Can't find the user account!");
        }
        try {
            systemUserDetailsAuthenticationUtils.userAccountCheck(userDetails);
            checkPasswordToken(userDetails,authenticationToken);
        }catch (AuthenticationException e) {
            throw new UsernameNotFoundException("User password or account is wrong!");
        }
        //不要暴露密码
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
