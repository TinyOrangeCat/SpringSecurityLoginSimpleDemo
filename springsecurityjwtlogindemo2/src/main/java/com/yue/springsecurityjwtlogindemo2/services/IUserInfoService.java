package com.yue.springsecurityjwtlogindemo2.services;

import com.yue.springsecurityjwtlogindemo2.beans.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YueLi/xiroiyuki@hotmail.com
 * @since 2021-02-26
 */
public interface IUserInfoService extends IService<UserInfo> {

    UserInfo getUserInfoByUserId(Integer userId);

}
