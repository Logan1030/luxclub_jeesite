/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.footing.website.modules.luxclub.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.footing.website.common.utils.CacheUtils;
import com.footing.website.common.utils.DateUtils;
import com.footing.website.common.utils.SpringContextHolder;
import com.footing.website.modules.luxclub.dao.CustomerClientTokenDao;
import com.footing.website.modules.luxclub.entity.CustomerClientToken;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class CustomerClientUtils {

	private static CustomerClientTokenDao customerClientTokenDao = SpringContextHolder.getBean(CustomerClientTokenDao.class);

	public static final String CLIENT_CACHE = "clientCache";
	public static final String CLIENT_CACHE_KEY = "clientCacheKey";
	public static final String CLIENT_CACHE_ID_ = "_id_";
	public static final String CLIENT_CACHE_TOKEN_ = "token";
	

	
	/**
	 * 根据账号获取
	 * @param account
	 * @return
	 */
	public static CustomerClientToken getByAccount(String account){
		CustomerClientToken customerClientToken = (CustomerClientToken)ClientCacheUtils.get(CLIENT_CACHE, CLIENT_CACHE_ID_ + account);
		return customerClientToken;
	}
	
	/**
	 * 根据token获取对象(无超时限制)
	 * @param token
	 * @return
	 */
	public static CustomerClientToken getByToken(String token){
		CustomerClientToken customerClientToken = (CustomerClientToken)ClientCacheUtils.get(CLIENT_CACHE, CLIENT_CACHE_TOKEN_ + token);
		if (customerClientToken == null){
			customerClientToken = customerClientTokenDao.getByToken(token);
			if (customerClientToken == null){
				return null;
			}
		}
		customerClientToken.setLastDate(new Date());
		addCache(customerClientToken);
		return customerClientToken;
	}

	/**
	 * 根据token获取对象
	 * @param token       token
	 * @param invalidTime 有效时间
	 * @return
	 */
	public static CustomerClientToken getByToken(String token, int invalidTime){
	    CustomerClientToken customerClientToken = (CustomerClientToken)ClientCacheUtils.get(CLIENT_CACHE, CLIENT_CACHE_TOKEN_ + token);
	    if (customerClientToken == null){
	        Date theDate = DateUtils.addMinutes(new Date(), invalidTime);
	        customerClientToken = customerClientTokenDao.getByTokenAndTheDate(token, theDate);
	        if (customerClientToken == null){
	            return null;
	        }
	    }
	    customerClientToken.setLastDate(new Date());
	    addCache(customerClientToken);
	    return customerClientToken;
	}
	

	/**
	 * 缓存列表
	 * @return
	 */
	public static List<CustomerClientToken> getCatchList(){
		Map<String,CustomerClientToken> map = getMap();
		List<CustomerClientToken> list = new ArrayList<CustomerClientToken>(map.values());
		return list;
	}
	
	
	public static Map<String,CustomerClientToken> getMap() {
		@SuppressWarnings("unchecked")
		Map<String, CustomerClientToken> map = (Map<String, CustomerClientToken>)CacheUtils.get(CLIENT_CACHE, CLIENT_CACHE_KEY);
		if(map == null) {
			map = new HashMap<String, CustomerClientToken>();
			ClientCacheUtils.put(CLIENT_CACHE, CLIENT_CACHE_KEY, map);
		}
		return map;
	}
	
	/**
	 * 清除指定用户缓存
	 * @param customerAccount
	 */
	public static void clearCache(CustomerClientToken customerClientToken){
	    if(customerClientToken != null){
    		ClientCacheUtils.remove(CLIENT_CACHE, CLIENT_CACHE_ID_ + customerClientToken.getAccount());
    		ClientCacheUtils.remove(CLIENT_CACHE, CLIENT_CACHE_TOKEN_ + customerClientToken.getToken());
    		Map<String, CustomerClientToken> map = getMap();
    		if(customerClientToken != null && map.keySet().contains(CLIENT_CACHE_ID_ + customerClientToken.getAccount())){
    			map.remove(CLIENT_CACHE_ID_ + customerClientToken.getAccount());
    		}
	    }
	}

	/**
	 * 添加用户缓存
	 * @param customerClientToken
	 */
	public static void addCache(CustomerClientToken customerClientToken){
		ClientCacheUtils.put(CLIENT_CACHE, CLIENT_CACHE_ID_ + customerClientToken.getAccount(), customerClientToken);
		ClientCacheUtils.put(CLIENT_CACHE, CLIENT_CACHE_TOKEN_ + customerClientToken.getToken(), customerClientToken);
		Map<String, CustomerClientToken> map = getMap();
		map.put(CLIENT_CACHE_ID_ + customerClientToken.getAccount(), customerClientToken);
	}
	
	/**
	 * 刷新cache
	 * @param account 账号
	 * @param opTerm 
	 */
	public static void refreshCache(String account, String opTerm) {
		CustomerClientToken CustomerClientToken = getByAccount(account);
		clearCache(CustomerClientToken);
		getByAccount(account);
	}
	
	/**
	 * 检测token
	 * @param token
	 * @return
	 */
	public static boolean checkToken(String token){
		CustomerClientToken customerClientToken = (CustomerClientToken)ClientCacheUtils.get(CLIENT_CACHE, CLIENT_CACHE_TOKEN_ + token);
		if(customerClientToken != null ){
			return true;
		}
		return false;
	}

}
