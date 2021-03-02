package com.yue.springsecurityjwtlogindemo2.services.impls;

import com.yue.springsecurityjwtlogindemo2.beans.Role;
import com.yue.springsecurityjwtlogindemo2.mappers.RoleMapper;
import com.yue.springsecurityjwtlogindemo2.services.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
