package com.yue.springsecurityjwtlogindemo2.models;

/*
 * @Description 消息投递状态
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/2 20:48
 */
public class MailConstants {
    //消息投递中
    public static final Integer DELIVERING = 0;
    //消息投递成功
    public static final Integer DELIVER_SUCCESS = 1;
    //消息投递失败
    public static final Integer DELIVER_FAILURE = 2;
    //最大重试次数
    public static final Integer MAX_TRY_COUNT = 3;
    //消息超时时间 分钟
    public static final Integer DELIVER_TIMEOUT = 1;
    //队列
    public static final String QUEUE_NAME = "mail.queue";
    //交换机
    public static final String EXCHANGE_NAME = "mail.exchange";
    //路由键
    public static final String ROUTER_KEY_NAME = "mail.routing.key";

}
