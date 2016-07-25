package com.footing.website.common.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.footing.website.common.utils.JedisUtils;
import com.footing.website.modules.luxclub.dao.OrderDao;
import com.footing.website.modules.luxclub.entity.Order;

public class TestDao extends SpringTransactionalContextTests{
	public static final String CACHE_ORDER_LIST = "orderList";
	@Autowired
	private  OrderDao orderDao;
    @Test
	public void testda(){
		get();
	}
    
    @SuppressWarnings("unchecked")
    public List<Order>get(){
		long start=System.currentTimeMillis();
    	List<Order>orders=(List<Order>)JedisUtils.getObject(CACHE_ORDER_LIST);
		if (orders == null){
		    orders=orderDao.findAllList(new Order());
			JedisUtils.setObject(CACHE_ORDER_LIST, orders ,5000);
		}
		System.out.println(System.currentTimeMillis()-start); 
		return orders;
    }
    
}
