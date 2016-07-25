package com.footing.website.modules.luxclub.common;

import java.math.BigDecimal;

/**
 *  
 * <p>
 * Description:基本常量类<br />
 * </p>
 * @title BusinessConstants.java
 * @package com.footing.website.modules.luxclub.common 
 * @author yubin
 * @version 0.1 2016年3月15日
 */
public interface BusinessConstants {
	
   /**'图片类型(1 充值凭证类型 2 消费凭证类型3 场所图片4 扣费凭证图片)',***/	
   public  static final String BUSINESS_RECHARGE_TYPE="1";
   public  static final String BUSINESS_FEE_TYPE="2";
   public  static final String BUSINESS_SITE_TYPE="3";
   public  static final String BUSINESS_DEDUCT_TYPE="4";
   
   public static final String SUCCESS="success";
   
   public static final String  ILLEGAL_OPT="非法操作提交方式";
   
   /** 费用类型(0 消费类型 1 充值类型 2 扣费)**/
   
   public static final Integer FEE_TYPE_CONSUME=0;
   public static final Integer FEE_TYPE_RECHARGE=1;
   public static final Integer FEE_TYPE_CHECK=2;
   /** 订单状态 0 提交、1 派单、2 确认、3 取消、5 待结账、6 已结账。**/
   public static final String ORDER_STATE_COMMIT="0";  
   public static final String ORDER_STATE_SEND="1";
   public static final String ORDER_STATE_CONFIRM="2"; 
   public static final String ORDER_STATE_CANCLE="3"; 
   public static final String ORDER_STATE_UNPAY="5"; 
   public static final String ORDER_STATE_PAY="6";
   /**静态图片**/
   public static final String URL_BLANK="/luxclub/static/images/site_album_fill@3x.png";
   public static final String URL_VIEW="/luxclub/static/images/view.jpg";
   /**年化利息**/
   public static final BigDecimal YEAR_INTEREST=new BigDecimal(3.68);
   /**信鸽不支持该渠道推送**/
   public static final String XG_RET_CODE="99";
   public static final String PICTRUE_NAME="私人订制";
   
   public static final String ORDER_DES_SEND="订单编号需要处理，请在20分钟内与客户确认完毕，否则订单将派给其他经理";
   public static final String ORDER_DES_CONFIRM="订单编号已确认";
   public static final String ORDER_DES_CANCLE="订单编号已取消";
   public static final String ORDER_DES_UNPAY="订单编号已上传凭证，等待审核";
   public static final String ORDER_DES_PAY="订单编号审核通过，完成结账";
   
   /**短信提醒模板*/
   public static final String  SMS_ACTIVATE_CARD="开卡激活成功，恭喜您已成功加入娱乐尊会员，会员卡帐号：${0};会员卡密 码：${1};下载娱乐尊APP，绑定会员卡开启您的私人party。如有任何问题请您第一时间联系我们的客服。客服电话：021-64737332。";
   public static final String  SMS_CHARGE_CARD="充值成功 恭喜您 ${0}会员卡已充值成功，如有任何问题请您第一时间联系我们的客服。客服电话：021-64737332。";
   public static final String  SMS_BIND_CARD="绑定成功 恭喜您 ${0}会员卡已绑定成功，打开APP立即预约吧。如有任何问题请您第一时间联系我们的客服。客服电话：021-64737332。";
   public static final String  SMS_MODIFY_CARD="修改密码 尊敬的用户您好，您在${0}${1}会员卡密码修改成功，如有任何问题请您第一时间联系我们的客服。客服电话：021-64737332。";
   /**站内短信模板*/
   public static final String  INSATE_RESERV="预约成功 尊敬的用户您好，您提交的订单已预约成功，服务经理${0}，电话号码${1}，如有任何问题请您联系服务经理。";
   public static final String  INSATE_SETTLE="结账成功 尊敬的用户您好，您的订单已结账成功，消费清单已出账，可对本次服务评价 ，谢谢您的使用。";
   
}
