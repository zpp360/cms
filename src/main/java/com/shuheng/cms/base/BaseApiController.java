package com.shuheng.cms.base;

import com.shuheng.cms.entity.PageData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
@PropertySource({"classpath:/application.properties"})
public class BaseApiController {

    @Value("${global.page_size}")
    private String pageSize;

    /**
     * 得到PageData
     *
     * @return
     */
    public PageData getPageData() {
        return new PageData(this.getRequest());
    }


    /**
     * 得到request对象
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request;
    }

    /**
     * 获取分页信息
     * @param pd
     * @return
     */
    public PageData getPageInfo(PageData pd){
        String pageNumber = pd.getString("page_number");
        String pageSize = pd.getString("page_size");
        if(StringUtils.isBlank(pageSize)){
            pageSize = this.pageSize;
        }
        int start = 0;
        if (StringUtils.isNotBlank(pageNumber)) {
            int pageNum = Integer.parseInt(pageNumber);
            if(pageNum>0){
                start = (pageNum-1)* Integer.parseInt(pageSize);
            }
        }
        pd.put("start", start);
        pd.put("page_size", Integer.parseInt(pageSize));
        return pd;
    }

    /**
     * 获取分页信息
     * @return
     */
    public PageData getPageInfo(){
        PageData pd = this.getPageData();
        String pageNumber = pd.getString("page_number");
        String pageSize = pd.getString("page_size");
        if(StringUtils.isBlank(pageSize)){
            pageSize = this.pageSize;
        }
        int start = 0;
        if (StringUtils.isNotBlank(pageNumber)) {
            int pageNum = Integer.parseInt(pageNumber);
            if(pageNum>0){
                start = (pageNum-1)* Integer.parseInt(pageSize);
            }
        }
        pd.put("start", start);
        pd.put("page_size", Integer.parseInt(pageSize));
        return pd;
    }
}
