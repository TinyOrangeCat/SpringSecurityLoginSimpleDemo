package com.yue.springsecurityjwtlogindemo2.controllers;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/*
 * @Description 验证码控制器。
 * @Author YueLi/xiroiyuki@hotmail.com
 * @Time 2021/3/1 17:52
 */
@Api(value = "验证码控制器")
@RestController
public class CaptchaController {
    @Autowired
    public DefaultKaptcha defaultKaptcha;

    private static final String PICTURE_CONTENT_TYPE_JPEG = "image/jpeg";
    private static final String CAPTCHA_TEXT_KEY = "captchaSessionCode";

    @ApiOperation(value = "验证码")
    @GetMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        //定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires",0);
        //set standard HTTP/1.0 no-cache headers
        response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        //set
        response.setHeader("Cache-Control","post-check=0, pre-check=0");
        //set standard HTTP/1.0 NO-cache header
        response.setHeader("Pragma","no-cache");
        //return a jpeg
        response.setContentType("image/jpeg");

        //生成验证码 START=======================================
        String captchaText = defaultKaptcha.createText();
        System.out.println("getCaptcha 验证码："+captchaText);

        HttpSession session = request.getSession();
        session.setAttribute(CAPTCHA_TEXT_KEY,captchaText);

        BufferedImage captchaImage = defaultKaptcha.createImage(captchaText);
        ServletOutputStream out = null;
        try{
            out = response.getOutputStream();
            ImageIO.write(captchaImage,"jpg",out);
            out.flush();
        }catch (IOException e) {
            System.out.println();
        }finally {
            if(out != null){
               try {
                   out.close();
               }catch (IOException e) {

               }
            }
        }
        //生成验证码 END=======================================
    }
}
