package com.yue.springsecurityjwtlogindemo2.controllers;


import com.yue.springsecurityjwtlogindemo2.beans.Role;
import com.yue.springsecurityjwtlogindemo2.beans.User;
import com.yue.springsecurityjwtlogindemo2.beans.UserInfo;
import com.yue.springsecurityjwtlogindemo2.models.SystemMessageConstants;
import com.yue.springsecurityjwtlogindemo2.services.IRoleService;
import com.yue.springsecurityjwtlogindemo2.services.IUserInfoService;
import com.yue.springsecurityjwtlogindemo2.services.IUserService;
import com.yue.springsecurityjwtlogindemo2.utils.LanguageUtils;
import com.yue.springsecurityjwtlogindemo2.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YueLi/xiroiyuki@hotmail.com
 * @since 2021-02-26
 */
@Api(value = "用户请求处理")
//@Controller
@RestController
public class UserController {

    @Autowired
    private IUserService userServiceImpl;

    @Autowired
    private IRoleService roleServiceImpl;

    @Autowired
    private IUserInfoService userInfoServiceImpl;

    @Autowired
    private LanguageUtils languageUtils;

    private String querySuccessMessage;
    private String queryEmptyMessage;

    @ApiOperation(value = "获取指定ID用户个人信息",httpMethod = "GET")
    @GetMapping("/getUserInfo")
    /*@ApiImplicitParams({
            @ApiImplicitParam(readOnly = true,name = "要查询的用户Id",example = "1")
    })*/
    public RespBean getUserInfo(@RequestParam(value = "userId",required = true) Integer userId,HttpServletRequest request){
        User user = userServiceImpl.getById(userId);
        Role userRole = new Role();
        UserInfo userInfo = new UserInfo();
        System.out.println("getUserInfo lang="+request.getHeader("lang"));
        System.out.println(LocaleContextHolder.getLocale());
        if(user != null && user.getUserRoleId() != null){
            userRole = roleServiceImpl.getById(user.getUserRoleId());
            userInfo = userInfoServiceImpl.getUserInfoByUserId(userId);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("account",user);
            result.put("role",userRole);
            result.put("userInfo",userInfo);
            querySuccessMessage = languageUtils.getMessage(SystemMessageConstants.QUERY_USER_SUCCESS,request.getHeader("lang"));
            return RespBean.success(querySuccessMessage,result);
        }else{
            queryEmptyMessage = languageUtils.getMessage(SystemMessageConstants.QUERY_USER_EMPTY,request.getHeader("lang"));
            return RespBean.emptySource(queryEmptyMessage);
        }

    }


}

