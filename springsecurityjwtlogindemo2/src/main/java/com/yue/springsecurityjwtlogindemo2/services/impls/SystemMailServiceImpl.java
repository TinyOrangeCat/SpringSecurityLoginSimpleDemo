package com.yue.springsecurityjwtlogindemo2.services.impls;

import com.yue.springsecurityjwtlogindemo2.beans.MailLog;
import com.yue.springsecurityjwtlogindemo2.models.MailConstants;
import com.yue.springsecurityjwtlogindemo2.services.IMailLogService;
import com.yue.springsecurityjwtlogindemo2.services.ISystemMailService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/2 17:51
 */
@Service
public class SystemMailServiceImpl implements ISystemMailService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IMailLogService mailLogServiceImpl;

    //发送邮件
    @Override
    public void sendMailMail(String username, String userEmail) {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName",username);
        map.put("sendDate",new Date());
        map.put("userEmail",userEmail);
        rabbitTemplate.convertAndSend("mail.welcome",map);
    }

    @Override
    public void sendMailMailQueue(String username, String userEmail) {
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.now();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName",username);
        map.put("sendDate",date);
        map.put("userEmail",userEmail);
        //数据库记录发送的消息
        String uuid = UUID.randomUUID().toString();
        System.out.println("UUID "+uuid);
        MailLog mailLog = new MailLog();
        mailLog.setMsgId(uuid);
        mailLog.setStatus(MailConstants.DELIVERING);
        mailLog.setLoginName(username);
        mailLog.setSendDate(localDateTime);
        mailLog.setExchange(MailConstants.EXCHANGE_NAME);
        mailLog.setCount(0);
        mailLog.setCreateTime(localDateTime);
        mailLog.setRouteKey(MailConstants.ROUTER_KEY_NAME);
        mailLog.setTryTime(localDateTime.plusMinutes(MailConstants.DELIVER_TIMEOUT));
        mailLogServiceImpl.save(mailLog);

        rabbitTemplate.convertAndSend(MailConstants.EXCHANGE_NAME,MailConstants.ROUTER_KEY_NAME,map,new CorrelationData(uuid));
    }
}
