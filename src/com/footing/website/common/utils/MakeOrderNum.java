package com.footing.website.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MakeOrderNum {
	
	private static final Logger logger=LoggerFactory.getLogger(MakeOrderNum.class);  
    /** 
     * 订单号生成计数器 
     */  
    private static long orderNumCount = 0L;  
    /** 
     * 每毫秒生成订单号数量最大值 
     */  
    private static int maxPerMSECSize=1000;  
    /** 
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展 
     * @param tname 测试用 
     */  
    public static String makeOrderNum(String code,int temp) {
        try {  
            // 最终生成的订单号  
            String finOrderNum = "";  
            // 取系统当前时间作为订单号变量前半部分，精确到毫秒  
            long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMdd").format(new Date()));  
            // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万  
            if (orderNumCount > maxPerMSECSize) {  
                orderNumCount = 0L;  
            }  
            //组装订单号  
            String countStr=maxPerMSECSize +temp+"";  
            finOrderNum=code+nowLong+countStr;  
            orderNumCount++;  
            return finOrderNum;
  
        } catch (Exception e) {  
           logger.error("制作订单", e);
            return null;
        }  
    }  
  
}
