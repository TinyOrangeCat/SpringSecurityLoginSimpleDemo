package com.yue.springsecurityjwtlogindemo2.tasks;

import com.yue.springsecurityjwtlogindemo2.beans.MailLog;
import com.yue.springsecurityjwtlogindemo2.models.MailConstants;
import com.yue.springsecurityjwtlogindemo2.services.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @Description 重试发送邮件
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/2 22:33
 */
@Component
public class MailTask {
    @Autowired
    private IMailLogService mailLogServiceImpl;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${developer.email}")
    private String email;

    //扩列表达式 每10秒执行一次
    @Scheduled(cron = "0/10 * * * * ?")
    public void scheduledMailTask(){
        List<MailLog> mailLogList = mailLogServiceImpl.getDeliveringMailLog(LocalDateTime.now());
        mailLogList.forEach(mailLog -> {
            //最大重试次数
            if(MailConstants.MAX_TRY_COUNT <= mailLog.getCount()){
                mailLog.setStatus(MailConstants.DELIVER_FAILURE);
                mailLogServiceImpl.updateById(mailLog);
            }
            int newCount = mailLog.getCount()+1;
            mailLog.setCount(newCount);
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.DELIVER_TIMEOUT));
            mailLogServiceImpl.updateById(mailLog);
            Map<String, Object> map = new HashMap<>();
            map.put("loginName",mailLog.getLoginName());
            map.put("sendDate",mailLog.getCreateTime());
            map.put("userEmail",email);
            rabbitTemplate.convertAndSend(MailConstants.EXCHANGE_NAME,MailConstants.ROUTER_KEY_NAME,map,new CorrelationData(mailLog.getMsgId()));
        });
    }


}
