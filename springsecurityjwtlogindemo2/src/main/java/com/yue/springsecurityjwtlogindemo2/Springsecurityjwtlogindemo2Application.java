package com.yue.springsecurityjwtlogindemo2;

import com.yue.springsecurityjwtlogindemo2.models.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//开启定时任务
@EnableScheduling
public class Springsecurityjwtlogindemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(Springsecurityjwtlogindemo2Application.class, args);
    }

    //rabbitMQ队列
    /*@Bean
    public Queue queue(){
        return new Queue("mail.welcome");
    }*/

    @Bean
    public Queue mailQueue(){
        return new Queue(MailConstants.QUEUE_NAME);
    }

}
