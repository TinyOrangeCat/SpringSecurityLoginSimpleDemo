package com.yue.springsecurityjwtlogindemo2.utils;

import com.yue.springsecurityjwtlogindemo2.enums.RespCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @Description TODO
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/2/25 22:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private Integer code;
    private String message;
    private Object obj;

    public static RespBean success(String message){
        return new RespBean(RespCode.SUCCESS.getResponseCode(),message,null);
    }

    public static RespBean success(String message,Object obj){
        return new RespBean(RespCode.SUCCESS.getResponseCode(),message,obj);
    }

    public static RespBean error(String message){
        return new RespBean(RespCode.ERROR.getResponseCode(),message,null);

    }

    public static RespBean error(String message,Object obj){
        return new RespBean(RespCode.ERROR.getResponseCode(),message,obj);

    }

    public static RespBean emptySource(String message){
        return new RespBean(RespCode.NOT_FOUND.getResponseCode(),message,null);

    }

    public static RespBean emptySource(String message,Object obj){
        return new RespBean(RespCode.NOT_FOUND.getResponseCode(),message,obj);

    }

    public static RespBean accessDeny(String message){
        return new RespBean(RespCode.AUTHORITY_REFUSED.getResponseCode(),message,null);

    }

    public static RespBean accessDeny(String message,Object obj){
        return new RespBean(RespCode.AUTHORITY_REFUSED.getResponseCode(),message,obj);

    }

    public static RespBean unauthorized(String message){
        return new RespBean(RespCode.UNAUTHORIZED.getResponseCode(),message,null);

    }

    public static RespBean unauthorized(String message,Object obj){
        return new RespBean(RespCode.UNAUTHORIZED.getResponseCode(),message,obj);

    }

}
