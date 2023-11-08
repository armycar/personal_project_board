package com.example.mini_project_cm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//swagger 사용을 위한 기본설정
@Configuration
public class WebConfig implements WebMvcConfigurer { 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
    @Bean
    public OpenAPI communityOpenAPI() {
        Info info =
                new Info().version("0.0.1").title("미니 프로젝트 API").description("커뮤니티서비스 Restful API명세서");
            return new OpenAPI().info(info);
    }
}
