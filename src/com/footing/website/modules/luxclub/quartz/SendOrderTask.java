package com.footing.website.modules.luxclub.quartz;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.footing.website.modules.luxclub.service.OrderService;

/**
 * 
 * <p>
 * Description:定时任务定时任务 20分钟后订单状态重新置为预定状态<br />
 * </p>
 * @title SendOrderQuartz.java
 * @package com.footing.website.quartz 
 * @author yubin
 * @version 0.1 2016年3月17日
 */
@Service 
@Lazy(false)
public class SendOrderTask {
	@Autowired
	private OrderService orderService;
	
	//@Scheduled(cron = "*/1 * * * * ?")
	public void  updateOrderState(){
	   orderService.sendOrderQuartz();
	}

}
