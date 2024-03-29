package com.footing.website.common.security.shiro.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footing.website.common.security.shiro.Cache;
import com.footing.website.common.utils.JedisUtils;

 
/**
 *  
 * <p>
 * Description:描述<br />
 * </p>
 * @title RedisCache.java
 * @package com.footing.website.common.security.shiro.cache 
 * @author yubin
 * @version 0.1 2016年2月29日
 */
public class RedisCache implements Cache {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	private RedisManager cache;
	
	
	private String keyPrefix = "shiro_redis_session:";
	
	
	public String getKeyPrefix() {
		return keyPrefix;
	}

	
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}
	
	/**
	 * 通过一个JedisManager实例构造RedisCache
	 */
	public RedisCache(RedisManager cache){
		 if (cache == null) {
	         throw new IllegalArgumentException("Cache argument cannot be null.");
	     }
	     this.cache = cache;
	}
	
	
	public RedisCache(RedisManager cache, 
				String prefix){
		 
		this( cache );
		
		
		this.keyPrefix = prefix;
	}
	
	/**
	 * 获得byte[]型的key
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(Object key){
		if(key instanceof String){
			String preKey = this.keyPrefix + key;
    		return preKey.getBytes();
    	}else{
    		return JedisUtils.toBytes(key);
    	}
	}
 	
	@Override
	public Object get(Object key) throws CacheException {
		//logger.debug("根据key从Redis中获取对象 key [" + key + "]");
		try {
			if (key == null) {
	            return null;
	        }else{
	        	byte[] rawValue = cache.get(getByteKey(key));
	        	
	        	Object value = (Object)JedisUtils.toObject(rawValue);
	        	return value;
	        }
		} catch (Throwable t) {
			throw new CacheException(t);
		}

	}

	@Override
	public Object put(Object key, Object value) throws CacheException {
		//logger.debug("根据key从存储 key [" + key + "]");
		 try {
			 	cache.set(getByteKey(key), JedisUtils.toBytes(value));
	            return value;
	        } catch (Throwable t) {
	            throw new CacheException(t);
	        }
	}

	@Override
	public Object remove(Object key) throws CacheException {
		//logger.debug("从redis中删除 key [" + key + "]");
		try {
			Object previous = get(key);
            cache.del(getByteKey(key));
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@Override
	public void clear() throws CacheException {
		logger.debug("从redis中删除所有元素");
		try {
            cache.flushDB();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@Override
	public int size() {
		try {
			Long longSize = new Long(cache.dbSize());
            return longSize.intValue();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	
	@Override
	public Set<Object> keys() {
		try {
            Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
            if (CollectionUtils.isEmpty(keys)) {
            	return Collections.emptySet();
            }else{
            	Set<Object> newKeys = new HashSet<Object>();
            	for(byte[] key:keys){
            		newKeys.add((Object)key);
            	}
            	return newKeys;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@Override
	public Collection<Object> values() {
		try {
            Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<Object> values = new ArrayList<Object>(keys.size());
                for (byte[] key : keys) {
                    
                    Object value = get((Object)key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
	}

	@Override
	public Element get(Serializable key) {
		
		return new Element(key, this.get((Object)key));
	}

	@Override
	public void put(Element element) {
		
		this.put(element.getObjectKey(), element.getObjectValue());
	}

	@Override
	public Cache getCacheConfiguration() {
		
		return null;
	}

	@Override
	public void setEternal(boolean param) {
		
		
	}


	

}
