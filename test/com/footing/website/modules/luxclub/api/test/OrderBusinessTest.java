package com.footing.website.modules.luxclub.api.test;

import java.util.Date;
import java.util.Random;

import org.junit.Test;

public class OrderBusinessTest {
    
    

    private static  String url = "http://localhost:8080/luxclub_jeesite/f/api/orderBusiness";
    
    public static void main(String[] args) {  
        // 测试多线程调用订单号生成工具  
        try {  
            for (int i = 0; i < 1000; i++) {  
                Thread t1 = new Thread(new Runnable() {  
                    public void run() {  
                    	submitOrder();
                    }  
                }, "at" + i);  
                t1.start();  
  
                Thread t2 = new Thread(new Runnable() {  
                    public void run() {  
                    	 submitOrder();
                    }  
                }, "ct" + i);  
                t2.start();  
                Thread t3 = new Thread(new Runnable() {  
                    public void run() {  
                    	 submitOrder();
                    }  
                }, "dt" + i);  
                t3.start(); 
                Thread t4= new Thread(new Runnable() {  
                    public void run() {  
                    	 submitOrder();
                    }  
                }, "bt" + i);  
                t4.start(); 
                
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
    @Test
    public static void submitOrder(){
     
    	
    	String cardno=String.valueOf((int)((Math.random()*9+1)*100000));
    	long siteId=5;
    	String contactMobile="13156458745";
    	String reserveNumber="0";
    	String reserveDate="2016-08-12 12:35:12";
    	int  reserveCost=2;
    	String reserveRequire="无要求";
        String token = "93a9edcbc09e4ccfa09ed68f7d7fb547";
        StringBuffer str = new StringBuffer();
        str.append("/submitOrder").append("?");
        str.append("cardno=").append(cardno).append("&");
        str.append("siteId=").append(siteId).append("&");
        str.append("contactMobile=").append(contactMobile).append("&");
        str.append("reserveNumber=").append(reserveNumber).append("&");
       
        str.append("reserveRequire=").append(reserveRequire).append("&");
        str.append("reserveCost=").append(reserveCost).append("&");
        str.append("token=").append(token);
       
        String result = SendPost.sendPost(url + str.toString().trim(), "");
        System.out.println(">>> 查询订单详情接口,返回参数:{}"+result);
    }
    @Test
    public void testOrderDetails() {
        String orderId = "36";
        String token = "93a9edcbc09e4ccfa09ed68f7d7fb547";
        StringBuffer str = new StringBuffer();
        str.append("/orderDetails").append("?");
        str.append("orderId=").append(orderId).append("&");
        str.append("token=").append(token).append("&");
        String result = SendPost.sendPost(url + str.toString().trim(), "");
         
    }
    @Test
    public void queryOrderPageList() {
    
        String token = "93a9edcbc09e4ccfa09ed68f7d7fb547";
        String cardno="XVGP8746";
        int pageSize=10;
        int pageNumber=1;
        StringBuffer str = new StringBuffer();
        str.append("/queryOrderPageList").append("?");
        str.append("cardno=").append(cardno).append("&");
        str.append("pageSize=").append(pageSize).append("&");
        str.append("pageNumber=").append(pageNumber).append("&");
        str.append("token=").append(token).append("&");
        String result = SendPost.sendPost(url + str.toString(), "");
       
    }

}
