package com.yue.springsecurityjwtlogindemo2.configs;

import com.yue.springsecurityjwtlogindemo2.models.SystemLoginAccount;
import com.yue.springsecurityjwtlogindemo2.services.impls.SystemUserDetailsService;
import com.yue.springsecurityjwtlogindemo2.utils.JWTTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/10 19:11
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
  private final String TAG = "WebsocketConfig: ";
  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired
  public JWTTokenUtils jwtTokenUtils;

  @Autowired
  @Qualifier("systemUserDetailsService")
  public SystemUserDetailsService systemUserDetailsService;

  /**
   * @Author YueLi/xiroiyuki@hotmail.com
   * @Date 2021/3/10 19:15
   * @Description 添加Endpoint 网页可以通过Websocket连接上服务，配置WebSocket的服务地址。
   * @Param
   * @Return
   **/
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    /*
    将ws/chatEndpoint路径注册为tomp的端点，用户可以用这个商战进行通信并支持websocketJS.
     */
    //registry.addEndpoint("/ws/chatEndpoint").setAllowedOrigins("*").withSockJS();
    registry.addEndpoint("/ws/chatEndpoint").setAllowedOrigins("http://localhost:8080").withSockJS();
    System.out.println(TAG+"YUE registerStompEndpoints");
  }

  /**
   * @Author YueLi/xiroiyuki@hotmail.com
   * @Date 2021/3/10 19:22
   * @Description 输入通道配置;使用token登录。(如果自定义了token需要做这一步。)
   * @Param
   * @Return
   **/
  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    System.out.println(TAG+"YUE configureClientInboundChannel ---------start");
    registration.interceptors(new ChannelInterceptor() {
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //判断是否连接
        System.out.println("YUE configureClientInboundChannel isConnected = "+StompCommand.CONNECTED.equals(accessor.getCommand()));
        System.out.println("YUE configureClientInboundChannel isConnect = "+StompCommand.CONNECT.equals(accessor.getCommand()));
        if(StompCommand.CONNECT.equals(accessor.getCommand())){
          String token = accessor.getFirstNativeHeader("Auth-Token");
          System.out.println(TAG+"YUE configureClientInboundChannel token:"+token);
          if(!StringUtils.isEmpty(token)){
            String rowToken = token.substring(tokenHead.length());
            String username = jwtTokenUtils.getUserNameFromToken(rowToken);
            if(!StringUtils.isEmpty(username)) {
              List<String> userRoles = jwtTokenUtils.getAuthoritiesFromToken(rowToken);
              SystemLoginAccount userDetails = null;
              for (String userRole : userRoles){
                if(userRole.equals("ROLE_manager")){
                  userDetails = systemUserDetailsService.loadUserByUsername(username,"tab_manager");
                  break;
                }else if(userRole.equals("ROLE_user")){
                  userDetails = systemUserDetailsService.loadUserByUsername(username,"tab_user");
                  break;
                }
              }
              if(userDetails != null && jwtTokenUtils.validateToken(rowToken,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                accessor.setUser(usernamePasswordAuthenticationToken);
              }
            }
          }
        }
        System.out.println(TAG+"YUE configureClientInboundChannel ---------end");
        return message;
      }
    });
  }

  /**
   * @Author YueLi/xiroiyuki@hotmail.com
   * @Date 2021/3/10 19:19
   * @Description 配置消息代理
   * @Param
   * @Return
   **/
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    System.out.println(TAG+"YUE configureMessageBroker");
    //配置代理，发送消息的位置。
    registry.enableSimpleBroker("/queue");
  }
}
