package com.footing.website.modules.luxclub.api.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AreaApiControllerTest {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

//    private String url = "http://localhost:8080/luxclub/f/api/area";
    private String url = "https://fakeapi.fdjf.net:7006/luxclub/f/api/area";
    

    /**
     * 城市列表
     */
    @Test
    public void testGetAreaList() {
        //https://fakeapi.fdjf.net:7006/luxclub/f/api/area/getAreaList
        String token = "9dd5f28d07b34a5c90d41d149edbe057";
        StringBuffer str = new StringBuffer();
        str.append("/getAreaList").append("?");
        str.append("token=").append(token);
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 城市列表接口,返回参数:{}",result);
    }


    /**
     * 热门搜索--城市列表
     */
    @Test
    public void testHotSearchAreaList() {
        String token = "9dd5f28d07b34a5c90d41d149edbe057";
        
        StringBuffer str = new StringBuffer();
        str.append("/hotSearchAreaList").append("?");
        str.append("token=").append(token);
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 消费/充值记录详细信息接口,返回参数:{}",result);
    }

    /**
     * 热门搜索--城市列表
     */
    @Test
    public void testSearchListByAreaName() {
        String token = "9dd5f28d07b34a5c90d41d149edbe057";
        String areaName = "你好";
        StringBuffer str = new StringBuffer();
        str.append("/searchListByAreaName").append("?");
        str.append("token=").append(token).append("&");
        str.append("areaName=").append(areaName);
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 消费/充值记录详细信息接口,返回参数:{}",result);
    }

}
