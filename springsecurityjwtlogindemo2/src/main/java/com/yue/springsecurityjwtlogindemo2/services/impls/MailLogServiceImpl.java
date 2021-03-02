package com.yue.springsecurityjwtlogindemo2.services.impls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yue.springsecurityjwtlogindemo2.beans.MailLog;
import com.yue.springsecurityjwtlogindemo2.mappers.MailLogMapper;
import com.yue.springsecurityjwtlogindemo2.models.MailConstants;
import com.yue.springsecurityjwtlogindemo2.services.IMailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YueLi/xiroiyuki@hotmail.com
 * @since 2021-03-02
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {
    @Autowired
    @Qualifier("mailLogMapper")
    private MailLogMapper mailLogMapper;

    public int updateMailLogStatus(MailLog mailLog){
        return mailLogMapper.updateById(mailLog);
    }

    @Override
    public List<MailLog> getDeliveringMailLog(LocalDateTime localDateTime) {
        return mailLogMapper.selectList(new QueryWrapper<MailLog>()
                .eq("status", MailConstants.DELIVERING)
                //大于等于
                .ge("try_time",localDateTime));
    }
}
