package com.yue.springsecurityjwtlogindemo2.models;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/15 0:03
 */
public class SystemMessageConstants {
  public static final String SYSTEM_MESSAGE_BASENAME = "i18n/systemMessage";
  //切换语言成功
  public static final String CHANGE_LANGUAGE_SUCCESS = "changeLanguageSuccess";
  //切换语言失败
  public static final String CHANGE_LANGUAGE_FAILED = "changeLanguageFailed";
  //账户或者密码错误
  public static final String ACCOUNT_OR_PASSWORD_WRONG = "accountOrPasswordError";
  //密码错误
  public static final String PASSWORD_WRONG = "passwordWrong";
  //登录成功
  public static final String LOGIN_SUCCESS = "loginSuccess";
  //登录失败
  public static final String LOGIN_FAILURE = "loginFailed";
  //权限不足
  public static final String AUTHENTICATION_FAILURE = "authenticateFailed";
  //访问被拒
  public static final String ACCESS_DENIED = "accessDeny";
  //图形验证码不正确
  public static final String CAPTCHA_NOT_MATCH_ERROR = "captchaNotMatch";
  //访问方法不支持
  public static final String AUTHENTICATION_METHOD_NOT_SUPPORTED = "authenticateMethodWrong";
  //找不到用户信息
  public static final String ACCOUNT_NOT_FOUND = "accountNotFound";
  //Bad credentials
  public static final String BAD_CREDENTIALS = "badCredentials";
  //密码为空
  public static final String PASSWORD_IS_NULL = "passwordIsNull";

  //退出登录成功
  public static final String LOGOUT_SUCCESS = "logoutSuccess";

  //查询成功
  public static final String QUERY_SUCCESS = "querySuccess";
  //查询不到信息
  public static final String QUERY_EMPTY = "queryEmpty";
  //查找到管理员信息
  public static final String QUERY_MANAGER_SUCCESS = "queryManagerSuccess";
  //没有查找到管理员信息
  public static final String QUERY_MANAGER_EMPTY = "queryManagerEmpty";
  //查找到用户信息
  public static final String QUERY_USER_SUCCESS = "queryUserSuccess";
  //没有查找到用户信息
  public static final String QUERY_USER_EMPTY = "queryUserEmpty";

}
