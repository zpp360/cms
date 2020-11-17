package com.shuheng.cms.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/***
 * 手机端用到的常量
 * 
 * @author 860115003
 *
 */
public class ApiConstants {
	

	
	public static final Map<String,String> ERROR_MAP;

	public static final String SYS_PLAT_SUCCESS = "请求成功";
	
	public static final String CODE_200 = "200";
	/**
	 * 参数错误
	 */
	public static final String CODE_201 = "201";
	public static final String CODE_202 = "202";
	public static final String CODE_301 = "301";


	static{
		Map<String,String> map = new HashMap<String, String>();
		map.put("200", "success");
		map.put("201", "参数错误");
		map.put("202", "系统异常");
		map.put("301", "内容不存在");



		
		ERROR_MAP = Collections.unmodifiableMap(map);
		
	}

	

}