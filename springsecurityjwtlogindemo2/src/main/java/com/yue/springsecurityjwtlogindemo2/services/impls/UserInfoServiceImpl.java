package com.yue.springsecurityjwtlogindemo2.services.impls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yue.springsecurityjwtlogindemo2.beans.UserInfo;
import com.yue.springsecurityjwtlogindemo2.mappers.UserInfoMapper;
import com.yue.springsecurityjwtlogindemo2.services.IUserInfoService;
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
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    @Autowired
    @Qualifier("userInfoMapper")
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoByUserId(Integer userId) {
        return userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("user_account_id",userId));
    }
}
