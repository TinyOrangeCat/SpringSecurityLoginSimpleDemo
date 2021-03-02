package com.yue.springsecurityjwtlogindemo2.services.impls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yue.springsecurityjwtlogindemo2.beans.User;
import com.yue.springsecurityjwtlogindemo2.mappers.UserMapper;
import com.yue.springsecurityjwtlogindemo2.services.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YueLi/xiroiyuki@hotmail.com
 * @since 2021-02-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Override
    public User loadUserByUsername(String name) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_account",name));
    }
}
