package com.yue.springsecurityjwtlogindemo2.controllers;


import com.yue.springsecurityjwtlogindemo2.beans.Manager;
import com.yue.springsecurityjwtlogindemo2.beans.ManagerInfo;
import com.yue.springsecurityjwtlogindemo2.beans.Role;
import com.yue.springsecurityjwtlogindemo2.services.IManagerInfoService;
import com.yue.springsecurityjwtlogindemo2.services.IManagerService;
import com.yue.springsecurityjwtlogindemo2.services.IRoleService;
import com.yue.springsecurityjwtlogindemo2.utils.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

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
@Api(value = "获取管理员信息")
@Controller
public class ManagerInfoController {

}

