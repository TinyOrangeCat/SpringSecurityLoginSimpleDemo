package com.yue.springsecurityjwtlogindemo2.services.impls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yue.springsecurityjwtlogindemo2.beans.Manager;
import com.yue.springsecurityjwtlogindemo2.mappers.ManagerMapper;
import com.yue.springsecurityjwtlogindemo2.services.IManagerService;
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
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements IManagerService {
    @Autowired
    @Qualifier("managerMapper")
    private ManagerMapper managerMapper;

    @Override
    public Manager loadManagerByName(String name) {
        return managerMapper.selectOne(new QueryWrapper<Manager>().eq("manager_account",name));
    }
}
