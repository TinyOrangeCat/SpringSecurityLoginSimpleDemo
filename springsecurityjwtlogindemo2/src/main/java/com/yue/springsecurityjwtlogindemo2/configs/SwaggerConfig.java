package com.yue.springsecurityjwtlogindemo2.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/25 15:17
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.OAS_30);
        docket.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yue.springsecurityjwtlogindemo2"))
                //.paths(PathSelectors.any())
                .build();
                //.securitySchemes(securitySchemes());
                //.securityContexts(securityContext());
        return docket;
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("SpringSecurity & Vue Login Demo")
                .description("使用SpringBoot结合SpringSecurity、Vue、MybatisPlus搭建而成的登录系统，此文档是该系统的API。")
                .contact(new Contact("YueLee","https://space.bilibili.com/23172977","xiroiyuki@hotmail.com"))
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeys = new ArrayList<ApiKey>();
        ApiKey apiKey = new ApiKey("Authorization","Authorization","Header");
        apiKeys.add(apiKey);
        return apiKeys;
    }

    private List<SecurityContext> securityContext(){
        List<SecurityContext> securityContext = new ArrayList<>();
        securityContext.add(getContextByPath("/user/.*"));
        return securityContext;
    }

    private SecurityContext getContextByPath(String path) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(path))
                .build();
    }
    private List<SecurityReference> defaultAuth(){
        List<SecurityReference> securityContext = new ArrayList<>();
        AuthorizationScope authorizationScope =
                new AuthorizationScope("global","accessEverything");

        AuthorizationScope []authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        securityContext.add(new SecurityReference("Authorization",authorizationScopes));
        return securityContext;
    }
}
