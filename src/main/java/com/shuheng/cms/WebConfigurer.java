package com.shuheng.cms;

import com.shuheng.cms.interceptor.CORSInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private CORSInterceptor cORSInterceptor;


    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //excludePathPatterns  除了获取token都需要拦截
        registry.addInterceptor(cORSInterceptor).addPathPatterns("/**").excludePathPatterns("/token");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true).setUseTrailingSlashMatch(false);
    }
}
