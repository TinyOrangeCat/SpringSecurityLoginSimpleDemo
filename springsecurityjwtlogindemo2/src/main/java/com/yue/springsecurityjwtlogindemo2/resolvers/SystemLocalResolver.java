package com.yue.springsecurityjwtlogindemo2.resolvers;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/*
 * @Description 国际化设置。
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/14 23:19
 */
public class SystemLocalResolver implements LocaleResolver {
  @Override
  public Locale resolveLocale(HttpServletRequest httpServletRequest) {
    String language = httpServletRequest.getParameter("lang");
    System.out.println("SystemLocalResolver : "+language);
    Locale locale = Locale.getDefault();
    if(language != null && !"".equals(language)){
      String []localeInfo = language.split("_");
      locale = new Locale(localeInfo[0],localeInfo[1]);
    }
    return locale;
  }

  @Override
  public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

  }
}
