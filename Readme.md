# SpringSecurityVueLoginDemo

## 一、统一配置
### 1.Vue端口
http://localhost:8080

### 2.SpringBoot端口
http://localhost:8081

### 3.响应码
200：成功
404：找不到资源
403：无权限
401：缺失认证
500：请求失败

### 4.Swagger页面

http://localhost:8081/developer/swagger-ui/index.html

### 5.数据库

测试数据库默认为：test_database

resources/sqls下有测试用的建表语句。



## 二、登录操作

### 1.User登录

用户登录页面(/userLogin)

### 2.Manager登录

可以跳转到管理员登录页面(/managerLogin)



## 三、跨域请求

前端向后端请求数据：

已配置跨域请求，所有向后端请求的数据需要以“/backendServer”开头。

除登录请求外，其他请求需要协带Token，用户请求路径以/backendServer/user开头，管理员请求路径以/backendServer/manager开头。



## 四、在线聊天

使用WebSocket实现，注册/ws/chatEndpoint端点，在/user/queue/chat发送消息。

聊天部分的前端demo来自https://github.com/is-liyiwei/vue-Chat-demo.git，感谢！



## 五、i18n

前端后端支持切换语言（简体中文/英文/日文）。