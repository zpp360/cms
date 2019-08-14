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
	public static final String CODE_203 = "203";
	public static final String CODE_204 = "204";
	public static final String CODE_205 = "205";
	public static final String CODE_206 = "206";
	public static final String CODE_207 = "207";
	public static final String CODE_208 = "208";
	public static final String CODE_209 = "209";
	public static final String CODE_211 = "211";
	public static final String CODE_212 = "212";
	public static final String CODE_213 = "213";
	public static final String CODE_214 = "214";
	
	
	public static final String CODE_300 = "300";
	public static final String CODE_301 = "301";
	public static final String CODE_302 = "302";
	public static final String CODE_303 = "303";
	public static final String CODE_304 = "304";
	public static final String CODE_305 = "305";
	public static final String CODE_306 = "306";

	public static final Integer APP_VERIFACATION_CODE_PERSISTENT = 2*60;
	
	static{
		Map<String,String> map = new HashMap<String, String>();
		map.put("200", "success");
		map.put("201", "参数错误");
		map.put("202", "系统异常");
		map.put("211", "日期校验失败");
		
		
		map.put("203", "验证码错误");
		map.put("204", "手机号错误");
		map.put("205", "您已注册，请直接登录");
		map.put("206", "验证码超时");
		map.put("207", "用户不存在");
		map.put("208", "用户身份证号码校验失败");
		map.put("209", "名字校验失败");
		map.put("212", "性别校验失败");
		map.put("213", "生日校验失败");
		map.put("214", "用户类型校验失败");
		map.put("300", "错误的手机号，请重新登录");
		map.put("301", "单位不存在");
		map.put("302", "请勿在"+APP_VERIFACATION_CODE_PERSISTENT/60+"分钟内重复获取");
		map.put("303", "用户审核中");
		map.put("304", "用户未选择部门");
		map.put("305", "用户已禁用");
		map.put("306", "已存在此身份证号码");

		
		ERROR_MAP = Collections.unmodifiableMap(map);
		
	}

	

}