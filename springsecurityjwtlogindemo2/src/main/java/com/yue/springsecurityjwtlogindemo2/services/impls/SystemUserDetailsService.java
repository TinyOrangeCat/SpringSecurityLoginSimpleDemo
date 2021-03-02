package com.yue.springsecurityjwtlogindemo2.services.impls;

import com.yue.springsecurityjwtlogindemo2.beans.Manager;
import com.yue.springsecurityjwtlogindemo2.beans.Role;
import com.yue.springsecurityjwtlogindemo2.beans.User;
import com.yue.springsecurityjwtlogindemo2.models.SystemLoginAccount;
import com.yue.springsecurityjwtlogindemo2.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/26 20:31
 */
@Service("systemUserDetailsService")
public class SystemUserDetailsService implements UserDetailsService {
    private static final String TABLE_NAME_USER = "tab_user";
    private static final String TABLE_NAME_MANAGER = "tab_manager";

    @Value("${developer.username}")
    private  String developerName;
    @Value("${developer.password}")
    private  String developerPassword;
    @Value("${developer.role}")
    private  String developerRole;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserService userServiceImpl;

    @Autowired
    private IManagerService managerServiceImpl;

    @Autowired
    private IRoleService roleServiceImpl;

    @Override
    public SystemLoginAccount loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("YUE SystemUserDetailsService");
        return new SystemLoginAccount(0,developerName,passwordEncoder.encode(developerPassword), AuthorityUtils.createAuthorityList(developerRole));
    }

    public SystemLoginAccount loadUserByUsername(String username,String tableName)throws UsernameNotFoundException  {
        System.out.println("YUE SystemUserDetailsService user tableName");
        SystemLoginAccount userDetails = new SystemLoginAccount();
        Role role=null;
        if(tableName.equals(TABLE_NAME_USER)){
            User user;
            try{
                user = userServiceImpl.loadUserByUsername(username);
            }catch (UsernameNotFoundException e) {
                throw new UsernameNotFoundException(e.getMessage());
            }
            if (user != null) {
                userDetails.setLoginAccount(user.getUserAccount());
                userDetails.setLoginPassword(passwordEncoder.encode(user.getUserPassword()));
                userDetails.setAccountId(user.getUserId());
                role = roleServiceImpl.getById(user.getUserRoleId());
            }
        }else if (tableName.equals(TABLE_NAME_MANAGER)) {
            Manager manager;
            try{
                manager = managerServiceImpl.loadManagerByName(username);
            }catch (UsernameNotFoundException e) {
                throw new UsernameNotFoundException(e.getMessage());
            }
            if(manager != null){
                userDetails.setLoginAccount(manager.getManagerAccount());
                userDetails.setLoginPassword(passwordEncoder.encode(manager.getManagerPassword()));
                userDetails.setAccountId(manager.getManagerId());
                role = roleServiceImpl.getById(manager.getManagerRoleId());
            }
        }
        if(role != null){
            userDetails.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_"+role.getRoleName()));
        }
        System.out.println("YUE SystemUserDetailsService userDetails = "+userDetails);
        System.out.println("YUE SystemUserDetailsService userDetails = "+userDetails.getUsername());
        System.out.println("YUE SystemUserDetailsService userDetails = "+userDetails.getAccountId());
        System.out.println("YUE SystemUserDetailsService userDetails = "+userDetails.getAuthorities());
        System.out.println("YUE SystemUserDetailsService userDetails = "+userDetails.getPassword());
        return userDetails;
    }
}
