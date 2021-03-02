package com.yue.springsecurityjwtlogindemo2.services;

import com.yue.springsecurityjwtlogindemo2.beans.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YueLi/xiroiyuki@hotmail.com
 * @since 2021-02-26
 */
public interface IUserService extends IService<User> {

    public User loadUserByUsername(String name);

}
