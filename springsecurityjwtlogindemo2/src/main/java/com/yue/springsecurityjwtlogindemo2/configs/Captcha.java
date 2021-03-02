package com.yue.springsecurityjwtlogindemo2.configs;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/*
 * @Description 验证码配置
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/1 17:32
 */
@Configuration
public class Captcha {
    @Bean
    public DefaultKaptcha defaultKaptcha() {
        String stringVerify = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //验证友生成器
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        //配置
        Properties properties = new Properties();
        //是否有边框
        properties.setProperty("kaptcha.border","yes");
        //设置边框颜色
        properties.setProperty("kaptcha.border.color","105,179,90");
        //边框粗细度
        //properties.setProperty("kaptcha.border.thickness","1");
        //验证码
        properties.setProperty("kaptcha.session.key","code");
        //验证友文本字符颜色 默认黑色
        properties.setProperty("kaptcha.textproducer.font.color","blue");
        //设置字体样式
        properties.setProperty("kaptcha.textproducer.font.names","宋体,楷体,微软雅黑");
        //字体大小 默认40
        properties.setProperty("kaptcha.textproducer.font.size","30");
        //验证码文本字符内容范围 默认为abced2345678ynmnpwx
        properties.setProperty("kaptcha.textproducer.char.string",stringVerify);
        //修改背景
        //图片样式： 水纹com.google.code.kaptcha.impl.WaterRipple
        // 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
        // 阴影com.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
        //字符长度 默认为5
        properties.setProperty("kaptcha.textproducer.char.length","4");
        //字符间距 默认为2
        properties.setProperty("kaptcha.textproducer.char.space","2");
        //验证友图片宽度 默认200
        properties.setProperty("kaptcha.image.width","100");
        //验证码图片高度 默认40
        properties.setProperty("kaptcha.image.height","40");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
