package com.yue.springsecurityjwtlogindemo2.mails;

import com.yue.springsecurityjwtlogindemo2.models.MailConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/*
 * @Description 接收邮件
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/2 17:56
 */
@Component
public class MailReceiver {
    private final String TAG = "MailReceiver: ";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    //Thymeleaf engine
    @Autowired
    private TemplateEngine templateEngine;

    //rabbitMQ监听
    //@RabbitListener(queues = "mail.welcome")
    @RabbitListener(queues = MailConstants.QUEUE_NAME)
    public void handler(Map<String,Object> mailParams){
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =
                new MimeMessageHelper(mailMessage);
        try {
            //发件人
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            //收件人
            mimeMessageHelper.setTo(mailParams.get("userEmail").toString());
            //主题
            mimeMessageHelper.setSubject("SpringMail");
            //发送日期
            mimeMessageHelper.setSentDate(new Date());

            //邮件内容
            //Thymeleaf context
            Context context = new Context();
            context.setVariable("loginName",mailParams.get("loginName").toString());
            context.setVariable("sendDate",mailParams.get("sendDate").toString());
            String mail = templateEngine.process("mail",context);
            mimeMessageHelper.setText(mail,true);
            javaMailSender.send(mailMessage);
        }catch (MessagingException e) {
            System.out.println(TAG+"邮件发送失败："+e.getMessage());
        }

    }

}
