package com.jjh.foohang.main;

import com.jjh.foohang.main.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@MapperScan(basePackages = "com.jjh.foohang.*.model.mapper")
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //인터셉터 추가, 현재 안씀
        registry
                .addInterceptor(interceptor)
                .addPathPatterns("/members/");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://192.168.206.84:5173", "http://192.168.206.85:5173")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization");
        /*registry.addMapping("/boards/**")
                .allowedOrigins("http://localhost:5173", "http://192.168.206.40:5173")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS");*/
    }

}
