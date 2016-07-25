package com.footing.website.modules.luxclub.api.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteInfoTest {
	 private final Logger logger = LoggerFactory.getLogger(getClass());

	    private String url = "http://localhost:8080/luxclub/f/api/siteinfo";

	    @Test
	    public void querySiteInfoByType() {
	        String siteType = "-1";
	        StringBuffer str = new StringBuffer();
	        str.append("/hotSearchSiteList").append("?");
	        str.append("siteType=").append(siteType);
	        String result = SendPost.sendPost(url + str.toString(), "");
	        logger.info(">>> 查询订单详情接口,返回参数:{}",result);
	    }
	    @Test
	    public void searchListBySiteName() {
	        String orderId = "2";
	        String siteName="汉庭";
	        StringBuffer str = new StringBuffer();
	        str.append("/searchListBySiteName").append("?");
	        str.append("siteName=").append(siteName).append("&");
	        str.append("siteType=").append(orderId);
	        String result = SendPost.sendPost(url + str.toString(), "");
	        logger.info(">>> 查询订单详情接口,返回参数:{}",result);
	    }
	    @Test
	    public void siteInfoDetails() {
	        int siteId =5;
	        StringBuffer str = new StringBuffer();
	        str.append("/siteInfoDetails").append("?");
	        str.append("siteId=").append(siteId);
	        String result = SendPost.sendPost(url + str.toString(), "");
	        logger.info(">>> 查询订单详情接口,返回参数:{}",result);
	    }
	    @Test
	    public void siteInfoPageList() {
	        int siteType=2;
	        long areaId=12;
	        int pageSize =10;
	        int pageNumber=1; 
	        StringBuffer str = new StringBuffer();
	        str.append("/siteInfoPageList").append("?");
	        str.append("siteType=").append(siteType).append("&");
	        str.append("pageSize=").append(pageSize).append("&");
	        str.append("pageNumber=").append(pageNumber).append("&");
	        str.append("areaId=").append(areaId);
	        String result = SendPost.sendPost(url + str.toString(), "");
	        logger.info(">>> 查询订单详情接口,返回参数:{}",result);
	    }
} 
