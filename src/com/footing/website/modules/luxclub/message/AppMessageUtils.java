package com.footing.website.modules.luxclub.message;

import org.json.JSONObject;

import com.footing.website.modules.luxclub.common.BusinessConstants;
import com.footing.website.modules.luxclub.entity.MessageNotify;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.Style;
import com.tencent.xinge.XingeApp;

/**
 * app 消息推送util
 *
 */
public class AppMessageUtils {
	
	public static JSONObject push(MessageNotify msgNotify) {
	    if(msgNotify.getMessageChannel() != null){
    	    //根据不同的渠道,调用推送方法
    		if(MessageConstant.MESSAGE_CHANNEL_ANDROID == msgNotify.getMessageChannel()) {
    			return pushAndroid(msgNotify);
    		} else if(MessageConstant.MESSAGE_CHANNEL_IOS == msgNotify.getMessageChannel()) {
    			return pushIOS(msgNotify);
    		} else {
    		    //throw new ServiceException("message instance channel is not app channel.");
    		    JSONObject json = new JSONObject();
    		    //自定义代码，非信鸽返回码内容
    		    json.put("ret_code", BusinessConstants.XG_RET_CODE);
    		    json.put("err_msg", "信鸽不支持该渠道推送");
    			return json;
    		}
	    }else{
	        JSONObject json = new JSONObject();
            //自定义代码，非信鸽返回码内容
            json.put("ret_code",BusinessConstants.XG_RET_CODE);
            json.put("err_msg", "信鸽不支持该渠道推送");
            return json;
	    }
	}
	
	public static JSONObject pushAndroid(MessageNotify msgNotify) {
		XingeApp xinge = new XingeApp(MessageConstant.XINGE_ANDROID_ACCESSID, MessageConstant.XINGE_ANDROID_SECRETKEY);
		Message message = new Message();
		message.setExpireTime(0);
		message.setStyle(new Style(0,1,0,1,0));
		message.setTitle(msgNotify.getTitle());
		message.setContent(msgNotify.getSendContent());
		message.setType(Message.TYPE_NOTIFICATION);
		message.addAcceptTime(MessageConstant.XINGE_ACCEPTTIME);
		return xinge.pushSingleAccount(0, String.valueOf(msgNotify.getReceiver()), message);
	}

	public static JSONObject pushIOS(MessageNotify msgNotify) {
		XingeApp xinge = new XingeApp(MessageConstant.XINGE_IOS_ACCESSID, MessageConstant.XINGE_IOS_SECRETKEY);
		MessageIOS message = new MessageIOS();
		message.setExpireTime(0);
		message.setAlert(abbr(msgNotify.getSendContent(), 50));
		message.setSound("beep.wav");
		message.addAcceptTime(MessageConstant.XINGE_ACCEPTTIME);
		return xinge.pushSingleAccount(0, String.valueOf(msgNotify.getReceiver()), message, MessageConstant.IOS_ENVIRONMENT);
	}
	
	private static String abbr(String str, int length) {
		if(str == null) {
			return "";
		} else if(str.length() > length) {
			str = str.substring(0, length) + "...";
		}
		return str;
	}
	
	public static void main(String[] args) {
		//IOS
//		XingeApp xinge = new XingeApp(MessageConstant.XINGE_IOS_ACCESSID, MessageConstant.XINGE_IOS_SECRETKEY);
//		for(int i = 1; i < 21; i++) {
//			MessageIOS message = new MessageIOS();
//			message.setExpireTime(0);
//			message.setAlert("啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊1111");
//			message.setSound("beep.wav");
//			message.addAcceptTime(MessageConstant.XINGE_ACCEPTTIME);
//			JSONObject data = xinge.pushSingleAccount(0, "564", message, MessageConstant.IOS_ENVIRONMENT);
//			System.out.println(":" + data.getInt("ret_code"));
//			System.out.println(":" + data);
//		}
		//Android
		XingeApp xinge = new XingeApp(MessageConstant.XINGE_ANDROID_ACCESSID, MessageConstant.XINGE_ANDROID_SECRETKEY);
		for(int i = 0; i < 1; i++) {
			Message message = new Message();
			message.setExpireTime(0);
			message.setTitle("qwr");
			message.setContent("123");
			System.out.println("type:"+Message.TYPE_NOTIFICATION);
			message.setType(Message.TYPE_NOTIFICATION);
			message.addAcceptTime(MessageConstant.XINGE_ACCEPTTIME);
			//JSONObject data = xinge.pushSingleAccount(0, "581", message);
			JSONObject data = xinge.pushAllDevice(0,message);
			
			System.out.println(":" + data.getInt("ret_code"));
			System.out.println(":" + data);
		}
	    
	    MessageNotify msgNotify = new MessageNotify();
	    msgNotify.setReceiver("1");
	    msgNotify.setTitle("测试1");
	    msgNotify.setSendContent("测试 测试 abc 123");
	    JSONObject result = AppMessageUtils.pushAndroid(msgNotify);
	    System.out.println(result);
	}
}
