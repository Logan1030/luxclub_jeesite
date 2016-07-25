package com.footing.website.modules.luxclub.message;

import com.tencent.xinge.TimeInterval;
import com.tencent.xinge.XingeApp;

public class MessageConstant {
    
	/**信鸽 android推送目标应用 id*/
	public static final long XINGE_ANDROID_ACCESSID = 2100191593L;
	/**信鸽 android推送密钥*/
	public static final String XINGE_ANDROID_SECRETKEY = "e3361b51f65b49ab66b1d98992bb581c";
	/**信鸽 ios推送目标应用 id*/
	public static final long XINGE_IOS_ACCESSID = 2200211775L;
	/**信鸽 ios推送密钥*/
	public static final String XINGE_IOS_SECRETKEY = "f2950ab6c29ac815f87f8883097d3f7d";
//	/**信鸽 测试推送环境（ios有效）*/
    public static final int IOS_ENVIRONMENT = XingeApp.IOSENV_DEV;
	/**信鸽 正式推送环境（ios有效）*/
	//public static final int IOS_ENVIRONMENT = XingeApp.IOSENV_PROD;
	/**--------------------------------信鸽 允许推送时间段----------------------------------**/
	public static final TimeInterval XINGE_ACCEPTTIME = new TimeInterval(9, 0, 20, 0);
	
	
	/**系统设置消息开关（认定account_id为此值的设置为系统设置）*/
	public static final Long SYSTEM_MESSAGE_SWITCH_ACCOUNT_ID = 0L;
	/**系统消息开关默认值*/
	public static final String SYSTEM_MESSAGE_SWITCH_DEFAULT_VALUE = "0";
	
	/**--------------------------------消息发送渠道----------------------------------**/
	/**web*/
	public static final int MESSAGE_CHANNEL_WEB = 0;
	/**android*/
	public static final int MESSAGE_CHANNEL_ANDROID = 1;
	/**ios*/
	public static final int MESSAGE_CHANNEL_IOS = 2;
	/**微信*/
	public static final int MESSAGE_CHANNEL_WECHAT = 3;
	/**短信*/
	public static final int MESSAGE_CHANNEL_SMS = 4;
	/**邮件*/
	public static final int MESSAGE_CHANNEL_EMAIL = 5;
	
	/**----------------------------------消息状态-----------------------------------**/
	/**失效*/
	public static final int MESSAGE_STATUS_EXPIRE = -2;
	/**已删除*/
	public static final int MESSAGE_STATUS_DELETE = -1;
	/**创建*/
	public static final int MESSAGE_STATUS_CREATE = 0;
	/**已发送*/
	public static final int MESSAGE_STATUS_SEND = 1;
	/**已读*/
	public static final int MESSAGE_STATUS_READ = 2;
	
	/**--------------------------------是否接收消息----------------------------------**/
	/**接收*/
	public static final String IS_RECEIVE_MESSAGE_YES = "0";
	/**不接收*/
	public static final String IS_RECEIVE_MESSAGE_NO = "1";
	
	/**-------------------------------消息来源类型----------------------------------**/
	/**规则产生*/
	public static final String MESSAGE_FROM_TYPE_RULE = "0";
	/**自定义产生*/
	public static final String MESSAGE_FROM_TYPE_CUSTOM = "1";
	
	/**------------------------------消息产生规则状态--------------------------------**/
	/**审批不通过*/
	public static final String MESSAGE_RULE_STATUS_REVIEW_FAILED = "-1";
	/**创建*/
	public static final String MESSAGE_RULE_STATUS_CREATE = "0";
	/**审批通过*/
	public static final String MESSAGE_RULE_STATUS_REVIEW_PASS = "1";
	/**执行结束*/
	public static final String MESSAGE_RULE_STATUS_FINISHED = "2";
	
	/**----------------------------------消息类型----------------------------------**/
	/**app消息*/
	public static final int MESSAGE_TYPE_APP = 1;
	/**短信*/
	public static final int MESSAGE_TYPE_SMS = 2;
	
	
	/**---------------------------------通用入参名称---------------------------------**/
	/**会员编号*/
	public static final String PARA_ACCOUNT_ID = "accountId";
	/**金额*/
	public static final String PARA_AMOUNT = "amount";
	/**行为编号*/
	public static final String PARA_BEHAVIOR_CODE = "behaviorCode";
	/**操作终端*/
	public static final String PARA_OP_TERM = "opTerm";
	/**消息产生规则*/
	public static final String PARA_MESSAGE_CREATE_RULE = "messageCreateRule";
	/**时间*/
	public static final String PARA_DATE = "date";
}
