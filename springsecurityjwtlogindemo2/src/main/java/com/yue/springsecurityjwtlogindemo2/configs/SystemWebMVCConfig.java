package com.yue.springsecurityjwtlogindemo2.configs;

import com.yue.springsecurityjwtlogindemo2.resolvers.SystemLocalResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/14 23:39
 */
@Configuration
public class SystemWebMVCConfig implements WebMvcConfigurer {

  @Bean("localResolver")
  public LocaleResolver localeResolver(){
    return new SystemLocalResolver();
  }
}
