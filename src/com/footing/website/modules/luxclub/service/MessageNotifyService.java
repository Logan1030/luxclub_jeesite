package com.footing.website.modules.luxclub.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footing.website.common.persistence.Page;
import com.footing.website.common.service.CrudService;
import com.footing.website.common.utils.DateUtils;
import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.dao.MessageNotifyDao;
import com.footing.website.modules.luxclub.entity.MemberInfo;
import com.footing.website.modules.luxclub.entity.MessageNotify;
import com.footing.website.modules.luxclub.message.AppMessageUtils;
import com.footing.website.modules.luxclub.message.MessageConstant;
import com.footing.website.modules.sms.SmsUtils;
import com.wmios.util.base.tool.MobileUtil;

/**
 * 消息通知Service
 * @author liuguoqing
 * @version 2016-03-15
 */
@Service
@Transactional(readOnly = true)
public class MessageNotifyService extends CrudService<MessageNotifyDao, MessageNotify> {
    
	@Autowired
	private SmsRecordService smsRecordService;
	public MessageNotify get(Long id) {
		return super.get(id);
	}
	
	public List<MessageNotify> findList(MessageNotify messageNotify) {
		return super.findList(messageNotify);
	}
	
	public Page<MessageNotify> findPage(Page<MessageNotify> page, MessageNotify messageNotify) {
	    // 设置默认时间范围，默认当前月
        if (messageNotify.getBeginCreateDate() == null){
            messageNotify.setBeginCreateDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
        }
        if (messageNotify.getEndCreateDate() == null){
            messageNotify.setEndCreateDate(DateUtils.getLastDayOfMonth());
        }
		return super.findPage(page, messageNotify);
	}
	
	@Transactional(readOnly = false)
	public void save(MessageNotify messageNotify) {
		super.save(messageNotify);
	}
	
	@Transactional(readOnly = false)
	public void delete(MessageNotify messageNotify) {
		super.delete(messageNotify);
	}
	
	public List<MessageNotify> findAppListByStatus(int state, int type){
	    return dao.findAppListByStatus(state, type);
	}
	@Transactional(readOnly = false,rollbackFor=Exception.class)
    public void pushMessage(MessageNotify messageNotify)throws Exception{
             JSONObject jsonObject = AppMessageUtils.push(messageNotify);
             logger.debug(">>> push result:{}",jsonObject.toString());//发送结果
             String code = String.valueOf(jsonObject.get("ret_code"));
             String errMsg = "";
             if(!jsonObject.isNull("err_msg")){
                 errMsg = String.valueOf(jsonObject.get("err_msg"));
                 //截取100个字符
                 if(errMsg.length() >=100){
                     errMsg = errMsg.substring(0, 100);
                 }
             }
             Map<String, Object> paramsMap = new HashMap<String, Object>();
             boolean flag=BusinessConstants.XG_RET_CODE.equals(code);
             paramsMap.put("state",flag?MessageConstant.MESSAGE_STATUS_EXPIRE:MessageConstant.MESSAGE_STATUS_SEND);
             paramsMap.put("id", messageNotify.getId());
             paramsMap.put("returnCode", code);
             paramsMap.put("errorMsg", errMsg);
             paramsMap.put("sendTime", new Date());
             dao.updateMessage(paramsMap);
    }
	/**
	 * 
	 * <p>
	 * Description:发送短信<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年5月23日
	 * @param memberInfo
	 * @param type 发送类型
	 * @param content 发送内容参数
	 * void
	 */
	@Transactional(readOnly = false)
	public void sendSmsMessage(MemberInfo memberInfo,String content,String... param){
		MessageNotify messageNotify=new MessageNotify();
		for(int i=0;i<param.length;i++){
			content=content.replace("${"+i+"}", param[i]);
		}
		messageNotify.setSendContent(content);
		messageNotify.setMemberCardno(memberInfo.getMemberCardno());
		messageNotify.setReceiver(memberInfo.getMemberMobile());  
		messageNotify.setMemberPhone(memberInfo.getMemberMobile());
		messageNotify.setSendTime(new Date());
		messageNotify.setState(MessageConstant.MESSAGE_STATUS_SEND);
		messageNotify.setMessageType(MessageConstant.MESSAGE_TYPE_SMS);
		messageNotify.setMessageChannel(MessageConstant.MESSAGE_CHANNEL_SMS);
		messageNotify.setRemarks("成功");
		this.save(messageNotify);
		if(MobileUtil.isMobile(memberInfo.getMemberMobile())){
		   smsRecordService.sendSmsRecord(memberInfo.getMemberMobile(),content,memberInfo.getIp());	
		}
	}
	 
}