/*
package com.yue.springsecurityjwtlogindemo2.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

*/
/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/1 20:29
 *//*

public class SystemUsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private Object credentials;

    private String captchaCode;

    public SystemUsernamePasswordAuthenticationToken(Object principal, Object credentials,String captchaCode) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.captchaCode = captchaCode;
        setAuthenticated(false);
    }

    */
/**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     *//*

    public SystemUsernamePasswordAuthenticationToken(Object principal, Object credentials,String captchaCode,
                                               Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.captchaCode = captchaCode;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getCaptchaCode() {
        return this.captchaCode;
    }
}
*/
