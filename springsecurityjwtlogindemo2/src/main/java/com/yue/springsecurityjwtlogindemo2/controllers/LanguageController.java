package com.yue.springsecurityjwtlogindemo2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yue.springsecurityjwtlogindemo2.models.SystemMessageConstants;
import com.yue.springsecurityjwtlogindemo2.utils.RespBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/15 0:27
 */
@Api("切换语言")
@RestController
public class LanguageController {
  @Autowired
  MessageSource messageSource;

  @GetMapping("/changeLanguage")
  public void changeLanguage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String lang = request.getParameter("lang");
    System.out.println("changeLanguage lang = "+lang);
    String []langInfo = lang.split("_");
    Locale nowLocale = new Locale(langInfo[0],langInfo[1]);
    LocaleContextHolder.setLocale(nowLocale);
    System.out.println("changeLanguage nowLocal : "+nowLocale.getCountry());
    String returnMessage;
    boolean result = false;
    if(lang == null || "".equals(lang)){
      returnMessage = messageSource.getMessage(SystemMessageConstants.CHANGE_LANGUAGE_FAILED, null, nowLocale);
      //return RespBean.error(returnMessage);
    }else {
      returnMessage = messageSource.getMessage(SystemMessageConstants.CHANGE_LANGUAGE_SUCCESS, null, nowLocale);
      result = true;
      //return RespBean.success(returnMessage);
    }
      doResponse(response,returnMessage,result);
  }
  private void doResponse(HttpServletResponse response,String message,boolean result) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    PrintWriter printWriter = response.getWriter();
    RespBean respBean = null;
    if (result) {
      respBean = RespBean.success(message);
    }else {
      respBean = RespBean.error(message);
    }
    printWriter.write(new ObjectMapper().writeValueAsString(respBean));
    printWriter.flush();
    printWriter.close();
  }
}
