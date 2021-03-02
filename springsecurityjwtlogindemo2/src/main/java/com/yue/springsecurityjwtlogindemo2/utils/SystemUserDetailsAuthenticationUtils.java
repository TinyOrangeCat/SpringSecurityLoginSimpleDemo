package com.yue.springsecurityjwtlogindemo2.utils;

import com.yue.springsecurityjwtlogindemo2.models.SystemLoginAccount;
import com.yue.springsecurityjwtlogindemo2.services.impls.SystemUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/27 15:19
 */
@Component
public class SystemUserDetailsAuthenticationUtils {

    public static void userAccountCheck(SystemLoginAccount user) {
        if (!user.isAccountNonLocked()) {
            //logger.debug("Failed to authenticate since user account is locked");
            throw new LockedException("User account is locked");
        }
        if (!user.isEnabled()) {
            //this.logger.debug("Failed to authenticate since user account is disabled");
            throw new DisabledException("User is disabled");
        }
        if (!user.isAccountNonExpired()) {
            //this.logger.debug("Failed to authenticate since user account is expired");
            throw new AccountExpiredException("User account has expired");
        }
        if (!user.isCredentialsNonExpired()) {
            //this.logger.debug("Failed to authenticate since user account credentials have expired");
            throw new CredentialsExpiredException("User credentials have expired");
        }
    }

}
