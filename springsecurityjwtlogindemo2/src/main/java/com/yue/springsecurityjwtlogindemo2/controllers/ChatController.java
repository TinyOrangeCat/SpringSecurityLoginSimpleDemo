package com.yue.springsecurityjwtlogindemo2.controllers;

import com.yue.springsecurityjwtlogindemo2.beans.Manager;
import com.yue.springsecurityjwtlogindemo2.beans.User;
import com.yue.springsecurityjwtlogindemo2.models.SystemLoginAccount;
import com.yue.springsecurityjwtlogindemo2.services.IManagerService;
import com.yue.springsecurityjwtlogindemo2.services.IUserService;
import com.yue.springsecurityjwtlogindemo2.utils.RespBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @ApiOperation(value ="用户获取所有管理员")
  @GetMapping("/getAllManagers")
  public RespBean getAllManagers(String currentUser){
    System.out.println("ChatController getAllManagers currentUser="+currentUser);
    List<Manager> managerList = managerServiceImpl.list();
    if(managerList == null || managerList.size() == 0){
      return RespBean.emptySource("未查询到数据!");
    }else {
      for (Manager manager : managerList) {
          manager.setManagerPassword("secret");
      }
      Map<String,Object> allManagers = new HashMap<>();
      allManagers.put("allManager",managerList);
      return RespBean.success("查询成功!",allManagers);
    }

  }

  @ApiOperation(value ="管理员获取所有管理员和用户")
  @GetMapping("/managerGetUserManagers")
  public RespBean managerGetAllUsersManagers(String currentUser){
    System.out.println("ChatController managerGetAllUsersManagers currentUser="+currentUser);
    List<User> userList = userServiceImpl.list();
    List<Manager> managerList = managerServiceImpl.getAllManagersByKeyWords(currentUser);
    if((userList == null && managerList == null) || (userList.size() == 0 && managerList.size() == 0)){
      return RespBean.emptySource("未查询到数据!");
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
      return RespBean.success("查询成功!",allUserManagerMap);
    }
  }

}
