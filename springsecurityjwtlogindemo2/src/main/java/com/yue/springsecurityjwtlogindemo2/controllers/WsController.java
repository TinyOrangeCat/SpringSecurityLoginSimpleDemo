package com.yue.springsecurityjwtlogindemo2.controllers;

import com.yue.springsecurityjwtlogindemo2.models.ChatMsg;
import com.yue.springsecurityjwtlogindemo2.models.SystemLoginAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/10 19:49
 */
@RestController
public class WsController {
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/ws/chat")
  public void handleMessage(Authentication authentication, ChatMsg chatMsg){
    System.out.println("authentication = "+authentication);
    System.out.println("chatMsg = "+chatMsg);
    SystemLoginAccount userDetail = (SystemLoginAccount) authentication.getPrincipal();
    chatMsg.setFrom(userDetail.getUsername());
    chatMsg.setFromNickName(userDetail.getUsername());
    chatMsg.setLocalDateTime(LocalDateTime.now());
    simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(),"/queue/chat",chatMsg);
  }

}
