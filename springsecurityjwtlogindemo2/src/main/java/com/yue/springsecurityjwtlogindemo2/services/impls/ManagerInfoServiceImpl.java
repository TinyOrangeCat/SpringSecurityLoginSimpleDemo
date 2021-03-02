package com.yue.springsecurityjwtlogindemo2.services.impls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yue.springsecurityjwtlogindemo2.beans.ManagerInfo;
import com.yue.springsecurityjwtlogindemo2.mappers.ManagerInfoMapper;
import com.yue.springsecurityjwtlogindemo2.services.IManagerInfoService;
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
public class ManagerInfoServiceImpl extends ServiceImpl<ManagerInfoMapper, ManagerInfo> implements IManagerInfoService {
    @Autowired
    @Qualifier("managerInfoMapper")
    private ManagerInfoMapper managerInfoMapper;

    @Override
    public ManagerInfo getManagerInfoByManagerId(Integer managerId) {
        return managerInfoMapper.selectOne(new QueryWrapper<ManagerInfo>()
                .eq("manager_account_id",managerId));
    }
}
