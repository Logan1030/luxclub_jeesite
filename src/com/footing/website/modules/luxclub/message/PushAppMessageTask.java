package com.footing.website.modules.luxclub.message;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.footing.website.modules.luxclub.entity.MessageNotify;
import com.footing.website.modules.luxclub.service.MessageNotifyService;


/**
 * 发送app消息任务
 *
 */
@Service 
@Lazy(false)
public class PushAppMessageTask {
	
	@Autowired
	private MessageNotifyService messageNotifyService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 
	 * <p>
	 * Description:推送app消息，每1秒执行一次<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年4月20日
	 * void
	 */
	//@Scheduled(cron = "*/1 * * * * ?")
	public void pushAppMessage() {
		 
		List<MessageNotify> resultList = messageNotifyService.findAppListByStatus(MessageConstant.MESSAGE_STATUS_CREATE, MessageConstant.MESSAGE_TYPE_APP);
		if(resultList != null && resultList.size() > 0){
			//更新要发送的短信
    		for(MessageNotify message : resultList) {
    			if(message.getCreateDate().getTime() <= new Date().getTime()) {
    			    try {
						messageNotifyService.pushMessage(message);
					} catch (Exception e) {
						logger.error("推送APP消息异常：",e);
					}
    			}
    		}
    		 
		}
		 
	}
}
