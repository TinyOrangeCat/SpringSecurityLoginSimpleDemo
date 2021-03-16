package com.yue.springsecurityjwtlogindemo2.filters;

import com.yue.springsecurityjwtlogindemo2.exceptions.CaptchaNotMatchException;
import com.yue.springsecurityjwtlogindemo2.models.SystemMessageConstants;
import com.yue.springsecurityjwtlogindemo2.utils.LanguageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @Description 处理对应路径的登录信息
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/26 19:33
 */
@Api(value = "系统处理User登录")
public class JWTUserLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final String TAG = "YUE UsernamePasswordAuthenticationFilter ";

    @Autowired
    private LanguageUtils languageUtils;

    @ApiParam(name = "loginAccount",required = true,value="用户登录账户")
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "loginAccount";
    @ApiParam(name = "loginPassword",required = true,value="用户登录密码")
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "loginPassword";

    @ApiParam(name = "loginCode",required = true,value="登录验证码")
    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "loginCode";//用户输入的图片验证码
    public static final String SPRING_SECURITY_FORM_SESSION_CAPTCHA_KEY = "captchaSessionCode";//系统的图片验证码

    @ApiParam(name = "用户登录路径",value = "/userLogin",readOnly = true,access = "POST")
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/userLogin",
            "POST");

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;

    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

    private String captchaCodeParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;
    private String captchaSessionCodeParameter = SPRING_SECURITY_FORM_SESSION_CAPTCHA_KEY;

    private boolean postOnly = true;

    public JWTUserLoginAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println(TAG+"user attemptAuthentication");
        if (this.postOnly && !request.getMethod().equals("POST")) {
            String exceptionMsg = languageUtils.getMessage(SystemMessageConstants.AUTHENTICATION_METHOD_NOT_SUPPORTED);
            throw new AuthenticationServiceException(exceptionMsg + request.getMethod());
        }
        //验证图形验证码 START
        String captchaCode = obtainCode(request);
        captchaCode = (captchaCode != null) ? captchaCode : "";
        String captchaSessionCode = obtainSessionCode(request);
        captchaSessionCode = (captchaSessionCode != null) ? captchaSessionCode : "";
        System.out.println(TAG+"manager attemptAuthentication code: "+captchaCode+",sessionCode : "+captchaSessionCode);
        if("".equals(captchaCode) || "".equals(captchaSessionCode) || !captchaCode.equalsIgnoreCase(captchaSessionCode)){
            String exceptionMsg = languageUtils.getMessage(SystemMessageConstants.CAPTCHA_NOT_MATCH_ERROR);
            throw new CaptchaNotMatchException(exceptionMsg);
        }
        //验证图形验证码 END

        String username = obtainUsername(request);
        username = (username != null) ? username : "";
        username = username.trim();
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(this.captchaCodeParameter);
    }

    protected String obtainSessionCode(HttpServletRequest request) {
        return String.valueOf(request.getSession().getAttribute(this.captchaSessionCodeParameter));
    }

    protected String obtainPassword(HttpServletRequest request) {
        System.out.println(TAG+"obtainPassword");

        return request.getParameter(this.passwordParameter);
    }

    protected String obtainUsername(HttpServletRequest request) {
        System.out.println(TAG+"obtainUsername");
        return request.getParameter(this.usernameParameter);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        System.out.println(TAG+"setDetails");

        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        System.out.println(TAG+"setUsernameParameter");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        System.out.println(TAG+"setPasswordParameter");
       this.passwordParameter = passwordParameter;
    }

    public void setPostOnly(boolean postOnly) {
        System.out.println(TAG+"setPostOnly");
        this.postOnly = postOnly;
    }
}
