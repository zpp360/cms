package com.shuheng.cms.exception;

import com.shuheng.cms.entity.ApiConstants;
import com.shuheng.cms.entity.ApiResponse;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class CommonExceptionHandler {
    private static Logger log = Logger.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse exceptionHandler(Exception e){
        //输错错误到日志文件
        log.error(e,e);
        //返回202系统异常
        ApiResponse data = new ApiResponse();
        data.setErrorCode(ApiConstants.CODE_202);
        return data;
    }

}
