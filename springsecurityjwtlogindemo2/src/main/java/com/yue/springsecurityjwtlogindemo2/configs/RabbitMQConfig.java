package com.yue.springsecurityjwtlogindemo2.configs;

import com.yue.springsecurityjwtlogindemo2.beans.MailLog;
import com.yue.springsecurityjwtlogindemo2.models.MailConstants;
import com.yue.springsecurityjwtlogindemo2.services.IMailLogService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/2 21:59
 */
@Configuration
public class RabbitMQConfig {
    private static final String TAG = "RabbitMQConfig : ";
    //消息回调
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    private IMailLogService mailServiceImpl;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);

        /*
        * 消息确认回调
        * data 消息惟一标志
        * ack 确认结果
        * cause 原因
        * */
        rabbitTemplate.setConfirmCallback((data,ack,cause)->{
            String msgId = data.getId();
            if(ack){
                MailLog mailLog = new MailLog();
                mailLog.setMsgId(msgId);
                mailLog.setStatus(MailConstants.DELIVER_SUCCESS);
                System.out.println(TAG+"消息发送成功:"+msgId);
                mailServiceImpl.updateMailLogStatus(mailLog);
            }else {
                System.out.println(TAG+"消息发送失败:"+msgId);
            }
        });
        /*
        * 消息失败回调
        * msg：消息主题
        * repCode：响应码
        * repText：响应描述
        *
        * */
        rabbitTemplate.setReturnCallback((msg,repCode,repText,exchange,routineKey) -> {
            System.out.println(TAG+"确认投递失败："+msg.getBody());
        });

        return rabbitTemplate;
    }

    @Bean
    public Queue queue(){
        return new Queue(MailConstants.QUEUE_NAME);
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(MailConstants.EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(directExchange()).with(MailConstants.ROUTER_KEY_NAME);
    }

}
