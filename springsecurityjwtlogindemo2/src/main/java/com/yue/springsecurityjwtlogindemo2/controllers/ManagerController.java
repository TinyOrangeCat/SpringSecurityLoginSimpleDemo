package com.yue.springsecurityjwtlogindemo2.controllers;


import com.yue.springsecurityjwtlogindemo2.beans.Manager;
import com.yue.springsecurityjwtlogindemo2.beans.ManagerInfo;
import com.yue.springsecurityjwtlogindemo2.beans.Role;
import com.yue.springsecurityjwtlogindemo2.models.SystemMessageConstants;
import com.yue.springsecurityjwtlogindemo2.services.IManagerInfoService;
import com.yue.springsecurityjwtlogindemo2.services.IManagerService;
import com.yue.springsecurityjwtlogindemo2.services.IRoleService;
import com.yue.springsecurityjwtlogindemo2.utils.LanguageUtils;
import com.yue.springsecurityjwtlogindemo2.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@Api(value = "获取用户")
@RestController
public class ManagerController {
    @Autowired
    public IManagerService managerServiceImpl;

    @Autowired
    public IManagerInfoService managerInfoServiceImpl;

    @Autowired
    public IRoleService roleServiceImpl;

    @Autowired
    private LanguageUtils languageUtils;

    private String querySuccessMessage;
    private String queryEmptyMessage;

    @ApiOperation(value = "获取管理员信息",httpMethod = "GET")
    @GetMapping("/getManagerInfo")
    public RespBean getManagerInfo(@RequestParam(value = "managerId",required = true) Integer managerId, HttpServletRequest request){
        System.out.println("getManagerInfo lang="+request.getHeader("lang"));
        Manager manager = managerServiceImpl.getById(managerId);
        ManagerInfo managerInfo = new ManagerInfo();
        Role managerRole = new Role();
        System.out.println(LocaleContextHolder.getLocale());
        if(manager != null && manager.getManagerId() != null){
            managerInfo = managerInfoServiceImpl.getById(managerId);
            managerRole = roleServiceImpl.getById(manager.getManagerRoleId());
            Map<String, Object> result = new HashMap<>();
            result.put("manager",manager);
            result.put("managerInfo",managerInfo);
            result.put("managerRole",managerRole);
            querySuccessMessage = languageUtils.getMessage(SystemMessageConstants.QUERY_MANAGER_SUCCESS,request.getHeader("lang"));
            return RespBean.success(querySuccessMessage,result);
        }else {
            queryEmptyMessage = languageUtils.getMessage(SystemMessageConstants.QUERY_MANAGER_EMPTY,request.getHeader("lang"));
            return RespBean.emptySource(queryEmptyMessage);
        }
    }
}

