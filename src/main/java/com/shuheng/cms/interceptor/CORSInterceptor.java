package com.shuheng.cms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 跨域请求
 * @author zhengpp
 *
 */
@Component
public class CORSInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
                             Object handler) throws Exception {

        response.setHeader("Access-Control-Allow-Origin", "*");
        
        return true;
    }

}
