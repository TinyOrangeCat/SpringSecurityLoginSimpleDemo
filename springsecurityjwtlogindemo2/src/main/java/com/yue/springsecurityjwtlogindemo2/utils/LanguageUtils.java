package com.yue.springsecurityjwtlogindemo2.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/15 8:55
 */
@Component
public class LanguageUtils {
  @Autowired
  private MessageSource messageSource;

  public String getMessage(String messageKey){
    System.out.println("message locale:"+LocaleContextHolder.getLocale());
    return messageSource.getMessage(messageKey,null,LocaleContextHolder.getLocale());
  }

  public String getMessage(String messageKey,String language){
    Locale locale = LocaleContextHolder.getLocale();
    if(language != null && !"".equals(language)){
      String []languageInfo = language.split("_");
      locale = new Locale(languageInfo[0], languageInfo[1]);
    }else {
      return getMessage(messageKey);
    }
    return messageSource.getMessage(messageKey,null,locale);
  }
  public String getMessage(String messageKey,Locale locale){
    Locale trueLocale = locale;
    if(trueLocale == null){
      trueLocale = Locale.getDefault();
    }
    return messageSource.getMessage(messageKey,null,trueLocale);
  }

}
