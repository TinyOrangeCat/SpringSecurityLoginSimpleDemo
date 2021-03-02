package com.yue.springsecurityjwtlogindemo2;

import com.yue.springsecurityjwtlogindemo2.controllers.ManagerController;
import com.yue.springsecurityjwtlogindemo2.controllers.UserController;
import com.yue.springsecurityjwtlogindemo2.mappers.UserMapper;
import com.yue.springsecurityjwtlogindemo2.services.IManagerService;
import com.yue.springsecurityjwtlogindemo2.services.IUserService;
import com.yue.springsecurityjwtlogindemo2.services.impls.UserServiceImpl;
import com.yue.springsecurityjwtlogindemo2.utils.JWTTokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springsecurityjwtlogindemo2ApplicationTests {
    @Autowired
    IUserService userService;
    @Autowired
    IManagerService managerServiceImpl;
    @Autowired
    JWTTokenUtils jwtTokenUtils;
    @Autowired
    UserController userController;
    @Autowired
    ManagerController managerController;

    @Test
    void contextLoads() {
    }

    @Test
    public void getUserTest(){
        System.out.println(userService.getById(1));
    }

    @Test
    public void parseTokenTest(){
        String token1 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZWQiOjE2MTQzNTI2NzgxNzksImV4cCI6MTYxNDM1MzI4Mn0.CzYEnnyj6eLSF4eRx5KGzjxV2F-SHabztG4lW_mj-dXkpRta2gZp37m30uvl6aeVF9GaPgB8CTpN_FRRdeUbRA";
        System.out.println(jwtTokenUtils.getUserNameFromToken(token1));
    }

    @Test
    public void getUserByName() {
        System.out.println(userService.loadUserByUsername("user"));
        System.out.println(managerServiceImpl.loadManagerByName("admin"));
    }

    @Test
    public void getUserInfoTest(){
        System.out.println(userController.getUserInfo(1));
    }
    @Test
    public void getManagerInfoTest(){
        System.out.println(managerController.getManagerInfo(1));
    }

}
