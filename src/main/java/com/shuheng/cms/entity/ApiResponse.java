package com.shuheng.cms.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: AppResponse.java
 * @Package com.shyl.framework.entity.app
 * @Description: 基础响应信息
 * @author:作者
 * @date 2016年3月11日 下午3:26:55
 * @version V1.0
 *
 * 包含返回码code和返回信息msg还有返回对象
 * 如果是列表用datas
 * 单个对象用data
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ApiResponse {
    protected String errorCode;
    protected String errorMsg;
    private Map<String, Object> data = null;
    private List<PageData> datas = null;

    private String countNum;//数量

    /**
     * 构造器： 请求成功
     */
    public ApiResponse() {
        this.errorCode = ApiConstants.SYS_PLAT_SUCCESS; // 设置成功
    }

    public ApiResponse(Map<String, Object> data) {
        this();
        this.data = data;
    }

    /**
     * 构造器 : 数据放在 data下
     * @param key 键
     * @param value 响应对应的对象值
     */
    public ApiResponse(String key, Object value) {
        this();
        this.put(key, value);
    }

    /**
     * @Description: 添加响应消息
     * @author: 作者
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        if(null == data) {
            data = new HashMap<String, Object>();
        }
        data.put(key, value);
    }

    /**
     * 添加相应信息
     * @Description:
     * @author: 作者
     * @param prams
     */
    public void put(Map<String, Object> prams) {
        if (prams != null && !prams.isEmpty()) {
            Object[] keys = prams.keySet().toArray();
            for (int i = 0; i < keys.length; i++) {
                String key = (String) keys[i];
                put(key, prams.get(key));
            }
        }
    }

    /**
     * @Description: 节点下增加指定值,只支持原先是map对象,或者新开启
     * @author: 作者
     * @param node
     * @param key
     * @param value
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void put(String node, String key, Object value) {
        Object obj = data.get(node);
        if(null == obj) {
            obj = new HashMap<>();
            data.put(node, obj);
        }

        if(!(obj instanceof Map)) {
            throw new RuntimeException("[不支持非Map节点增加键值对]");
        }

        Map map = (Map) obj;
        map.put(key, value);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public List<PageData> getDatas() {
        return datas;
    }

    public void setDatas(List<PageData> datas) {
        this.datas = datas;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        String errorMsg = ApiConstants.ERROR_MAP.get(errorCode);
        if(StringUtils.isNotBlank(errorMsg)) {
            this.errorMsg = errorMsg;
        }
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getCountNum() {
        return countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum;
    }


}
