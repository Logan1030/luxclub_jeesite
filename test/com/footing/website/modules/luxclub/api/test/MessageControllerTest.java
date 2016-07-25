package com.footing.website.modules.luxclub.api.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageControllerTest {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

//    private String url = "http://localhost:8080/luxclub/f/api/message";
    private String url = "http://fakeapi.fdjf.net:7006/luxclub/f/api/message";
    

    /**
     * 个人信息
     */
    @Test
    public void testMessagePageList() {
        String token = "01c22a6b7e3e4bb1a8e5a38817a47cd8";
        StringBuffer str = new StringBuffer();
        str.append("/messagePageList").append("?");
        str.append("token=").append(token).append("&");
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 绑定会员卡接口,返回参数:{}",result);
    }
    
    /**
     * 标记为已读
     */
    @Test
    public void testRead() {
        String messageIds = "7,8,a";
        String token = "ba31bf58d3844de9a6c387dcfbd973ce";

        StringBuffer str = new StringBuffer();
        str.append("/read").append("?");
        str.append("messageIds=").append(messageIds).append("&");
        str.append("token=").append(token);

        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 标记为已读接口,返回参数:{}",result);
    }

    /**
     * 标记为已读
     */
    @Test
    public void testUnreadCount() {
        String token = "01c22a6b7e3e4bb1a8e5a38817a47cd8";
        
        StringBuffer str = new StringBuffer();
        str.append("/unreadCount").append("?");
        str.append("token=").append(token);
        
        String result = SendPost.sendPost(url + str.toString(), "");
        logger.info(">>> 标记为已读接口,返回参数:{}",result);
    }

}
