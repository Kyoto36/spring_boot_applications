package com.ls.library.login.configuration;

import com.ls.library.login.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * <描述功能>
 *
 * @author Lang
 * @Classname TokenWebConfig
 * @Version 1.0.0
 * @Date 2021/12/17 22:13
 **/
@Configuration
public class TokenWebConfig implements WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;

    /**
     * <描述功能> 注册拦截器
     * @author lang
     * @date 22:15 2021/12/17
     * @return void
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register**"
//                        , "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js",
//                        "/swagger-resources/**",
//                        "/v2/api-docs/**"
                );
    }

    /**
     * <描述功能> 资源映射
     * @author lang
     * @date 22:14 2021/12/17
     * @return void
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    /**
     * <描述功能> 跨域请求
     * @author lang
     * @date 22:14 2021/12/17
     * @return void
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
                .maxAge(3600 * 24);
    }
}
