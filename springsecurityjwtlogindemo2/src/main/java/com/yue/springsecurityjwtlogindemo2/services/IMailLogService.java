package com.yue.springsecurityjwtlogindemo2.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yue.springsecurityjwtlogindemo2.beans.MailLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YueLi/xiroiyuki@hotmail.com
 * @since 2021-03-02
 */
public interface IMailLogService extends IService<MailLog> {
    public int updateMailLogStatus(MailLog mailLog);

    public List<MailLog> getDeliveringMailLog(LocalDateTime localDateTime);
}
