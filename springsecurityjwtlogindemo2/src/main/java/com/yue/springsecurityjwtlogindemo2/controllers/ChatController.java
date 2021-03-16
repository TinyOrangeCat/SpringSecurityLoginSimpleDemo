package com.yue.springsecurityjwtlogindemo2.controllers;

import com.yue.springsecurityjwtlogindemo2.beans.Manager;
import com.yue.springsecurityjwtlogindemo2.beans.User;
import com.yue.springsecurityjwtlogindemo2.models.SystemLoginAccount;
import com.yue.springsecurityjwtlogindemo2.models.SystemMessageConstants;
import com.yue.springsecurityjwtlogindemo2.services.IManagerService;
import com.yue.springsecurityjwtlogindemo2.services.IUserService;
import com.yue.springsecurityjwtlogindemo2.utils.LanguageUtils;
import com.yue.springsecurityjwtlogindemo2.utils.RespBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/10 19:58
 */
@RestController()
@RequestMapping("/chat")
public class ChatController {
  @Autowired
  private IManagerService managerServiceImpl;

  @Autowired
  private IUserService userServiceImpl;

  @Autowired
  private LanguageUtils languageUtils;

  private String querySuccessMessage;
  private String queryEmptyMessage;

  @ApiOperation(value ="用户获取所有管理员")
  @GetMapping("/getAllManagers")
  public RespBean getAllManagers(String currentUser, HttpServletRequest request){
    System.out.println("ChatController getAllManagers currentUser="+currentUser);
    System.out.println("ChatController getAllManagers lang="+request.getHeader("lang"));
    System.out.println(LocaleContextHolder.getLocale());
    List<Manager> managerList = managerServiceImpl.list();
    if(managerList == null || managerList.size() == 0){
      queryEmptyMessage = languageUtils.getMessage(SystemMessageConstants.QUERY_EMPTY,request.getHeader("lang"));
      return RespBean.emptySource(queryEmptyMessage);
    }else {
      for (Manager manager : managerList) {
          manager.setManagerPassword("secret");
      }
      Map<String,Object> allManagers = new HashMap<>();
      allManagers.put("allManager",managerList);
      querySuccessMessage = languageUtils.getMessage(SystemMessageConstants.QUERY_SUCCESS,request.getHeader("lang"));
      return RespBean.success(querySuccessMessage,allManagers);
    }

  }

  @ApiOperation(value ="管理员获取所有管理员和用户")
  @GetMapping("/managerGetUserManagers")
  public RespBean managerGetAllUsersManagers(String currentUser,HttpServletRequest request){
    System.out.println("ChatController managerGetAllUsersManagers currentUser="+currentUser);
    System.out.println("ChatController managerGetAllUsersManagers lang="+request.getHeader("lang"));
    System.out.println(LocaleContextHolder.getLocale());
    List<User> userList = userServiceImpl.list();
    List<Manager> managerList = managerServiceImpl.getAllManagersByKeyWords(currentUser);
    if((userList == null && managerList == null) || (userList.size() == 0 && managerList.size() == 0)){
      queryEmptyMessage = languageUtils.getMessage(SystemMessageConstants.QUERY_EMPTY,request.getHeader("lang"));
      return RespBean.emptySource(queryEmptyMessage);
    }else {
      for (Manager manager : managerList) {
        manager.setManagerPassword("secret");
      }
      for (User user : userList) {
          user.setUserPassword("secret");
      }
      Map<String,Object> allUserManagerMap = new HashMap<>();
      allUserManagerMap.put("allUser",userList);
      allUserManagerMap.put("allManager",managerList);
      querySuccessMessage = languageUtils.getMessage(SystemMessageConstants.QUERY_SUCCESS,request.getHeader("lang"));
      return RespBean.success(querySuccessMessage,allUserManagerMap);
    }
  }

}
