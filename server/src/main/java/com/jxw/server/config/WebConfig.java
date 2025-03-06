package com.jxw.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许所有来源访问
        registry.addMapping("/**")
                .allowedOrigins("*") // 设置前端的IP或域名，或者 "*" 表示所有域名都可以访问
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}

// 点击准备好了，向后端发送本次训练的设置，后端controller接收，将训练信息储存在训练类中，
// 训练配置类中包括本次训练的方式和主题和开始时间
// 对于上传的每张照片，调用python脚本，首先获取这张照片
