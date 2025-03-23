package com.TMDT.api.Api.springboot.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Cho phép tất cả các đường dẫn
                .allowedOrigins("*") // Chỉ định nguồn gốc được phép
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Chỉ định phương thức HTTP được phép
                .allowedHeaders("*"); // Cho phép tất cả các tiêu đề HTTP
    }
}
