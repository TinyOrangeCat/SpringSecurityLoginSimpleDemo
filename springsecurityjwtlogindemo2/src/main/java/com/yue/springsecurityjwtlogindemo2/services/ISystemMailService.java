package com.yue.springsecurityjwtlogindemo2.services;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/03/02 2021/3/2
 */
public interface ISystemMailService {
    public void sendMailMail(String username,String userEmail);

    public void sendMailMailQueue(String username,String userEmail);
}
