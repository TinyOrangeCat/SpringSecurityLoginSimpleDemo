package com.yue.springsecurityjwtlogindemo2.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/10 19:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg {
  private String from;
  private String to;
  private String messageContent;
  private LocalDateTime localDateTime;
  private String fromNickName;


}
