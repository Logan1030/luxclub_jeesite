/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.footing.website.modules.luxclub.utils;

import com.footing.website.common.security.shiro.Cache;
import com.footing.website.common.security.shiro.CacheManager;
import com.footing.website.common.security.shiro.cache.Element;
import com.footing.website.common.utils.SpringContextHolder;

/**
 * Cache工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class ClientCacheUtils {
	
    private static CacheManager cacheManager = ((CacheManager)SpringContextHolder.getBean("redisCacheManager"));
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element==null?null:element.getObjectValue();
	}

	/**
	 * 写入缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	/**
	 * 从缓存中移除
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}
	
	/**
	 * 获得一个Cache，没有则创建一个。
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName){
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null){
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}
	
}
