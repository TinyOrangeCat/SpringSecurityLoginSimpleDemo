package com.yue.springsecurityjwtlogindemo2.utils;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/15 8:46
 */
@Component
public class LocaleUtils {

  public boolean changeLocaleByLangString(String lang){
    if (lang == null || "".equals(lang)) {
        return false;
    }{
      String []langInfo = lang.split("_");
      Locale locale = new Locale(langInfo[0], langInfo[1]);
      LocaleContextHolder.setLocale(locale);
      return true;
    }
  }
}
